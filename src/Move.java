/**
 * Move holds info of
 * 		- the id of the box that shall be pushed
 * 		- a Position at where the box will be pushed
 */

public class Move {
	private final int id;
	private final Position newPos;
	
	//	Constructor, create a move with id of a box and
	//	a position to where it will be pushed
	public Move(int id, Position newPos) {
		this.id = id;
		this.newPos = newPos;
	}
	
	// Construct a new move using a Box object 
	// and new changes to row and col value (-1, 0 or 1)
	public Move(Box box, int row, int col) {
		this.id = box.getID();
		Position pos = new Position();
		pos.setCol(box.getPosition().getCol() + col);
		pos.setRow(box.getPosition().getRow() + row);
		this.newPos = pos;
	}	
	
	// Return id
	public int getID() {
		return this.id;
	}
	
	// Return position
	public Position getPosition() {
		return this.newPos;
	}
}
