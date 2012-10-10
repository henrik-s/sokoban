/**
 * A box in the game, holding information of 
 * 		- what box is supposed to be moved
 * 		- to what position the box will be pushed
 */

public class Box {
	private final int id;
	private Position currentPosition;
	private boolean onGoal;
	
	// Constructor, id of box, start position and boolean 
	public Box(int id, Position startPos, boolean onGoal) {
		this.id = id; 
		this.currentPosition = startPos;
		this.onGoal = onGoal;
	}
	
	// Construct new box from another box
	public Box(Box box) {
		this.id = box.getID();
		currentPosition = new Position(box.getPosition());
		onGoal = box.isOnGoal();
	}
	
	// Return the box's id
	public int getID() {
		return id;
	}
	
	// Return the box's currentPosition
	public Position getPosition() {
		return currentPosition;
	}
	
	// Set a new position
	public void setPosition(Position newPos) {
		currentPosition = newPos;
	}
	
	// Get boolean if the box is standing on a goal
	public boolean isOnGoal() {
		return onGoal;
	}
	
	// Set if the box is on a goal 
	public void setIsOnGoal(boolean bool) {
		this.onGoal = bool;
	}
	
}
