package nova.game.engine;

import java.util.Random;

import nova.game.ship.Bullet;
import nova.game.ship.CarrierShip;
import nova.game.ship.DartShip;
import nova.game.ship.DiamondShip;
import nova.game.ship.MainShip;
import nova.game.ship.Ship;
import nova.game.ship.TriangleShip;
import nova.game.util.LinkList;

/**
 * Enemy generator that simply creates a new enemy every second.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.1
 */
public class EnemyGenerator
{
	private static final Random randGen = new Random();
	private long lastEnemy;
	
	/**
	 * Initializes enemy generator.
	 */
	public EnemyGenerator()
	{
		lastEnemy = System.currentTimeMillis();
	}
	
	/**
	 * Generates a single enemy every second.
	 * 
	 * @param enemies Engine's list of enemies
	 * @param bullets Engine's lis of bullets
	 * @param ship Reference to the main ship
	 */
	public void generateWave(LinkList<Ship> enemies, LinkList<Bullet> bullets, MainShip ship)
	{
		if(System.currentTimeMillis() - lastEnemy >= 1000)
		{
			lastEnemy = System.currentTimeMillis();
			int rand = randGen.nextInt(4);
			int x = randGen.nextInt(GameSettings.WIDTH);
			int y = randGen.nextInt(GameSettings.HEIGHT);
			if(rand == 0)
			{
				enemies.add(new TriangleShip(x, y));
			}
			else if(rand == 1)
			{
				enemies.add(new DiamondShip(ship, x, y));
			}
			else if(rand == 2)
			{
				enemies.add(new DartShip(ship, x, y));
			}
			else
			{
				enemies.add(new CarrierShip(x, y));
			}
		}
	}
}
