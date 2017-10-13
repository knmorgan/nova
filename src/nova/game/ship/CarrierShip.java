package nova.game.ship;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.Random;

import nova.game.engine.Engine;
import nova.game.engine.GameSettings;

/**
 * A class representing a carrier that will occasionally spawn
 * several XShips at a time.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.5
 */
public class CarrierShip extends Ship
{
    private static final int SPAWN_OFFSET = 250;
    private int offset;
    private boolean isSpawning;
    private static Random numGen = new Random();
    
    /**
     * Initializes the CarrierShip at the specified position.
     * 
     * @param x x-coordinate of this ship
     * @param y y-coordiante of this ship
     */
    public CarrierShip(int x, int y)
    {
        super(x,y);
        offset = 0;
        isSpawning = false;
        adjustPosition();
    }
    
    /**
     * Accelerates this ship with a specified force in a specified direction.
     * 
     * @param force Force of the acceleration
     * @param dir Direction of the acceleration
     */
    public void accelerate(double force, double dir)
    {
        if(dir == 0)
            xVel += force;
        else if(dir == 1)
            yVel += force;
        else if(dir == 2)
            xVel -= force;
        else if(dir == 3)
            yVel -= force;
        
        if(xVel > 2)
            xVel = 2;
        else if(xVel < -2)
            xVel = -2;
        if(yVel > 2)
            yVel = 2;
        else if(yVel < -2)
            yVel = -2;

    }
    
    /**
     * Causes this Carrier Ship to act.
     */
    public void act()
    {
        if(!isSpawning)
        {
            double force = 0.50;
            double dir = numGen.nextInt(4);
            accelerate(force, dir);
            xPos += xVel;
            yPos += yVel;
            adjustPosition();
        }
        
        offset++;
        if (offset == SPAWN_OFFSET)
        {
            offset = 0;
            spawn();
        }
    }
    
    /**
     * Helper method used to spawn other ships.
     */
    private void spawn()
    {
        isSpawning = true;
        
        for(int i = 0; i < numGen.nextInt(15)+1; i++)
        {
            Engine.instance.addShip(new XShip((int)xPos, (int)yPos));
        }
    }

    /**
     * Helper method used to make sure the ships stays within the 
     * game bounds.
     */
    private void adjustPosition()
    {
        Rectangle bounds = getBounds();
        
        if(bounds.x + bounds.width > GameSettings.WIDTH)
        {
            xPos -= (bounds.x + bounds.width - GameSettings.WIDTH);
            isSpawning = false;
        }
        else if(bounds.x < 0)
        {
            xPos -= bounds.x;
            isSpawning = false;
        }
        
        if(bounds.y + bounds.height > GameSettings.HEIGHT)
        {
            yPos -= (bounds.y + bounds.height - GameSettings.HEIGHT);
            isSpawning = false;
        }
        else if(bounds.y < 0)
        {
            yPos -= bounds.y;
            isSpawning = false;
        }
    }

    /**
     * This ship is a dark gray.
     */
    public Color getColor()
    {
        return Color.darkGray;
    }

    /**
     * The polygon of this ship is a simple square.
     */
    public Line2D[] getLines()
    {
        Line2D[] lines = new Line2D[4];
        lines[0] = new Line2D.Double(-10, -10, -10, 10);
        lines[1] = new Line2D.Double(-10, 10, 10, 10);
        lines[2] = new Line2D.Double(10, 10, 10, -10);
        lines[3] = new Line2D.Double(10, -10, -10, -10);
        return lines;
    }

    /**
     * This ship is worth 300 points.
     */
    public int getPointValue()
    {
        return 300;
    }

}
