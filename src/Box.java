/**
 * A box in the game, holding information of 
 * 		where the player is standing
 * 		what box is supposed to be moved
 * 		to what position the box will be pushed
 * @author MacHenrik
 *
 */

public class Box {
	private final int id;
	private Position currentPosition;
	
	// Constructor
	public Box(int id, Position currentPosition) {
		this.id = id; 
		this.currentPosition = currentPosition;
	}
	
	// Return the box's id
	public int getID() {
		return id;
	}
	
	//Return the box's currentPosition
	public Position getPosition() {
		return currentPosition;
	}
}
