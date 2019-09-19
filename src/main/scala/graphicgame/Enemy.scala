package graphicgame

class Enemy (
    private var _x: Double,
    private var _y: Double,
     val level: Level
 ) extends Entity {
     def x: Double = _x
     def y: Double = _y
     def width: Double = 1
     def height: Double = 1
     
     def update(delay: Double): Unit = ???
     def postCheck(): Unit = ???
     def stillHere(): Boolean = ???
 }