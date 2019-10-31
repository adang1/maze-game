package graphicgame

class Projectile(
    private var _x: Double,
    private var _y: Double,
    val level: Level,
    val dir: String
) extends Entity {
  def x: Double = _x
  def y: Double = _y
  def width: Double = 0.2
  def height: Double = 0.2
  def etype = "b"
  private var here = true

  val speedB = 10
  def update(delay: Double): Unit = {
    if ((dir == "left") && (level.maze
          .isClear(x - 0.1, y, width, height, this))) _x -= speedB * delay
    else if ((dir == "right") && (level.maze
               .isClear(x + 0.1, y, width, height, this))) _x += speedB * delay
    else if ((dir == "up") && (level.maze
               .isClear(x, y - 0.1, width, height, this))) _y -= speedB * delay
    else if ((dir == "down") && (level.maze
               .isClear(x, y + 0.1, width, height, this))) _y += speedB * delay
    else here = false

  }
  
  def makePassable: PassableEntity = new PassableEntity("b", x, y, width, height)
  def postCheck(): Unit = ???
  def stillHere(): Boolean = here
}
