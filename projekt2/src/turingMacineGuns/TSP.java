package turingMacineGuns;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TSP {

	TSP() {
		Map map;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 50; ++i) {
			map = new Map(in);
			solve(map);
		}
	}

	public void solve(Map map) {
		int[] tour = greedyTour(map);
		print_tour(tour);
	}

	public int[] greedyTour(Map map) {
		int[] tour = new int[map.numNodes];
		boolean[] used = new boolean[map.numNodes];
		// GreedyTour
		// tour[0] = 0
		// used[0] = true
		// for i = 1 to n-1
		// best = -1
		// for j = 0 to n-1
		// if not used[j] and (best = -1 or dist(tour[i-1],j) <
		// dist(tour[i-1],best))
		// best = j
		// tour[i] = best
		// used[best] = true
		// return tour

		tour[0] = 0;
		used[0] = true;
		int best;
		for (int i = 1; i < map.numNodes; ++i) {
			best = -1;
			for (int j = 0; i < map.numNodes; ++j) {
				if (!used[j]) {
					if (best == -1) {
						best = j;
					} else if ((Utility.calculateDistance(map.nodes[i - 1],
							map.nodes[j]) < Utility.calculateDistance(
							map.nodes[i - 1], map.nodes[best]))) {
						best = j;
					}
				}
				tour[i] = j;
				used[best] = true;
			}
		}
		return tour;
	}

	public void print_tour(int[] tour) {
		for (int i = 0; i < tour.length; ++i) {
			System.out.println(tour[i]);
		}
	}

	public static void main(String argv[]) {
		new TSP();
	}

}
