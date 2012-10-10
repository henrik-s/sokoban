import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeadLock {

	private static boolean DEBUG = false;
	private static boolean[][] dlm;
	private static boolean[][] player_dlm;
	private static int rows, cols;

	public DeadLock(String fileName) {
		File file = new File(fileName);
		String row;
		Scanner s;
		Map map;
		int rowLength = 0;
		try {
			s = new Scanner(file).useDelimiter("\n");
			rows = Integer.parseInt(s.next());
			cols = Integer.parseInt(s.next());
			map = new Map(rows, cols);
			dlm = new boolean[rows][cols];
			player_dlm = new boolean[rows][cols];
			initDLM(rows, cols, map);

			for (int i = 0; i < rows; i++) {
				row = s.next();
				System.out.println(row);
				rowLength = row.length();
				if (rowLength < cols) {
					for (int j = 0; j < (cols - rowLength); j++) {
						row += " ";
					}
				}
				map.insertRow(row, i);
			}

			analyzeDefiniteDeadlocks(map);

			if (DEBUG) {
				System.out.println("Rows: " + rows);
				System.out.println("Cols: " + cols);
				printDLM(map);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

	}

	public DeadLock(Map map) {
		rows = map.getRows();
		cols = map.getCols();
		if (rows != 0 || cols != 0) {
			dlm = new boolean[rows][cols];
			player_dlm = new boolean[rows][cols];
			initDLM(rows, cols, map);

		} else {
			throw new RuntimeException("DeadLock Constructor: The initializing"
					+ " map is empty");
		}
		analyzeDefiniteDeadlocks(map);
		if (DEBUG) {
			printDLM(map);
			System.out.println();
			// printPlayerDLM(map);
		}
	}

	private void analyzeDefiniteDeadlocks(Map map) {
		char[][] board = map.getMap();
		int res = 0;
		for (int i = 1; i < board.length - 1; i++) {
			for (int j = 1; j < board[0].length - 1; j++) {
				if (board[i][j] == '#')
					continue;
				if ((res = isCorner(map, i, j)) != 0 && board[i][j] != '.') {
					dlm[i][j] = true;
					checkDeadlockSides(map, i, j, res);
				}
			}
		}
	}

	public boolean analyzeDeadlocks(Map map) {

		return false;
	}

	private void checkDeadlockSides(Map map, int i, int j, int res) {
		if (res == 1) {
			checkSides(map, i, j, -1, 1);
		} else if (res == 2) {
			checkSides(map, i, j, 1, 1);
		} else if (res == 3) {
			checkSides(map, i, j, 1, -1);
		} else if (res == 4) {
			checkSides(map, i, j, -1, -1);
		}

	}

	private void checkSides(Map map, int row, int col, int rowDiff, int colDiff) {
		ArrayList<Position> deadlocks = new ArrayList<Position>();
		ArrayList<Position> r = new ArrayList<Position>();
		ArrayList<Position> b = new ArrayList<Position>();
		char[][] board = map.getMap();

		// Search to the right of the given position
		for (int j = col + 1; j < dlm[0].length - 1; j++) {
			if (board[row][j] == '#' || board[row][j] == '.'
					|| board[row][j] == '+' || board[row][j] == '*')
				break;
			if (isCorner(map, row, j) != 0) {
				deadlocks.addAll(r);
				break;
			} else if (board[row + rowDiff][j] != '#') {
				isTunnel(map, row + rowDiff, j, false, rowDiff);
				break;
			} else {
				if (dlm[row][j] == false)
					r.add(new Position(row, j));
			}
		}

		// Search below of the given position
		for (int i = row + 1; i < board.length - 1; i++) {
			if (board[i][col] == '#' || board[i][col] == '.'
					|| board[i][col] == '+' || board[i][col] == '*')
				break;
			if (isCorner(map, i, col) != 0) {
				deadlocks.addAll(b);
				break;
			} else if (board[i][col + colDiff] != '#') {
				isTunnel(map, i, col+colDiff, true, colDiff);
				break;
			} else {
				if (dlm[i][col] == false)
					b.add(new Position(i, col));
			}
		}

		setDeadLocks(deadlocks);
	}

	private boolean isTunnel(Map map, int row, int col, boolean horizontal
			,int diff) {
		char[][] board = map.getMap();

		if (row >= board.length || col >= board[0].length)
			return true;
		if(row <= 0 || col <= 0){
			return true;
		}

		ArrayList<Position> deadlocks = new ArrayList<Position>();
		
		if (!horizontal) { // Tunneln går vertikalt
			deadlocks.addAll(checkVertical(map, row, col, diff));			
		} else if (horizontal) { // Tunneln går horizontelt
			deadlocks.addAll(checkHorizontal(map, row, col, diff));
		}
		setDeadLocks(deadlocks);
		return true;
	}
	
	public ArrayList<Position> checkVertical(Map map, int row, int col, 
			int diff){
		ArrayList<Position> deadlocks = new ArrayList<Position>();
		char[][] board = map.getMap();
		
		if (col-1 < 0 || !(map.getMap()[row][col - 1] == '#')) {
			return deadlocks;
		} else if (col + 1 > board[0].length -1 || 
				!(map.getMap()[row][col + 1] == '#')) {
			return deadlocks;
		}
		
		if(isCorner(map, row, col) != 0){
			return deadlocks;
		}
		
		int rowPos = row + diff;
				
		while(rowPos < board.length && rowPos > 0){
			if(board[rowPos][col] == '.' || board[rowPos][col] == '+' 
					|| board[rowPos][col] == '*'){
				deadlocks.clear();
				break;
			}
			if(isCorner(map, rowPos, col) == 0){
				if (!(map.getMap()[rowPos][col -1] == '#')) {
					// Vertical tunnel
					isTunnel(map, row, col -1 , true, -1); 
					deadlocks.clear();
					break;
				} else if (!(map.getMap()[rowPos][col + 1] == '#')) {
					// Horizontal tunnel
					isTunnel(map, row, col+ 1, true, 1); 
					deadlocks.clear();
					break;
				}
				deadlocks.add(new Position(rowPos, col));
				rowPos += diff;
			}
			else{
				break;
			}
		}
		return deadlocks;
	}
	
	public ArrayList<Position> checkHorizontal(Map map, int row, 
			int col, int diff){
		ArrayList<Position> deadlocks = new ArrayList<Position>();
		char[][] board = map.getMap();
		
		if ((row -1) < 0 || !(map.getMap()[row - 1][col] == '#')) {
			return deadlocks;
		} else if ((row +1) > board.length -1 
				|| !(map.getMap()[row + 1][col] == '#')) {
			return deadlocks;
		}
		
		if(isCorner(map, row, col) != 0){
			return deadlocks;
		}
		
		int colPos = col + diff;
		while (colPos > 0 && colPos< board[0].length) {
			if(board[row][colPos] == '.' || board[row][colPos] == '+' 
					|| board[row][colPos] == '*'){
				deadlocks.clear();
				break;
			}
			if(isCorner(map, row, colPos) == 0){
				if (!(map.getMap()[row - 1][colPos] == '#')) {
					// Vertical tunnel
					isTunnel(map, row -1, colPos, false, -1); 
					deadlocks.clear();
					break;
				} else if (!(map.getMap()[row + 1][colPos] == '#')) {
					// Horizontal tunnel
					isTunnel(map, row+1, colPos, false, 1); 
					deadlocks.clear();
					break;
				}
				deadlocks.add(new Position(row, colPos));
				colPos += diff;
			}
			else{
				break;
			}
		}
		return deadlocks;
	}

	public void setDeadLocks(ArrayList<Position> deadlocks) {
		Position tmp;
		for (int i = 0; i < deadlocks.size(); i++) {
			tmp = deadlocks.get(i);
			dlm[tmp.getRow()][tmp.getCol()] = true;
		}
	}

	private int isCorner(Map map, int i, int j) {
		char[][] board = map.getMap();
		if (i >= board.length || j >= board[0].length)
			return 0;
		if(i <= 0 || j <= 0){
			return 0;
		}
		int up = ((board[i - 1][j] == '#') ? 1 : 0);
		int right = ((board[i][j + 1] == '#') ? 1 : 0);
		int down = ((board[i + 1][j] == '#') ? 1 : 0);
		int left = ((board[i][j - 1] == '#') ? 1 : 0);

		if (up + right == 2)
			return 1;
		else if (right + down == 2)
			return 2;
		else if (down + left == 2)
			return 3;
		else if (left + up == 2)
			return 4;
		else
			return 0;
	}

	public void printDLM(Map map) {
		char[][] board = map.getMap();
		for (int i = 0; i < dlm.length; i++) {
			for (int j = 0; j < dlm[0].length; j++) {
				if (dlm[i][j] == true)
					System.out.print("D");
				else {
					System.out.print(board[i][j]);
				}
			}
			System.out.println("");
		}
	}

	public void printPlayerDLM(Map map) {
		char[][] board = map.getMap();
		for (int i = 0; i < player_dlm.length; i++) {
			for (int j = 0; j < player_dlm[0].length; j++) {
				if (player_dlm[i][j] == true)
					System.out.print("D");
				else {
					System.out.print(board[i][j]);
				}
			}
			System.out.println("");
		}
	}

	private void initDLM(int rows, int cols, Map map) {
		char[][] board = map.getMap();
		boolean found;
		for (int i = 0; i < rows; i++) {
			found = false;
			// check from left
			for (int j = 0; j < cols; j++) {				
				if(i == 0 || i == rows-1) {
					dlm[i][j] = true;	
					player_dlm[i][j] = true;				
				}
				else {
					if (board[i][j] == '#') {
						found = true;
					}
					if (!found || board[i][j] == '#') {
						dlm[i][j] = true;
						player_dlm[i][j] = true;
					} else {
						dlm[i][j] = false;
						player_dlm[i][j] = false;
					}
				}				
			}
			found = false;
			// check from right
			for (int j = cols - 1; j >= 0; j--) {
				if (board[i][j] == '#') {
					found = true;
				}
				if (!found) {
					dlm[i][j] = true;
					player_dlm[i][j] = true;
				}
			}
		}
	}

	public static void main(String[] argv) {
		new DeadLock(argv[0]);
	}

	public static boolean getDL(Position pos) {
		return dlm[pos.getRow()][pos.getCol()];
	}

	public static boolean[][] getPlayerDLM() {
		return player_dlm;
	}

	public static boolean[][] getDLM() {
		return dlm;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}
}
