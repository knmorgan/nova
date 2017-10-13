package nova.game.engine;

import java.awt.Graphics;

/**
 * This class manages the various states in the game.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.1
 */
public class StateManager
{
	public static final StateManager instance = new StateManager();
	private State currentState;
	
	/**
	 * Sets the current state and displays it.
	 */
	private StateManager()
	{
		currentState = new GameState();
		currentState.show();
	}
	
	/**
	 * Returns the current state.
	 * @return The current state
	 */
	public State currentState()
	{
		return currentState;
	}
	
	/**
	 * Makes the current state render.
	 * @param g The Graphics object being drawn to
	 */
	public void render(Graphics g)
	{
		currentState.render(g);
	}
}
