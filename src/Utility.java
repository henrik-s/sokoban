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
	 * Hittar alla sätt som man från utgångspositionen kan flytta på lådor.
	 * 
	 * @return En lista av möjliga lådförflyttningar (Moves)
	 */
	public static ArrayList<Move> findPossibleMoves(Map map) {
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		Position playerPos = map.getPlayerPosition();// Startpostionen för
														// spelaren
//		System.out.println("Startpos: (" + playerPos.getRow() + ","
//				+ playerPos.getCol() + ")");
		ArrayList<Box> boxes = map.getAllBoxes(); // Startpositionen och id för
													// alla lådor
		boolean[] directions = new boolean[4];
		for (int boxNr = 0; boxNr < boxes.size(); boxNr++) { // leta upp alla
																// möjliga vägar
																// till alla
																// lådor
			Box currBox = boxes.get(boxNr);
			directions = search(playerPos, currBox, map);
//			System.out
//					.println("Kommer ur search! Antal sätt att komma runt låda "
//							+ boxNr
//							+ " => Väster: "
//							+ directions[0]
//							+ ", Öster: "
//							+ directions[1]
//							+ ", Norr: "
//							+ directions[2] + ", Söder: " + directions[3]);
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
//		System.out.println("Antal möjliga pushes: " + possibleMoves.size());
//		System.out.println("----------------------");
//		for (int i = 0; i < possibleMoves.size(); i++) {
//			Move move = possibleMoves.get(i);
//			System.out.println("Låda nr: " + move.getID() + " till: " + "("
//					+ move.getPosition().getRow() + ","
//					+ move.getPosition().getCol() + ")");
//		}
		return possibleMoves;
	}

	private static boolean legalPush(Position newBoxPos, Map map) {
		boolean deadLock = DeadLock.getDL(newBoxPos);
		boolean obstacle = !isAvailiable(map.getMap(), newBoxPos);
		if (!deadLock && !obstacle) {
			return true;
		}
		return false;
	}

	private static boolean[] search(Position playerPos, Box box, Map map) {
		char[][] board = map.getMap();
		// Directions[0-4] = west, east, north, south!
		boolean[] directions = new boolean[4];
		for (int i = 0; i < 4; i++) {
			directions[i] = false; // initiera till false
		}
		Position boxPos = box.getPosition();
		// System.out.println("Lådpos: (" + boxPos.getRow() + "," +
		// boxPos.getCol() + ")");
		// directions of the box
		Position west = new Position(boxPos.getRow(), boxPos.getCol() - 1);
		Position east = new Position(boxPos.getRow(), boxPos.getCol() + 1);
		Position north = new Position(boxPos.getRow() - 1, boxPos.getCol());
		Position south = new Position(boxPos.getRow() + 1, boxPos.getCol());
		boolean[][] visited = new boolean[map.getRows()][map.getCols()];
		// initiera visited till false.
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
			if (currPos.isEqualTo(west)) { // kan komma till vänster om lådan
				directions[0] = true;
			}
			if (currPos.isEqualTo(east)) { // kan komma till höger om lådan
				directions[1] = true;
			}
			if (currPos.isEqualTo(north)) { // kan komma till positionen över
											// lådan
				directions[2] = true;
			}
			if (currPos.isEqualTo(south)) { // kan komma till positionen under
											// lådan
				directions[3] = true;
			}
			for (int direction = 0; direction < 4; direction++) { // kolla
																	// grannar
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

	public static String findPath(Position startPos, Position endPos, char[][] board) {
		String solution = null;
		int[][] distance = new int[board.length][board[0].length];
		for (int a = 0; a < board.length; a++) {
			for (int b = 1; b < board[0].length; b++) { // initierar till -1
				distance[a][b] = -1;
			}
		}
		solution = bfs(startPos, endPos, board, solution, distance);
		return solution;
	}

	public static String bfs(Position pos, Position endPos, char[][] board, String sol, int[][] distance) {
		Queue<Position> q = new LinkedList<Position>();
		q.add(pos);
		distance[pos.getRow()][pos.getCol()] = 0;
		int currDistance = 0;
		while (!q.isEmpty()) {
			Position currPos = q.remove();
			if (currPos.isEqualTo(endPos)) { //Återskapa svars-strängen
				sol = printSolution(distance, currDistance, currPos);
				return sol;
			}
			
			for (int i = 0; i < 4; i++) {
				if (i == 0 && (distance[currPos.getRow() + 1][currPos.getCol()] == -1)) { // NED!
					Position newPos = new Position(currPos.getRow()+1, currPos.getCol());
					if (isAvailiable(board, newPos)) {
						q.add(new Position(newPos));
						distance[newPos.getRow()][newPos.getCol()] = currDistance + 1;
					}
				}
				else if (i == 1 && distance[currPos.getRow() - 1][currPos.getCol()] == -1) { // UPP!
					Position newPos = new Position(currPos.getRow()-1, currPos.getCol());
					if (isAvailiable(board, newPos)) {
						q.add(new Position(newPos));
						distance[newPos.getRow()][newPos.getCol()] = currDistance + 1;
					}
				}
				else if (i == 2 && distance[currPos.getRow()][currPos.getCol() + 1] == -1) { // HÖGER!
					Position newPos = new Position(currPos.getRow(), currPos.getCol()+1);
					if (isAvailiable(board, newPos)) {
						q.add(new Position(newPos));
						distance[newPos.getRow()][newPos.getCol()] = currDistance + 1;
					}
				}
				else if (i == 3 && distance[currPos.getRow()][currPos.getCol() - 1] == -1) { // VÄNSTER!
					Position newPos = new Position(currPos.getRow(), currPos.getCol()-1);
					if (isAvailiable(board, newPos)) {
						q.add(new Position(newPos));
						distance[newPos.getRow()][newPos.getCol()] = currDistance + 1;
					}
				}
			}
			currDistance++;
		}
		System.out.println("HITTAR INGET SVAR");
		return null;
	}
	
	public static String printSolution(int[][] distance, int currDistance, Position currPos){
		StringBuilder sb = new StringBuilder();
		for (int i = currDistance; i > 0; i--) { 
			if (distance[currPos.getRow() + 1][currPos.getCol()] == i - 1) { // NER
				sb.append("U");
				currPos = new Position(currPos.getRow() + 1, currPos.getCol());
			} else if (distance[currPos.getRow() - 1][currPos.getCol()] == i - 1) { // UPP
				sb.append("D");
				currPos = new Position(currPos.getRow() - 1, currPos.getCol());
			} else if (distance[currPos.getRow()][currPos.getCol() + 1] == i - 1) { // HÖGER
				sb.append("L");
				currPos = new Position(currPos.getRow(), currPos.getCol() + 1);
			} else if (distance[currPos.getRow()][currPos.getCol() - 1] == i - 1) { // VÄNSTER
				sb.append("R");
				currPos = new Position(currPos.getRow(), currPos.getCol() - 1);
			}

		}
		sb.reverse();
		String sol = sb.toString();
		return sol;
	}

}
