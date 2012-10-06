import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Utility {
		
	private static final int WEST = 0;
	private static final int EAST = 1;
	private static final int NORTH = 2;
	private static final int SOUTH = 3;
	
	public Utility(){
		
	}
	
	/**
	 * Hittar alla sätt som man från utgångspositionen kan flytta på lådor.
	 * @return En lista av möjliga lådförflyttningar (Moves)
	 */
	public ArrayList<Move> findPossibleMoves(Map map){
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		Position playerPos = map.getPlayerPosition();//Startpostionen för spelaren
		ArrayList<Box> boxes = map.getAllBoxes(); //Startpositionen och id för alla lådor
		boolean[] directions = new boolean[4];
		for(int boxNr = 0; boxNr > boxes.size(); boxNr++){ //leta upp alla möjliga vägar till alla lådor
			Box currBox = boxes.get(boxNr);
			directions = search(playerPos, currBox, map.getMap());
			for(int i = 0; i < 4; i++){
				if(directions[i]){
					if(i == WEST){ //Push the box east
						Position newBoxPos = new Position(currBox.getPosition().getRow(), currBox.getPosition().getCol()+1);
						Move move = new Move(boxNr, newBoxPos);
						possibleMoves.add(move);
					}
					if(i == EAST){ //Push the box west
						Position newBoxPos = new Position(currBox.getPosition().getRow(), currBox.getPosition().getCol()-1);
						Move move = new Move(boxNr, newBoxPos);
						possibleMoves.add(move);
					}
					if(i == NORTH){ //Push the box south
						Position newBoxPos = new Position(currBox.getPosition().getRow()+1, currBox.getPosition().getCol());
						Move move = new Move(boxNr, newBoxPos);
						possibleMoves.add(move);
					}
					if(i == SOUTH){ //Push the box north
						Position newBoxPos = new Position(currBox.getPosition().getRow()-1, currBox.getPosition().getCol());
						Move move = new Move(boxNr, newBoxPos);
						possibleMoves.add(move);
					}
				}
			}
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
				if(direction == WEST){ 
					Position newPos = new Position(currPos.getRow(), currPos.getCol()-1); 
					if(!visited.contains(newPos) && isAvailiable(map, newPos)){
						visited.add(newPos);
						q.add(newPos);
					}
				}
				if(direction == EAST){ 
					Position newPos = new Position(currPos.getRow(), currPos.getCol()+1);
					if(!visited.contains(newPos) && isAvailiable(map, newPos)){
						visited.add(newPos);
						q.add(newPos);
					}
				}
				if(direction == NORTH){ 
					Position newPos = new Position(currPos.getRow()-1, currPos.getCol());
					if(!visited.contains(newPos) && isAvailiable(map, newPos)){
						visited.add(newPos);
						q.add(newPos);
					}
				}
				if(direction == SOUTH){ 
					Position newPos = new Position(currPos.getRow()+1, currPos.getCol());
					if(!visited.contains(newPos) && isAvailiable(map, newPos)){
						visited.add(newPos);
						q.add(newPos);
					}
				}
			}
			
		}
	return directions;	
	}
	
	public boolean isAvailiable(char[][] map, Position pos){
		if(map[pos.getRow()][pos.getCol()] == ' ' || map[pos.getRow()][pos.getCol()] == '.'){
			return true;
		}
		return false;
	}
	
}
