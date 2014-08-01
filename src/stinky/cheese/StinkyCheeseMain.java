package stinky.cheese;


/* See MainWindow.java for an overview comment
 * 
 * This class just takes care of running things.  Not much to see here.
 */

import java.awt.EventQueue;
import stinky.cheese.gui.MainWindow;

public class StinkyCheeseMain {

	/**
	 * Launch the application and handle initializing any libraries.
	 */
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		window.frame.setVisible(true);
	}
}
