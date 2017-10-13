package nova.game.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import nova.game.engine.particle.ParticleEngine;
import nova.game.engine.particle.LineParticle;
import nova.game.gui.NovaPanel;
import nova.game.ship.Bullet;
import nova.game.ship.MainShip;
import nova.game.ship.Ship;
import nova.game.util.LinkList;

/**
 * The main game engine.  It maintains all the main game
 * data (score, multiplier, etc) and is responsible for
 * painting the main game.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class Engine
{
    public static final Engine instance = new Engine();
    private static final int TIMER_DELAY = 20;

    private Timer gameTimer;
    private Font font;
    private BufferedImage lifeHeart;

    private MainShip ship;
    private static LinkList<Ship> enemies;
    private static LinkList<Bullet> bullets;

    private CollisionChecker collisionHandler;
    private EnemyGenerator2 enemyGenerator;

    private int score;
    private int highScore;
    private int livesLeft;
    private int enemiesKilledThisLife;
    private int multiplier;
    private boolean gameOver;
    private int toggle;

    /**
     * Initializes all the necessary elements and reads
     * the high score.
     */
    private Engine()
    {
        ship = new MainShip(GameSettings.WIDTH/2, GameSettings.HEIGHT/2);
        enemies = new LinkList<Ship>();
        bullets = new LinkList<Bullet>();

        collisionHandler = new CollisionChecker();
        enemyGenerator = new EnemyGenerator2();

        loadFont();
        loadImages();
        setUp();

        try
        {
            HighScoreManager.refreshScores();
            highScore = HighScoreManager.getTopScore();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Causes all entities on screen to move, including
     * the particles.  It also handles mouse and key events.
     */
    private void act()
    {
        moveShips();
        ParticleEngine.instance.act();
        checkForCollisions();
        enemyGenerator.generateWave(enemies, bullets, ship);
        handleKeyEvents();
        handleMouseEvents();
    }

    /**
     * Adds a ship to the game.
     *
     * @param s The ship to be added
     */
    public void addShip(Ship s)
    {
        enemies.add(s);
    }

    /**
     * Polls the collision checker to handle collions.
     */
    private void checkForCollisions()
    {
        collisionHandler.updateShipLocations(enemies);
        collisionHandler.checkForCollisions(ship);
        collisionHandler.checkForCollisions(bullets);
    }

    /**
     * Returns the special font used for the game.
     *
     * @return The font
     */
    public Font getFont()
    {
        return font;
    }

    /**
     * Returns the current score of the game.
     *
     * @return Current score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Handles necessary key events.  Namely pressing 'R' when
     * the game is over.
     */
    private void handleKeyEvents()
    {
        if(EventHandler.instance.isKeyPressed(KeyEvent.VK_R) && gameOver)
        {
            setUp();
        }
    }

    /**
     * Handles necessary mouse events related to the main ship.
     */
    private void handleMouseEvents()
    {
        ship.faceMouse(EventHandler.instance.getMouseX(), EventHandler.instance.getMouseY());

        if(EventHandler.instance.isMousePressed())
        {
            Bullet b = ship.shoot();
            if(b != null)
            {
                bullets.add(b);
            }
        }
    }

    /**
     * Called when an enemy has been killed.  It removes the
     * ship from the game and reacts accordingly, namely creating
     * the necessary particles and updating the score and multiplier
     * if need be.
     *
     * @param s The deceased enemy
     * @param assignPoints Whether or not points will be added
     */
    private void killEnemy(Ship s, boolean assignPoints)
    {
        ParticleEngine.instance.createParticles(s.getX(), s.getY());
        for(Line2D line : s.getTransformedLines())
        {
            ParticleEngine.instance.addParticle(new LineParticle(line, 0.025, s.getColor()));
        }

        if(assignPoints)
        {
            score += s.getPointValue() * multiplier;
            enemiesKilledThisLife++;
            multiplier = Math.min(10, enemiesKilledThisLife/100 + 1);
        }
    }

    /**
     * Called when the main ship has been killed.
     */
    private void killMainShip()
    {
        ship.setDone(false);
        livesLeft--;
        enemiesKilledThisLife = 0;
        multiplier = 1;
        gameOver = livesLeft < 0;

        enemies.startOver();
        while(enemies.hasNext())
        {
            killEnemy(enemies.next(), false);
            enemies.remove();
        }
    }

    /**
     * Loads the font used for the game.
     */
    private void loadFont()
    {
        try
        {
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("lib/Straightline.ttf");
            Font temp = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = temp.deriveFont(35f);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Loads the life-heart image used for the game.
     */
    private void loadImages()
    {
        try
        {
            lifeHeart = ImageIO.read(getClass().getClassLoader().getResource("lib/lifeheart.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Moves all ships and is responsible for creating the stream of
     * particles emitted by the main ship if necessary.
     */
    private void moveShips()
    {
        double tempX = ship.getX();
        double tempY = ship.getY();
        if(ship.isDone())
        {
            killMainShip();
        }
        ship.act();
        if(Math.abs(ship.getX()-tempX) > 0.5 || Math.abs(ship.getY()-tempY) > 0.5)
        {
            ParticleEngine.instance.createLineParticles(ship.getX(), ship.getY());
        }

        enemies.startOver();
        while(enemies.hasNext())
        {
            Ship s = enemies.next();
            if(s.isDone())
            {
                enemies.remove();
                killEnemy(s, true);
            }
            else
            {
                s.act();
            }
        }
        bullets.startOver();
        while(bullets.hasNext())
        {
            bullets.next().act();
        }
    }

    /**
     * Pauses the game by stopping the game timer.
     */
    public void pause()
    {
        gameTimer.stop();
    }

    /**
     * Renders the entire game.
     *
     * @param g The Graphics object being drawn to
     */
    public void render(Graphics g)
    {
        //draw particles
        ParticleEngine.instance.paint(g);

        if(gameOver)
        {
            renderGameOver(g);
        }
        else
        {
            //draw main ship
            ship.paint(g);

            //draw all enemies
            enemies.startOver();
            while(enemies.hasNext())
            {
                enemies.next().paint(g);
            }

            //draw bullets
            bullets.startOver();
            while(bullets.hasNext())
            {
                bullets.next().paint(g);
            }

            //render text (HUD)
            g.setFont(font);
            g.setColor(new Color(0, 255, 0, 125));
            renderLives(g);
            renderMultiplier(g);
            renderScores(g);
        }
    }

    /**
     * Helper method used to render text on the screen when
     * the players lives have run out.
     *
     * @param g The Graphics object being drawn to
     */
    private void renderGameOver(Graphics g)
    {
        g.setFont(font);
        g.setColor(new Color(0, 255, 0, 125));
        g.setColor(new java.awt.Color(0, 255, 0, 125));
        java.awt.FontMetrics fm = g.getFontMetrics();
        g.drawString("GAME OVER", GameSettings.WIDTH/2-fm.stringWidth("GAME OVER")/2, 100);
        g.drawString("FINAL SCORE: "+score, GameSettings.WIDTH/2-fm.stringWidth("FINAL SCORE: "+score)/2, 150);
        g.drawString("PRESS R TO PLAY AGAIN", GameSettings.WIDTH/2-fm.stringWidth("PRESS R TO PLAY AGAIN")/2, GameSettings.HEIGHT - 10);
    }

    /**
     * Helper method used to correctly position the number of lives left.
     *
     * @param g The Graphics object being drawn to
     */
    private void renderLives(Graphics g)
    {
        FontMetrics fm = g.getFontMetrics();
        int width = lifeHeart.getWidth() + 15 + fm.stringWidth("x "+livesLeft);
        g.drawImage(lifeHeart, GameSettings.WIDTH/2 - width/2, fm.getHeight()-lifeHeart.getHeight(), null);
        g.drawString("x "+livesLeft, GameSettings.WIDTH/2-width/2+lifeHeart.getWidth() + 15, fm.getHeight());
    }

    /**
     * Helper method used to correctly position the multiplier.
     *
     * @param g The Graphics object being drawn to
     */
    private void renderMultiplier(Graphics g)
    {
        FontMetrics fm = g.getFontMetrics();
        String str = "x" + multiplier;
        g.drawString(str, GameSettings.WIDTH - fm.stringWidth(str) - 10, GameSettings.HEIGHT - 10);
    }

    /**
     * Helper method used to draw the current and high score.
     *
     * @param g The Graphics object being drawn to
     */
    private void renderScores(Graphics g)
    {
        System.out.println("A");
        String high = String.valueOf(Math.max(score, highScore));

        FontMetrics fm = g.getFontMetrics();
        g.drawString(String.valueOf(score), 10, fm.getHeight());
        g.drawString(high, GameSettings.WIDTH - 10 - fm.stringWidth(high), fm.getHeight());
    }

    /**
     * Sets up the game by giving all variables an initial value.
     */
    public void setUp()
    {
        score = 0;
        livesLeft = 3;
        enemiesKilledThisLife = 0;
        multiplier = 1;
        gameOver = false;

        enemies.clear();
        bullets.clear();

        gameTimer = new Timer(TIMER_DELAY, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(gameOver)
                {
                    if(++toggle % 3 == 0)
                    {
                        Random randGen = new Random();
                        int x = randGen.nextInt(GameSettings.WIDTH);
                        int y = randGen.nextInt(GameSettings.HEIGHT);
                        ParticleEngine.instance.createParticles(x, y);
                    }
                    ParticleEngine.instance.act();
                    handleKeyEvents();
                }
                else
                {
                    act();
                }
                NovaPanel.instance.repaint();
            }
        });
    }

    /**
     * Starts the game timer - can be used as an unpause.
     */
    public void start()
    {
        gameTimer.start();
    }
}
