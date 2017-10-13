package nova.game.ship;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import nova.game.engine.GameSettings;

/**
 * A ship that acts by pointing at the main ship, then shooting
 * quickly at it after a fixed interval of time.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.8
 */
public class DartShip extends Ship
{
    private static final int DART_SPEED = 20;
    private static final int DART_OFFSET = 200;
    
    private MainShip mainShip;
    private int offset;
    private boolean isDarting;
    
    /**
     * Initializes this DartShip.
     * 
     * @param ms Main ship of the game
     * @param x x-coordinate of this ship
     * @param y y-coordinate of this ship
     */
    public DartShip(MainShip ms, int x, int y)
    {
		super(x, y);
		mainShip = ms;
		offset = 0;
		rotation = Math.atan2(mainShip.yPos - yPos, mainShip.xPos - xPos) - Math.PI / 2;
		isDarting = false;
		adjustPosition();
    }
    
    /**
     * Accelerates this DartShip - currently not used.
     */
    public void accelerate(double force, double dir)
    {
    	//do nothing for now
    }
    
    /**
     * Acts by updating position if necessary.
     */
    public void act()
    {
		if(isDarting)
		{
		    xPos += xVel;
		    yPos += yVel;
		    adjustPosition();
		}
		else
		{
		    double angle = Math.atan2(mainShip.yPos - yPos, mainShip.xPos - xPos) - Math.PI / 2;
		    if(rotation > angle + Math.PI/128)
		    {
		    	rotation -= Math.PI/64;
		    }
		    else if(rotation < angle - Math.PI/128)
		    {
		    	rotation += Math.PI/64;
		    }
		    adjustPosition();
	
		    offset++;
		    if (offset == DART_OFFSET)
		    {
		    	offset = 0;
		    	dart();
		    }
		}
    }
    
    /**
     * Keeps the DartShip in the bounds of the game.  Also used
     * to tell when the DartShip should stop "darting".
     */
    private void adjustPosition()
    {
		Rectangle bounds = getBounds();
		
		if(bounds.x + bounds.width > GameSettings.WIDTH)
		{
		    xPos -= (bounds.x + bounds.width - GameSettings.WIDTH);
		    isDarting = false;
		}
		else if(bounds.x < 0)
		{
		    xPos -= bounds.x;
		    isDarting = false;
		}
		
		if(bounds.y + bounds.height > GameSettings.HEIGHT)
		{
		    yPos -= (bounds.y + bounds.height - GameSettings.HEIGHT);
		    isDarting = false;
		}
		else if(bounds.y < 0)
		{
		    yPos -= bounds.y;
		    isDarting = false;
		}
    }
    
    /**
     * Helper method used to shoot at the main ship.
     */
    private void dart()
    {
		isDarting = true;
		xVel = DART_SPEED * Math.cos(rotation + Math.PI / 2);
		yVel = DART_SPEED * Math.sin(rotation + Math.PI / 2);
    }
    
    /**
     * The Dart ship is orange.
     */
    public Color getColor()
    {
    	return Color.ORANGE;
    }
    
    /**
     * Returns the lines that define the ship of this ship.
     * 
     * @return The array of lines
     */
    public Line2D[] getLines()
    {
    	int[] xPoints = new int[]
    	{
    			-10, 0, 10, 0,
    	};
    	int[] yPoints = new int[]
    	{
    			-10, 30, -10, 0,
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
     * Dart Ships are worth 200 points.
     */
    public int getPointValue()
    {
    	return 200;
    }
}
