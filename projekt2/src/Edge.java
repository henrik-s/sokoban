

public class Edge {
	Node a;
	Node b;
	double distance;
	
	Edge(Node a, Node b){
		this.a = a;
		this.b = b;
		distance = Util.dist(a,b);
	}
}
