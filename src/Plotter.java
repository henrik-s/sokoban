import java.awt.*;

import javax.swing.*;


public class Plotter extends JPanel {
	private JPanel c;
	private JFrame frame;
	private MST m;
	private Tour t;
	private Integer[][] i;
	
	private Map map;
	private int[] tour;
	
	private double[][] verts;
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0; i<verts.length; i++) {
			g.fillOval((int) verts[i][0]-2, (int) verts[i][1]-2, 4, 4);
		}
		if (m != null) {
			plotMSTGraph(g); 
			return;
		}
		if (t != null){
			plotTourGraph(g);
			return;
		}
		if(i != null){
			plotCWGraph(g);
			return;
		}
	}
	
	private void plotCWGraph(Graphics g){
		for(int i = 0; i < this.i.length; i++){
			if(this.i[i][0] != null)
				g.drawLine((int) verts[i][0], (int) verts[i][1], (int) verts[this.i[i][0]][0], (int) verts[this.i[i][0]][1]);
			if(this.i[i][1] != null)
				g.drawLine((int) verts[i][0], (int) verts[i][1], (int) verts[this.i[i][1]][0], (int) verts[this.i[i][1]][1]);
		}
	}
	
	private void plotTourGraph(Graphics g) {
//		for(int i=0; i<t.size(); i++) {
//			g.drawLine((int) verts[t.getNext(t.get(i))][0], (int) verts[t.getNext(t.get(i))][1], (int) verts[this.t.get(i)][0], (int) verts[this.t.get(i)][1]);
//		}
		
		for(int i = 0; i < tour.length; i++){
			x1 = map.nodes[tour[i]].x;
			x2 = map.nodes[tour[i]].y;


			if(i == tour.length - 1) j = 0;
			else j = i+1;

			y1 = map.nodes[tour[j]].x;
			y2 = map.nodes[tour[j]].y;
			g2d.drawLine((int)x1*3, (int)x2*3, (int)y1*3, (int)y2);
		}
		
	}

	private void plotMSTGraph(Graphics g) {
		MST.LL tmp;
		for (int i=0; i<m.verts.length; i++) {
			tmp = m.verts[i];
			while (tmp != null) {
				g.drawLine((int) verts[i][0], (int) verts[i][1], (int) verts[tmp.neighbour][0], (int) verts[tmp.neighbour][1]);
				tmp = tmp.next;
			}
		}
	}

	public Plotter() {		
		frame = new JFrame();
		c = this; //new JPanel();
		c.setVisible(true);
		c.setBackground(Color.white);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void plotMST(MST m, double[][] verts) {
		this.m = m;
		this.verts = verts;
		frame.getContentPane().removeAll();
		frame.getContentPane().add(c).validate();
		
	}
//	public void plotTour(Tour t, double[][] verts) {
//		this.t = t;
//		this.verts = verts;
//		frame.getContentPane().removeAll();
//		frame.getContentPane().add(c).validate();
//	}
	
	public void plotTour(int[] tour, Map map) {
		this.tour = tour;
		this.map = map;
		frame.getContentPane().removeAll();
		frame.getContentPane().add(c).validate();
	}
	public void plotCW(Integer[][] i, double[][] verts ){
		this.i = i;
		this.verts = verts;
		frame.getContentPane().removeAll();
		frame.getContentPane().add(c).validate();
	}
}
