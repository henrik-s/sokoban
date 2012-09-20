
/**
 * Map contains:
 * 		A char matrix of the board, where each position is
 * 			# = Wall
 * 			@ = Player
 * 			+ = Player on goal square
 * 			$ = Box
 * 			* = Box on goal
 * 			. = Goal on square
 * 			(space) = empty square
 *  
 * @author MacHenriks
 *
 */

public class Map {
	
	public char[][] board;
	Map prev_map;
	private int rows, cols;
	
	public Map(char[][] newMap) {
		board = newMap;
	}
	
	public Map(int rows, int cols) {
		board = new char[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}
	
	/**
	 * Insert a row of the board into board matrix
	 * @param s = row string of the board
	 * @param cRow = current row
	 */
	public void insertRow(String s, int cRow) {
		char current;
		for(int i = 0; i < cols; i++) {
			current = s.charAt(i);
			board[cRow][i] = current;
		}
	}
	
	/**
	 * Return a string of the board
	 * @return
	 */
	public String print() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				sb.append(board[i][j]);
			}
			sb.append('\n');
		}
		sb.append('\n');
		return sb.toString();
	}

}
