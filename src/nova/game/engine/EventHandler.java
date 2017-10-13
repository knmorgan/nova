package nova.game.engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class receives all events necessary for the game
 * and sets appropriates flags so the data can be retrieved
 * from other classes.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class EventHandler
    implements MouseListener, MouseMotionListener, KeyListener
{
    //single static instance
    public static final EventHandler instance = new EventHandler();

    private boolean[] keys = new boolean[256];
    private boolean mousePressed;
    private int mouseX;
    private int mouseY;

    /**
     * Private constructor - can't instantiate.
     */
    private EventHandler() { }

    /**
     * Returns true if a mouse button is pressed, false otherwise.
     *
     * @return Whether or not the mouse button is pressed
     */
    public boolean isMousePressed()
    {
        return mousePressed;
    }

    /**
     * Given a key code, it returns whether or not that key is pressed.
     *
     * @param keyCode KeyEvent keycode (e.g. KeyEvent.VK_A)
     * @return Whether or not a specified key is pressed
     */
    public boolean isKeyPressed(int keyCode)
    {
        return keys[keyCode];
    }

    /**
     * Gets the x-coordinate of the mouse as specified by the most
     * recent mouseMoved event.
     *
     * @return The x-coordinate of the mouse
     */
    public int getMouseX()
    {
        return mouseX;
    }

    /**
     * Gets the y-coordinate of the mouse as specified by the most
     * recent mouseMoved event.
     *
     * @return The y-coordinate of the mouse
     */
    public int getMouseY()
    {
        return mouseY;
    }

    /**
     * Handles the mousePressed event by setting the
     * appropriate flag.
     */
    public void mousePressed(MouseEvent e)
    {
        mousePressed = true;
    }

    /**
     * Handles the mouseReleased event by setting the
     * appropriate flag.
     */
    public void mouseReleased(MouseEvent e)
    {
        mousePressed = false;
    }

    /**
     * Handles the mouseMoved event by updating the
     * x and y coordinates of the mouse.
     */
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /**
     * Handles the mouseDragged event by updating the
     * x and y coordinates of the mouse.
     */
    public void mouseDragged(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /**
     * Handles the keyPressed event by updating the
     * appropriate flag.
     */
    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
    }

    /**
     * Handles the keyReleased event by updating the
     * appropriate flag.
     */
    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
    }

    /**
     * Current unused events.
     */
    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void keyTyped(KeyEvent e) { }
}
