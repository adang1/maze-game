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

    val speed = 50
  
    def move(delay:Double) = {
        if (leftHeld && level.maze.isClear(x-1, y, width, height, this)) _x -= speed*delay
        if (rightHeld && level.maze.isClear(x+1, y, width, height, this)) _x += speed*delay
        if (upHeld && level.maze.isClear(x, y-1, width, height, this)) _y -= speed*delay
        if (downHeld && level.maze.isClear(x, y+1, width, height, this)) _y += speed*delay
    }

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

    def update(delay: Double): Unit = {
        move(delay)

    }
    def postCheck(): Unit = ???
    def stillHere(): Boolean = ???
}   