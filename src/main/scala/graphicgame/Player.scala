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
  def etype = "p"
  val speedP = 5
  private var count = 15

  def move(delay: Double) = {
    if (leftHeld && level.maze.isClear(
          x - speedP * delay,
          y,
          width,
          height,
          this
        )) _x -= speedP * delay
    if (rightHeld && level.maze.isClear(
          x + speedP * delay,
          y,
          width,
          height,
          this
        )) _x += speedP * delay
    if (upHeld && level.maze.isClear(
          x,
          y - speedP * delay,
          width,
          height,
          this
        )) _y -= speedP * delay
    if (downHeld && level.maze.isClear(
          x,
          y + speedP * delay,
          width,
          height,
          this
        )) _y += speedP * delay
  }
  def fire(delay: Double) = {
    count -= 1
    if ((aHeld) && (count <= 0)) {
      count = 15
      val b = new Projectile(_x, _y, level, "left")
      level += b
    }
    if ((dHeld) && (count <= 0)) {
      count = 15
      val b = new Projectile(_x, _y, level, "right")
      level += b
    }
    if ((wHeld) && (count <= 0)) {
      count = 15
      val b = new Projectile(_x, _y, level, "up")
      level += b
    }
    if ((sHeld) && (count <= 0)) {
      count = 15
      val b = new Projectile(_x, _y, level, "down")
      level += b
    }
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

  private var aHeld = false
  private var dHeld = false
  private var wHeld = false
  private var sHeld = false
  def aPressed() = aHeld = true
  def dPressed() = dHeld = true
  def wPressed() = wHeld = true
  def sPressed() = sHeld = true
  def aReleased() = aHeld = false
  def dReleased() = dHeld = false
  def wReleased() = wHeld = false
  def sReleased() = sHeld = false

  private var here = true
  def update(delay: Double): Unit = {
    move(delay)
    fire(delay)
    for (e <- level.enemies)
      if (Entity.intersect(e, this) == true) here = false
  }

  def postCheck(): Unit = ???
  def stillHere(): Boolean = here
  def makePassable: PassableEntity = {
    new PassableEntity("p", x, y, width, height)
  }
}
