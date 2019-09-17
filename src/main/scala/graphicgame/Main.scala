package graphicgame

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image

/**
 * This is a stub for the graphical game.
 */
object Main extends JFXApp {
	val canvas = new Canvas(800, 800)
	val gc = canvas.graphics.Context2D
	val renderer = new Renderer2D(gc, canvas)
	val player = new Player
	stage = new JFXApp.PrimaryStage {
		title = "2D Snipes" // Change this to match the theme of your game.
		scene = new Scene(1000, 800) {
		  // Put your code here.
		}
	}
}
