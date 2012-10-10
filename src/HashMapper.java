import java.util.ArrayList;
import java.util.HashMap;


public class HashMapper {

	private int[][] playerBoard;
	private int[][] boxBoard;
	private int rows, cols;
	private static int possiblePlayerCells, possibleBoxCells, 
	numberOfBoxHashes;
	private HashMap hm;
	private boolean isGreen;
	
	
	// Create a new hashMapper object
	// it requires a deadlock object for a specific
	// map as input
	public HashMapper(DeadLock dl) {
		rows = dl.getRows(); cols = dl.getCols();
		playerBoard = new int[rows][cols];
		boxBoard = new int[rows][cols];
		isGreen = true;
		init(dl);
	}


	/**
	 * Input is a map, output is true if the map and the move
	 * is new, else false. If the move is new it will be inserted
	 * into the hashmap hm 
	 * @param map
	 * @return true if new map, false if map seen before
	 */
	public boolean checkEntry(Map map) {
		Position playerPos = map.getPlayerPosition();
		
		// Get player's hashkey
		int playerHKey = playerBoard[playerPos.getRow()][playerPos.getCol()];

		int [] boxPositions = getBoxPositions(map.getAllBoxes());
		switch(numberOfBoxHashes) {
		case 1:
			int b_1_1 = getBoxHash(boxPositions, 0);
			return isNewEntry_1(playerHKey, b_1_1);
		case 2:
			int b_2_1 = getBoxHash(boxPositions, 0);
			int b_2_2 = getBoxHash(boxPositions, 32);
			return isNewEntry_2(playerHKey, b_2_1, b_2_2);
		case 3:
			int b_3_1 = getBoxHash(boxPositions, 0);
			int b_3_2 = getBoxHash(boxPositions, 32);
			int b_3_3 = getBoxHash(boxPositions, 64);
			return isNewEntry_3(playerHKey, b_3_1, b_3_2, b_3_3);
		case 4:
			int b_4_1 = getBoxHash(boxPositions, 0);
			int b_4_2 = getBoxHash(boxPositions, 32);
			int b_4_3 = getBoxHash(boxPositions, 64);
			int b_4_4 = getBoxHash(boxPositions, 96);
			return isNewEntry_4(playerHKey, b_4_1, b_4_2, b_4_3, b_4_4);
		}
		return false;
		
	}
	
	// Get the hashKey for boxes with positions
	// between [0 + offset, 31 + offset]
	private int getBoxHash(int [] boxes, int offset) {
		int res = 0;
		int mask = 1;
		for(int i = 0; i < boxes.length; i++) {
			mask = 1;
			if(boxes[i] - offset < 32) {
				 mask <<= (boxes[i] - offset);
				 res = res ^ mask;
			}
		}
		return res;
	}
	
