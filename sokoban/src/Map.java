

public class Map {
	
	public Position startPos = new Position(-1, -1);
	public Position finalPos = new Position(-1, -1);
	public char[][] board;
	public char[][] visited;
	private int height, width;
	
	public Map(int height, int width) {
		board = new char[height][width];
		visited = new char[height][width];
		for(int e = 0; e < height; e++) {
			for(int r = 0; r < width; r++) {
				visited[e][r] = '7';
			}
		}
		this.height = height;
		this.width = width;
	}
	
	public void insertRow(String a, int y, int x) {
		char current;
		for(int i = 0; i < x; i++) {
			current = a.charAt(i);
			if(current == '@') {
				startPos.y = y;
				startPos.x = i;
				visited[y][i] = '4';
			}
			board[y][i] = current;
		}
	}
	
	public int step(int y, int x, char direction) {
		if (validStep(y,x)) {
			if(board[y][x] == '.') {
				visited[y][x] = direction;
				finalPos.y = y;
				finalPos.x = x;
				return 2;
			}
			else if (board[y][x] == ' ') {
				visited[y][x] = direction;
				return 1;
			}
		}
		return 0;
	}
	
	private boolean validStep(int y, int x) {
		if(x >= 0 && y >= 0 && x < width && y < height)
			if(visited[y][x] == '7')
				return true;
		return false;
	}
	
	public String getSolution() {
		StringBuilder res = new StringBuilder();
		char a = getNextChar();
		while (a != '4') {
			res.append(' ');
			res.append(a);			
			a = getNextChar();
		}
		res.reverse();
		return res.toString();
	}
	
	private char getNextChar() {
		char i = visited[finalPos.y][finalPos.x];
		switch(i) {
		case '0':
			finalPos.y++;
			break;
		case '1':
			finalPos.y--;
			break;
		case '2':
			finalPos.x++;
			break;
		case '3':
			finalPos.x--;
			break;
		case '4':
			break;
		default:
			i = '4';
			break;
		}
		return i;
	}
}
