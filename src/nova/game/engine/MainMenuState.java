package nova.game.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Class that is responsible for handling and rendering the main menu
 * of the game.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.1
 */
public class MainMenuState extends MenuState
{
	private BufferedImage logo;
	
	/**
	 * Loads necessary images.
	 */
	public MainMenuState()
	{
		loadImages();
	}
	
	/**
	 * Called at regular intervals - currently not implemented.
	 */
	public void act()
	{
		
	}
	
	/**
	 * Loads all images necessary for this state.
	 */
    private void loadImages()
    {
		try
		{
		    logo = ImageIO.read(getClass().getClassLoader().getResource("lib/NovaLogo.png"));
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		}
    }
    
    /**
     * Renders this state by drawing the logo and the text.
     * 
     * @param g The Graphics object being drawn to
     */
    public void render(Graphics g)
    {
    	super.render(g);
    	renderLogo(g);
    	renderText(g);
    }
    
    /**
     * Helper method responsible for positioning and rendering the logo.
     * 
     * @param g The Graphics object being drawn to
     */
    private void renderLogo(Graphics g)
    {
    	int x = GameSettings.WIDTH/2 - logo.getWidth()/2;
    	int y = 0;
    	g.drawImage(logo, x, y, null);
    }
    
    /**
     * Helper method responsible for positioning and rendering text labels.
     * 
     * @param g The Graphics object being drawn to
     */
    private void renderText(Graphics g)
    {
    	int gap = 25;
    	int height = logo.getHeight() + gap;
    	
    	g.setFont(Engine.instance.getFont());
    	renderString("PLAY GAME", height, g);
    	height += getBounds("PLAY GAME", g).getHeight() + gap;
    	renderString("INSTRUCTIONS", height, g);
    	height += getBounds("INSTRUCTIONS", g).getHeight() + gap;
    	renderString("HI SCORES", height, g);
    	height += getBounds("HI SCORES", g).getHeight() + gap;
    	renderString("CREDITS", height, g);
    	height += getBounds("CREDITS", g).getHeight() + gap;
    }
    
    /**
     * Helper method for rendering a specific string.
     * 
     * @param s The String to render
     * @param height The y-position of the String
     * @param g The Graphics object being drawn to
     */
    private void renderString(String s, int height, Graphics g)
    {
    	int x = GameSettings.WIDTH/2 - (int)getBounds(s, g).getWidth()/2;
    	Color c = new Color(0, 255, 0, 125);
    	if(getBounds(s, g).contains(EventHandler.instance.getMouseX()-x, EventHandler.instance.getMouseY()-height))
    	{
    		c = new Color(0, 255, 0);
    	}
    	g.setColor(c);
    	g.drawString(s, x, height);
    }
}
