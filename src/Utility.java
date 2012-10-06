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
		System.out.println("Startpos: (" + playerPos.getRow() + "," + playerPos.getCol() + ")");
		ArrayList<Box> boxes = map.getAllBoxes(); //Startpositionen och id för alla lådor
		System.out.println("antal lådor: " + boxes.size());
		boolean[] directions = new boolean[4];
		for(int boxNr = 0; boxNr < boxes.size(); boxNr++){ //leta upp alla möjliga vägar till alla lådor
			Box currBox = boxes.get(boxNr);
			directions = search(playerPos, currBox, map);
			System.out.println("Kommer ur search!");
			for(int i = 0; i < 4; i++){
				if(directions[i]){
					if(i == WEST){ //Push the box east
						Position newBoxPos = new Position(currBox.getPosition().getRow(), currBox.getPosition().getCol()+1);
						if(legalPush(newBoxPos, map)){
							Move move = new Move(boxNr, newBoxPos);
							possibleMoves.add(move);
						}
					}
					if(i == EAST){ //Push the box west
						Position newBoxPos = new Position(currBox.getPosition().getRow(), currBox.getPosition().getCol()-1);
						if(legalPush(newBoxPos, map)){
						Move move = new Move(boxNr, newBoxPos);
						possibleMoves.add(move);
						}
					}
					if(i == NORTH){ //Push the box south
						Position newBoxPos = new Position(currBox.getPosition().getRow()+1, currBox.getPosition().getCol());
						if(legalPush(newBoxPos, map)){
						Move move = new Move(boxNr, newBoxPos);
						possibleMoves.add(move);
						}
					}
					if(i == SOUTH){ //Push the box north
						Position newBoxPos = new Position(currBox.getPosition().getRow()-1, currBox.getPosition().getCol());
						if(legalPush(newBoxPos, map)){
						Move move = new Move(boxNr, newBoxPos);
						possibleMoves.add(move);
						}
					}
				}
			}
		}
		return possibleMoves;
	}

	private boolean legalPush(Position newBoxPos, Map map) {
		boolean deadLock = checkDeadLock(newBoxPos);
		if(deadLock)
			System.out.println("deadlock detected!");
		boolean obstacle = !isAvailiable(map.getMap(), newBoxPos);
		if(obstacle)
			System.out.println("Obstacle detected!");
		if(!deadLock && !obstacle){
			return true;
		}
		return false;
	}

	private boolean checkDeadLock(Position newBoxPos) {
		boolean[][] dMap = DeadLock.getDLM();
		return dMap[newBoxPos.getRow()][newBoxPos.getCol()];
	}

	private boolean[] search(Position playerPos, Box box, Map map) {
		char[][] board = map.getMap();
		//Directions[0-4] = west, east, north, south! 
		boolean[] directions = new boolean[4];
		for(int i = 0; i < 4; i++){
			directions[i] = false; //initiera till false
		}
		Position boxPos = box.getPosition();
		System.out.println("Lådpos: (" + boxPos.getRow() + "," + boxPos.getCol() + ")");
		//directions of the box
		Position west = new Position(boxPos.getRow(), boxPos.getCol()-1);
		Position east = new Position(boxPos.getRow(), boxPos.getCol()+1);
		Position north = new Position(boxPos.getRow()-1, boxPos.getCol());
		Position south = new Position(boxPos.getRow()+1, boxPos.getCol());
		System.out.println("Norr: (" + north.getRow() + "," + north.getCol() + ")");
		boolean[][] visited = new boolean[map.getRows()][map.getCols()];
		//initiera visited till false.
		for(int row = 0; row < map.getRows(); row++){
			for(int col = 0; col < map.getCols(); col++){
				visited[row][col] = false;
			}
		}
		Queue<Position> q = new LinkedList<Position>();
		q.add(playerPos);
		visited[playerPos.getRow()][playerPos.getCol()] = true;
		while(!q.isEmpty()){
			Position currPos = q.remove();
			System.out.println("Kollar ny position: (" + currPos.getRow() + "," + currPos.getCol() + ")");
			if(currPos.isEqualTo(boxPos)){ //kan komma till vänster om lådan 
				System.out.println("Kan komma vänster om lådan!");
				directions[0] = true;
			}
			if(currPos.isEqualTo(boxPos)){ //kan komma till höger om lådan 
				System.out.println("Kan komma höger om lådan!");
				directions[1] = true;
			}
			if(currPos.isEqualTo(boxPos)){ //kan komma till positionen över lådan
				System.out.println("Kan komma över lådan!");
				directions[2] = true;
			}
			if(currPos.isEqualTo(boxPos)){ //kan komma till positionen under lådan
				System.out.println("Kan komma under lådan!");
				directions[3] = true;
			}
			for(int direction = 0; direction < 4; direction++){ //kolla grannar
				if(direction == WEST){ 
					System.out.println("Kollar grannen västerut");
					Position newPos = new Position(currPos.getRow(), currPos.getCol()-1); 
					if(!visited[currPos.getRow()][currPos.getCol()] && isAvailiable(board, newPos)){
						System.out.println("lägger på en granne");
						visited[currPos.getRow()][currPos.getCol()] = true;
						q.add(newPos);
					}
				}
				if(direction == EAST){ 
					System.out.println("Kollar grannen österut");
					Position newPos = new Position(currPos.getRow(), currPos.getCol()+1);
					if(!visited[currPos.getRow()][currPos.getCol()] && isAvailiable(board, newPos)){
						System.out.println("lägger på en granne");
						visited[currPos.getRow()][currPos.getCol()] = true;
						q.add(newPos);
					}
				}
				if(direction == NORTH){ 
					System.out.println("Kollar grannen norrut");
					Position newPos = new Position(currPos.getRow()-1, currPos.getCol());
					if(!visited[currPos.getRow()][currPos.getCol()] && isAvailiable(board, newPos)){
						System.out.println("lägger på en granne");
						visited[currPos.getRow()][currPos.getCol()] = true;
						q.add(newPos);
					}
				}
				if(direction == SOUTH){ 
					System.out.println("Kollar grannen söderut");
					Position newPos = new Position(currPos.getRow()+1, currPos.getCol());
					if(!visited[currPos.getRow()][currPos.getCol()] && isAvailiable(board, newPos)){
						System.out.println("lägger på en granne");
						visited[currPos.getRow()][currPos.getCol()] = true;
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
