package nova.game.engine;

/**
 * A class dedicated to containing constants critical to the game.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class GameSettings
{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    public static final int NUM_PARTICLES = 50;
    
    public static final boolean ANTIALIASED = true;
    public static final boolean DEV_MODE = true;
    
    /* Private constructor - can't instantiate */
    private GameSettings() { }
}
