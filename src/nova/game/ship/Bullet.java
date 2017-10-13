package nova.game.ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

import nova.game.engine.GameSettings;
import nova.game.util.LineIntersection;

/**
 * A class representing a bullet fired by the main ship.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class Bullet
{
    private double xPos;
    private double yPos;
    private double lastX; //used for collision purposes
    private double lastY; //used for collision purposes
    private double xVel;
    private double yVel;
    private double angle;
    
    /**
     * Initializes this bullet at a certain position and angle.
     * 
     * @param x x-coordinate of this bullet
     * @param y y-coordinate of this bullet
     * @param a Angle of this bullet
     */
    public Bullet(double x, double y, double a)
    {
		xPos = x;
		yPos = y;
		angle = a;
		
		xVel = 15 * Math.cos(angle);
		yVel = 15 * Math.sin(angle);
    }
    
    /**
     * Accelerates this bullet with a specified force in a 
     * specified direction.
     * 
     * @param force Given force
     * @param dir Given direction
     */
    public void accelerate(double force, double dir)
    {
		xVel += force * Math.cos(dir);
		yVel += force * Math.sin(dir);
    }
    
    /**
     * Causes this bullet to act by updating its position.
     */
    public void act()
    {
		lastX = xPos;
		lastY = yPos;
		
		xPos += xVel;
		yPos += yVel;
    }
    
    /**
     * Tests whether this bullet has collided with a given ship.
     * The bullet is treated as a line whose endpoints are its
     * current position, and its previous position.
     * 
     * @param ship The ship collision is being checked against
     * @return True if the bullet collides with the ship, false otherwise
     */
    public boolean collidesWith(Ship ship)
    {
    	Line2D path = new Line2D.Double(lastX, lastY, xPos, yPos);
    	for(Line2D line : ship.getTransformedLines())
    	{
    		if(LineIntersection.intersects(path, line))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Returns the previous x-position of this bullet.
     * 
     * @return Previous x position
     */
    public int getLastX()
    {
    	return (int)lastX;
    }
    
    /**
     * Returns the previous y-position of this bullet.
     * 
     * @return Previous y position
     */
    public int getLastY()
    {
    	return (int)lastY;
    }
    
    /**
     * Returns the x-position of this bullet.
     * 
     * @return Current x position
     */
    public int getX()
    {
    	return (int)xPos;
    }
    
    /**
     * Returns the y-position of this bullet.
     * 
     * @return Current y position
     */
    public int getY()
    {
    	return (int)yPos;
    }
    
    /**
     * Tests to see if the bullet is within the bounds of the game.
     * 
     * @return True if the bullet is in the bounds, false otherwise
     */
    public boolean isInBounds()
    {
    	return xPos > 0 && yPos > 0
			&& xPos < GameSettings.WIDTH && yPos < GameSettings.HEIGHT;
    }
    
    /**
     * Paints this bullet.
     * 
     * @param g The Graphics object being painted to
     */
    public void paint(Graphics g)
    {
		g.setColor(Color.WHITE);
		g.fillOval((int)xPos-2, (int)yPos-2, 4, 4);
    }
}
