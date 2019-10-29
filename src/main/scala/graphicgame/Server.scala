package graphicgame

import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import scalafx.scene.input.KeyCode
import java.net.Socket
import scala.collection.mutable
import java.util.concurrent.LinkedBlockingQueue
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Server extends App {
  val board = new Board
  case class NetworkPlayer(
      sock: Socket,
      in: ObjectInputStream,
      out: ObjectOutputStream,
      board: Board
  )

  val players = mutable.Buffer[NetworkPlayer]()
  val playerQueue = new LinkedBlockingQueue[NetworkPlayer]()

  val ss = new ServerSocket(8080)
  val sock = ss.accept()
  val in = new ObjectInputStream(sock.getInputStream())
  val out = new ObjectOutputStream(sock.getOutputStream())
  Future {
    while (true) {
      val sock = ss.accept()
      val in = new ObjectInputStream(sock.getInputStream())
      val out = new ObjectOutputStream(sock.getOutputStream())
      val board = new Board
      playerQueue.put(NetworkPlayer(sock, in, out, board))
    }
  }

  var lastTime = -1L
  var lastTime = System.nanoTime()
  val sendInterval = 0.05
  var sendDelay = 0.0
  while (true) {
    if (in.available() > 0) {
      val code = in.readInt()
      val key = in.readObject
      if (code == 98) {
        key match {
          case KeyEnums.Left  => board.leftPressed()
          case KeyEnums.Right => board.rightPressed()
          case KeyEnums.Up    => board.upPressed()
          case KeyEnums.Down  => board.downPressed()
          case _              =>
        }
      } else {
        key match {
          case KeyEnums.Left  => board.leftReleased()
          case KeyEnums.Right => board.rightReleased()
          case KeyEnums.Down  => board.downReleased()
          case _              =>
        }
      }
    }
    while (true) {
      // Move new players from queue to buffer
      while (!playerQueue.isEmpty()) players += playerQueue.poll()
      val time = System.nanoTime()
      if (lastTime > 0) {
        val delay = (time - lastTime) / 1e9
        sendDelay += delay
        board.update(delay)
        if (sendDelay >= sendInterval) {
          val pb = board.makePassable
          // out.writeInt(99)
          out.writeObject(pb)
          sendDelay = 0.0
          val delay = (time - lastTime) / 1e9
          sendDelay += delay
          val sendUpdate = sendDelay >= sendInterval
          if (sendUpdate) sendDelay = 0.0
          for (np <- players) {
            if (np.in.available() > 0) {
              val code = np.in.readInt()
              val key = np.in.readObject
              if (code == 98) {
                key match {
                  case KeyEnums.Left  => np.board.leftPressed()
                  case KeyEnums.Right => np.board.rightPressed()
                  case KeyEnums.Up    => np.board.upPressed()
                  case KeyEnums.Down  => np.board.downPressed()
                  case _              =>
                }
              } else {
                key match {
                  case KeyEnums.Left  => np.board.leftReleased()
                  case KeyEnums.Right => np.board.rightReleased()
                  case KeyEnums.Down  => np.board.downReleased()
                  case _              =>
                }
              }
            }
            np.board.update(delay)
            if (sendUpdate) {
              val pb = np.board.makePassable
              np.out.writeObject(pb)
            }
          }
          lastTime = time
        }
      }
    }
  }
}
