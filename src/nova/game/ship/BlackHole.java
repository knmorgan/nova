package nova.game.ship;

import java.awt.Color;
import java.awt.geom.Line2D;

import nova.game.util.LinkList;

/**
 * An enemy ship that is stationary, yet invokes gravity both on the
 * player and all projectiles the player shoots.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.5
 */
public class BlackHole extends Ship
{
    private static final double G = 75.0; //gravitational constant
    private MainShip ship;
    private LinkList<Bullet> bullets;
    
    /**
     * Initializes the Black Hole.
     * 
     * @param x x position
     * @param y y position
     * @param ms Reference to main ship
     * @param b Reference to list of bullets
     */
    public BlackHole(int x, int y, MainShip ms, LinkList<Bullet> b)
    {
		super(x, y);
		ship = ms;
		bullets = b;
		//pulseTimer = 0; //used to create a pulsing effect on the black hole
    }
    
    /**
     * Black holes do not move, so this method is pointless.
     */
    public void accelerate(double force, double dir)
    {
    	//do nothing, black holes do not move
    }
    
    /**
     * Acts by forcing gravity on the main ship and all bullets
     * in the game.
     */
    public void act()
    {
		//find distance between black hole and ship
		double distance = Math.sqrt(Math.pow(xPos-ship.xPos, 2) + Math.pow(yPos-ship.yPos, 2));
		double force = G / distance;
		double angle = Math.atan2(yPos-ship.yPos, xPos-ship.xPos);
		
		ship.accelerate(force, angle);
		
		/*for(Ship s : enemyShips)
		{
		    double d = Math.sqrt(Math.pow(xPos-s.xPos, 2) + Math.pow(yPos-s.yPos, 2));
		    double f = 3 * G / d;
		    double a = Math.atan2(yPos-ship.yPos, xPos-ship.xPos);
		    
		    s.accelerate(f, a);
		}*/
		
		bullets.startOver();
		while(bullets.hasNext())
		{
			Bullet b = bullets.next();
		    double d = Math.sqrt(Math.pow(xPos-b.getX(), 2) + Math.pow(yPos-b.getY(), 2));
		    double f = 3 * G / d;
		    double a = Math.atan2(yPos-ship.yPos, xPos-ship.xPos);
		    
		    b.accelerate(f, a);
		}
		
		rotation += Math.PI/64;
    }
    
    /**
     * Blacks holes are designated to be red.
     */
    public Color getColor()
    {
    	return Color.RED;
    }
    
    /**
     * Returns all lines making up this black hole.
     */
    public Line2D[] getLines()
    {
    	double[] xPoints = new double[]
		{
			0, 4, 4, -8, -8, 12, 12, -16, -16, 20, 20, -24, -24, 24,
		};
		double[] yPoints = new double[]
		{
			0, 0, 8, 8, -8, -8, 16, 16, -16, -16, 24, 24, -24, -24,
		};
		
		Line2D[] lines = new Line2D[xPoints.length-1];
		for(int i=0; i<xPoints.length-1; i++)
		{
			lines[i] = new Line2D.Double(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
		}
		return lines;
    }
    
    /**
     * Black hole are worth 500 points.
     */
    public int getPointValue()
    {
    	return 500;
    }
}
