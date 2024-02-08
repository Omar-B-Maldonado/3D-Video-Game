package graphics3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Game;

/* An Artist can draw points, lines, and wireframe triangles,
 * filled triangles, and shaded triangles */

public class Artist extends JPanel
{
	Viewport vp = new Viewport(0, 0,
            Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT,
            (float)Math.toRadians(90));
	
	public static void main(String[] args) 
	{ 
		new Artist(); 
	}
	
	JFrame frame;
	
	public Artist()
	{
		frame = new JFrame("DrawLineTest");
		frame.setSize(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		this.setSize(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		
		frame.add(this);
		this.setVisible(true);
		frame.setVisible(true);
	}	
	
	public void paintComponent(Graphics pen)
	{
		//defining 2 points
		Point P0 = new Point(750, 100), 
			  P1 = new Point(860, 840);
		
		//drawing a line between those 2 points
		drawLine(P0, P1, Color.BLACK, pen);
		
		//defining 3 points with varying color intensity values
		Point A = new Point( 40, 850, 0.0),
			  B = new Point(500,  45, 0.7),
			  C = new Point(700, 325, 1.0);
		
		//defining 3 points
		Point F = new Point(700, 400),
			  G = new Point(860, 300),
			  H = new Point(760, 500);
		
		//drawing a filled triangle and its wire-frame 
		drawFilledTriangle(F, G, H, Color.ORANGE, pen);
		drawWireframeTriangle(F,G,H, Color.BLACK, pen);
		
		//drawing a shaded triangle and its wire-frame
		drawShadedTriangle(A,B,C, Color.ORANGE, pen);
		drawWireframeTriangle(A,B,C, Color.BLACK, pen);
		
		
		//-----------RUDIMENTARY RENDERING OF A CUBE IN 3D-------------------------
		
		//4 vertices to describe the front face
		Vertex vAf = new Vertex(-2, -0.5, 5),
			   vBf = new Vertex(-2,  0.5, 5),
			   vCf = new Vertex(-1,  0.5, 5),
			   vDf = new Vertex(-1, -0.5, 5);
		//4 vertices to describe the back face
		Vertex vAb = new Vertex(-2, -0.5, 6),
			   vBb = new Vertex(-2,  0.5, 6),
			   vCb = new Vertex(-1,  0.5, 6),
			   vDb = new Vertex(-1, -0.5, 6);
		
		//drawing lines between the appropriate front-face vertices
		drawLine(vp.projectVertex(vAf), vp.projectVertex(vBf), Color.BLUE, pen);
		drawLine(vp.projectVertex(vBf), vp.projectVertex(vCf), Color.BLUE, pen);
		drawLine(vp.projectVertex(vCf), vp.projectVertex(vDf), Color.BLUE, pen);
		drawLine(vp.projectVertex(vDf), vp.projectVertex(vAf), Color.BLUE, pen);

		//drawing lines between the appropriate back-face vertices
		drawLine(vp.projectVertex(vAb), vp.projectVertex(vBb), Color.RED, pen);
		drawLine(vp.projectVertex(vBb), vp.projectVertex(vCb), Color.RED, pen);
		drawLine(vp.projectVertex(vCb), vp.projectVertex(vDb), Color.RED, pen);
		drawLine(vp.projectVertex(vDb), vp.projectVertex(vAb), Color.RED, pen);

		//drawing lines to connect the front and back vertices
		drawLine(vp.projectVertex(vAf), vp.projectVertex(vAb), Color.GREEN, pen);
		drawLine(vp.projectVertex(vBf), vp.projectVertex(vBb), Color.GREEN, pen);
		drawLine(vp.projectVertex(vCf), vp.projectVertex(vCb), Color.GREEN, pen);
		drawLine(vp.projectVertex(vDf), vp.projectVertex(vDb), Color.GREEN, pen);
		
		//-----------------------------------------------------------------------------
		
	}
	
	//sadly, there is no default method in Java to draw a point
	public void drawPoint(Point P, Color c, Graphics pen)
	{
		pen.setColor(c);
		pen.drawLine(P.x, P.y, P.x, P.y);
	}
	
	public void swapPoints(Point A, Point B)
	{
		Point temp = new Point(A);
		
		//swap all attributes between point A and B
		
		A.x = B.x;
		A.y = B.y;
		A.h = B.h;
		
		B.x = temp.x;
		B.y = temp.y;
		B.h = temp.h;
	}
	
	public void drawLine(Point A, Point B, Color color, Graphics pen)
	{
		Point P0 = new Point(A), P1 = new Point(B);
		
		int dx = Math.abs(P1.x - P0.x);
		int dy = Math.abs(P1.y - P0.y);
		
		if (dx > dy) //more horizontally-oriented
		{	
			//make sure x0 < x1
			if (P0.x > P1.x) swapPoints(P0, P1);
			
			int    x0 = P0.x, x1 = P1.x;
			double y0 = P0.y, y1 = P1.y;
	
			double m = (y1 - y0) / (x1 - x0);
			
			double y = y0;
			for (int x = x0; x <= x1; x++)
			{			
				Point P = new Point(x, y);	//automatic widening conversion on x from int to double!
				drawPoint(P, color, pen);
				y += m;
			}
		}
		else //more vertically-oriented
		{	
			//make sure y0 < y1
			if (P0.y > P1.y) swapPoints(P0, P1);
			
			double x0 = P0.x, x1 = P1.x;
			int    y0 = P0.y, y1 = P1.y;
			
			double m = (x1 - x0) / (y1 - y0);
			
			double x = x0;	
			for (int y = y0; y <= y1; y++)
			{		
				Point P = new Point(x, y);	//automatic widening conversion on y from int to double!
				drawPoint(P, color, pen);
				x += m;
			}
		}
	}
	
	//draws an outline of a triangle
	public void drawWireframeTriangle(Point A, Point B, Point C,
									  Color color, Graphics pen)
	{
		drawLine(A, B, color, pen);
		drawLine(B, C, color, pen);
		drawLine(A, C, color, pen);
	}
	
	/* here: d = f(i) generalizes y = f(x) and x = f(y)
	 *  
	 *  independent variables i are always integers (they represent pixels)
	 *  dependent variables d are always floating point (they represent values of the linear function)
	 */
	public ArrayList<Double> interpolate(int i0, double d0, int i1, double d1)
	{
		//handles edge case
		if (i0 == i1) return new ArrayList<>(Arrays.asList(d0));
		
		ArrayList<Double> values = new ArrayList<Double>();
		double m = (d1 - d0) / (i1 - i0);
		double d = d0;
		
		for (int i = i0; i <= i1; i++)
		{
			values.add(d);
			d += m;
		}
		return values;
	}
	
	public void drawFilledTriangle(Point A, Point B, Point C,
		  	   Color color, Graphics pen)
	{
		Point P0 = new Point(A), P1 = new Point(B), P2 = new Point(C);
		
		//1) Ensure points are ordered such that (y0 <= y1 <= y2)
		if (P1.y < P0.y) swapPoints(P1, P0);
		if (P2.y < P0.y) swapPoints(P2, P0);
		if (P2.y < P1.y) swapPoints(P2, P1);
		
		int x0 = P0.x, y0 = P0.y,
		x1 = P1.x, y1 = P1.y,
		x2 = P2.x, y2 = P2.y;
		
		//2) Compute the x coordinates of the triangle's sides
		ArrayList<Double> x0_1 = interpolate(y0, x0, y1, x1); //short side
		ArrayList<Double> x1_2 = interpolate(y1, x1, y2, x2); //short side
		ArrayList<Double> x0_2 = interpolate(y0, x0, y2, x2); //tall  side (connects lowest and highest point)
		
		//3) Concatenate the short sides and store them as one list	
		ArrayList<Double> x0_1_2 = new ArrayList<>(x0_1);
		x0_1_2.remove(x0_1_2.size() - 1);  // remove the last element from x0_1
		x0_1_2.addAll(x1_2);
		
		//4) Determine which is left and which is right
		ArrayList<Double> x_left, x_right; 
		
		int middle = x0_1_2.size() / 2; //middle index
		
		if (x0_2.get(middle) < x0_1_2.get(middle)) 
		{
			x_left  = x0_2; 	x_right = x0_1_2;
		} else {
			x_right = x0_2;		x_left  = x0_1_2;
		}
		
		//5) Draw the horizontal segments
		for (int y = y0; y <= y2; y++)
		{	
			for (double x = x_left.get(y - y0); x <= x_right.get(y - y0); x++)
			{
				Point P = new Point(x, y);
				drawPoint(P, color, pen);
			}
		}//since we're always drawing horizontal left-to-right lines, this is more efficient than calling drawLine
	}
	
	
	public void drawShadedTriangle(Point A, Point B, Point C,
								   Color color, Graphics pen)
	{
		Point P0 = new Point(A), P1 = new Point(B), P2 = new Point(C);
		
		//1) Ensure points are ordered such that (y0 <= y1 <= y2)
		if (P1.y < P0.y) swapPoints(P1, P0);
		if (P2.y < P0.y) swapPoints(P2, P0);
		if (P2.y < P1.y) swapPoints(P2, P1);
		
		int x0 = P0.x, y0 = P0.y, 
			x1 = P1.x, y1 = P1.y, 
			x2 = P2.x, y2 = P2.y;
		
		double h0 = P0.h, h1 = P1.h, h2 = P2.h;
		
		//2) Compute the x coordinates and h values of the triangle's sides
		ArrayList<Double> x0_1 = interpolate(y0, x0, y1, x1); //lists all x's between P0 and P1
		ArrayList<Double> h0_1 = interpolate(y0, h0, y1, h1); //lists all hue intensities between P0 and P1
		
		ArrayList<Double> x1_2 = interpolate(y1, x1, y2, x2); 
		ArrayList<Double> h1_2 = interpolate(y1, h1, y2, h2);
		
		ArrayList<Double> x0_2 = interpolate(y0, x0, y2, x2);
		ArrayList<Double> h0_2 = interpolate(y0, h0, y2, h2);
		
		//3a) Concatenate the short sides for x
		ArrayList<Double> x0_1_2 = new ArrayList<>(x0_1);
		x0_1_2.remove(x0_1_2.size() - 1);  // remove the last element from x0_1
		x0_1_2.addAll(x1_2);
		
		//3b) Concatenate the short sides for h
		ArrayList<Double> h0_1_2 = new ArrayList<>(h0_1);
		h0_1_2.remove(h0_1_2.size() - 1);  // remove the last element from h0_1
		h0_1_2.addAll(h1_2);
		
		//4) Determine which is left and which is right
		ArrayList<Double> x_left, x_right,
						  h_left, h_right;
		
		int middle = x0_1_2.size() / 2; //middle index
		
		if (x0_2.get(middle) < x0_1_2.get(middle)) 
		{
			x_left  = x0_2; 	x_right = x0_1_2;
			h_left  = h0_2;		h_right = h0_1_2;
		} else {
			x_right = x0_2;		x_left  = x0_1_2;
			h_right = h0_2;		h_left  = h0_1_2;
		}
		
		//5) Draw the horizontal segments
		for (int y = y0; y <= y2; y++)
		{
			int x_l = (int)Math.round(x_left.get (y - y0));
			int x_r = (int)Math.round(x_right.get(y - y0));
			
			ArrayList<Double >hSegment = interpolate(x_l, h_left.get (y - y0),
								   					 x_r, h_right.get(y - y0));
			for (int x = x_l; x <= x_r; x++)
			{
				double h = hSegment.get(x - x_l);
				
				int red   = (int) (Math.round(color.getRed  () * h));
				int green = (int) (Math.round(color.getGreen() * h));
				int blue  = (int) (Math.round(color.getBlue () * h));
				
				Color shadedColor = new Color(red, green, blue);
				
				Point P = new Point(x, y);
				
				drawPoint(P, shadedColor, pen);
			}
		}	
	}
}

/* --------------!!!! OUTSIDE OF CLASS !!!! UNUSED METHOD ZONE !!!!--------------------------//

 	public void drawLineViaInterpolation(Point P0, Point P1, Color color, Graphics pen)
	{
		int dx = Math.abs(P1.x - P0.x);
		int dy = Math.abs(P1.y - P0.y);
		
		if (dx > dy) //more horizontally-oriented
		{
			//make sure x0 < x1
			if (P0.x > P1.x) swapPoints(P0, P1);
			
			int    x0 = P0.x, x1 = P1.x;
			double y0 = P0.y, y1 = P1.y;
			
			ArrayList<Double> yVals = interpolate(x0, y0, x1, y1);
			
			for (int x = x0; x <= x1; x++)
			{		
				Point xy = new Point(x, (int)Math.round(yVals.get(x - x0)));
				
				drawPoint(xy, color, pen);
			}
		}
		else //more vertically-oriented
		{
			//make sure y0 < y1
			if (P0.y > P1.y) swapPoints(P0, P1);
			
			double x0 = P0.x, x1 = P1.x;
			int    y0 = P0.y, y1 = P1.y;
			
			ArrayList<Double> xVals = interpolate(y0, x0, y1, x1);
			
			for (int y = y0; y <= y1; y++)
			{		
				Point P = new Point((int)Math.round(xVals.get(y - y0)), y);
				
				drawPoint(P, color, pen);	
			}
		}
	}	
 // ----------------------------------------------------------------------------------------*/
