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
		//System.out.println(dist.print());
		//DL.printDLM(startMap);
		hm = new HashMapper(DL);
		
	}

	public String solve() {
		if(!hm.isGreen()) { // kartan for stor for hashMapper
			return "";
		}
		Map finalMap = BFS();
		//System.out.println(finalMap.print());
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
			while (tmp.prevMap != null) { // Backtracka till originalplanen
				tmp.prevMap.nextMap = tmp;
				tmp.prevMap.hasNextMap = true;
				tmp = tmp.prevMap;
			}
		} catch (NullPointerException e) {
			//Lugna er, hittat första kartan bara..
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
				tmp = tmp.nextMap; // kolla nästa
			}
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
		return sb.toString();
	}

	public Map BFS() {
		//PriorityQueue<Map> prioQueue = new PriorityQueue<Map>();
		PriorityQueue<Map> prioQueue = new PriorityQueue<Map>();
		ArrayList<Move> moves = new ArrayList<Move>();

		Map curr = null;
		prioQueue.add(startMap);
		while (!prioQueue.isEmpty()) {
			curr = prioQueue.remove();
			System.out.println(curr.print());
			moves = curr.getMoves();
			for (Move m : moves) {
				Map nextMap = new Map(curr, m); //Skapa en ny karta, värdet av den beräknas via konstruktorn
				nextMap.evaluateMap();
				if(nextMap.isWon()){
					return nextMap;
				}
				else if (nextMap.getMoves().size() != 0 && hm.checkEntry(nextMap)) {
					prioQueue.add(nextMap);
				}
				
			}
		}

		return null;
	}
	
}