	// Init with dl object to reduce the number
	// of cells the boxes may be located at
	private void init(DeadLock dl) {
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
			numberOfBoxHashes = getNumberOfHashes();
		}
		initHashMap();
	}
	
	// Get number of hashkeys needed for the boxes
	private int getNumberOfHashes() {
		int tot = 1;
		int tmp = possibleBoxCells;
		while (tmp > 32) {
			tot++;
			tmp -= 32;
		}
		return tot;
	}
	
	// Print a board's position values
	public void print() {
		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<cols; j++) {
				System.out.print(boxBoard[i][j] + " ");
			}
			System.out.println();
		}
		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<cols; j++) {
				System.out.print(playerBoard[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	// Get an array of all boxes position value
	private int[] getBoxPositions(ArrayList<Box> boxes) {
		int []res = new int[boxes.size()];
		Position currentPos;
		for(int i = 0; i<boxes.size(); i++) {
			currentPos = boxes.get(i).getPosition();
			res[i] = boxBoard[currentPos.getRow()][currentPos.getCol()];
		}
		return res;
	}

	private void initHashMap() {
		int i = numberOfBoxHashes + 1;
		switch(i) {
		case 1:
			throw new RuntimeException("Init hashmap, " +
					"seems there are no possibleBoxCells");
		case 2:
			hm =  new HashMap<Integer, HashMap<Integer, Object>>();
			break;
		case 3:
			hm =  new HashMap<Integer, HashMap<Integer, 
			HashMap<Integer, Object>>>();
			break;
		case 4:
			hm =  new HashMap<Integer, HashMap<Integer, 
			HashMap<Integer, HashMap<Integer, Object>>>>();
			break;
		case 5:
			hm =  new HashMap<Integer, HashMap<Integer, 
			HashMap<Integer, HashMap<Integer, HashMap<Integer, Object>>>>>();
			break;
		default:
			isGreen = false;
			break;			
		}
	}
	
	public boolean isGreen() {
		return isGreen;
	}
	
	// Check entry for one box key
	private boolean isNewEntry_1(int pKey, int boxKey) {
		HashMap<Integer, HashMap<Integer, Object>> tmpHM;
		tmpHM = hm;
		
		// hm does'nt contain playerkey
		if(!tmpHM.containsKey(pKey)) {
			tmpHM.put(pKey, new HashMap<Integer, Object>());
			tmpHM.get(pKey).put(boxKey, new Object());
			return true;
		}
		// hm contain playerkey but not boxkey
		else if(!tmpHM.get(pKey).containsKey(boxKey)) {
			tmpHM.get(pKey).put(boxKey, new Object());
			return true;
		}
		// hm contain this entry
		else {
			return false;
		}
	}
	
	// Check entry for two box keys
	private boolean isNewEntry_2(int pKey, int boxKey1, int boxKey2) {
		HashMap<Integer, HashMap<Integer, HashMap<Integer, Object>>> tmpHM;
		tmpHM = hm;
		
		// hm does'nt contain playerkey
		if(!tmpHM.containsKey(pKey)) {
			tmpHM.put(pKey, new HashMap<Integer, HashMap<Integer, Object>>());
			tmpHM.get(pKey).put(boxKey1, new HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).put(boxKey2,new Object());
			return true;
		}
		// hm contains playerkey but not boxkey1
		else if(!tmpHM.get(pKey).containsKey(boxKey1)) {
			tmpHM.get(pKey).put(boxKey1, new HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).put(boxKey2,new Object());
			return true;
		}
		// hm contains playerkey, boxkey1 but not boxkey 2
		else if(!tmpHM.get(pKey).get(boxKey1).containsKey(boxKey2)) {
			tmpHM.get(pKey).get(boxKey1).put(boxKey2,new Object());
			return true;
		}		
		// hm contain this entry
		else {
			return false;
		}
	}
	
	// Check entry for three box keys
	private boolean isNewEntry_3(int pKey, int boxKey1, 
			int boxKey2, int boxKey3) {
		HashMap<Integer, HashMap<Integer, HashMap<Integer, 
		HashMap<Integer, Object>>>> tmpHM;
		tmpHM = hm;
		
		// hm does'nt contain playerkey
		if(!tmpHM.containsKey(pKey)) {
			tmpHM.put(pKey, new HashMap<Integer, HashMap<Integer,
					HashMap<Integer, Object>>>());
			tmpHM.get(pKey).put(boxKey1, new HashMap<Integer, 
					HashMap<Integer, Object>>());
			tmpHM.get(pKey).get(boxKey1).put(boxKey2, new 
					HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			put(boxKey3, new Object());
			return true;
		}
		// hm contain playerkey but not boxkey1
		else if(!tmpHM.get(pKey).containsKey(boxKey1)) {
			tmpHM.get(pKey).put(boxKey1, new HashMap<Integer,
					HashMap<Integer, Object>>());
			tmpHM.get(pKey).get(boxKey1).put(boxKey2, new 
					HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			put(boxKey3, new Object());
			return true;
		}
		// hm contain playerkey, boxkey1 but not boxkey2
		else if(!tmpHM.get(pKey).get(boxKey1).containsKey(boxKey2)) {
			tmpHM.get(pKey).get(boxKey1).put(boxKey2, 
					new HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			put(boxKey3, new Object());
			return true;
		}
		// hm contains playerkey, boxkey1 and boxkey2 but not boxkey3
		else if(!tmpHM.get(pKey).get(boxKey1).get(boxKey2).
				containsKey(boxKey3)) {
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			put(boxKey3, new Object());
			return true;
		}
		// hm contain this entry
		else {
			return false;
		}
	}
	// Check entry for three box keys
	private boolean isNewEntry_4(int pKey, int boxKey1, int boxKey2, 
			int boxKey3, int boxKey4) {
		HashMap<Integer, HashMap<Integer, HashMap<Integer, 
		HashMap<Integer, HashMap<Integer, Object>>>>> tmpHM;
		tmpHM = hm;
		
		// hm does'nt contain playerkey
		if(!tmpHM.containsKey(pKey)) {
			tmpHM.put(pKey, new HashMap<Integer, HashMap<Integer, 
					HashMap<Integer, HashMap<Integer, Object>>>>());
			tmpHM.get(pKey).put(boxKey1, new HashMap<Integer, 
					HashMap<Integer, HashMap<Integer, Object>>>());
			tmpHM.get(pKey).get(boxKey1).put(boxKey2, new 
					HashMap<Integer, HashMap<Integer, Object>>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			put(boxKey3, new HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			get(boxKey3).put(boxKey4, new Object());
			return true;
		}
		// hm contain playerkey but not boxkey1
		else if(!tmpHM.get(pKey).containsKey(boxKey1)) {
			tmpHM.get(pKey).put(boxKey1, new HashMap<Integer, 
					HashMap<Integer, HashMap<Integer, Object>>>());
			tmpHM.get(pKey).get(boxKey1).put(boxKey2, new 
					HashMap<Integer, HashMap<Integer, Object>>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			put(boxKey3, new HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			get(boxKey3).put(boxKey4, new Object());
			return true;
		}
		// hm contain playerkey, boxkey1 but not boxkey2
		else if(!tmpHM.get(pKey).get(boxKey1).containsKey(boxKey2)) {
			tmpHM.get(pKey).get(boxKey1).put(boxKey2, new 
					HashMap<Integer, HashMap<Integer, Object>>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			put(boxKey3, new HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			get(boxKey3).put(boxKey4, new Object());
			return true;
		}
		// hm contains playerkey, boxkey1 and boxkey2 but not boxkey3
		else if(!tmpHM.get(pKey).get(boxKey1).get(boxKey2).containsKey(boxKey3)) {
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			put(boxKey3, new HashMap<Integer, Object>());
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			get(boxKey3).put(boxKey4, new Object());
			return true;
		}
		// hm contains playerkey, boxkey 1-3 but not 4
		else if(!tmpHM.get(pKey).get(boxKey1).get(boxKey2).
				get(boxKey3).containsKey(boxKey4)) {
			tmpHM.get(pKey).get(boxKey1).get(boxKey2).
			get(boxKey3).put(boxKey4, new Object());
			return true;
		}
		// hm contain this entry
		else {
			return false;
		}
	}
}
