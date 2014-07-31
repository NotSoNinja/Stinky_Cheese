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

import javax.swing.JDialog;

public class CancelListener implements ActionListener{
	
	JDialog parent;

	CancelListener(JDialog w){
		parent = w;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		parent.dispose();
	}
	

}
