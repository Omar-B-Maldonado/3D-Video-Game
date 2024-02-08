package graphics3D;

public class Viewport
{
	private int x, y, width, height;
	private double 	  angle; //FOV angle
	private double 	  d; //distance between camera and viewport
	
	public Viewport(int left, int top, int width, int height, double angle)
	{
		x = left;
		y = top;
		
		this.width  = width;
		this.height = height;
		this.angle  = angle;
		
		this.d = (double)(this.width/2) / (Math.tan(angle/2));
	}
	
	public Point viewportToScreen(double x, double y)
	{
		/* Formula for converting a point on the viewport (x,y) to a point on the screen (x',y'):
		 * 		
		 * 		x' = left + (width/2)  + x
		 * 		y' = top  + (height/2) + y
		 */
		
		return new Point
		(
				this.x + (this.width /2) + x,
				this.y + (this.height/2) - y
		);
	}
	
	public Point projectVertex(Vertex v)
	{
		/* Formula for projecting a 2D point (x', y') onto the viewPort accurately based on
		 * the 3D point (x, y, z):
		 * 
		 * 		x' = x * d / z
		 * 		y' = y * d / z
		 */
		
		return viewportToScreen
		(
				v.x * d / v.z,
				v.y * d / v.z)
		;
	}
}
