

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TSP {
	
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
		//print_tour(tour,map);
		
		tour = twoOpt(tour, map);
		//pl = new Plot(tour, map);
		print_tour(tour,map);
	}

	public int[] greedyTour(Map map) {
		int[] tour = new int[map.numNodes];
		boolean[] used = new boolean[map.numNodes];

//		System.out.println("numNodes: " + map.numNodes);
//		System.out.println("real numnodes: " + map.nodes.length);

		tour[0] = 0;
		used[0] = true;
		int best;
		for (int i = 1; i < map.numNodes; i++) {
			best = -1;
			for (int j = 0; j < map.numNodes; j++) {
				if (!used[j]) {
					if (best == -1) {
						best = j;
					} else if ((Util.dist(map.nodes[tour[i - 1]],
							map.nodes[j]) < Util.dist(
							map.nodes[tour[i - 1]], map.nodes[best]))) {
						best = j;
					}
				}
			}
			tour[i] = best;
			used[best] = true;
		}
		return tour;
	}
	
	public int[] twoOpt(int[] tour, Map map){
		int[] T = tour.clone();
		int x1,x2,y1,y2;
		for(x1 = 0;x1 < tour.length - 1; x1 ++){
			x2 = x1 + 1;
			for(y1 = x2; y1 < tour.length; y1++){
				if(y1 == (tour.length - 1))
					y2 = 0;
				else 
					y2 = y1 + 1;
				
				if((Util.dist(tour[x1], tour[x2], map) + Util.dist(tour[y1], tour[y2], map)) > 
					(Util.dist(tour[x1], tour[y1], map) + Util.dist(tour[x2], tour[y2], map))){
					int[] tmp = T.clone();
					Util.swap(x2,y1,T);
					if(Util.dist(T, map) > Util.dist(tmp, map))
						T = tmp;
					//print_tour(T, map);			
				}
			}
		}
		return T;
	}



	public void print_tour(int[] tour, Map map) {
//		int distance = 0;
		for (int i = 0; i < tour.length; ++i) {
			System.out.println(tour[i]);
//			if(i <= tour.length - 2){
//				System.out.println(Util.dist(map.nodes[tour[i]],map.nodes[tour[i+1]]));
				//distance += Util.dist(map.nodes[tour[i]],map.nodes[tour[i+1]]);
//			}
		}
		//System.out.println();
		//System.out.println("Distance: " + Util.dist(tour, map));
//		distance += Util.dist(map.nodes[tour[0]],map.nodes[tour[tour.length-1]]);
//		System.out.println("Distance: " + distance);
	}

	public static void main(String argv[]) {
		new TSP();
	}

}
