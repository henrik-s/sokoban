import java.io.*;
import java.net.*;
import java.util.*;

/**
 * HW1 test command: 
   java Client dd2380.csc.kth.se 5001 2sdsd
 * 
 * @author MacHenrik
 *
 */

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
            int rows=Integer.parseInt(inputLine);
            
            //read first row, get number of columns and initialize a Map object
            inputLine=lIn.readLine();
            int cols = inputLine.length();
            Map map = new Map(rows, cols);            
            map.insertRow(inputLine, 0);
            
            //read each row and fill the board matrix in the map object
            for(int i=1;i<rows;i++)
            {
            	inputLine=lIn.readLine();
            	map.insertRow(inputLine, i);
            }
            
            String lMySol = "";
            
            System.out.println(map.print());
            
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
