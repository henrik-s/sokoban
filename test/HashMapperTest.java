import static org.junit.Assert.*;

import org.junit.Test;


public class HashMapperTest {
	static final int LEVELS = 1
			;

	@Test
	public void test() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1); 
		System.out.println(map.print());
		DeadLock dl = new DeadLock(map);
		HashMapper hm = new HashMapper(dl);
		hm.createEntry(map);
		
		Move move = new Move(map.getBox(9), 0, 1);
		Map newMap = new Map(map, move);
		System.out.println(newMap.print());
		hm.createEntry(newMap);
	}

}
