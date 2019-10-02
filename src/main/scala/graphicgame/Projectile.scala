package graphicgame

class Projectile(
    private var _x: Double,
    private var _y: Double,
    val level: Level
) extends Entity {
    def x: Double = _x
     def y: Double = _y
     def width: Double = 0.2
     def height: Double = 0.2
     
     val speed = 10


    private var leftHeld = false
    private var rightHeld = false
    private var upHeld = false
    private var downHeld = false
  
     
     def update(delay: Double): Unit = {
         
     }
     def postCheck(): Unit = ???
     def stillHere(): Boolean = ???
    }