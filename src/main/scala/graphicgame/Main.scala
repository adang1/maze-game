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
	// val entities = Seq(player, enemy)
	val maze = RandomMaze(3, false, 20, 20, 0.6)
	val level = new Level(maze, Nil)
	var bullet = List[Projectile]()
	for (i <- 1 to 100) {
		bullet ::= new Projectile(50, 50, level) 
	}
	val player = new Player(50, 50, bullet, level)
	val enemy = new Enemy(60, 50, level)
	
	level += player
	level += enemy
	for (i <- bullet) {
		level += i
	}
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
						bullet(0).aPressed()
						bullet = bullet.tail
					}
					case KeyCode.D => {
						bullet(0).dPressed()
						bullet = bullet.tail
					}
					case KeyCode.W => {
						bullet(0).wPressed()
						bullet = bullet.tail
					}
					case KeyCode.S => {
						bullet(0).sPressed()
						bullet = bullet.tail
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
					case KeyCode.A => bullet(0).aReleased()
					case KeyCode.D => bullet(0).dReleased()
					case KeyCode.W => bullet(0).wReleased()
					case KeyCode.S => bullet(0).sReleased()
					case _ => 
				}
			}

			var lastTime = -1L
			val timer = AnimationTimer(time => {
				if (lastTime > 0) {
				val delay = (time - lastTime)/1e9
				level.updateAll(delay)
				renderer.render(level, player.x, player.y)
			}
			lastTime = time
		})
			timer.start()
		}
	}
}
