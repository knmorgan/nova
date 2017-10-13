package nova.game.ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;

import nova.game.engine.EventHandler;
import nova.game.engine.GameSettings;

/**
 * The main ship of the game that is controlled by the user.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.8
 */
public class MainShip extends Ship
{
    private static final double FORCE = 1.0;

    private int bulletDelay;
    private int shotToggle;
    private long lastShotTime;

    /**
     * Initializes this ship at the specified position.
     *
     * @param x x-coordinate of this ship
     * @param y y-coordinate of this ship
     */
    public MainShip(int x, int y)
    {
        super(x, y);
        bulletDelay = 50;
        shotToggle = 0;
        lastShotTime = 0;
    }

    /**
     * Acclerates the ship in a given direction.
     *
     * @param force The force given to the ship
     * @param dir The direction of the force
     */
    public void accelerate(double force, double dir)
    {
        xVel += force * Math.cos(dir);
        yVel += force * Math.sin(dir);
    }

    /**
     * Makes sure the ship doesn't go outside the bounds of the game.
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
     * Acts by updating the ships position.
     */
    public void act()
    {
        handleKeyEvents();
        xVel *= 0.90;
        yVel *= 0.90;
        xPos += xVel;
        yPos += yVel;
        adjustPosition();
    }

    /**
     * Causes the ship to face the mouse.
     *
     * @param x x-coordinate of the mouse
     * @param y y-coordinate of the mouse
     */
    public void faceMouse(int x, int y)
    {
        rotation = Math.atan2(yPos-y, xPos-x) - Math.PI/2;
    }

    /**
     * Returns the area of this ship.
     *
     * @return Area of this ship
     */
    public Area getArea()
    {
        int[] xPoints1 = new int[]
        {
            0, 5, 0, -5,
        };
        int[] yPoints1 = new int[]
        {
            -10, 0, 10, 0,
        };

        int[] xPoints2 = new int[]
        {
            -8, -8, -3, -14,
        };
        int[] yPoints2 = new int[]
        {
            -25, 0, 12, 0,
        };

        int[] xPoints3 = new int[]
        {
            8, 8, 3, 14,
        };
        int[] yPoints3 = new int[]
        {
            -25, 0, 12, 0,
        };

        Polygon poly1 = new Polygon(xPoints1, yPoints1, 4);
        Polygon poly2 = new Polygon(xPoints2, yPoints2, 4);
        Polygon poly3 = new Polygon(xPoints3, yPoints3, 4);

        Area area = new Area(poly1);
        area.add(new Area(poly2));
        area.add(new Area(poly3));
        return area;
    }

    /**
     * The main ship is white.
     */
    public Color getColor()
    {
        return Color.WHITE;
    }

    /**
     * Returns the lines that define the bounds of this ship.
     *
     * @return The array of lines.
     */
    public Line2D[] getLines()
    {
        int[] xPoints1 = new int[]
         {
             0, 5, 0, -5,
         };
         int[] yPoints1 = new int[]
         {
             -10, 0, 10, 0,
         };

         int[] xPoints2 = new int[]
         {
             -8, -8, -3, -14,
         };
         int[] yPoints2 = new int[]
         {
             -25, 0, 12, 0,
         };

         int[] xPoints3 = new int[]
         {
             8, 8, 3, 14,
         };
         int[] yPoints3 = new int[]
         {
             -25, 0, 12, 0,
         };

         Line2D[] lines = new Line2D[xPoints1.length*3];
        for(int i=0; i<xPoints1.length; i++)
        {
            if(i < xPoints1.length - 1)
            {
                lines[i] = new Line2D.Double(xPoints1[i], yPoints1[i], xPoints1[i+1], yPoints1[i+1]);
                lines[i+4] = new Line2D.Double(xPoints2[i], yPoints2[i], xPoints2[i+1], yPoints2[i+1]);
                lines[i+8] = new Line2D.Double(xPoints3[i], yPoints3[i], xPoints3[i+1], yPoints3[i+1]);
            }
            else
            {
                lines[i] = new Line2D.Double(xPoints1[i], yPoints1[i], xPoints1[0], yPoints1[0]);
                lines[i+4] = new Line2D.Double(xPoints2[i], yPoints2[i], xPoints2[0], yPoints2[0]);
                lines[i+8] = new Line2D.Double(xPoints3[i], yPoints3[i], xPoints3[0], yPoints3[0]);
            }
        }
        return lines;
    }

    /**
     * The main ship does not have a point value.
     */
    public int getPointValue()
    {
        return 0;
    }

    /**
     * Handles the key events to accelerate the ship in a certain direction.
     */
    private void handleKeyEvents()
    {
        if(EventHandler.instance.isKeyPressed(KeyEvent.VK_W))
        {
            accelerate(FORCE, -Math.PI/2);
        }
        if(EventHandler.instance.isKeyPressed(KeyEvent.VK_S))
        {
            accelerate(FORCE, Math.PI/2);
        }
        if(EventHandler.instance.isKeyPressed(KeyEvent.VK_A))
        {
            accelerate(FORCE, Math.PI);
        }
        if(EventHandler.instance.isKeyPressed(KeyEvent.VK_D))
        {
            accelerate(FORCE, Math.PI*2);
        }
    }

    /**
     * Paints this ship.
     *
     * @param g The Graphics object being painted to.
     */
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform oldTransform = g2d.getTransform();

        g2d.transform(AffineTransform.getTranslateInstance(xPos, yPos));
        g2d.transform(AffineTransform.getRotateInstance(rotation));
        g2d.setColor(getColor());
        g2d.fill(getArea());

        g2d.setTransform(oldTransform);
    }

    /**
     * Polls the ship to shoot a bullet, which may or may not happen
     * depending on when the last bullet shot was.
     *
     * @return A bullet if the ship can shoot now, null otherwise
     */
    public Bullet shoot()
    {
        long time = System.currentTimeMillis();
        if(time - lastShotTime > bulletDelay)
        {
            lastShotTime = time;
            double angleOffset = (shotToggle++ % 2 == 0) ? Math.PI/8 : -Math.PI/8;
            double x = xPos + 25 * Math.cos(rotation - Math.PI/2 + angleOffset);
            double y = yPos + 25 * Math.sin(rotation - Math.PI/2 + angleOffset);
            return new Bullet(x, y, rotation - Math.PI/2);
        }

        return null;
    }
}
