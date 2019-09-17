package graphicgame

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image


import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode

/**
 * This is a stub for the graphical game.
 */
object Main extends JFXApp {

	stage = new JFXApp.PrimaryStage {
		title = "2D Snipes" // Change this to match the theme of your game.
		scene = new Scene(1000, 800) {
			onKeyPressed = (ke: KeyEvent) => {
				println("Key pressed")
				ke.code match {
					case KeyCode.Left =>
					case _ =>
				}
			}
	// 			val canvas = new Canvas(800, 800)
	// val gc = canvas.graphicsContext2D
	// val renderer = new Renderer2D(gc, 50)
	// val player = new Player(0, 0, ???)
	// val enemy = new Enemy(0, 0)
	// val entities = Seq(player, enemy)
	// val maze = RandomMaze
			var lastTime = -1L
			val timer = AnimationTimer(time => {
				if (lastTime > 0) {
				val delay = (time - lastTime)/1e9
				println(delay)
				// level.update()
				// renderer2D.render(level)
			}
			lastTime = time
		})
			timer.start()
		}
	}
}
