
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
	private Box boxes[];
	

	
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
		
		updateMove(withMove);
	}
	
	
	/**
	 * Insert a row of the map into map matrix
	 * @param s = row string of the map
	 * @param cRow = current row
	 */
	public void insertRow(String s, int cRow) {
		char current;
		for(int i = 0; i < cols; i++) {
			current = s.charAt(i);
			map[cRow][i] = current;
		}
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
