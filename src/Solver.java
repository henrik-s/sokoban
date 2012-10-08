import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {
	Map startMap;
	DeadLock DL;
	private static final int FINAL_SOLUTION = -1337;

	public Solver(Map map) {
		startMap = map;
		DL = new DeadLock(map);
	}

	public String solve() {
		System.out.println(startMap.print());
		Map finalMap = BFS();
		String solution = backtrack(finalMap);
		DL.printDLM(startMap);
		return solution;
	}

	private String backtrack(Map finalMap) {
		Map tmp = finalMap;
		StringBuilder sb = new StringBuilder();
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
			e.printStackTrace();
		}
		return sb.toString();
	}

	public Map BFS() {
		Queue<Map> queue = new LinkedList<Map>();
		ArrayList<Move> moves = new ArrayList<Move>();
		Map curr;
		queue.add(startMap);
		while (!queue.isEmpty()) {
			curr = queue.remove();				

			if (curr.isWon()) {
				System.out.println("Hittat en lösning");
				return curr;
			}

			moves = Utility.findPossibleMoves(curr);
			for (Move m : moves) {
				Map nextMap = new Map(curr, m);
				if (Utility.findPossibleMoves(nextMap).size() != 0) {
					queue.add(nextMap);
				}
				else if(nextMap.isWon()){
					return nextMap;
				}
			}
		}

		return null;
	}
	
	private Map doubleBFS(){
		Queue<Map> queue = new LinkedList<Map>();
		ArrayList<Move> moves = new ArrayList<Move>();
		Map curr;
		queue.add(startMap);
		while (!queue.isEmpty()) {
			curr = queue.remove();				

			if (curr.isWon()) {
				System.out.println("Hittat en lösning");
				return curr;
			}

			moves = Utility.findPossibleMoves(curr);
			for (Move m : moves) {
				Map nextMap = new Map(curr, m);
				if (Utility.findPossibleMoves(nextMap).size() != 0) {
					queue.add(nextMap);
				}
				else if(nextMap.isWon()){
					return nextMap;
				}
			}
		}
		
		return null;
	}
	private boolean allBoxesOnGoal(Map map) {
		ArrayList<Box> boxes = map.getAllBoxes();
		for(int i = 0; i < boxes.size(); i++){
			if(!boxes.get(i).isOnGoal()){
				return false;
			}
		}
		return true;
	}

	public void setVisited(Map map) {

	}

	public boolean visited(Map map) {
		return true;
	}

	public int Hash(Map map) {
		return 1;
	}
}
