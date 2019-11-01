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
import scala.util.Random

/**
 * This is a stub for the graphical game.
 */
object Main extends JFXApp {
	val canvas = new Canvas(800, 800)
	val gc = canvas.graphicsContext2D
	val renderer = new Renderer2D(gc, 50)
	val maze = RandomMaze(3, false, 20, 20, 0.6)
	val level = new Level(maze, Nil)
	val player = new Player(50, 50, level)
	
	val r = new Random
	for (i <- 1 to 20) {
	val enemy = new Enemy(r.nextInt(100), r.nextInt(100), level)
	level += enemy
	}
	level += player
	
	
	stage = new JFXApp.PrimaryStage {
		title = "2D Snipes" // Change this to match the theme of your game.
		scene = new Scene(800, 800) {
			content = canvas
			
			onKeyPressed = (ke: KeyEvent) => {
				ke.code match {
					case KeyCode.Left => player.leftPressed()
					case KeyCode.Right => player.rightPressed()
					case KeyCode.Up => player.upPressed()
					case KeyCode.Down => player.downPressed()
					case KeyCode.A => {
						player.aPressed()
					
					}
					case KeyCode.D => {
						player.dPressed()
						
					}
					case KeyCode.W => {
						player.wPressed()
						
					}
					case KeyCode.S => {
						player.sPressed()
						
					}
					case _ => 
				}
			}
			
			onKeyReleased = (ke: KeyEvent) => {
				ke.code match {
					case KeyCode.Left => player.leftReleased()
					case KeyCode.Right => player.rightReleased()
					case KeyCode.Up => player.upReleased()
					case KeyCode.Down => player.downReleased()
					case KeyCode.A => player.aReleased()
					case KeyCode.D => player.dReleased()
					case KeyCode.W => player.wReleased()
					case KeyCode.S => player.sReleased()
					case _ => 
				}
			}

			var lastTime = -1L
			val timer = AnimationTimer(time => {
				if (lastTime > 0) {
				val delay = (time - lastTime)/1e9
				level.updateAll(delay)
				renderer.render(level.makePassable, player.x, player.y)
			}
			lastTime = time
		})
			timer.start()
		}
	}
}
