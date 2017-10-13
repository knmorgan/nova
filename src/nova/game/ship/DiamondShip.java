package nova.game.ship;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import nova.game.engine.GameSettings;

/**
 * A ship that just follows the main ship indefinitely.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class DiamondShip extends Ship
{
	private static final double FORCE = 0.50;
	
    private MainShip mainShip;
    private float pulseTimer; //used to create pulsing effect
    
    /**
     * Initializes this Diamond ship.
     * @param ms The main ship to be followed
     * @param x The x-coordinate of this ship
     * @param y The y-coordinate of this ship
     */
    public DiamondShip(MainShip ms, int x, int y)
    {
		super(x, y);
		mainShip = ms;
		pulseTimer = 0;
    }
    
    /**
     * Accelerates this ship.
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
     * Helper method used to accelerate directly towards the main ship.
     */
    private void accelerateTowardMainShip()
    {
		double angle = Math.atan2(mainShip.yPos-yPos, mainShip.xPos-xPos);
		accelerate(FORCE, angle);
    }
    
    /**
     * Acts by updating its position.
     */
    public void act()
    {
		accelerateTowardMainShip();
		xVel *= .90;
		yVel *= .90;
		xPos += xVel;
		yPos += yVel;
		adjustPosition();
		
		pulseTimer += 0.2;
    }
    
    /**
     * Makes sure this ship doesn't go outside the bounds of the game.
     */
    private void adjustPosition()
    {
		Rectangle bounds = getBounds();
		
		if(bounds.x + bounds.width > GameSettings.WIDTH)
		{
		    xPos -= (bounds.x + bounds.width - GameSettings.WIDTH);
		}
		else if(bounds.x < 0)
		{
		    xPos -= bounds.x;
		}
		
		if(bounds.y + bounds.height > GameSettings.HEIGHT)
		{
		    yPos -= (bounds.y + bounds.height - GameSettings.HEIGHT);
		}
		else if(bounds.y < 0)
		{
		    yPos -= bounds.y;
		}
    }
    
    /**
     * Diamond ships are cyan.
     */
    public Color getColor()
    {
    	return Color.CYAN;
    }
    
    /**
     * Returns the lines that make up this ship.
     * 
     * @return The array of lines
     */
    public Line2D[] getLines()
    {
    	int offset = (int)pulseTimer % 5;
		offset = (pulseTimer % 10 < 5) ? offset : 5-offset;
		
		int[] xPoints = new int[]
		{
			-10-offset, 0, 10+offset, 0,
		};
		int[] yPoints = new int[]
		{
			0, 20-offset, 0, -20+offset,
		};
		
		Line2D[] lines = new Line2D[xPoints.length];
		for(int i=0; i<xPoints.length; i++)
		{
			if(i < xPoints.length - 1)
			{
				lines[i] = new Line2D.Double(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
			}
			else
			{
				lines[i] = new Line2D.Double(xPoints[i], yPoints[i], xPoints[0], yPoints[0]);
			}
		}
		return lines;
    }
    
    /**
     * Diamond ships are worth 100 points.
     */
    public int getPointValue()
    {
		return 100;
    }
}
