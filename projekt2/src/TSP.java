
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class TSP {

	static boolean LOCAL = false;
	static int NUMBER_OF_GREEDY = 1;
	Plot pl;

	TSP() {
		Map map;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		map = new Map(in);
		solve(map);
	}

	TSP(String fileName) {
		Map map;
		map = new Map(fileName);
		solve(map);
	}

	public void solve(Map map) {	
		if(map.numNodes == 1) {
			System.out.println("0");
			return;
		}
		int[][] listOfTours = getGreedyTours(NUMBER_OF_GREEDY, map);
		for(int n = 0; n < NUMBER_OF_GREEDY; n++) {
			//System.out.println("är det här jag ballar?");
			listOfTours[n] = findLocalMin(listOfTours[n], map);			
		}
		
		int[] tour = getBestTour(listOfTours, map);
		double best = Util.tourDist(tour, map);
		
		
		
		int[] current = tour.clone();
		double tmp;	
				
		for(int j = 0; j < 4; j++){
			Util.randomMove(current);
			//current = findLocalMin(current, map);
			current = twoOptWithNeighbourListButNotLinkedNeighbourList(current, map);
			tmp = Util.tourDist(current, map);
			if(tmp < best) {
				tour = current.clone();
				best = tmp;
			}
			else
				current = tour.clone();
		}

		if (LOCAL){
			//pl = new Plot(tour, map);
		}
		print_tour(tour, map);
	}
	
	public int[] getBestTour(int[][] listOfTours, Map map) {
		double best = Double.MAX_VALUE;
		int res = 0;
		for(int i = 0; i < NUMBER_OF_GREEDY; i++) {
			if(best > Util.tourDist(listOfTours[i], map))
				res = i; 
		}
		return listOfTours[res];
	}
	
	public int[] findLocalMin(int[] tour, Map map) {
		double best =  Util.tourDist(tour, map);
		double tmp = 0;
		
		while(true) {			
			//tour = twoOptWithNeighbourListButNotLinkedNeighbourList(tour, map);
			//System.out.println("är det här jag ballar?");
			tour = twoOpt(tour, map);
			tmp = Util.tourDist(tour, map);
			if(best == tmp) {
				if (LOCAL) {					
					//System.out.println("Found a local min = " + best);
				}			
				return tour;
			}
			else
				best = tmp;			
			
		}
	}
	
	public int[][] getGreedyTours(int n, Map map) {
		int[][] res = new int[n][map.numNodes];
		int[] newTour;
		for(int i = 0; i < n; i++) {
			newTour = greedyTour(map); 
			for(int j = 0; j < map.numNodes; j++) {
				res[i][j] = newTour[j];
			}
		}
		return res;
	}

	public int[] greedyTour(Map map) {
		int[] tour = new int[map.numNodes];
		boolean[] used = new boolean[map.numNodes];		
		Random rand = new Random();		
		int x = rand.nextInt(map.numNodes-1);

		tour[0] = x;
		used[x] = true;
		int best;
		for (int i = 1; i < map.numNodes; i++) {
			best = -1;
			for (int j = 0; j < map.numNodes; j++) {
				if (!used[j]) {
					if (best == -1) {
						best = j;
					} else if (map.dist_vec[tour[i - 1]][j] < map.dist_vec[tour[i - 1]][best]) {
						best = j;
					}
				}
			}
			tour[i] = best;
			used[best] = true;
		}
		return tour;
	}

	public int[] twoOpt(int[] tour, Map map) {
		int[] T = tour.clone();
		int x1, x2, y1, y2;
		for (x1 = 0; x1 < tour.length - 1; x1++) {
			x2 = x1 + 1;
			for (y1 = x2; y1 < tour.length; y1++) {
				if (y1 == (tour.length - 1))
					y2 = 0;
				else
					y2 = y1 + 1;
				if (dist(x1, x2, y1, y2, T, map) > (dist(x1, y1, x2, y2, T, map))) {
					Util.swap(x2, y1, T);
					//if (LOCAL)
						//print_tour(T, map);
				}
			}
		}
		return T;
	}
	
	public int[] twoOptWithNeighbourListButNotLinkedNeighbourList(int[] tour, Map map) {
		int[] T = tour.clone();
		Node tmp;		
		int x1, x2, y1, y2;
		for (x1 = 0; x1 < tour.length - 1; x1++) {
			tmp = map.nodes[T[x1]];
			//print_tour_l(T, map);
			//System.out.println("Node(x1): " + tmp.num + " Nod(x2): " + map.nodes[T[x1+1]].num);
			//tmp.printGrannar();
			x2 = x1 + 1;			
			for (int i = 0; i < tmp.neighbours.length; i++) {
				y1 = findNode(tmp.neighbours[i], T);
				if(y1 == T.length -1)
					y2 = 0;
				else 
					y2 = y1 + 1; 
				//y2 = findNextNode(tmp.neighbours[i], T);	
				//System.out.println("y1, y2 = "+ y1 + " "+ y2);
				if (dist(x1, x2, y1, y2, T, map) > (dist(x1, y1, x2, y2, T, map))) {
					Util.swap(x2, y1, T);
				}
			}
		}
		return T;
	}
	int findNode(int y1, int[] tour){
		int index = 0;
		for(int i = 0; i < tour.length;i++){
			if(tour[i] == y1)
				index = i;
		}
		return index;
	}
	
	int findNextNode(int y1, int[] tour){
		int index = 0;
		for(int i = 0; i < tour.length;i++){
			if(tour[i] == y1)
				index = i+1;
			if(i == tour.length - 1)
				index = 0;
		}
		return tour[index];
	}

	public int[] threeOpt(int[] tour, Map map) {
		int[] T = tour.clone();

		int x1, x2, y1, y2, z1, z2;
		for (x1 = 0; x1 < tour.length - 2; x1++) {
			x2 = x1 + 1;
			for (y1 = x2; y1 < tour.length - 1; y1++) {
				y2 = y1 + 1;
				for (z1 = y2; z1 < tour.length; z1++) {
					if (z1 == tour.length - 1)
						z2 = 0;
					else
						z2 = z1 + 1;

					if (dist(x1, x2, y1, y2, z1, z2, T, map) > dist(x1, z1, y2, x2, y1, z2, T, map)) {
						if (dist(x1, z1, y2, x2, y1, z2, T, map) > dist(x1, y2, z1, y1, x2, z2,  T, map)) {
							//do ver3
							Util.swap(x2, z1, T);
							Util.swap(y2, z1, T);
							if (LOCAL)
								print_tour(T, map);
						}
						//do ver2
						Util.swap(x2, z1, T);
						Util.swap(x2, y1, T);
						if (LOCAL)
							print_tour(T, map);
					} else if (dist(x1, x2, y1, y2, z1, z2, T, map) > dist(x1, y2, z1, y1, x2, z2, T, map)) {
						//do ver3
						Util.swap(x2, z1, T);
						Util.swap(y2, z1, T);
						if (LOCAL)
							print_tour(T, map);
					}
				}
			}
		}

		return T;
	}

	public double dist(int x1, int x2, int y1, int y2, int[] T, Map map) {
		return map.dist_vec[T[x1]][T[x2]] + map.dist_vec[T[y1]][T[y2]];
	}

	public double dist(int x1, int x2, int y1, int y2, int z1, int z2, int[] T,
			Map map) {
		return map.dist_vec[T[x1]][T[x2]] + map.dist_vec[T[y1]][T[y2]]	+ map.dist_vec[T[z1]][T[z2]];
	}

	public void print_tour(int[] tour, Map map) {
		if (LOCAL) {
			for (int i = 0; i < tour.length; ++i) {
				System.out.print(tour[i] + " ");
			}
			System.out.println();
			System.out.println("Distance: " + Util.tourDist(tour, map));
		} else {
			for (int i = 0; i < tour.length; ++i) {
				System.out.println(tour[i]);
			}
		}

	}
	public void print_tour_l(int[] tour, Map map) {
		System.out.print("Tour: [");
		for (int i = 0; i < tour.length; ++i) {
				System.out.print(tour[i] + " ");
			}
		System.out.println("]");
	}


	public static void main(String argv[]) {
		if (LOCAL)
			new TSP("test");
		else
			new TSP();
	}

}
