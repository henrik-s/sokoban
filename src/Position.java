
public class Position {
		private int row, col;
		
			// Default constructor, col = row = -1
			public Position() {
				row = -1;
				col = -1;
			}
			
			public Position(Position pos) {
				this.row = pos.getRow();
				this.col = pos.getCol();
			}
			
			// Construct a new Position with coordinates
			public Position(int row, int col) {
				this.row = row;
				this.col = col;
			}
			// Return col
			public int getCol() {
				return col;
			}
			
			// Return row
			public int getRow() {
				return row;
			}
			
			// Set a position to a new position
			// using a postion object
			public void set(Position pos) {
				this.row = pos.row;
				this.col = pos.col;
			}
			
			// Set col
			public void setCol(int i) {
				col = i;
			}
			
			// Set row
			public void setRow(int i) {
				row = i;
			}
			
			// Compare this object with another position object
			// If same position, return true
			public boolean isEqualTo(Position pos) {
				if (pos.getCol() == col && pos.getRow() == row)
					return true;
			return false;
			}
			
			public String print() {
				return ("(" + this.row + "," + this.col + ")");
			}
}

