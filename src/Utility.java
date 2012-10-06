import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Utility {
	
	public Utility(){
		//behöver konstruktorn göra nått?
	}
	
	/**
	 * Hittar alla sätt som man från utgångspositionen kan flytta på lådor.
	 * @return En lista av möjliga lådförflyttningar (Moves)
	 */
	public ArrayList<Move> findPossibleMoves(Map map){
		//Startpostionen för spelaren
		Position playerPos = map.getPlayerPosition();
		//Startpositionen och id för alla lådor
		ArrayList<Box> boxes = map.getAllBoxes();
		//leta upp alla möjliga vägar till alla lådor
		for(int boxNr = 0; boxNr > boxes.size(); boxNr++){
			search(playerPos, boxes.get(boxNr), map.getMap());
		}
	}

	private boolean[] search(Position playerPos, Box box, char[][] map) {
		//Directions[0-4] = west, east, north, south! 
		boolean[] directions = new boolean[4];
		for(int i = 0; i < 4; i++){
			directions[i] = false; //initiera till false
		}
		Position boxPos = box.getPosition();
		//directions of the box
		Position west = new Position(boxPos.getRow(), boxPos.getCol()-1);
		Position east = new Position(boxPos.getRow(), boxPos.getCol()+1);
		Position north = new Position(boxPos.getRow()-1, boxPos.getCol());
		Position south = new Position(boxPos.getRow()+1, boxPos.getCol());
		ArrayList<Position> visited = new ArrayList<Position>();
		Queue<Position> q = new LinkedList<Position>();
		q.add(playerPos);
		visited.add(playerPos);
		while(!q.isEmpty()){
			Position currPos = q.remove();
			if(currPos == west){ //kan komma till vänster om lådan 
				directions[0] = true;
			}
			if(currPos == east){ //kan komma till höger om lådan 
				directions[1] = true;
			}
			if(currPos == north){ //kan komma till positionen över lådan
				directions[2] = true;
			}
			if(currPos == south){ //kan komma till positionen under lådan
				directions[3] = true;
			}
			for(int direction = 0; direction < 4; direction++){ //kolla grannar
				if(direction == 0){ //west
					Position newPos = new Position(currPos.getRow(), currPos.getCol()-1); 
					if(!visited.contains(newPos) && 
							(map[newPos.getRow()][newPos.getCol()] == ' ' || map[newPos.getRow()][newPos.getCol()] == '.')){
						visited.add(newPos);
						q.add(newPos);
					}
				}
				if(direction == 2){ //east
					Position newPos = new Position(currPos.getRow(), currPos.getCol()+1);
					if(!visited.contains(newPos) && 
							(map[newPos.getRow()][newPos.getCol()] == ' ' || map[newPos.getRow()][newPos.getCol()] == '.')){
						visited.add(newPos);
						q.add(newPos);
					}
				}
				if(direction == 3){ //north
					Position newPos = new Position(currPos.getRow()-1, currPos.getCol());
					if(!visited.contains(newPos) && 
							(map[newPos.getRow()][newPos.getCol()] == ' ' || map[newPos.getRow()][newPos.getCol()] == '.')){
						visited.add(newPos);
						q.add(newPos);
					}
				}
				if(direction == 4){ //south
					Position newPos = new Position(currPos.getRow()+1, currPos.getCol());
					if(!visited.contains(newPos) && 
							(map[newPos.getRow()][newPos.getCol()] == ' ' || map[newPos.getRow()][newPos.getCol()] == '.')){
						visited.add(newPos);
						q.add(newPos);
					}
				}
			}
			
		}
		
		
		
		
		enqueue v onto Q
		4      mark v
		5      while Q is not empty:
		6          t ← Q.dequeue()
		7          if t is what we are looking for:
		8              return t
		9          for all edges e in G.incidentEdges(t) do
		10              // G.opposite returns adjacent vertex 
		12             o ← G.opposite(t,e)
		13             if o is not marked:
		14                  mark o
		15                  enqueue o onto Q
	}
	
}
