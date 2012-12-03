import java.util.Random;




public class Util {
	
	
	public static double dist(int[] tour, Map map){
		double dist = 0;
		int x1,x2;
		for(x1 = 0; x1 < tour.length; x1++){
			if(x1 == tour.length - 1)
				x2 = 0;
			else
				x2 = x1 + 1;
			
			dist += dist(tour[x1],tour[x2],map);
		}
		return dist;
	}
	
	public static double dist(int x, int y, Map map){
		return dist(map.nodes[x],map.nodes[y]);
	}
	
	public static double dist(Node a, Node b){
		double distance = Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
		return distance;
	}
	
	public static void swap(int x2, int y1, int[] T) {
		int x = x2;
		int y = y1;
		int tmp = 0;
		while(x < y){
			tmp = T[y];
			T[y] = T[x];
			T[x] = tmp;
			x++;
			y--;
		}
	}
	
	public static void plot(int[] tour, Map map){
		
	}

	public static void randomMove(int[] tour) {
		Random rand = new Random();
		
		int x = rand.nextInt(tour.length-4);
		int y = x + 2;
		swap(x,y,tour);
	}
	
	
}
