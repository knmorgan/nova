package nova.game.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import nova.game.engine.GameSettings;
import nova.game.engine.EventHandler;
import nova.game.engine.StateManager;

/**
 * This class is responsible for rendering the current state of the game.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class NovaPanel extends GameCanvas
{
	public static final NovaPanel instance = new NovaPanel();
	
	/**
	 * Initializes this panel.
	 */
	private NovaPanel()
	{
		addListeners();
		setFocusable(true);
		requestFocus();
	}
	
	/**
	 * Helper method used to add all the listeners.
	 */
	private void addListeners()
	{
		addMouseListener(EventHandler.instance);
		addMouseMotionListener(EventHandler.instance);
		addKeyListener(EventHandler.instance);
	}
	
	/**
	 * Renders this game with the current state.
	 */
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		
		g2d.scale((double)getWidth()/GameSettings.WIDTH,
				  (double)getHeight()/GameSettings.HEIGHT);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameSettings.WIDTH, GameSettings.HEIGHT);
		
		StateManager.instance.currentState().render(g);
	}
}
