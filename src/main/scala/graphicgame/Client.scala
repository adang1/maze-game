package graphicgame

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.canvas.Canvas
import scalafx.scene.Scene
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import java.net.Socket
import java.io.ObjectOutputStream
import java.io.ObjectInputStream
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalafx.application.Platform
import scalafx.scene.control.TextInputDialog


object Client extends JFXApp {
  val canvas = new Canvas(800, 800)
  val gc = canvas.graphicsContext2D
	val renderer = new Renderer2D(gc, 50)

  val dialog = new TextInputDialog("localhost")
  dialog.title = "Server Machine"
  dialog.contentText = "What machine is the server running on?"
  val host = dialog.showAndWait().getOrElse("localhost")

  val sock = new Socket("localhost", 4041)
  val out = new ObjectOutputStream(sock.getOutputStream())
  val in = new ObjectInputStream(sock.getInputStream())
  
  val keyMap = Map[KeyCode, KeyEnums.Value](
    KeyCode.Left -> KeyEnums.Left,
    KeyCode.Right -> KeyEnums.Right,
    KeyCode.Up -> KeyEnums.Up,
    KeyCode.Down -> KeyEnums.Down,
    KeyCode.A -> KeyEnums.A,
    KeyCode.W -> KeyEnums.W,
    KeyCode.S -> KeyEnums.S,
    KeyCode.D -> KeyEnums.D
  ).withDefaultValue(KeyEnums.Other)
  stage = new JFXApp.PrimaryStage {
		title = "2D Snipes"
		scene = new Scene(800, 800) {
			content = canvas
			onKeyPressed = (ke: KeyEvent) => {
        out.writeInt(98)
        out.writeObject(keyMap(ke.code))
        out.flush()
			}
			
			onKeyReleased = (ke: KeyEvent) => {
        out.writeInt(97)
        out.writeObject(keyMap(ke.code))
        out.flush()
      }
      
      Future {
        while (true) {
          val pb = in.readObject() match {
            case level: PassableLevel => level
          }
          val x = in.readDouble()
          val y = in.readDouble()
          val enemies = in.readInt()
          Platform.runLater(renderer.render(pb, x, y, enemies))
        }
      }
    }
  }
}