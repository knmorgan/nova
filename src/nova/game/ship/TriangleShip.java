package nova.game.ship;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.Random;

/**
 * Defines a triangular ship which kind of just sits there and does
 * nothing.  Oh yeah, it undulates too!
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class TriangleShip extends Ship
{
    private static final int maxDist = 25;
    private static final int minDist = 10;

    private int[] vDist;
    private int[] offset;

    /**
     * Initializes a TriangleShip at the specified location.
     *
     * @param x x-coordinate of this ship
     * @param y y-coordinate of this ship
     */
    public TriangleShip(int x, int y)
    {
        super(x, y);
        vDist = new int[3];
        offset = new int[3];

        Random numGen = new Random();
        for(int i=0; i<vDist.length; i++)
        {
            vDist[i] = numGen.nextInt(maxDist-minDist) + minDist;
            offset[i] = numGen.nextInt(2) == 0 ? -1 : 1;
        }
    }

    /**
     * TriangleShips do not move, so this method is
     * unnecessary.
     */
    public void accelerate(double force, double dir)
    {
        //do nothing yet
    }

    /**
     * Acts by undulating!  And rotating!
     */
    public void act()
    {
        for(int i=0; i<vDist.length; i++)
        {
            vDist[i] += offset[i];
            if(vDist[i] <= minDist || vDist[i] >= maxDist)
            {
                offset[i] *= -1;
            }
        }
        rotation -= Math.PI / 128;
    }

    /**
     * Returns the lines that define this triangle.
     *
     * @return Array of lines
     */
    public Line2D[] getLines()
    {
        Line2D line1 = new Line2D.Double(0, vDist[0], .866 * vDist[1], -.5 * vDist[1]);
        Line2D line2 = new Line2D.Double(.866 * vDist[1], -.5 * vDist[1], -.866 * vDist[2], -.5f * vDist[2]);
        Line2D line3 = new Line2D.Double(-.866 * vDist[2], -.5 * vDist[2], 0, vDist[0]);
        return new Line2D[]{line1, line2, line3};
    }

    /**
     * Triangle ships are green.
     */
    public Color getColor()
    {
        return Color.GREEN;
    }

    /**
     * Triangle ships are worth 25 points.
     */
    public int getPointValue()
    {
        return 25;
    }
}
