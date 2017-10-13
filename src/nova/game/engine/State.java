package nova.game.engine;

import java.awt.Graphics;

/**
 * This class represents the current state of the game and
 * dictates what will be painted onto the screen.
 * 
 * @author Kyle Morgan
 */
public interface State
{
	/**
	 * This method will be called when the state
	 * is removed from the screen.
	 */
	public void hide();
	
	/**
	 * This method will be called when the state
	 * needs to repaint itself.
	 * 
	 * @param g Graphics object used for painting.
	 */
	public void render(Graphics g);
	
	/**
	 * This method will be called when the state
	 * is shown on screen.
	 */
	public void show();
}
