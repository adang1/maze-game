package graphicgame

import scala.util.Random

class Enemy (
    private var _x: Double,
    private var _y: Double,
     val level: Level
 ) extends Entity {
     def x: Double = _x
     def y: Double = _y
     def width: Double = 1
     def height: Double = 1
     
     val speed = 5

     def move(delay: Double) = {
        val r = new Random()
      
        if (level.maze.isClear(x-0.1, y, width, height, this)) _x -= r.nextDouble*delay
        if (level.maze.isClear(x+0.1, y, width, height, this)) _x += r.nextDouble*delay
        if (level.maze.isClear(x, y-0.1, width, height, this)) _y -= r.nextDouble*delay
        if (level.maze.isClear(x, y+0.1, width, height, this)) _y += r.nextDouble*delay
        
     }
     def update(delay: Double): Unit = {
        move(delay)
     }
     def postCheck(): Unit = ???
     def stillHere(): Boolean = ???
 }