package nova.game.engine;

import java.awt.Graphics;

/**
 * Class responsible for rendering the frame when the
 * main game portion is running.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class GameState implements State
{
    /**
     * Simply pauses the engine when this state is no longer visible.
     */
    public void hide()
    {
        Engine.instance.pause();
    }

    /**
     * Relies on the game engine to render.
     *
     * @param g The Graphics object being drawn to
     */
    public void render(Graphics g)
    {
        Engine.instance.render(g);
    }

    /**
     * Starts the game engine again when this state is visible.
     */
    public void show()
    {
        Engine.instance.start();
    }
}
