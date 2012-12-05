import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solver {
	Map startMap;
	DeadLock DL;
	HashMapper hm;
	Distances dist;

	public Solver(Map map) {
		startMap = map;
		System.out.println(map.print());
		DL = new DeadLock(map);
		dist = new Distances(map);
		hm = new HashMapper(DL);		
	}

	public String solve() {
		if(!hm.isGreen()) { // map is to big for hashmapper
			return "";
		}
		Map finalMap = BFS();
		String solution = backtrack(finalMap);
		return solution;
	}

	private String backtrack(Map finalMap) {
		Map tmp = finalMap;
		StringBuilder sb = new StringBuilder();
		sb.append("");
		Position origBoxPos;
		Position endPosBox;
		Position posBeforePush;
		String path;
		try {
			while (tmp.prevMap != null) { // Backtrack to start map
				tmp.prevMap.nextMap = tmp;
				tmp.prevMap.hasNextMap = true;
				tmp = tmp.prevMap;
			}
		} catch (NullPointerException e) {
			//Calm down
		}
		try {
			while (tmp.nextMap != null) {
				Move nextMove = tmp.nextMap.withMove;
				Position pp = tmp.getPlayerPosition();
				
				origBoxPos = tmp.getBox(nextMove.getID()).getPosition();
				endPosBox = nextMove.getPosition();
				int yDiff = origBoxPos.getRow() - endPosBox.getRow();
				int xDiff = origBoxPos.getCol() - endPosBox.getCol();
				posBeforePush = new Position(origBoxPos.getRow() + yDiff,
						origBoxPos.getCol() + xDiff);
				path = Utility.findPath(pp, posBeforePush, tmp.getMap());
				sb.append(path);
				if (xDiff != 0) {
					if (xDiff > 0) {
						sb.append("L");
					} else {
						sb.append("R");
					}
				} else {
					if (yDiff > 0) {
						sb.append("U");
					} else {
						sb.append("D");
					}
				}
				tmp = tmp.nextMap; // check next
			}
		} catch (NullPointerException e) {
			//e.printStackTrace(); No please
		}
		return sb.toString();
	}

	public Map BFS() {
		PriorityQueue<Map> prioQueue = new PriorityQueue<Map>();
		ArrayList<Move> moves = new ArrayList<Move>();
		startMap.evaluateMap();
		Map curr = null;
		prioQueue.add(startMap);
		while (!prioQueue.isEmpty()) {
			curr = prioQueue.remove();
			moves = curr.getMoves();
			for (Move m : moves) {
				Map nextMap = new Map(curr, m); 
				// Create a new map, the value is calculated 
				// by the constructor
				nextMap.evaluateMap();		
				if(nextMap.isWon()){
					return nextMap;
				}
				else if (nextMap.getMoves().size() != 0 && 
						hm.checkEntry(nextMap)) {
					prioQueue.add(nextMap);
				}		
			}
		}
		return null;
	}	
}
