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
		
		// Box 9 should NOT be standing on goal
		box = map.getBox(9);
		assertFalse(box.isOnGoal());
		
		// Box 8 should be standing on goal
		box = map.getBox(8);
		assertTrue(box.isOnGoal());
	}
	
	@Test
	public void generateMap() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1);	
		
		//save player and box 9 pos on previous map
		Position box_prev = new Position(map.getBox(9).getPosition());
		Position player_prev1 = new Position(map.getPlayerPosition());
		
		// Create push on the last box (id 9) to right
		Move move = new Move(map.getBox(9), 0, 1);
		Map newMap = new Map(map, move);

		// Player's new position should be box(9) previous
		Position playerNewPos = newMap.getPlayerPosition();
		assertTrue(playerNewPos.isEqualTo(box_prev));
		
		// Old player have player_prev1 equal to its pos
		Position player_prev2 = map.getPlayerPosition();
		assertTrue(player_prev2.isEqualTo(player_prev1));		
		
		// Box 9 expected new position
		assertTrue(newMap.getBox(9).getPosition().isEqualTo(move.getPosition()));
		
		// And old box 9 should be unchanged
		Position prevBoxExp = new Position(map.getRows()-3, map.getCols()-5);
		assertTrue(map.getBox(9).getPosition().isEqualTo(prevBoxExp));
		
		// Old map and new map should then contain different info
		Box box1 = map.getBox(9);	Position pos1 = box1.getPosition();	
		Box box2 = newMap.getBox(9);Position pos2 = box2.getPosition();
		assertFalse(pos1.isEqualTo(pos2));
		
		Position player1 = map.getPlayerPosition();
		Position player2 = newMap.getPlayerPosition();
		assertFalse(player1.isEqualTo(player2));
		
		// Number of boxes of old and new map
		assertEquals(map.getAllBoxes().size(), 10);
		assertEquals(newMap.getAllBoxes().size(), 10);		
		
		// New map should refer to old map
		assertTrue(newMap.getPrevMap() == map);
		assertTrue(map.getPrevMap() == null);
	}
	
	@Test
	public void moves() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1);
		System.out.println("Map0\n" +map.print());
		
		// Create push on the last box (id 9) to right
		Move move = new Move(map.getBox(9), 0, 1);
		Map newMap1 = new Map(map, move);		
		System.out.println("Map1\n" +newMap1.print());
		
		// Now push goal box (id 7) up
		move = new Move(newMap1.getBox(7), -1, 0);
		Map newMap2 = new Map(newMap1, move);
		System.out.println("Map2\n" +newMap2.print());
		
		// Box 8 is standing on goal on map2
		assertTrue(newMap2.getBox(8).isOnGoal());
		
		// Now push goal box (id 8) right
		move = new Move(newMap2.getBox(8), 0, 1);
		Map newMap3 = new Map(newMap2, move);
		System.out.println("Map3\n" +newMap3.print());
		
		// Box 8 should still be standing on goal on map2
		// but not on map3
		assertTrue(newMap2.getBox(8).isOnGoal());
		assertFalse(newMap3.getBox(8).isOnGoal());
		
		// Now teleport and push goal box (id 5) down
		move = new Move(newMap3.getBox(5), 1, 0);
		Map newMap4 = new Map(newMap3, move);
		System.out.println("Map4\n" +newMap4.print());		
	}
	
	@Test
	public void win() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1);
		System.out.println("Map0\n" +map.print());
		
		// Let's cheat n win!
		map = new Map(map, new Move(map.getBox(7), -1, 0));		
		System.out.println("Map1\n" +map.print());
		
		// We havent won yet
		assertFalse(map.isWon());
		
		map = new Map(map, new Move(map.getBox(7), -1, 0));
		assertFalse(map.isWon());
		System.out.println("Map1\n" +map.print());
		
		map = new Map(map, new Move(map.getBox(7), 0, -1));
		assertFalse(map.isWon());
		System.out.println("Map1\n" +map.print());
		
		map = new Map(map, new Move(map.getBox(9), -1, 0));
		assertFalse(map.isWon());
		System.out.println("Map1\n" +map.print());
		
		map = new Map(map, new Move(map.getBox(3), 1, 0));
		assertFalse(map.isWon());
		System.out.println("Map1\n" +map.print());
		
		map = new Map(map, new Move(map.getBox(0), 0, 1));	
		assertFalse(map.isWon());
		System.out.println("Map1\n" +map.print());
		
		map = new Map(map, new Move(map.getBox(0), 1, 0));
		// We won
		assertTrue(map.isWon()); 
		System.out.println("Map1\n" +map.print());
	}

}
