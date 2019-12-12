package graphicgame

import scala.util.Random

class DumbEnemy (
    private var _x: Double,
    private var _y: Double,
    val level: Level
 ) extends Entity {
     def x: Double = _x
     def y: Double = _y
     def width: Double = 1
     def height: Double = 1
     def etype = "de"
     val speed = 2
     
     val r = new Random
     def move(delay: Double) = {
         val dx = speed*delay*(-1 + r.nextDouble*2)*10
         val dy = speed*delay*(-1 + r.nextDouble*2)*10
         println(dx,dy)

         if (level.maze.isClear(_x+dx, _y+dy, width, height, this)) {
         _x += dx
         _y += dy
     }
    }
     private var here = true
     def update(delay: Double): Unit = {
        move(delay)
        for (b <- level.bullets)
           if (Entity.intersect(b, this) == true) here = false
     }
     def postCheck(): Unit = ???
     def stillHere(): Boolean = here
     def makePassable: PassableEntity = new PassableEntity("de", x, y, width, height)
 }
    