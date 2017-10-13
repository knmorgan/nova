package nova.game.engine.particle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Class represents a single particle that is represented by a point,
 * or a small circle when painted.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 1.0
 */
public class PointParticle extends Particle
{
    private static Random randGen = new Random();

    private double xPos;
    private double yPos;
    private double vx;
    private double vy;
    private double xDrag;
    private double yDrag;

    /**
     * Initializes the PointParticle at the specified location.
     *
     * @param x x-coordinate of this particle
     * @param y y-coordinate of this particle
     */
    public PointParticle(double x, double y)
    {
        xPos = x;
        yPos = y;
        initParticle(null);
    }

    /**
     * Initializes the PointParticle at the specified location and
     * with the specified color.
     *
     * @param x x-coordinate of this particle
     * @param y y-coordinate of this particle
     * @param c Color of this particle
     */
    public PointParticle(double x, double y, Color c)
    {
        xPos = x;
        yPos = y;
        initParticle(c);
    }

    /**
     * Helper method used to initialize this particle.  If the passed
     * color is null, then a random color is chosen.
     *
     * @param c Specified color
     */
    private void initParticle(Color c)
    {
        double a = randGen.nextDouble() * Math.PI * 2;
        vx = (randGen.nextDouble() * 15.0 + 5) * Math.cos(a);
        vy = (randGen.nextDouble() * 15.0 + 5) * Math.sin(a);

        int drag = randGen.nextInt(15) + 10;
        xDrag = vx / drag;
        yDrag = vy / drag;

        if(color == null)
        {
            color = AVAILABLE_COLORS[randGen.nextInt(AVAILABLE_COLORS.length)];
        }
        else
        {
            color = c;
        }
    }

    /**
     * Updates location of the particle according to its velocity.
     */
    public void act()
    {
        xPos += vx;
        yPos += vy;
        vx -= xDrag;
        vy -= yDrag;
    }

    /**
     * Returns whether or not this particle is finished.
     *
     * @return Whether or not this particle is finished.
     */
    public boolean isDone()
    {
        return Math.abs(vx-0) < 0.01;
    }

    /**
     * Paints this particle.
     *
     * @param g The Graphics object being drawn to
     */
    public void paint(Graphics g)
    {
        if(!isDone())
        {
            double v = Math.sqrt(vx*vx + vy*vy);
            Color c = new Color(color.getRed(),
                                color.getGreen(),
                                color.getBlue(),
                                (int)Math.min(v*50, 255));
            g.setColor(c);
            g.fillOval((int)xPos-2, (int)yPos-2, 4, 4);
        }
    }
}
