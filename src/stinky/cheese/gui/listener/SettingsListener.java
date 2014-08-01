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

import javax.swing.JDialog;

import stinky.cheese.gui.SettingsDialog;
public class SettingsListener implements ActionListener {
	private SettingsDialog s;
	
	public SettingsListener(){
	}
	
	public void actionPerformed(ActionEvent e) {
		if(s == null){
			try {
				SettingsDialog dialog = new SettingsDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}