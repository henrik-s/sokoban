import static org.junit.Assert.*;

import org.junit.Test;


public class SolverTest {

	public int LEVELS = 100;
	@Test
	public void test() {
		Reader reader = new Reader("test/all", LEVELS);
		Map map = reader.getLevel(15);
		String sol = new Solver(map).solve();
		
		System.out.println(sol);
		
	}

}
