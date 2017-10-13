package nova.game.engine;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nova.game.ship.Bullet;
import nova.game.ship.MainShip;
import nova.game.ship.Ship;
import nova.game.util.LinkList;

/**
 * This class is responsible for checking for collisions among objects
 * in the game.  It does so by using a hashed partitioning algorithm so
 * only objects within close proximity to each other (in the same partition)
 * will be checked for collions.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class CollisionChecker
{
    //the dimensions (width and height) of the partitions
    private static final int GRID_SIZE = 50;

    //use for implementation of a Hashed collision detection algorithm
    private HashMap<Point, LinkList<Ship>> hashedGrid;

    /**
     * Initializes the grid by creating an empty list in every partition.
     */
    public CollisionChecker()
    {
        hashedGrid = new HashMap<Point, LinkList<Ship>>();
        for(int i=0; i<=GameSettings.HEIGHT / GRID_SIZE; i++)
        {
            for(int j=0; j<=GameSettings.WIDTH / GRID_SIZE; j++)
            {
                hashedGrid.put(new Point(j, i), new LinkList<Ship>());
            }
        }
    }

    /**
     * Checks for collisions against enemies near the main ship.
     *
     * @param mainShip Used to check for collisions against enemies
     */
    public void checkForCollisions(MainShip mainShip)
    {
        Rectangle bounds = mainShip.getBounds();
        Set<Point> points = new HashSet<Point>(); //to ensure that no grid is checked twice
        points.add(convertCoordinates(bounds.x, bounds.y));
        points.add(convertCoordinates(bounds.x + bounds.width, bounds.y));
        points.add(convertCoordinates(bounds.x, bounds.y + bounds.height));
        points.add(convertCoordinates(bounds.x + bounds.width, bounds.y + bounds.height));

        for(Point p : points)
        {
            hashedGrid.get(p).startOver();
            while(hashedGrid.get(p).hasNext())
            {
                Ship s = hashedGrid.get(p).next();
                if(mainShip.collidesWith(s))
                {
                    mainShip.setDone(true);
                    return;
                }
            }
        }
    }

    /**
     * Checks for collisions between the bullets and the enemies.
     *
     * @param bullets The list of bullets maintained by the engine
     */
    public void checkForCollisions(LinkList<Bullet> bullets)
    {
        bullets.startOver();
        while(bullets.hasNext())
        {
            boolean bulletUsed = false;
            Bullet b = bullets.next();
            if(!b.isInBounds())
            {
                bullets.remove();
            }
            else
            {
                Set<Point> points = new HashSet<Point>(); //to ensure that no grid is checked twice
                points.add(convertCoordinates(b.getLastX(), b.getLastY()));
                points.add(convertCoordinates(b.getX(), b.getY()));

                for(Point p : points)
                {
                    hashedGrid.get(p).startOver();
                    while(hashedGrid.get(p).hasNext() && !bulletUsed)
                    {
                        Ship s = hashedGrid.get(p).next();
                        if(b.collidesWith(s))
                        {
                            s.setDone(true);
                            bullets.remove();
                            bulletUsed = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Empties all partitions - called once per time-step.
     */
    private void clearAll()
    {
        Set<Point> points = hashedGrid.keySet();
        for(Point p : points)
        {
            hashedGrid.get(p).clear();
        }
    }

    /**
     * Converts a coordinate of a ship to a coordinate of a partition.
     *
     * @param x The x coordinate of the ship
     * @param y The y coordinate of the ship
     * @return The coordinate of the partition the point will fall in
     */
    private Point convertCoordinates(int x, int y)
    {
        int newX = x / GRID_SIZE;
        int newY = y / GRID_SIZE;
        return new Point(newX, newY);
    }

    /**
     * Places all ships into the necessary partitions.  Each ship
     * may be placed in up to 4 partitions, depending on its bounds.
     *
     * @param ships The ships in the game to be placed.
     */
    public void updateShipLocations(LinkList<Ship> ships)
    {
        clearAll();

        ships.startOver();
        while(ships.hasNext())
        {
            Ship ship = ships.next();

            //Rectangle bounds = ship.getTransformedArea().getBounds();
            Rectangle bounds = ship.getBounds();
            Set<Point> points = new HashSet<Point>(); // to ensure that no grid is checked twice
            points.add(convertCoordinates(bounds.x, bounds.y));
            points.add(convertCoordinates(bounds.x + bounds.width, bounds.y));
            points.add(convertCoordinates(bounds.x, bounds.y + bounds.height));
            points.add(convertCoordinates(bounds.x + bounds.width, bounds.y + bounds.height));

            for(Point p : points)
            {
                hashedGrid.get(p).add(ship);
            }
        }
    }
}
