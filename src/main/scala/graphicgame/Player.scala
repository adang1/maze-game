package graphicgame

class Player(
   private var _x: Double,
   private var _y: Double,
    val level: Level
) extends Entity {
    def x: Double = _x
    def y: Double = _y
    def width: Double = 1
    def height: Double = 1

    val dropInterval = 1.0
               private var dropDelay = 0.0
               val moveInterval = 0.1
               private var moveDelay = 0.0
               private var leftHeld = false
               private var rightHeld = false
               private var upHeld = false
               private var downHeld = false
             
               def leftPressed() = leftHeld = true
               def rightPressed() = rightHeld = true
               def upPressed() = upHeld = true
               def downPressed() = downHeld = true
               def leftReleased() = leftHeld = false
               def rightReleased() = rightHeld = false
               def upReleased() = upHeld = false
               def downReleased() = downHeld = false
    
    def update(delay: Double): Unit = ???
    def postCheck(): Unit = ???
    def stillHere(): Boolean = ???
}   