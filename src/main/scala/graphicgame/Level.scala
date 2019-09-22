package graphicgame

class Level (val maze: Maze, 
            private var _entities: Seq[Entity]) {
               def entities = _entities 
               def += (e:Entity): Unit = _entities +:= e
              
               
             
               def updateAll(delay: Double): Unit = {
               for (e <- _entities) e.update(delay)
                
              }
               
            }