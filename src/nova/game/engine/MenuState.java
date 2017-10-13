package nova.game.engine;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.Timer;

import nova.game.engine.particle.ParticleEngine;
import nova.game.gui.NovaPanel;

/**
 * Abstract class to give a general guideline for a menu.  All
 * menus in the game have the particle engine running in the 
 * background, and this class automagically provides that to
 * subclasses.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.1
 */
public abstract class MenuState implements State
{
	private static final int TIMER_DELAY = 20;
	private Timer timer;
	private int toggle;
	
	/**
	 * Initializes the timer that is used to run the particle engine.
	 */
	public MenuState()
	{
		toggle = 0;
		timer = new Timer(TIMER_DELAY, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				NovaPanel.instance.repaint();
				ParticleEngine.instance.act();
				makeParticles();
				act();
			}
		});
	}
	
	/**
	 * Returns the bounds of the String to be drawn.
	 * 
	 * @param s The String
	 * @param g The Graphics object being drawn to
	 * @return A Rectangle2D describing the bounds of the String
	 */
	public Rectangle2D getBounds(String s, Graphics g)
	{
		return g.getFontMetrics().getStringBounds(s, g);
	}
	
	/**
	 * Adds particles to the engine at a random location at fixed intervals.
	 */
	private void makeParticles()
	{
		toggle = ++toggle % 3;
		
		if(toggle == 0)
		{
			Random randGen = new Random();
			int x = randGen.nextInt(GameSettings.WIDTH);
			int y = randGen.nextInt(GameSettings.HEIGHT);
			ParticleEngine.instance.createParticles(x, y);
		}
	}
	
	/**
	 * Called when the menu is no longer visible, and pauses this menu
	 * from repainting.
	 */
	public void hide()
	{
		timer.stop();
	}
	
	/**
	 * Called when the menu becomes visible, and resumes repainting the 
	 * menu.
	 */
	public void show()
	{
		timer.start();
	}
	
	/**
	 * Renders the particle engine.  Further rendering is implemented
	 * by subclasses.
	 */
	public void render(Graphics g)
	{
		ParticleEngine.instance.paint(g);
	}
	
	/**
	 * Used to act at each timestep.
	 */
	public abstract void act();
}
