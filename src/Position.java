
public class Position {
		private int row, col;		
			public Position(int row, int col) {
				this.row = row;
				this.col = col;
			}				
			public int getCol() {
				return col;
			}
			public int getRow() {
				return row;
			}
			public void set(Position pos) {
				this.row = pos.row;
				this.col = pos.col;
			}
}

