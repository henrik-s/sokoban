

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Map {
	int numNodes = 0;
	Node[] nodes;

	Map(BufferedReader in) {
		try {
			numNodes = Integer.parseInt(in.readLine());
			nodes = new Node[numNodes];
			
			String[] points;
			for(int i = 0; i< numNodes; ++i){
				points = in.readLine().split(" ");
				nodes[i] = new Node(Double.parseDouble(points[0]),Double.parseDouble(points[1]));
			}			
			
		} catch (NumberFormatException e) {
			System.out.println("Problem formatting string to number in constructor: Map");
		} catch (IOException e) {
			System.out.println("No input stream in constructor: Map");
		}
	}
	
	Map(String fileName) {
		try {
			FileReader FR = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(FR);
			
			numNodes = Integer.parseInt(br.readLine());
			nodes = new Node[numNodes];			
			String inLine = "";
			String[] points;
			
			for(int i = 0; i< numNodes; ++i){
				inLine = br.readLine();
				points = inLine.split(" ");
				nodes[i] = new Node(Double.parseDouble(points[0]),Double.parseDouble(points[1]));
			}			
			
		} catch (NumberFormatException e) {
			System.out.println("Problem formatting string to number in constructor: Map");
		} catch (IOException e) {
			System.out.println("No input stream in constructor: Map");
		}
	}

}
