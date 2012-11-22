package turingMacineGuns;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
	int numNodes = 0;
	Node[] nodes;

	Map(BufferedReader in) {
		try {
			System.out.println(">");
			numNodes = Integer.parseInt(in.readLine());
			nodes = new Node[numNodes];
			
			String[] points;
			for(int i = 0; i< numNodes; ++i){
				System.out.println(">");
				points = in.readLine().split(" ");
				nodes[i] = new Node(Double.parseDouble(points[0]),Double.parseDouble(points[1]));
			}			
			
		} catch (NumberFormatException e) {
			System.out.println("Problem formatting string to number in constructor: Map");
		} catch (IOException e) {
			System.out.println("No input stream in constructor: Map");
		}
	}

}
