package nova.game.ship;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.Random;

import nova.game.engine.GameSettings;

/**
 * Defines a really small ship that moves at random and is quite
 * hard to shoot because of the size.  Because of this, a large
 * number of them on screen can be quite difficult.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.8
 */
public class XShip extends Ship
{
    private static Random numGen = new Random();

    /**
     * Initializes this ship at the specified location.
     *
     * @param x x-coordinate of this ship
     * @param y y-coordinate of this ship
     */
    public XShip(int x, int y)
    {
        super(x, y);
    }

    /**
     * Accelerates this ship in a given direction with a given force.
     *
     * @param force The force
     * @param dir The direction
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

        if(xVel > 3)
            xVel = 3;
        else if(xVel < -3)
            xVel = -3;
        if(yVel > 3)
            yVel = 3;
        else if(yVel < -3)
            yVel = -3;
    }

    /**
     * Acts by randomly accelerating
     */
    public void act()
    {
        double force = 0.50;
        double dir = numGen.nextInt(4);
        accelerate(force, dir);
        xPos += xVel;
        yPos += yVel;
        adjustPosition();
    }

    /**
     * Used to make sure the ship does not go outside the bounds
     * of the game.
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
     * XShips are blue.
     */
    public Color getColor()
    {
        return Color.BLUE;
    }

    /**
     * Returns the lines that define the XShip.
     *
     * @return Array of lines.
     */
    public Line2D[] getLines()
    {
        Line2D[] lines = new Line2D[2];
        lines[0] = new Line2D.Double(-3, -3, 3, 3);
        lines[1] = new Line2D.Double(-3, 3, 3, -3);
        return lines;
    }

    /**
     * XShips are worth 25 points.
     */
    public int getPointValue()
    {
        return 25;
    }
}
