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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitListener implements ActionListener{
	MainWindow parent;
	
	ExitListener(MainWindow w){
		super();
		parent = w;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parent.quit();
		
	}

}
