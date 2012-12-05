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
 */

public class Map implements Comparable<Map>{
	final Map prevMap; // pointer to parentmap
	public Map nextMap; // Pointer to childmap
	private char[][] map;
	private int rows, cols;
	private Position playerPos;
	private ArrayList<Box> boxes;
	public Move withMove;
	public boolean hasNextMap = false;
	public boolean hasPrevMap = false;
	private ArrayList<Move> moves;
	public int value;
	private ArrayList<Position> goals;
	

	
	/**
	 * Constructor for creating the map for the first time.
	 * The pointer prev_map will we null
	 *  
	 * @param rows = number of rows of the map
	 * @param cols = number of columns of the map
	 */
	public Map(int rows, int cols) {
		prevMap = null;
		nextMap = null;
		map = new char[rows][cols];
		this.rows = rows;
		this.cols = cols;
		boxes = new ArrayList<Box>();
		goals = new ArrayList<Position>();
	}
	
	/**
	 * Constructor for creating a map from a corresponding map
	 * with a new move.
	 * 
	 * @param fromMap = parent Map
	 * @param withMove = new Move
	 */
	public Map(Map fromMap, Move withMove) {
		// point
		prevMap = fromMap;
		hasPrevMap = true;
		nextMap = null;
		this.withMove = withMove;
		// map
		this.rows = fromMap.getRows();
		this.cols = fromMap.getCols();
		this.map = new char[rows][cols];
		cloneMap(fromMap.getMap()); 
		
		// boxes
		boxes = new ArrayList<Box>(); 
		for(int i = 0; i<fromMap.getAllBoxes().size(); i++) {
			Box newB = new Box(fromMap.getAllBoxes().get(i));
			boxes.add(newB);
		}	
		
		playerPos = new Position(); // player pos
		playerPos.set(fromMap.getPlayerPosition());
		doMove(withMove);
		
	}
	
	public void evaluateMap() {
		int moveVal = 0;
		int boxVal = 0;
		int distanceVal = Distances.getDistance(playerPos);
		moves = Utility.findPossibleMoves(this);
		moveVal = moves.size();
		distanceVal = Distances.getDistance(playerPos);
 		for(int box = 0; box < boxes.size(); box++){
			if(boxes.get(box).isOnGoal()){
				boxVal++;
			}
		}
		value = moveVal + 5*boxVal - distanceVal;
	}

	/**
	 * Perform a move (push-a-box) and update 
	 * the map. This method assume that the move is valid
	 * @param move = the move
	 */
	private void doMove(Move move) {
		Box currentBox = boxes.get(move.getID());
		Position fromPos = currentBox.getPosition();
		Position toPos = move.getPosition();
		char from = map[fromPos.getRow()][fromPos.getCol()];
		char to = map[toPos.getRow()][toPos.getCol()];
		
		// Clear players prevoius position fram the map
		if (map[playerPos.getRow()][playerPos.getCol()] == '+') {
			map[playerPos.getRow()][playerPos.getCol()] = '.';
		}
		else {
			map[playerPos.getRow()][playerPos.getCol()] = ' ';
		}
		
		// Update 'from'-position on map
		switch (from) {
		case '*':
			map[fromPos.getRow()][fromPos.getCol()] = '+'; break;
		case '$':
			map[fromPos.getRow()][fromPos.getCol()] = '@'; break;
		default:
			throw new RuntimeException("Pushed a box when there " +
					"was no box there!");
		}
		
		// Update 'to'-position on map
		boolean toGoal = false;
		switch (to) {
		case ' ':
			map[toPos.getRow()][toPos.getCol()] = '$'; break;
		case '@':
			map[toPos.getRow()][toPos.getCol()] = '$'; break;
		case '.':
			map[toPos.getRow()][toPos.getCol()] = '*';
			toGoal = true;
			break;
		case '+':
			map[toPos.getRow()][toPos.getCol()] = '*';
			toGoal = true;
			break;
		default:
			throw new RuntimeException("Pushed a box to an " +
					"illegal position!");
		}
		
		// update player and the moved box's positions
		playerPos.set(fromPos);
		currentBox.setPosition(move.getPosition());
		currentBox.setIsOnGoal(toGoal);
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
					goals.add(new Position(cRow, i));
					playerPos = new Position(cRow, i); break;
				case '$':
					boxes.add(new Box(boxes.size(), 
							new Position(cRow, i), false)); break;
				case '*':
					goals.add(new Position(cRow, i));
					boxes.add(new Box(boxes.size(), 
							new Position(cRow, i), true)); break;
				case '.':
					goals.add(new Position(cRow, i)); break;
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
	
	// Return pointer to parent
	public Map getPrevMap() {
		return prevMap;
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
	
	// Clone a char matrix into this map object
	private void cloneMap(char [][] fromMap) {
		for (int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				this.map[i][j] = fromMap[i][j];
			}
		}
	}
	
	// Return true if this board is winning one!
	public boolean isWon() {
		for(int i = 0; i<boxes.size(); i++) {
			if(!boxes.get(i).isOnGoal()) 
				return false;
		}
		return true;
	}
	/**
	 * Compares the value of two different maps
	 * returns 1 if the current map is better than 
	 * the one being compared, 0 otherwise.
	 */
	
	public int compareTo(Map map){
		return map.value - value;
	}
	/**
	 * Get all moves for this map
	 * @return moves
	 */
	public ArrayList<Move> getMoves(){
		return moves;
	}
	/**
	 * Return all the goal positions of this map
	 * @return
	 */
	public ArrayList<Position> getGoals(){
		return goals;
	}
}
