import static org.junit.Assert.*;

import org.junit.Test;


public class DeadLockTest {
	static final int LEVELS = 1
			;
	@Test
	public void test() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(1);
		DeadLock dl = new DeadLock(map);
		System.out.println();
		dl.printPlayerDLM(map);
		System.out.println(map.print());
	}

}
