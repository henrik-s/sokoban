
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class TSP {

	static boolean LOCAL = false;
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
		
		int[] tour = greedyTour(map);
		double best =  Util.tourDist(tour, map);
		double tmp = 0;
		
				
		boolean atLocalMin = false;
		int counter = 1;
		
		while(!atLocalMin) {			
			tour = twoOpt(tour, map);
			tmp = Util.tourDist(tour, map);
			if(best == tmp)
				atLocalMin = true;
			else
				best = tmp;
			if (LOCAL) {
				print_tour(tour, map);
				System.out.println("Counter = " + counter++);
			}
		}
		
		
		// Nuke da local mini dick		
		int[] current = tour.clone();
		
		if(tour.length == 1) {
			print_tour(tour, map);
			return;
		}
		
		atLocalMin = false;
		double currentRandom;
		tmp = 0;
		
		for(int j = 0; j < 100; j++){
			Util.randomMove(current);
			
			while(!atLocalMin) {
				current = twoOpt(current, map);
				currentRandom = Util.tourDist(current, map);
				if(currentRandom == tmp)
					atLocalMin = true;
				else
					tmp = currentRandom;
			}
						
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

	public static void main(String argv[]) {
		if (LOCAL)
			new TSP("test");
		else
			new TSP();
	}

}
