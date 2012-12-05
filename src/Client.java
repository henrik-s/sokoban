import java.io.*;
import java.net.*;
import java.util.*;

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
			BufferedReader lIn=new BufferedReader(new InputStreamReader(
					lSocket.getInputStream()));
	
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
            Solver solver = new Solver(map);
            String lMySol = solver.solve();
            System.out.println(lMySol);

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
