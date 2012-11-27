import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Plot extends JFrame {

	class DrawPanel extends JPanel {
		int[] tour;
		Map map;

		public DrawPanel(int[] tour, Map map){
			this.tour = tour;
			this.map = map;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(Color.blue);

			double x1,x2,y1,y2;
			int j;
			for(int i = 0; i < tour.length; i++){
				x1 = map.nodes[tour[i]].x;
				x2 = map.nodes[tour[i]].y;


				if(i == tour.length - 1) j = 0;
				else j = i+1;

				y1 = map.nodes[tour[j]].x;
				y2 = map.nodes[tour[j]].y;
				g2d.drawLine((int)x1*3, (int)x2*3, (int)y1*3, (int)y2*3);
			}

		}
	}

	public Plot(int[] tour, Map map) {
		initUI(tour,map);
		setVisible(true);
	}
	
	public void plot(int[] tour, Map map){
		DrawPanel dpnl = new DrawPanel(tour, map);
		add(dpnl);
		paint(this.getGraphics());
	}
	
	public final void initUI(int[] tour, Map map) {
		DrawPanel dpnl = new DrawPanel(tour, map);
		add(dpnl);

		setSize(600,600);
		setTitle("Points");
		setLocationRelativeTo(null);
	//	setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
