import java.util.List;

import javax.security.auth.login.CredentialException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class Util {
	
	
	public static double dist(int[] tour, Map map){
		double dist = 0;
		int x1,x2;
		for(x1 = 0; x1 < tour.length; x1++){
			if(x1 == tour.length - 1)
				x2 = 0;
			else
				x2 = x1 + 1;
			
			dist += dist(tour[x1],tour[x2],map);
		}
		return dist;
	}
	
	public static double dist(int x, int y, Map map){
		return dist(map.nodes[x],map.nodes[y]);
	}
	
	public static double dist(Node a, Node b){
		double distance = Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
		return distance;
	}
}
