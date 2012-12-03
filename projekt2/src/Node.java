

import java.util.ArrayList;

public class Node {
	static int NUM_GRANNAR = 9;
	double x;
	double y;
	int num;
	int[] neighbours = new int[NUM_GRANNAR];
	
	Node(double x, double y,int num){
		this.x = x;
		this.y = y;
		this.num = num;
		for(int i = 0; i < NUM_GRANNAR; i++){
			neighbours[i] = -1;
		}
	}
	
	public void addClosestNeighbour(int node, double[][] dist_vec){
		int curr = node;
		int tmp = 0;
		
		for(int i = 0; i < neighbours.length; i++){
			
			if(neighbours[i] == -1){
				neighbours[i] = curr;
				break;
			}
			
			if(dist_vec[num][curr] < dist_vec[num][neighbours[i]]){
				tmp = neighbours[i];
				neighbours[i] = curr;
				curr = tmp;
			}
		}
	}
	
	int numNeighbours(){
		int size = 0;
		for(int i = 0; i < neighbours.length; i++){
			if( neighbours[i] > -1)
				size++;
		}
		return size;
	}
	public void printGrannar() {
		for(int i = 0; i< NUM_GRANNAR; i++) {
			System.out.print(neighbours[i] + " ");
		}
		System.out.println();
	}
}
