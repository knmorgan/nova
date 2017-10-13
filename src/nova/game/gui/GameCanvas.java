package nova.game.gui;

import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Graphics;

/**
 * This class is used to provide hardware accelerated graphics
 * for 2D Java games.  Game should create a subclass of GameCanvas
 * to override the render() method.  The GameCanvas will handle
 * the rest of the graphics rendering.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 1.0
 */
public abstract class GameCanvas extends Canvas
{
	//number of buffers used
	private int numBuffers;
	
	//used to calculate rendering time
	private long renderTime;
	
	/**
	 * Initializes the GameCanvas with 2 buffers.
	 */
	public GameCanvas()
	{
		this(2);
	}
	
	/**
	 * Initializes this GameCanvas with a specified number of buffers.
	 * 
	 * @param buffers The number of buffers to use.
	 */
	public GameCanvas(int buffers)
	{
		numBuffers = buffers;
	}
	
	/**
	 * Returns the time (in milliseconds) of the last render.
	 * Can be used to calculate frame rate.
	 * 
	 * @return Last render time
	 */
	public long getLastRenderTime()
	{
		return renderTime;
	}
	
	/**
	 * Handles double buffered and should NOT be overriden.
	 */
	public void paint(Graphics g)
	{
		long time = System.currentTimeMillis();
		
		createBufferStrategy(numBuffers);
		BufferStrategy strat = getBufferStrategy();
		render(strat.getDrawGraphics());
		strat.getDrawGraphics().dispose();
		strat.show();
		
		renderTime = System.currentTimeMillis() - time;
	}
	
	/**
	 * Should be overriden by subclasses to provide desired painting
	 * functionality.
	 * 
	 * @param g The Graphics object being drawn to
	 */
	public abstract void render(Graphics g);
	
	/**
	 * Should NOT be overriden.
	 */
	public void update(Graphics g)
	{
		paint(g);
	}
}
