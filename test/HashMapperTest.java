import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;


public class HashMapperTest {
	static final int LEVELS = 100
			;

	@Test
	public void test_2Keys() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1); 
		System.out.println(map.print());
		DeadLock dl = new DeadLock(map);
		HashMapper hm = new HashMapper(dl);
		boolean checkEntry;
		
		// Init board
		checkEntry = hm.checkEntry(map);
		assertTrue(checkEntry);
		
		// Move id 4 up
		Map map1 = new Map(map, new Move(map.getBox(4), -1, 0));
		checkEntry = hm.checkEntry(map1);
		assertTrue(checkEntry);
		
		// Move id 4 down
		Map map2 = new Map(map1, new Move(map1.getBox(4), 1, 0));
		checkEntry = hm.checkEntry(map2);
		assertTrue(checkEntry);
		
		// Move id 4 up - this move has been done before
		Map map3 = new Map(map2, new Move(map2.getBox(4), -1, 0));
		checkEntry = hm.checkEntry(map3);
		assertFalse(checkEntry);
		
		// Move id 4 down - same situation here
		Map map4 = new Map(map3, new Move(map3.getBox(4), 1, 0));
		checkEntry = hm.checkEntry(map4);
		assertFalse(checkEntry);
		
		// Move id 9 right = new Move
		Map map5 = new Map(map4, new Move(map4.getBox(9), 0, 1));
		checkEntry = hm.checkEntry(map5);
		assertTrue(checkEntry);
	}
	
	@Test
	public void test_3Keys() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(3);

		System.out.println(map.print());
		DeadLock dl = new DeadLock(map);
		HashMapper hm = new HashMapper(dl);
		boolean checkEntry;
		
		// Init board - new move
		checkEntry = hm.checkEntry(map);
		assertTrue(checkEntry);
		
		// Move id 3 left - new move
		Map map1 = new Map(map, new Move(map.getBox(3), 0, -1));
		checkEntry = hm.checkEntry(map1);
		assertTrue(checkEntry);
		
		// Move id 3 right - new move
		// same boad as init but different player pos
		Map map2 = new Map(map1, new Move(map1.getBox(3), 0, 1));
		checkEntry = hm.checkEntry(map2);
		assertTrue(checkEntry);
		
		// Move id 3 left again, this move should be false
		Map map3 = new Map(map2, new Move(map2.getBox(3), 0, -1));
		checkEntry = hm.checkEntry(map3);
		assertFalse(checkEntry);
		
		
	}
		

}
