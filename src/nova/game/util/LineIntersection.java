package nova.game.util;

import java.awt.geom.Line2D;

/**
 * This is a utility class used to calculate whether or not
 * two given lines are intersecting. 
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 1.0
 */
public class LineIntersection
{
	/* Private constructor - can't instantiate */
	private LineIntersection() { }
	
	/**
	 * Determines whether the two given lines are intersecting.  It
	 * does this expressing the line segments parametrically.  It then
	 * calculates the intersection of the parametric equations, and
	 * determines if it is in the bounds of the original line segments.
	 * 
	 * @param l1 The first line
	 * @param l2 The second line
	 * @return True if the lines intersect, false otherwise
	 */
	public static boolean intersects(Line2D l1, Line2D l2)
	{
		double denom = (l2.getY2()-l2.getY1())*(l1.getX2()-l1.getX1())
				- (l2.getX2()-l2.getX1())*(l1.getY2()-l1.getY1());
		
		if(denom == 0.0)
		{
			return false;
		}
		
		double int1 = ((l2.getX2()-l2.getX1())*(l1.getY1()-l2.getY1())
				- (l2.getY2()-l2.getY1())*(l1.getX1()-l2.getX1())) / denom;
		
		double int2 = ((l1.getX2()-l1.getX1())*(l1.getY1()-l2.getY1())
				- (l1.getY2()-l1.getY1())*(l1.getX1()-l2.getX1())) / denom;
		
		return (int1>=0 && int1<=1) && (int2>=0 && int2<=1);
	}
}
