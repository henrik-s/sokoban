import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Reader {
	File file;
	Map [] maps;
	
	public Reader(String fileName, int levels) {
		file = new File(fileName);
		maps = new Map[levels];
		String line;
		Scanner s1,s2;
		
		try {
			s1 = new Scanner(file).useDelimiter("\n");
			s2 = new Scanner(file).useDelimiter("\n");
			line = s1.nextLine(); // trim the first row 
			line = s2.nextLine();
			
			for (int i = 0; i< levels; i++) {
				int rows = 0;
				int cols = 0;
				boolean found = false;
				
				// find dimension of a level
				while (!found) {
					line = s1.nextLine();
					if(line.charAt(0) == ';') {
						found = true;
					}
					else {
						if(cols < line.length()) {
							cols = line.length();
						}
						rows++;
					}
				}				
				Map map = new Map(rows, cols);				
				// let second scanner create a the map
				for (int j = 0; j<rows; j++) {
					StringBuilder sb = new StringBuilder();
					line = s2.nextLine();
					if(line.length() < cols) {
						sb.append(line);
						while(sb.length() < cols) {
							sb.append(' ');
						}
						map.insertRow(sb.toString(), j);
					}
					else {
						map.insertRow(line, j);
					}
				}
				line = s2.nextLine(); // trim
				// insert into map array
				maps[i] = map;
				
			}
	
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	
	public Map getLevel(int i) {
		return maps[i-1];
	}
}
