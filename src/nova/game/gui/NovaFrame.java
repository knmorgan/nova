package nova.game.gui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This is the main frame of the application.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 0.9
 */
public class NovaFrame extends Frame
{
	/**
	 * Initializes this frame and adds a NovaPanel instance to it.
	 */
	public NovaFrame()
	{
		super("Nova");
		setSize(800, 600);
		setLocationRelativeTo(null);
		add(NovaPanel.instance);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		setVisible(true);
	}
}
