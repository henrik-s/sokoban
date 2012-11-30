
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TSP {

	static boolean LOCAL = false;
	Plot pl;

	TSP() {
		Map map;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 1; ++i) {
			map = new Map(in);
			solve(map);
		}
	}

	TSP(String fileName) {
		Map map;
		for (int i = 0; i < 1; ++i) {
			map = new Map(fileName);
			solve(map);
		}
	}

	public void solve(Map map) {
		int[] tour = greedyTour(map);
		
		double curr = 0;
		double prev =  Util.dist(tour, map);
		
		if (LOCAL)
			print_tour(tour, map);
		
		for(int i = 0; i < 	10; i++){
			tour = twoOpt(tour, map);
			curr = Util.dist(tour, map);
		}
//			
//			if(curr == prev && i < 8){
//				//System.out.println("RANDOM");
//				//Util.randomMove(tour);
//			}
//				
//			prev = curr;
////		}
//		for(int i = 0; i < 3; i++)
//			tour = threeOpt(tour, map);
//		
//		for(int i = 0; i < 	5; i++)
//			tour = twoOpt(tour, map);
		
		//FIXA LOOPEN!!!
		if (LOCAL)
			pl = new Plot(tour, map);
		print_tour(tour, map);
	}

	public int[] greedyTour(Map map) {
		int[] tour = new int[map.numNodes];
		boolean[] used = new boolean[map.numNodes];

		tour[0] = 0;
		used[0] = true;
		int best;
		for (int i = 1; i < map.numNodes; i++) {
			best = -1;
			for (int j = 0; j < map.numNodes; j++) {
				if (!used[j]) {
					if (best == -1) {
						best = j;
					} else if ((Util.dist(map.nodes[tour[i - 1]], map.nodes[j]) < Util
							.dist(map.nodes[tour[i - 1]], map.nodes[best]))) {
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
					if (LOCAL)
						print_tour(T, map);
				}
//				else if(x1 % 100 == 0){
//					Util.swap(x2,y1,T);
//				}
			}
		}
		return T;
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
		return Util.dist(T[x1], T[x2], map) + Util.dist(T[y1], T[y2], map);
	}

	public double dist(int x1, int x2, int y1, int y2, int z1, int z2, int[] T,
			Map map) {
		return Util.dist(T[x1], T[x2], map) + Util.dist(T[y1], T[y2], map)
				+ Util.dist(T[z1], T[z2], map);
	}

	public void print_tour(int[] tour, Map map) {
		// int distance = 0;
		if (LOCAL) {
			for (int i = 0; i < tour.length; ++i) {
				System.out.print(tour[i] + " ");
			}
			System.out.println();
			System.out.println("Distance: " + Util.dist(tour, map));
		} else {
			for (int i = 0; i < tour.length; ++i) {
				System.out.println(tour[i]);
			}
		}

	}

	public static void main(String argv[]) {
		if (LOCAL)
			new TSP("test");
		else
			new TSP();
	}

}
