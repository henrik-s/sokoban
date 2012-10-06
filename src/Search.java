
import java.util.LinkedList;
import java.util.Queue;

/**
 * Search returns a 
 * @author MacHenrik
 *


public class Search {

	public Search() {
		
	}
	
	public 
	   
    Queue<Position> q = new LinkedList<Position>();
    q.add(map.startPos);
    boolean found = false;
    Position currentPos;
    if(map.startPos.x == -1)
    	found = true;
    while(!found) {
    	currentPos = q.remove();
    	// try step up
    	int stepValue = map.step(currentPos.y - 1, currentPos.x, '0');
    	switch (stepValue) {
    	case 0:
    		break;
    	case 1:
    		q.add(new Position(currentPos.y - 1, currentPos.x));
    		break;
    	case 2:
    		found = true;
    		break;
    	}
    	// try step down
    	stepValue = map.step(currentPos.y + 1, currentPos.x, '1');
    	switch (stepValue) {
    	case 0:
    		break;
    	case 1:
    		q.add(new Position(currentPos.y + 1, currentPos.x));
    		break;
    	case 2:
    		found = true;
    		break;
    	}
    	// try step left
    	stepValue = map.step(currentPos.y, currentPos.x - 1, '2');
    	switch (stepValue) {
    	case 0:
    		break;
    	case 1:
    		q.add(new Position(currentPos.y, currentPos.x - 1));
    		break;
    	case 2:
    		found = true;
    		break;
    	}
    	// try step right
    	stepValue = map.step(currentPos.y, currentPos.x + 1, '3');
    	switch (stepValue) {
    	case 0:
    		break;
    	case 1:
    		q.add(new Position(currentPos.y, currentPos.x + 1));
    		break;
    	case 2:
    		found = true;
    		break;
    	}
    }
	
	
}*/
