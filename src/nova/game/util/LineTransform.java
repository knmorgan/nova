package nova.game.util;

import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * This class is used to compute line transforms.
 *  
 * @author Kyle Morgan (knmorgan)
 * @version 1.0
 */
public class LineTransform
{
	/* Private constructor - can't instantiate */
	private LineTransform() { }
	
	/**
	 * Uses an AffineTransform object to return a new line given a certain
	 * translation and rotation.
	 * 
	 * @param line The line to be transformed
	 * @param tx The translation along the x-axis
	 * @param ty The translation along the y-axis
	 * @param rot The amount to be rotated
	 * @return The new, transformed line
	 */
	public static Line2D transform(Line2D line, double tx, double ty, double rot)
	{
		AffineTransform t = new AffineTransform();
		t.rotate(rot, tx, ty);
		t.translate(tx, ty);
		Point2D newP1 = t.transform(line.getP1(), null);
		Point2D newP2 = t.transform(line.getP2(), null);
		
		return new Line2D.Double(newP1, newP2);
	}
}
