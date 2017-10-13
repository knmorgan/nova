package nova.game.engine.particle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.Random;

/**
 * This class represents a single line-shaped particle.  Upon creation,
 * two cartesian endpoints are passed as parameters.  This is converted
 * into a single cartesian midpoint, and the two endpoints are converted
 * into relative polar coordinates.  This makes for easy rotation and
 * scalability.  When rendering the line, the polar coordinates are
 * converted back into cartesian.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class LineParticle extends Particle
{
    private static Random randGen = new Random();

    private Line line;
    private double vx;
    private double vy;
    private double va;
    private double degrade;
    private double currentLife;

    /**
     * Constructor that accepts two endpoints and a degrade value.
     *
     * @param x1 x-coordinate of first endpoint
     * @param y1 y-coordinate of first endpoint
     * @param x2 x-coordinate of second endpoint
     * @param y2 y-coordinate of second endpoint
     * @param d Dictates how quickly the particle will degrade
     */
    public LineParticle(double x1, double y1, double x2, double y2, double d)
    {
        this(x1, y1, x2, y2, d, randColor());
    }

    /**
     * Constructor that accepts two endpoints, a degrade value,
     * and a color.
     *
     * @param x1 x-coordinate of first endpoint
     * @param y1 y-coordinate of first endpoint
     * @param x2 x-coordinate of second endpoint
     * @param y2 y-coordinate of second endpoint
     * @param d Dictates how quickly the particle will degrade
     * @param c The color of this particle
     */
    public LineParticle(double x1, double y1, double x2, double y2, double d, Color c)
    {
        double cx = (x1 + x2) / 2.0;
        double cy = (y1 + y2) / 2.0;
        double angle = Math.atan2(y2-y1, x2-x1);
        double radius = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2))/2;
        line = new Line(cx, cy, angle, radius);
        degrade = d;
        color = c;

        initParticle();
    }

    /**
     * Constructor that accepts a Line2D object and a degrade value.
     *
     * @param l Line that endpoints are taken from
     * @param d Dictates how quickly the particle will degrade
     */
    public LineParticle(Line2D l, double d)
    {
        this(l, d, randColor());
    }

    /**
     * Constructor that accepts a Line2D object, a degrade value,
     * and a color.
     *
     * @param l Line that endpoints are taken from
     * @param d Dictates how quickly the particle will degrade
     * @param c The color of this particle
     */
    public LineParticle(Line2D l, double d, Color c)
    {
        this(l.getX1(), l.getY1(), l.getX2(), l.getY2(), d, c);
    }

    /**
     * Helper method to determine a random color from the predefined set.
     *
     * @return The random color of the particle.
     */
    private static Color randColor()
    {
        return AVAILABLE_COLORS[randGen.nextInt(AVAILABLE_COLORS.length)];
    }

    /**
     * Helper method to set up this particle.
     */
    private void initParticle()
    {
        double a = randGen.nextDouble() * Math.PI * 2;
        vx = randGen.nextDouble() * 2.5 * Math.cos(a);
        vy = randGen.nextDouble() * 2.5 * Math.sin(a);
        va = randGen.nextDouble() * (Math.PI/16) - (Math.PI/32);
        currentLife = 1.0;
    }

    /**
     * This particle acts by degrading, which in turn affects
     * its rotation and size.
     */
    public void act()
    {
        currentLife -= degrade;
    }

    /**
     * Returns true if the particle is done acting, false otherwise.
     *
     * @return Whether or not the particle is done.
     */
    public boolean isDone()
    {
        return currentLife <= 0;
    }

    /**
     * Paints this particle to the screen.
     *
     * @param g The Graphics object being drawn to
     */
    public void paint(Graphics g)
    {
        if(!isDone())
        {
            double m = (1 - currentLife) / degrade;
            double tx = m * vx;
            double ty = m * vy;
            double ta = m * va;

            g.setColor(color);
            line.paint(g, tx, ty, ta, currentLife);
        }
    }

    /**
     * Helper class to store data pertaining to a line particle.
     * Specifically the midpoint in cartesian coordinates and the
     * endpoints in relative polar coordinates.
     *
     * @author Kyle Morgan (knmorgan)
     * @version 1.0
     */
    private class Line
    {
        private double cx;
        private double cy;
        private double angle;
        private double radius;

        /**
         * Initializes this line.
         *
         * @param x x-coordinate of midpoint
         * @param y y-coordinate of midpoint
         * @param a Angle
         * @param r Radius (from midpoint to endpoint)
         */
        public Line(double x, double y, double a, double r)
        {
            cx = x;
            cy = y;
            angle = a;
            radius = r;
        }

        /**
         * Paints this line by converting polar coordinates to cartesian.
         *
         * @param g The Graphics object being drawn to
         * @param tx Translated x-coordinate (relative)
         * @param ty Translated y-coordinate (relative)
         * @param ta Change in angle (relative)
         * @param scale The scale of the line
         */
        public void paint(Graphics g, double tx, double ty, double ta, double scale)
        {
            double x = cx + tx;
            double y = cy + ty;
            double r = scale * radius;
            double cos = r * Math.cos(angle + ta);
            double sin = r * Math.sin(angle + ta);
            g.drawLine((int)(x + cos), (int)(y + sin), (int)(x - cos), (int)(y - sin));
        }
    }
}
