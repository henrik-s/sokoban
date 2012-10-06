import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DeadLock {

	private static boolean DEBUG = true;
	private boolean[][] dlm;



	public DeadLock(String fileName){
		File file = new File(fileName);
		String row;
		Scanner s;
		Map map;
		int cols,rows,rowLength = 0;
		try {
			s = new Scanner(file).useDelimiter("\n");
			rows = Integer.parseInt(s.next());
			cols = Integer.parseInt(s.next());
			map = new Map(rows,cols);
			dlm = new boolean[rows][cols];
			initDLM(rows,cols);

			for(int i = 0; i<rows;i++){
				row = s.next();
				System.out.println(row);
				rowLength = row.length();
				if(rowLength < cols){
					for(int j = 0; j < (cols - rowLength);j++){
						row += " ";
					}
				}
				map.insertRow(row, i);
			}

			analyzeDefiniteDeadlocks(map);

			if(DEBUG)
				printDLM(map);

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

	}


	public DeadLock(Map map){
		if(map.board.length != 0){
			dlm = new boolean[map.board.length][map.board[0].length];
			initDLM(map.board.length,map.board[0].length);
		}
		else{
			throw new RuntimeException("DeadLock Constructor: The initializing" +
					" map is empty");
		}
		analyzeDefiniteDeadlocks(map);
		if(DEBUG)
			printDLM(map);
	}

	private void analyzeDefiniteDeadlocks(Map map) {
		int res = 0;
		for(int i = 1; i < map.board.length-1;i++){
			for(int j = 1; j < map.board[0].length-1;j++){
				if(map.board[i][j] == '#')
					continue;
				if((res = isCorner(map,i,j)) != 0){
					dlm[i][j] = true;
					checkDeadlockSides(map,i,j,res);
				}
			}
		}		
	}

	public boolean	analyzeDeadlocks(Map map){

		return false;
	}

	private void checkDeadlockSides(Map map,int i, int j, int res) {
		if(res == 1){
			checkSides(map,i,j,-1,1);
		}
		else if(res == 2){
			checkSides(map,i, j, 1, 1);
		}
		else if(res == 3){
			checkSides(map,i, j, 1, -1);
		}
		else if(res == 4){
			checkSides(map,i, j, -1, -1);
		}

	}

	private void checkSides(Map map,int row, int col, int rowDiff,int colDiff){
		List<Position> deadlocks = new ArrayList<Position>();
		List<Position> r = new ArrayList<Position>();
		List<Position> l = new ArrayList<Position>();
		List<Position> b = new ArrayList<Position>();
		List<Position> a = new ArrayList<Position>();

		//Search to the right of the given position
		for(int j = col+1; j < dlm[0].length-1; j++){
			if(map.board[row][j] == '#' ||map.board[row][j] == '.' || map.board[row][j] == '+' || map.board[row][j] == '*')
				break;
			if(isCorner(map,row,j) != 0){
				System.out.println("Adding to deadlocks r y:" + row + " x: " + j);
				deadlocks.addAll(r);
				break;
			}	
			else if(map.board[row+rowDiff][j]!='#'){
				break;
			}
			else{
				if(dlm[row][j]==false)
					r.add(new Position(row,j));
			}
		}

		//Search below of the given position
		for(int i = row+1; i<map.board.length-1; i++){
			if(map.board[i][col] == '#' || map.board[i][col] == '.' || map.board[i][col] == '+' || map.board[i][col] == '*')
				break;
			if(isCorner(map,i,col) != 0){
				System.out.println("Adding to deadlocks r y:" + i + " x: " + col);
				deadlocks.addAll(b);
				break;
			}	
			else if(map.board[i][col + colDiff]!='#'){
				break;
			}
			else{
				if(dlm[i][col]==false)
					b.add(new Position(i,col));
			}
		}

		Position tmp;
		for(int i = 0;i < deadlocks.size(); i++){
			tmp = deadlocks.get(i);
			dlm[tmp.y][tmp.x] = true;
		}
	}


	private int isCorner(Map map,int i, int j) {
		if(i == map.board.length || j == map.board[0].length)
			return 0;
		int up = ((map.board[i-1][j] == '#') ? 1:0);
		int right = ((map.board[i][j+1] == '#') ? 1:0);
		int down = ((map.board[i+1][j] == '#') ? 1:0);
		int left = ((map.board[i][j-1] == '#') ? 1:0);

		if(up+right == 2)
			return 1;
		else if(right+down == 2)
			return 2;
		else if(down+left == 2)
			return 3;
		else if(left + up == 2)
			return 4;
		else
			return 0;
	}

	private void printDLM(Map map){
		for(int i = 0; i < dlm.length;i++){
			for(int j = 0; j < dlm[0].length;j++){
				if(dlm[i][j] == true)
					System.out.print("D");
				else{
					System.out.print(map.board[i][j]);
				}
			}
			System.out.println("");
		}
	}
	private void initDLM(int rows, int cols){
		for(int i = 0; i< rows;i++){
			for(int j = 0; j <cols;j++){
				dlm[i][j] = false;
			}
		}
	}
	public static void main(String[] argv){
		new DeadLock(argv[0]);
	}
}