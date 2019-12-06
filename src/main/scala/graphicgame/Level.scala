package graphicgame

class Level (val maze: Maze, 
            private var _entities: Seq[Entity]) {
               def entities = _entities 
               def += (e:Entity): Unit = _entities +:= e
              
              def bullets = _entities.filter(_.etype == "b")
              def enemies = _entities.filter(_.etype == "e")
              def players = _entities.collect { case p: Player => p }
               def updateAll(delay: Double): Unit = {
                 _entities = _entities.filter(_.stillHere)
               for (e <- _entities) {
                if (e.stillHere()) 
                e.update(delay)
               }
              }
               def makePassable: PassableLevel = {
                 new PassableLevel(maze, entities.map(_.makePassable))
               }
            }