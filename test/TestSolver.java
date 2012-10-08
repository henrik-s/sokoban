import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestSolver {
	private Solver solver;
	
	public TestSolver(String fileName) {
		File file = new File(fileName);
		String row;
		Scanner s;
		Map map;
		int cols, rows, rowLength = 0;
		try {
			s = new Scanner(file).useDelimiter("\n");
			rows = Integer.parseInt(s.next());
			cols = Integer.parseInt(s.next());
			map = new Map(rows, cols);

			for (int i = 0; i < rows; i++) {
				row = s.next();
				System.out.println(row);
				rowLength = row.length();
				if (rowLength < cols) {
					for (int j = 0; j < (cols - rowLength); j++) {
						row += " ";
					}
				}
				map.insertRow(row, i);
			}
			solver = new Solver(map);
			System.out.println(solver.solve());
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
		
	}

	public static void main(String[] argv) {
		new TestSolver(argv[0]);
	}
}
