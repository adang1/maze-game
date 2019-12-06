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
import scala.util.Random

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
  val r = new Random
	for (i <- 1 to 20) {
	val enemy = new Enemy(r.nextInt(100), r.nextInt(100), level)
	level += enemy
  }

  val ss = new ServerSocket(4041)

  Future {
    while (true) {
      val sock = ss.accept()
      val in = new ObjectInputStream(sock.getInputStream())
      val out = new ObjectOutputStream(sock.getOutputStream())
      val player = new Player(50, 50, level)
      val np = NetworkPlayer(sock, in, out, player)
      playerQueue.put(np)
      
    }
  }

  var lastTime = System.nanoTime()
  val sendInterval = 0.01
  var sendDelay = 0.0
  while (true) {
    // Move new players from queue to buffer
    while (!playerQueue.isEmpty()) {
      val np = playerQueue.poll()
      level += np.player
      players += np
    }
    val time = System.nanoTime()
    if (lastTime > 0) {
      val delay = (time - lastTime) / 1e9
      sendDelay += delay
      level.updateAll(delay)
      if (sendDelay >= sendInterval) {
        val pb = level.makePassable
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
                case KeyCode.A =>
                  np.player.aPressed()

                case KeyCode.D =>
                  np.player.dPressed()

                case KeyCode.W =>
                  np.player.wPressed()

                case KeyCode.S =>
                  np.player.sPressed()

                case _ =>
              }
            } else {
              key match {
                case KeyEnums.Left  => np.player.leftReleased()
                case KeyEnums.Right => np.player.rightReleased()
                case KeyEnums.Up    => np.player.upReleased()
                case KeyEnums.Down  => np.player.downReleased()
                case KeyCode.A      => np.player.aReleased()
                case KeyCode.D      => np.player.dReleased()
                case KeyCode.W      => np.player.wReleased()
                case KeyCode.S      => np.player.sReleased()
                case _              =>
              }
            }
          }

          np.out.writeObject(pb)

          np.out.writeDouble(np.player.x)
          np.out.writeDouble(np.player.y)

        }
      }
      lastTime = time
    }
  }
}
