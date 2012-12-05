import java.util.ArrayList;


public class Distances {
	
	private static int[][] distanceMap;
	private static final int GOAL_VAL = 0;
	private static final boolean DEBUG = true;
	
	public Distances(Map origMap){
		init(origMap);
	}
	
	public static int getDistance(Position pos){
		return distanceMap[pos.getRow()][pos.getCol()];
	}
	private void init(Map origMap){
		boolean[][] board = DeadLock.getDLM();
		distanceMap = new int[board.length][board[0].length];
		int maxVal = 0;
		//Check every position
		for(int row = 0; row < board.length; row++){
			if(DEBUG)
				System.out.println();
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col]){
					if(DEBUG){
						System.out.print(" D");
					}
					continue; // No need to set a value here (deadlock)
				}
				else if(origMap.getMap()[row][col] == '.' 
						|| origMap.getMap()[row][col] == '*'){
					if(DEBUG){
						System.out.print(" .");
					}
					distanceMap[row][col] = GOAL_VAL; //Found a goal
				}
				else{ //Found a regular cell
					calcDistance(new Position(row, col), origMap);
				}
			}
		}
	}

	private void calcDistance(Position currPos, Map map) {
		ArrayList<Position> goals = map.getGoals();
		int minDistance = Integer.MAX_VALUE;
		int distance = Integer.MIN_VALUE;
		int rowDist, colDist;
		for(int goal = 0; goal < goals.size(); goal++){ 
			//Check the distance to every goal
			rowDist = Math.abs(goals.get(goal).getRow() - currPos.getRow()); 
			colDist = Math.abs(goals.get(goal).getCol() - currPos.getCol());
			distance = colDist + rowDist;
			if(distance < minDistance){
				minDistance = distance;
			}
		}
		if(DEBUG){
			if(minDistance > 9){
				System.out.print(minDistance);
			}else 
				System.out.print(" " + minDistance);
		}
		// Will be a smaller value the closer you are
		distanceMap[currPos.getRow()][currPos.getCol()] = minDistance;
	}
}
