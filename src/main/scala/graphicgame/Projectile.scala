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
     
     val speed = 7

     def move(delay:Double) = {
        if (aHeld && level.maze.isClear(x-0.1, y, width, height, this)) _x -= speed*delay
        else if (dHeld && level.maze.isClear(x+0.1, y, width, height, this)) _x += speed*delay
        else if (wHeld && level.maze.isClear(x, y-0.1, width, height, this)) _y -= speed*delay
        else if (sHeld && level.maze.isClear(x, y+0.1, width, height, this)) _y += speed*delay
        else {
            _x = Main.player.x
            _y = Main.player.y
        }
    }
    private var aHeld = false
    private var dHeld = false
    private var wHeld = false
    private var sHeld = false
    def aPressed() = aHeld = true
    def dPressed() = dHeld = true
    def wPressed() = wHeld = true
    def sPressed() = sHeld = true
     
     def update(delay: Double): Unit = {
        move(delay)
     }
     def postCheck(): Unit = ???
     def stillHere(): Boolean = ???
    }