package turingMacineGuns;

public class Utility {
	
	public static double calculateDistance(Node a, Node b){
		double distance = Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
		return distance;
	}

}
