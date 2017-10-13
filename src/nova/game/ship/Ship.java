package nova.game.ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import nova.game.util.LineIntersection;
import nova.game.util.LineTransform;

/**
 * Abstract ship class that all others are derived from.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public abstract class Ship
{
    protected double xPos;
    protected double yPos;
    protected double xVel;
    protected double yVel;
    protected double rotation;
    private boolean isDone;
    
    /**
     * Initializes the ship at a given location.
     * 
     * @param x x-coordinate of this ship
     * @param y y-coordinate of this ship
     */
    public Ship(int x, int y)
    {
		xPos = x;
		yPos = y;
		isDone = false;
    }
    
    /**
     * Abstract method used to accelerate the force in a given
     * direction with a given force.
     * 
     * @param force The magnitude of the force
     * @param dir The direction of the force
     */
    public abstract void accelerate(double force, double dir);
    
    /**
     * Causes the ship to act - and is called at each time step
     * of the game.
     */
    public abstract void act();
    
    /**
     * Returns the color of the ship.  All ships should have a unique
     * color.
     * 
     * @return The color of this ship
     */
    public abstract Color getColor();
    
    /**
     * Returns an array of lines defining this ship.  All ships
     * should have a unique polygonal shape.
     * 
     * @return Array of lines
     */
    public abstract Line2D[] getLines();
    
    /**
     * Returns the point value of this ship.
     * 
     * @return Point value
     */
    public abstract int getPointValue();
    
    /**
     * Gets the bounds of this ship - which is useful
     * when making sure the ship is staying within the
     * bounds of the course.
     * 
     * @return A rectangle defining the bounds of this ship
     */
    public Rectangle getBounds()
    {
    	int xMin = Integer.MAX_VALUE;
    	int xMax = Integer.MIN_VALUE;
    	int yMin = Integer.MAX_VALUE;
    	int yMax = Integer.MIN_VALUE;
    	
    	for(Line2D line : getTransformedLines())
    	{
    		xMin = Math.min(xMin, (int)line.getX1());
    		xMin = Math.min(xMin, (int)line.getX2());
    		xMax = Math.max(xMax, (int)line.getX1());
    		xMax = Math.max(xMax, (int)line.getX2());
    		
    		yMin = Math.min(yMin, (int)line.getY1());
    		yMin = Math.min(yMin, (int)line.getY2());
    		yMax = Math.max(yMax, (int)line.getY1());
    		yMax = Math.max(yMax, (int)line.getY2());
    	}
    	return new Rectangle(xMin, yMin, xMax-xMin, yMax-yMin);
    }
    
    /**
     * Check collision with another ship.  Ships are defined
     * to be colliding if any of the line segments between the
     * ships cross.
     * 
     * @param other The ship collision is being checked against
     * @return True if the ships are colliding, false otherwise
     */
    public boolean collidesWith(Ship other)
    {
		for(Line2D line1 : this.getTransformedLines())
		{
			for(Line2D line2 : other.getTransformedLines())
			{
				if(LineIntersection.intersects(line1, line2))
				{
					return true;
				}
			}
		}
		return false;
    }
    
    /**
     * Transforms the lines of the ship based on current position and rotation.
     * @return Array of transformed lines
     */
    public Line2D[] getTransformedLines()
    {
    	AffineTransform transform = AffineTransform.getRotateInstance(rotation);
    	transform.translate(xPos, yPos);
    	
    	Line2D[] lines = getLines();
    	for(int i=0; i<lines.length; i++)
    	{
    		lines[i] = LineTransform.transform(lines[i], xPos, yPos, rotation);
    	}
    	return lines;
    }
    
    /**
     * Gets the x position of the ship.
     * 
     * @return x-coordinate of the ship
     */
    public double getX()
    {
    	return xPos;
    }
    
    /**
     * Returns the y position of the ship.
     * 
     * @return y-coordinate of the ship
     */
    public double getY()
    {
    	return yPos;
    }
    
    /**
     * Returns whether or not the ship is finished.
     * 
     * @return True if the ship is done, false otherwise
     */
    public boolean isDone()
    {
    	return isDone;
    }
    
    /**
     * Sets whether or not the ship is done.
     * 
     * @param d Flag telling whether the ship is done
     */
    public void setDone(boolean d)
    {
    	isDone = d;
    }
    
    /**
     * Paints this ship.
     * 
     * @param g The Graphics object being painted to
     */
    public void paint(Graphics g)
    {
    	if(!isDone())
    	{
    		Graphics2D g2d = (Graphics2D)g;
    		
    		g2d.setColor(getColor());
    		for(Line2D line : getTransformedLines())
    		{
    			g2d.draw(line);
    		}
    	}
    }
}
