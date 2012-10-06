/**
 * Move holds info of
 * 		- the id of the box that shall be pushed
 * 		- a Position at where the box will be pushed
 * @author MacHenrik
 *
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
	
	// Return id
	public int getID() {
		return this.id;
	}
	
	// Return position
	public Position getPosition() {
		return this.newPos;
	}
}
