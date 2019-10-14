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
     
     val speed = 4

     def move(delay: Double) = {
        val uStep = shortestPath(level.maze, _x, _y-1, Main.player.x, Main.player.y)
        val dStep = shortestPath(level.maze, _x, _y+1, Main.player.x, Main.player.y)
        val lStep = shortestPath(level.maze, _x-1, _y, Main.player.x, Main.player.y)
        val rStep = shortestPath(level.maze, _x+1, _y, Main.player.x, Main.player.y)

        val minStep = uStep min dStep min lStep min rStep
        if (minStep == uStep) _y -= speed*delay
        else if (minStep == dStep) _y += speed*delay
        else if (minStep == lStep) _x -= speed*delay
        else if (minStep == rStep) _x += speed*delay        
     }

     val offsets = Array((-1, 0), (1, 0), (0, -1), (0, 1))

    def shortestPath(maze: Maze, sx: Double, sy: Double, ex: Double, ey: Double): Int = {
        if ( !maze.isClear(sx, sy, width, height, this) ) 1000000000
        else {
            val queue = new ArrayQueue[(Double, Double, Int)]()
            queue.enqueue((sx, sy, 0))
            val visited = collection.mutable.Set[(Double, Double)](sx -> sy)
            while (!queue.isEmpty) {
            val (x, y, steps) = queue.dequeue()
            for ((dx, dy) <- offsets) {
                val (nx, ny) = (x + dx, y + dy)
                if (Math.abs(nx - ex) < 1 && Math.abs(ny - ey) < 1) return steps + 1
                if (maze.isClear(nx, ny, width, height, this) &&
                    !visited(nx -> ny)) {
                queue.enqueue((nx, ny, steps + 1))
                visited += nx -> ny
                }
            }
            }
            1000000000
    }
    }

     def update(delay: Double): Unit = {
        move(delay)
     }
     def postCheck(): Unit = ???
     def stillHere(): Boolean = true
 }