import java.util.ArrayList;


/**
 * Map contains:
 * 		- A char matrix of the map, where each position is
 * 			# = Wall
 * 			@ = Player
 * 			+ = Player on goal square
 * 			$ = Box
 * 			* = Box on goal
 * 			. = Goal on square
 * 			(space) = empty square
 * 		
 * 		- A list of all the boxes
 * 		- The position of the players 
 * 	
 *  
 * @author MacHenriks
 *
 */

public class Map {
	final Map prevMap;
	private char[][] map;
	private int rows, cols;
	private Position playerPos;
	private ArrayList<Box> boxes;
	

	
	/**
	 * Constructor for creating the map for the first time.
	 * The pointer prev_map will we null
	 *  
	 * @param rows = number of rows of the map
	 * @param cols = number of columns of the map
	 */
	public Map(int rows, int cols) {
		prevMap = null;
		map = new char[rows][cols];
		this.rows = rows;
		this.cols = cols;
		boxes = new ArrayList<Box>();
	}
	
	/**
	 * Constructor for creating a map from a corresponding map
	 * with a new move.
	 * 
	 * @param fromMap = parent Map
	 * @param withMove = new Move
	 */
	public Map(Map fromMap, Move withMove) {
		prevMap = fromMap;
		this.map = fromMap.getMap().clone();
		this.rows = fromMap.getRows();
		this.cols = fromMap.getCols();
		boxes = new ArrayList<Box>();
		boxes = fromMap.getAllBoxes();
		doMove(withMove);
	}
	
	/**
	 * Perform a move (push-a-box) and update 
	 * the map. This method assume that the move is valid
	 * @param move = the move
	 */
	private void doMove(Move move) {
		Position fromPos = boxes.get(move.getID()).getPosition();
		Position toPos = move.getPosition();
		char from = map[fromPos.getRow()][fromPos.getCol()];
		char to = map[toPos.getRow()][toPos.getCol()];
		
		// Clear players prevoius position fram the map
		map[playerPos.getRow()][playerPos.getCol()] = ' ';
		
		// Update 'from'-position on map
		if(from == '*') {
			map[fromPos.getRow()][fromPos.getCol()] = '+';
		}
		else if(from == '$') {
			map[fromPos.getRow()][fromPos.getCol()] = '@';
		}
		else {
			throw new RuntimeException("Pushed a box when there was no box there!");
		}
		
		// Update 'to'-position on map
		if(to == ' ') {
			map[toPos.getRow()][toPos.getCol()] = '$';
		}
		else if(to == '.') {
			map[fromPos.getRow()][toPos.getCol()] = '*';
		}
		else {
			throw new RuntimeException("Pushed a box to an illegal position!");
		}		
		
		// update player and the moved box's positions
		playerPos.set(fromPos);
		boxes.get(move.getID()).getPosition().set(move.getPosition());
	}
	
	/**
	 * Insert a row of the map into map matrix
	 * and determine the player's and all the boxes
	 * positions.
	 * 
	 * @param s = row string of the map
	 * @param cRow = current row
	 */
	public void insertRow(String s, int cRow) {
		char current;
		for(int i = 0; i < cols; i++) {
			current = s.charAt(i);
			switch (current) {
				case '@':
					playerPos = new Position(cRow, i); break;
				case '+':
					playerPos = new Position(cRow, i); break;
				case '$':
					boxes.add(new Box(boxes.size(), new Position(cRow, i))); break;
				case '*':
					boxes.add(new Box(boxes.size(), new Position(cRow, i))); break;
				default:
					break;
			}
			map[cRow][i] = current;
		}
	}
	

	// Return all the boxes for the map
	public ArrayList<Box> getAllBoxes() {
		return boxes;
	}
	
	// Return a specific box
	public Box getBox(int id) {
		return boxes.get(id);
	}
	
	// Return this map's char matrix
	public char[][] getMap() {
		return this.map;
	}	 
	
	// Return the player's position on the map
	public Position getPlayerPosition() {
		return playerPos;
	}
	
	// Return number of columns
	public int getCols() {
		return this.cols;
	}
	
	// Return number of rows
	public int getRows() {
		return this.rows;
	}
	
	// Return a string of the map

	public String print() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				sb.append(map[i][j]);
			}
			sb.append('\n');
		}
		sb.append('\n');
		return sb.toString();
	}
}
