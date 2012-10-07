import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class UtilityTest {

	@Test
	public void test() {
		Map testMap1 = new Map(5,6);
		String row1 = "######";
		String row2 = "#    #";
		String row3 = "#@$$ #";
		String row4 = "#   .#";
		String row5 = "######";
		testMap1.insertRow(row1, 0);
		testMap1.insertRow(row2, 1);
		testMap1.insertRow(row3, 2);
		testMap1.insertRow(row4, 3);
		testMap1.insertRow(row5, 4);
		new DeadLock(testMap1);
		assertEquals(testMap1.getPlayerPosition().getRow(), 2);
		assertEquals(testMap1.getPlayerPosition().getCol(), 1);
		Utility util = new Utility();
		ArrayList<Move> moves = util.findPossibleMoves(testMap1);
		assertEquals(moves.size(), 2);
		assertEquals(moves.get(0).getPosition().getRow(), 3);
		assertEquals(moves.get(0).getPosition().getCol(), 2);
		assertEquals(moves.get(1).getPosition().getRow(), 3);
		assertEquals(moves.get(1).getPosition().getCol(), 3);
		
		Map testMap2 = new Map(8,8);
		String r1 = "#####   ";
		String r2 = "#@  ####";
		String r3 = "#    $ #";
		String r4 = "#      #";
		String r5 = "#    ###";
		String r6 = "#    ###";
		String r7 = "#  $ ..#";
		String r8 = "########";
		testMap2.insertRow(r1, 0);
		testMap2.insertRow(r2, 1);
		testMap2.insertRow(r3, 2);
		testMap2.insertRow(r4, 3);
		testMap2.insertRow(r5, 4);
		testMap2.insertRow(r6, 5);
		testMap2.insertRow(r7, 6);
		testMap2.insertRow(r8, 7);
		new DeadLock(testMap2);
		ArrayList<Move> moves2 = util.findPossibleMoves(testMap2);
		assertEquals(moves2.size(), 3);
	}

}
