import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Utility {

	private static final int WEST = 0;
	private static final int EAST = 1;
	private static final int NORTH = 2;
	private static final int SOUTH = 3;

	public Utility() {

	}

	/**
	 * Find all possible directions from where the player is standing,
	 * to boxes that can be pushed
	 */
	public static ArrayList<Move> findPossibleMoves(Map map) {
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		Position playerPos = map.getPlayerPosition();
		ArrayList<Box> boxes = map.getAllBoxes(); 
		boolean[] directions = new boolean[4];
		for (int boxNr = 0; boxNr < boxes.size(); boxNr++) { 
			Box currBox = boxes.get(boxNr);
			directions = search(playerPos, currBox, map);
			for (int i = 0; i < 4; i++) {
				if (directions[i]) {
					if (i == WEST) { // Push the box east
						Position newBoxPos = new Position(currBox.getPosition()
								.getRow(), currBox.getPosition().getCol() + 1);
						if (legalPush(newBoxPos, map)) {
							Move move = new Move(boxNr, newBoxPos);
							possibleMoves.add(move);
						} else {
							continue;
						}
					} else if (i == EAST) { // Push the box west
						Position newBoxPos = new Position(currBox.getPosition()
								.getRow(), currBox.getPosition().getCol() - 1);
						if (legalPush(newBoxPos, map)) {
							Move move = new Move(boxNr, newBoxPos);
							possibleMoves.add(move);
						}
					} else if (i == NORTH) { // Push the box south
						Position newBoxPos = new Position(currBox.getPosition()
								.getRow() + 1, currBox.getPosition().getCol());
						if (legalPush(newBoxPos, map)) {
							Move move = new Move(boxNr, newBoxPos);
							possibleMoves.add(move);
						}
					} else if (i == SOUTH) { // Push the box north
						Position newBoxPos = new Position(currBox.getPosition()
								.getRow() - 1, currBox.getPosition().getCol());
						if (legalPush(newBoxPos, map)) {
							Move move = new Move(boxNr, newBoxPos);
							possibleMoves.add(move);
						}
					}
				}
			}
		}
		return possibleMoves;
	}

	private static boolean legalPush(Position newBoxPos, Map map) {
		boolean deadLock = DeadLock.getDL(newBoxPos);
		boolean obstacle = !isAvailiable(map.getMap(), newBoxPos);
		boolean boxDeadLock = createsDeadlock(map.getMap(), newBoxPos);
		if (!deadLock && !obstacle && !boxDeadLock) {
			return true;
		}
		return false;
	}
	
	private static boolean shareWall(char[][] map, Position 
			box1, Position box2) {
		int row1 = box1.getRow(); int row2 = box2.getRow();
		int col1 = box1.getCol(); int col2 = box2.getCol();
		if		(map[row1+1][col1] == '#' &&
				 map[row2+1][col2] == '#') {
			return true;
		}
		else if (map[row1-1][col1] == '#' &&
				 map[row2-1][col2] == '#') {
			return true;
		}
		else if (map[row1][col1-1] == '#' &&
				 map[row2][col2-1] == '#') {
			return true;
		}
		else if (map[row1][col1+1] == '#' &&
				 map[row2][col2+1] == '#') {
			return true;
		}
		return false;
			
	}
	
	private static boolean createsDeadlock(char[][] map, Position pos) {
		int row = pos.getRow(); int col = pos.getCol();
		Position tmp = new Position();
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		if(map[row][col] == ' ') {
			// Up
			if (map[row-1][col] == '$'||
					map[row-1][col] == '*') {
				tmp.setRow(row-1); tmp.setCol(col);
				up = shareWall(map, pos, tmp);
			}
			// Down
			if (map[row+1][col] == '$'||
					map[row+1][col] == '*') {
				tmp.setRow(row+1); tmp.setCol(col);
				down = shareWall(map, pos, tmp);
			}
			// Left
			if (map[row][col-1] == '$'||
					map[row][col-1] == '*') {
				tmp.setRow(row); tmp.setCol(col-1);	
				left = shareWall(map, pos, tmp);		
			}
			// Right
			if (map[row][col+1] == '$' ||
					map[row][col+1] == '*') {
				tmp.setRow(row); tmp.setCol(col+1);
				right = shareWall(map, pos, tmp);
			}
		}
		else if(map[row][col] == '.')  {
			// Up
			if (map[row-1][col] == '$') {
				tmp.setRow(row-1); tmp.setCol(col);
				up = shareWall(map, pos, tmp);
			}
			// Down
			if (map[row+1][col] == '$') {
				tmp.setRow(row+1); tmp.setCol(col);
				down = shareWall(map, pos, tmp);
			}
			// Left
			if (map[row][col-1] == '$') {
				tmp.setRow(row); tmp.setCol(col-1);	
				left = shareWall(map, pos, tmp);		
			}
			// Right
			if (map[row][col+1] == '$') {
				tmp.setRow(row); tmp.setCol(col+1);
				right = shareWall(map, pos, tmp);
			}
		}
		if(up || down || right || left) {
			return true;
		}		
		return false;
	}

	private static boolean[] search(Position playerPos, Box box, Map map) {
		char[][] board = map.getMap();
		// Directions[0-4] = west, east, north, south!
		boolean[] directions = new boolean[4];
		for (int i = 0; i < 4; i++) {
			directions[i] = false; 
		}
		Position boxPos = box.getPosition();
		Position west = new Position(boxPos.getRow(), boxPos.getCol() - 1);
		Position east = new Position(boxPos.getRow(), boxPos.getCol() + 1);
		Position north = new Position(boxPos.getRow() - 1, boxPos.getCol());
		Position south = new Position(boxPos.getRow() + 1, boxPos.getCol());
		boolean[][] visited = new boolean[map.getRows()][map.getCols()];
		// Initialize visited to false
		for (int row = 0; row < map.getRows(); row++) {
			for (int col = 0; col < map.getCols(); col++) {
				visited[row][col] = false;
			}
		}
		Queue<Position> q = new LinkedList<Position>();
		q.add(playerPos);
		visited[playerPos.getRow()][playerPos.getCol()] = true;
		while (!q.isEmpty()) {
			Position currPos = q.remove();
			// may reach left position of the box
			if (currPos.isEqualTo(west)) { 
				directions[0] = true;
			}
			// may reach right position of the box
			if (currPos.isEqualTo(east)) {
				directions[1] = true;
			}
			// may reach peter north position of the box
			if (currPos.isEqualTo(north)) {
				directions[2] = true;
			}
			// may reach south position of the box
			if (currPos.isEqualTo(south)) { 
				directions[3] = true;
			}
			for (int direction = 0; direction < 4; direction++) { 
				if (direction == WEST) {
					Position newPos = new Position(currPos.getRow(),
							currPos.getCol() - 1);
					if (!visited[newPos.getRow()][newPos.getCol()]
							&& isAvailiable(board, newPos)) {
						visited[newPos.getRow()][newPos.getCol()] = true;
						q.add(newPos);
					}
				}
				else if (direction == EAST) {
					Position newPos = new Position(currPos.getRow(),
							currPos.getCol() + 1);
					if (!visited[newPos.getRow()][newPos.getCol()]
							&& isAvailiable(board, newPos)) {
						visited[newPos.getRow()][newPos.getCol()] = true;
						q.add(newPos);
					}
				}
				else if (direction == NORTH) {
					Position newPos = new Position(currPos.getRow() - 1,
							currPos.getCol());
					if (!visited[newPos.getRow()][newPos.getCol()]
							&& isAvailiable(board, newPos)) {
						visited[newPos.getRow()][newPos.getCol()] = true;
						q.add(newPos);
					}
				}
				else 	if (direction == SOUTH) {
					Position newPos = new Position(currPos.getRow() + 1,
							currPos.getCol());
					if (!visited[newPos.getRow()][newPos.getCol()]
							&& isAvailiable(board, newPos)) {
						visited[currPos.getRow()][currPos.getCol()] = true;
						q.add(newPos);
					}
				}
			}
		}
		return directions;
	}

	public static boolean isAvailiable(char[][] map, Position pos) {
		if (map[pos.getRow()][pos.getCol()] == ' '
				|| map[pos.getRow()][pos.getCol()] == '.') {
			return true;
		}
		return false;
	}

	public static String findPath(Position startPos, 
			Position endPos, char[][] board) {
		String solution = null;
		int[][] distance = new int[board.length][board[0].length];
		for (int a = 0; a < board.length; a++) {
			for (int b = 1; b < board[0].length; b++) { 
				distance[a][b] = -1;
			}
		}
		solution = bfs(startPos, endPos, board, solution, distance);
		return solution;
	}

	public static String bfs(Position pos, Position endPos, 
			char[][] board, String sol, int[][] distance) {
		Queue<Position> q = new LinkedList<Position>();
		q.add(pos);
		distance[pos.getRow()][pos.getCol()] = 0;
		int currDistance = 0;
		while (!q.isEmpty()) {
			Position currPos = q.remove();
			if (currPos.isEqualTo(endPos)) { //Recreate the start string
				sol = printSolution(distance, currDistance, currPos);
				return sol;
			}
			
			for (int i = 0; i < 4; i++) {
				if (i == 0 && (distance[currPos.getRow() + 1]
						[currPos.getCol()] == -1)) { // Down
					Position newPos = new Position
							(currPos.getRow()+1, currPos.getCol());
					if (isAvailiable(board, newPos)) {
						q.add(new Position(newPos));
						distance[newPos.getRow()]
								[newPos.getCol()] = currDistance + 1;
					}
				}
				else if (i == 1 && distance[currPos.getRow() - 1]
						[currPos.getCol()] == -1) { // Up
					Position newPos = new Position
							(currPos.getRow()-1, currPos.getCol());
					if (isAvailiable(board, newPos)) {
						q.add(new Position(newPos));
						distance[newPos.getRow()]
								[newPos.getCol()] = currDistance + 1;
					}
				}
				else if (i == 2 && distance[currPos.getRow()]
						[currPos.getCol() + 1] == -1) { // Right
					Position newPos = new Position
							(currPos.getRow(), currPos.getCol()+1);
					if (isAvailiable(board, newPos)) {
						q.add(new Position(newPos));
						distance[newPos.getRow()]
								[newPos.getCol()] = currDistance + 1;
					}
				}
				else if (i == 3 && distance[currPos.getRow()]
						[currPos.getCol() - 1] == -1) { // Left
					Position newPos = new Position
							(currPos.getRow(), currPos.getCol()-1);
					if (isAvailiable(board, newPos)) {
						q.add(new Position(newPos));
						distance[newPos.getRow()]
								[newPos.getCol()] = currDistance + 1;
					}
				}
			}
			currDistance++;
		}
		System.out.println("HITTAR INGET SVAR");
		return null;
	}
	
	public static String printSolution(int[][] distance, 
			int currDistance, Position currPos){
		StringBuilder sb = new StringBuilder();
		for (int i = currDistance; i > 0; i--) { 
			if (distance[currPos.getRow() + 1]
					[currPos.getCol()] == i - 1) { // Down
				sb.append("U");
				currPos = new Position(currPos.getRow() 
						+ 1, currPos.getCol());
			} else if (distance[currPos.getRow() - 1]
					[currPos.getCol()] == i - 1) { // Up
				sb.append("D");
				currPos = new Position(currPos.getRow()
						- 1, currPos.getCol());
			} else if (distance[currPos.getRow()]
					[currPos.getCol() + 1] == i - 1) { // Right
				sb.append("L");
				currPos = new Position(currPos.getRow(), currPos.getCol()
						+ 1);
			} else if (distance[currPos.getRow()]
					[currPos.getCol() - 1] == i - 1) { // Left
				sb.append("R");
				currPos = new Position(currPos.getRow(), currPos.getCol()
						- 1);
			}
		}
		sb.reverse();
		String sol = sb.toString();
		return sol;
	}
}
