package stinky.cheese.gui.listener;

/* SHUT UP!  I KNOW!
 * 
 * Just pretend this file doesn't exist...
 * 
 * FINE!  IT'S OBJECT-ORIENTED, OKAY?
 * 
 * Sigh.  If you don't like it, you can rewrite it.
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import stinky.cheese.gui.MainWindow;

public class SendListener implements ActionListener{

	MainWindow parent;
	
	public SendListener(MainWindow w){
		parent = w;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		parent.addOutputLine("You: "+ parent.getInputText());
		parent.clearInput();
		
	}

}
