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
	val canvas = new Canvas(800, 800)
	val gc = canvas.graphicsContext2D
	val renderer = new Renderer2D(gc, 50)
	// val enemy = new Enemy(0, 0)
	// val entities = Seq(player, enemy)
	val maze = RandomMaze(3, false, 20, 20, 0.6)
	val level = new Level(maze, Nil)
	val player = new Player(-100, -100, level)
	level += player
	stage = new JFXApp.PrimaryStage {
		title = "2D Snipes" // Change this to match the theme of your game.
		scene = new Scene(800, 800) {
			content = canvas
			onKeyPressed = (ke: KeyEvent) => {
				println("Key pressed")
				ke.code match {
					case KeyCode.Left =>
					case _ =>
				}
			}
	
			var lastTime = -1L
			val timer = AnimationTimer(time => {
				if (lastTime > 0) {
				val delay = (time - lastTime)/1e9
				// println(delay)
				// level.update()
				renderer.render(level, player.x, player.y)
			}
			lastTime = time
		})
			timer.start()
		}
	}
}
