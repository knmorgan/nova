package nova.game.engine.particle;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Abstract class for a particle.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 1.0
 */
public abstract class Particle
{
	protected Color color;
	
    protected static Color[] AVAILABLE_COLORS = 
    {
    	Color.BLUE, Color.RED, Color.ORANGE,
    	Color.YELLOW, Color.GREEN, Color.CYAN,
    	Color.PINK, Color.WHITE,
    };
    
    /**
     * Called each time step and allows the particle to reposition itself.
     */
    public abstract void act();
    
    /**
     * When this method returns true, the particle will be removed
     * from the engine.
     * 
     * @return Whether or not this particle is done acting
     */
    public abstract boolean isDone();
    
    /**
     * Used to paint this particle to the screen.
     * 
     * @param g The Graphics object being drawn to
     */
    public abstract void paint(Graphics g);
}
