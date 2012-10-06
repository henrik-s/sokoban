import static org.junit.Assert.*;

import org.junit.Test;


public class MapTest {
	
	static final int LEVELS = 5
			;
	
	@Test
	public void test() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(5);
		System.out.println(map.print());
	}

}
