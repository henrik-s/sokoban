import static org.junit.Assert.*;

import org.junit.Test;


public class MapTest {
	
	static final int LEVELS = 1
			;
	
	@Test
	// This perfoms a test on Level 1 from /all
	public void init() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1);
		
		// Player's position
		Position player = map.getPlayerPosition();
		Position exp = new Position(map.getRows()-2, map.getCols()-5);
		assertTrue(player.isEqualTo(exp));
		
		// Number of boxes
		assertEquals(map.getAllBoxes().size(), 10);
		
		// The last box's position
		Position newPos = new Position(map.getRows()-3, map.getCols()-5);
		exp.set(newPos);
		
		Box box = map.getBox(9);
		assertTrue(box.getPosition().isEqualTo(exp));
		
		// The second last box's position, a * box
		newPos = new Position(map.getRows()-4, map.getCols()-4);
		exp.set(newPos);
		
		box = map.getBox(8);
		assertTrue(box.getPosition().isEqualTo(exp));
	}
	
	@Test
	public void generateMap() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1);
		System.out.println(map.print());
		
		// Create push on the last box (id 9) to right
		Box box = map.getBox(9);
		Move move = new Move(box, 0, 1);
		Map newMap = new Map(map, move);

		System.out.println(newMap.print());
	}

}
