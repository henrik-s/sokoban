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
		//Gå igenom varje position
		for(int row = 0; row < board.length; row++){
			if(DEBUG)
				System.out.println();
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col]){
					if(DEBUG){
						System.out.print(" D");
					}
					continue; //behöver inte sätta nått värde här (deadlock)
				}
				else if(origMap.getMap()[row][col] == '.' || origMap.getMap()[row][col] == '*'){
					if(DEBUG){
						System.out.print(" G");
					}
					distanceMap[row][col] = GOAL_VAL; //Hittat ett mål
				}
				else{ //Hittat en vanlig ruta
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
		for(int goal = 0; goal < goals.size(); goal++){ //Gå igenom avståndet till varje mål
			rowDist = Math.abs(goals.get(goal).getRow() - currPos.getRow()); 
			colDist = Math.abs(goals.get(goal).getCol() - currPos.getCol());
			distance = colDist + rowDist;
			if(distance < minDistance){
				minDistance = distance;
			}
		}
		if(DEBUG){
			System.out.print(" " + minDistance);
		}
		distanceMap[currPos.getRow()][currPos.getCol()] = minDistance; //Kommer va lägre värde ju närmare man är!
	}
}
