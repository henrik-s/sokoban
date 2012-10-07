import static org.junit.Assert.*;

import org.junit.Test;


public class HashMapperTest {
	static final int LEVELS = 1
			;

	@Test
	public void test() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1); 
		DeadLock dl = new DeadLock(map);
		HashMapper hm = new HashMapper(dl);
		
		// Start map hash
		int originHash = hm.createEntry(map);
		System.out.println(map.print());
		
		// Move id 4 up and print hash
		Map map1 = new Map(map, new Move(map.getBox(4), -1, 0));
		hm.createEntry(map1);
		System.out.println(map1.print());
		
		// Move id 5 to where box 4 stood at
		Map map2 = new Map(map1, new Move(map1.getBox(5), 0, -1));
		Map map3 = new Map(map2, new Move(map2.getBox(5), 0, -1));
		hm.createEntry(map3);
		System.out.println(map3.print());
		
		// Move id 4 to where box 5 stood at
		Map map4 = new Map(map3, new Move(map3.getBox(4), 0, 1));
		Map map5 = new Map(map4, new Move(map4.getBox(4), 0, 1));
		Map map6 = new Map(map5, new Move(map5.getBox(4), 1, 0));
		int newHash = hm.createEntry(map6);	
		System.out.println(map6.print());
			
		assertEquals(newHash, originHash);
		
		
		
		
		
		
		
	}

}
