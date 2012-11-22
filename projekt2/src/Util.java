

public class Util {
	
	public static double dist(Node a, Node b){
		double distance = Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
		return distance;
	}

}
