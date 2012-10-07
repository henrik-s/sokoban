import java.util.ArrayList;


public class HashMapper {

	private int[][] playerBoard;
	private int[][] boxBoard;
	private int rows, cols;
	private static int possiblePlayerCells, possibleBoxCells;
	
	public HashMapper(DeadLock dl) {
		rows = dl.getRows(); cols = dl.getCols();
		playerBoard = new int[rows][cols];
		boxBoard = new int[rows][cols];
		initBoards(dl);
	}
	
	private int getBoxHash(int [] boxes, int firstBit, int lastBit) {
		
	}
	
	private int[] getBoxPositions(ArrayList<Box> boxes) {
		int []res = new int[boxes.size()];
		Position currentPos;
		for(int i = 0; i<boxes.size(); i++) {
			currentPos = boxes.get(i).getPosition();
			res[i] = boxBoard[currentPos.getRow()][currentPos.getCol()];
		}
		return res;
	}
	
	public void createEntry(Map map) {
		Position playerPos = map.getPlayerPosition();
		int playerHash = playerBoard[playerPos.getRow()][playerPos.getCol()];
		System.out.println(playerHash);
		
		int [] boxPositions = getBoxPositions(map.getAllBoxes());
		
		
		if(possibleBoxCells < 33) {
			int boxHash1 = getBoxHash(boxPositions, 0, 32);
		}
		
		// Need 2 hash int's
		else if(possibleBoxCells > 32 && possibleBoxCells < 65) {
			
		}		
		// Need 3 hash int's
		else if(possibleBoxCells > 64 && possibleBoxCells < 97) {
				
		}
		// Need 4 hash int's
		else if(possibleBoxCells > 96 && possibleBoxCells < 129) {
						
		}
		// Need 5 hash int's
		else if(possibleBoxCells > 128 && possibleBoxCells < 161) {
								
		}	
		else {
			throw new RuntimeException("Sorry but this map is huge as fuck");
		}
	}
	
	private void initBoards(DeadLock dl) {
		boolean [][] player_dlm = DeadLock.getPlayerDLM();
		boolean [][] dlm = DeadLock.getDLM();
		int playerCounter = 0; int boxCounter = 0;
		for (int i = 0; i<dl.getRows(); i++) {
			for (int j = 0; j<dl.getCols(); j++) {
				// PlayerBoard
				if(!player_dlm[i][j]) {
					playerBoard[i][j] = playerCounter;
					playerCounter++;
				}
				else {
					playerBoard[i][j] = -1;
				}
				// BoxBoard
				if(!dlm[i][j]) {
					boxBoard[i][j] = boxCounter;
					boxCounter++;
				}
				else {
					boxBoard[i][j] = -1;
				}
			}
			possiblePlayerCells = playerCounter;
			possibleBoxCells = boxCounter;
		}
	}
	
	public void print() {
		System.out.println("BoxBoard with: " + possibleBoxCells + " possible cells");
		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<cols; j++) {
				System.out.print(boxBoard[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("\nPlayerBoard: " + possiblePlayerCells + " possible cells");
		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<cols; j++) {
				System.out.print(playerBoard[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}
