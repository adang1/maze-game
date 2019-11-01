package graphicgame

import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import scalafx.scene.input.KeyCode
import java.net.Socket
import scala.collection.mutable
import java.util.concurrent.LinkedBlockingQueue
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.net.ServerSocket

object Server extends App {
  val maze = RandomMaze(3, false, 20, 20, 0.6)
  val level = new Level(maze, Seq.empty)
  case class NetworkPlayer(
      sock: Socket,
      in: ObjectInputStream,
      out: ObjectOutputStream,
      player: Player
  )

  val players = mutable.Buffer[NetworkPlayer]()
  val playerQueue = new LinkedBlockingQueue[NetworkPlayer]()

  val ss = new ServerSocket(4041)
  val sock = ss.accept()
  val in = new ObjectInputStream(sock.getInputStream())
  val out = new ObjectOutputStream(sock.getOutputStream())
  Future {
    while (true) {
      val sock = ss.accept()
      val in = new ObjectInputStream(sock.getInputStream())
      val out = new ObjectOutputStream(sock.getOutputStream())
      val player = new Player(50, 50, level)
      val np = NetworkPlayer(sock, in, out, player)
      playerQueue.put(np)
      level += player
    }
  }

  var lastTime = System.nanoTime()
  val sendInterval = 0.05
  var sendDelay = 0.0
    while (true) {
      // Move new players from queue to buffer
      while (!playerQueue.isEmpty()) players += playerQueue.poll()
      val time = System.nanoTime()
      if (lastTime > 0) {
        val delay = (time - lastTime) / 1e9
        sendDelay += delay
        level.updateAll(delay)
        if (sendDelay >= sendInterval) {
          val pb = level.makePassable
          out.writeInt(99)
          out.writeObject(pb)
          sendDelay = 0.0
          // val delay = (time - lastTime) / 1e9
          // sendDelay += delay
          // val sendUpdate = sendDelay >= sendInterval
          // if (sendUpdate) sendDelay = 0.0
          for (np <- players) {
            if (np.in.available() > 0) {
              val code = np.in.readInt()
              val key = np.in.readObject
              if (code == 98) {
                key match {
                  case KeyEnums.Left  => np.player.leftPressed()
                  case KeyEnums.Right => np.player.rightPressed()
                  case KeyEnums.Up    => np.player.upPressed()
                  case KeyEnums.Down  => np.player.downPressed()
                  case _              =>
                }
              } else {
                key match {
                  case KeyEnums.Left  => np.player.leftReleased()
                  case KeyEnums.Right => np.player.rightReleased()
                  case KeyEnums.Down  => np.player.downReleased()
                  case _              =>
                }
              }
            }
            
              val pb = np.player.makePassable
              np.out.writeObject(pb)
            
          }
          lastTime = time
        }
      }
    }
  }
