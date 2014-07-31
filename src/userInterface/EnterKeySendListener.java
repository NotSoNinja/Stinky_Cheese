package userInterface;

/* SHUT UP!  I KNOW!
 * 
 * Just pretend this file doesn't exist...
 * 
 * FINE!  IT'S OBJECT-ORIENTED, OKAY?
 * 
 * Sigh.  If you don't like it, you can rewrite it.
 * 
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EnterKeySendListener implements KeyListener{
	
	MainWindow parent;
	
	public EnterKeySendListener(MainWindow w){
		parent = w;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
			parent.addOutputLine(parent.getInputText());
			parent.clearInput();
			
		}
		//Do Nothing
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// Do Nothing
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Do Nothing
		
	}

}
