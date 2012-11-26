

import java.util.ArrayList;

public class Node {
	double x;
	double y;
	
	ArrayList<Edge> edges = new ArrayList<Edge>();
	
	Node(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void addNeighbour(Node x){
		edges.add(new Edge(this, x));
	}
}
