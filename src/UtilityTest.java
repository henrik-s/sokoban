import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class UtilityTest {

	@Test
	public void test() {
		Map testMap = new Map(5,4);
		String row1 = "####";
		String row2 = "#  #";
		String row3 = "#@ #";
		String row4 = "#*.#";
		String row5 = "####";
		testMap.insertRow(row1, 0);
		testMap.insertRow(row2, 1);
		testMap.insertRow(row3, 2);
		testMap.insertRow(row4, 3);
		testMap.insertRow(row5, 4);
		assertEquals(testMap.getPlayerPosition().getRow(), 2);
		assertEquals(testMap.getPlayerPosition().getCol(), 1);
		Utility util = new Utility();
		ArrayList<Move> moves = util.findPossibleMoves(testMap);
		assertEquals(moves.size(), 0);
	}

}
