package graphics3D;

public class Point 
{
	int x, y;
	
	double h;
	
	/* h is the intensity value at this point for shading, 
	 * where 1.0 is the original color and 0.0 is black
	 * h must be in the range [0.0, 1.0]
	 */
	
	//ALL CONSTRUCTORS CALL THIS CONSTRUCTOR
	Point(int x, int y, double h)
	{
		this.x = x;
		this.y = y;
			
		//handle h being declared outside of its range
			 if (h > 1.0) this.h = 1.0;
		else if (h < 0.0) this.h = 0.0;
		else              this.h = h;
		}
	
	Point(int x, int y)
	{
		this(x, y, 1.0); //default shading intensity is 1.0 (original color)
	}
	
	//this method is called to create a point that is equal in values to the point specified
	Point(Point other)
	{
		this(other.x, other.y, other.h);
	}
	
	Point(double x, double y) //if passed doubles as points, round the doubles and type-cast them to integers
	{
		this((int)Math.round(x), (int)Math.round(y), 1.0); //default shading intensity is 1.0 (original color)
	}
	
	Point(double x, double y, double h)
	{
		this((int)Math.round(x), (int)Math.round(y), h);
	}	
}
