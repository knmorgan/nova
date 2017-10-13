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
 * Generates enemies and gets progressively harder as time goes on.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.5
 */
public class EnemyGenerator2
{
    private static final Random randGen = new Random();
    private long lastEnemy;
    private int waveInterval = 0;

    /**
     * Initializes this enemy generator.
     */
    public EnemyGenerator2()
    {
        lastEnemy = System.currentTimeMillis();
    }

    /**
     * Generates a single enemy or a wave of enemies.  Gets
     * progressively harder as the game goes on.
     *
     * @param enemies Engine's list of enemies
     * @param bullets Engine's list of bullets
     * @param ship Reference to the main ship
     */
    public void generateWave(LinkList<Ship> enemies, LinkList<Bullet> bullets, MainShip ship)
    {
        int width = (GameSettings.WIDTH);
        int height = (GameSettings.HEIGHT);

        if (Engine.instance.getScore() <= 15000)
        {
            if (Engine.instance.getScore() <= 2000)
                waveInterval = 1500;
            if ((Engine.instance.getScore() >= 2000) && (Engine.instance.getScore() <= 5000))
                waveInterval = 1000;
            if ((Engine.instance.getScore() >= 5000) && (Engine.instance.getScore() <= 10000))
                waveInterval = 500;
            if ((Engine.instance.getScore() >= 10000) && (Engine.instance.getScore() <= 13000))
                waveInterval = 300;
            if(System.currentTimeMillis() - lastEnemy >= waveInterval)
            {
                lastEnemy = System.currentTimeMillis();
                int randWave = randGen.nextInt(4);
                int x = randGen.nextInt(GameSettings.WIDTH);
                int y = randGen.nextInt(GameSettings.HEIGHT);

                if(randWave == 0)
                {
                    enemies.add(new TriangleShip(x, y));
                }
                else if(randWave == 1)
                {
                    enemies.add(new DiamondShip(ship, x, y));
                }
                else if(randWave == 2)
                {
                    enemies.add(new DartShip(ship, x, y));
                }
                else
                {
                    enemies.add(new CarrierShip(x, y));
                }
            }
        }
        else if (Engine.instance.getScore() > 13000)
        {
            if ((Engine.instance.getScore() > 13000) && (Engine.instance.getScore() <= 25000))
                waveInterval = 5000;
            else if ((Engine.instance.getScore() > 25000) && (Engine.instance.getScore() <= 50000))
                waveInterval = 3500;
            else if ((Engine.instance.getScore() > 50000) && (Engine.instance.getScore() <= 125000))
                waveInterval = 2500;
            else if ((Engine.instance.getScore() > 125000) && (Engine.instance.getScore() <= 500000))
                waveInterval = 1750;
            else if ((Engine.instance.getScore() > 500000) && (Engine.instance.getScore() <= 1000000))
                waveInterval = 1250;
            else if (Engine.instance.getScore() >= 1000000)
                waveInterval = 750;

            if(System.currentTimeMillis() - lastEnemy >= waveInterval)
            {
                lastEnemy = System.currentTimeMillis();
                int randWave = randGen.nextInt(4);
                int x = randGen.nextInt(GameSettings.WIDTH);
                int y = randGen.nextInt(GameSettings.HEIGHT);
                if(randWave == 0)
                {
                    for (int i = 0; i <= 4; i++)
                    {
                        x = randGen.nextInt(GameSettings.WIDTH);
                        y = randGen.nextInt(GameSettings.HEIGHT);
                        enemies.add(new TriangleShip(x, y));
                    }
                }
                else if (randWave == 1)
                {
                    int randDiamond = randGen.nextInt(6);
                    if (randDiamond == 0)
                    {
                        enemies.add(new DiamondShip(ship, 125, 125));
                        enemies.add(new DiamondShip(ship, width - 125, 125));
                        enemies.add(new DiamondShip(ship, 125, height - 125));
                        enemies.add(new DiamondShip(ship, width - 125, height - 125));
                    }
                    else if (randDiamond == 1)
                    {
                        enemies.add(new DiamondShip(ship, width/2, 110));
                        enemies.add(new DiamondShip(ship, width/2, height - 110));
                        enemies.add(new DiamondShip(ship, 125, height/2));
                        enemies.add(new DiamondShip(ship, width - 125, height/2));
                    }
                    else if (randDiamond == 2)
                    {
                        int spread = width / 6;
                        for (int i = 1; i < 6; i++)
                        {
                            enemies.add(new DiamondShip(ship, spread * i, 35));
                        }
                    }
                    else if (randDiamond == 3)
                    {
                        int spread = width / 6;
                        for (int i = 1; i < 6; i++)
                        {
                            enemies.add(new DiamondShip(ship, spread * i, height - 35));
                        }
                    }
                    else if (randDiamond == 4)
                    {
                        int spread = height / 6;
                        for (int i = 1; i < 6; i++)
                        {
                            enemies.add(new DiamondShip(ship, 35, spread * i));
                        }
                    }
                    else if (randDiamond == 5)
                    {
                        int spread = height / 6;
                        for (int i = 1; i < 6; i++)
                        {
                            enemies.add(new DiamondShip(ship, width - 35, spread * i));
                        }
                    }
                }

                else if(randWave == 2)
                {
                    int randDart = randGen.nextInt(6);
                    if (randDart == 0)
                    {
                        enemies.add(new DartShip(ship, 100, 100));
                        enemies.add(new DartShip(ship, width - 100, 100));
                        enemies.add(new DartShip(ship, 100, height - 100));
                        enemies.add(new DartShip(ship, width - 100, height - 100));
                    }
                    else if (randDart == 1)
                    {
                        enemies.add(new DartShip(ship, width/2, 70));
                        enemies.add(new DartShip(ship, width/2, height - 70));
                        enemies.add(new DartShip(ship, 70, height/2));
                        enemies.add(new DartShip(ship, width - 70, height/2));
                    }
                    else if (randDart == 2)
                    {
                        int spread = width / 5;
                        for (int i = 1; i < 5; i++)
                        {
                            enemies.add(new DartShip(ship, spread * i, 25));
                        }
                    }
                    else if (randDart == 3)
                    {
                        int spread = width / 5;
                        for (int i = 1; i < 5; i++)
                        {
                            enemies.add(new DartShip(ship, spread * i, height - 25));
                        }
                    }
                    else if (randDart == 4)
                    {
                        int spread = height / 5;
                        for (int i = 1; i < 5; i++)
                        {
                            enemies.add(new DartShip(ship, 25, spread * i));
                        }
                    }
                    else if (randDart == 5)
                    {
                        int spread = height / 5;
                        for (int i = 1; i < 5; i++)
                        {
                            enemies.add(new DartShip(ship, width - 25, spread * i));
                        }
                    }
                }
                else
                {
                    int randX = randGen.nextInt(2);
                    if (randX == 0)
                    {
                        enemies.add(new CarrierShip(width/2, 35));
                        enemies.add(new CarrierShip(width/2, height - 35));
                        enemies.add(new CarrierShip(35, height/2));
                        enemies.add(new CarrierShip(width - 35, height/2));
                    }
                    else if (randX == 1)
                    {
                        enemies.add(new CarrierShip(35, 35));
                        enemies.add(new CarrierShip(width - 35, 35));
                        enemies.add(new CarrierShip(35, height - 35));
                        enemies.add(new CarrierShip(width - 35, height - 35));
                    }
                }
            }
        }
    }
}
