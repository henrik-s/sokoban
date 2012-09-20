import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Client {

	
	
	
	public static void main(String[] pArgs) 
	{
		if(pArgs.length<3)
		{
			System.out.println("usage: java Client host port boardnum");
			return;
		}
	
		try
		{
			Socket lSocket=new Socket(pArgs[0],Integer.parseInt(pArgs[1]));
			PrintWriter lOut=new PrintWriter(lSocket.getOutputStream());
			BufferedReader lIn=new BufferedReader(new InputStreamReader(lSocket.getInputStream()));
	
            lOut.println(pArgs[2]);
            lOut.flush();

            String inputLine=lIn.readLine();

            //read number of rows
            int height=Integer.parseInt(inputLine);
            
            //read first row and init a Map object
            inputLine=lIn.readLine();
            int width = inputLine.length();
            Map map = new Map(height, width);            
            map.insertRow(inputLine, 0, width);
            
            //read each row and fill the board matrix in the map object
            for(int i=1;i<height;i++)
            {
            	inputLine=lIn.readLine();
            	map.insertRow(inputLine, i, width);
            }
            
            //now, we should find a path from the player to any goal
            
            Queue<Position> q = new LinkedList<Position>();
            q.add(map.startPos);
            boolean found = false;
            Position currentPos;
            if(map.startPos.x == -1)
            	found = true;
            while(!found) {
            	currentPos = q.remove();
            	// try step up
            	int stepValue = map.step(currentPos.y - 1, currentPos.x, '0');
            	switch (stepValue) {
            	case 0:
            		break;
            	case 1:
            		q.add(new Position(currentPos.y - 1, currentPos.x));
            		break;
            	case 2:
            		found = true;
            		break;
            	}
            	// try step down
            	stepValue = map.step(currentPos.y + 1, currentPos.x, '1');
            	switch (stepValue) {
            	case 0:
            		break;
            	case 1:
            		q.add(new Position(currentPos.y + 1, currentPos.x));
            		break;
            	case 2:
            		found = true;
            		break;
            	}
            	// try step left
            	stepValue = map.step(currentPos.y, currentPos.x - 1, '2');
            	switch (stepValue) {
            	case 0:
            		break;
            	case 1:
            		q.add(new Position(currentPos.y, currentPos.x - 1));
            		break;
            	case 2:
            		found = true;
            		break;
            	}
            	// try step right
            	stepValue = map.step(currentPos.y, currentPos.x + 1, '3');
            	switch (stepValue) {
            	case 0:
            		break;
            	case 1:
            		q.add(new Position(currentPos.y, currentPos.x + 1));
            		break;
            	case 2:
            		found = true;
            		break;
            	}
            }
            
            
            
            String lMySol = "";
            if(map.startPos.x != -1)
            	 lMySol = map.getSolution();
            
            //we've found our solution
            //String lMySol="U R R U U L D L L U L L D R R R R L D D R U R U D L L U R";
            //these formats are also valid:
            //String lMySol="URRUULDLLULLDRRRRLDDRURUDLLUR";
            //String lMySol="0 3 3 0 0 2 1 2 2 0 2 2 1 3 3 3 3 2 1 1 3 0 3 0 1 2 2 0 3";

            //send the solution to the server
            lOut.println(lMySol);
            lOut.flush();
    
            //read answer from the server
            inputLine=lIn.readLine();
    
            System.out.println(inputLine);
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
}
