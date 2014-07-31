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

import utility.SettingsHost;

public class SettingsListener implements ActionListener {
	Settings s;
	SettingsHost mem;
	
	public SettingsListener(SettingsHost s){
		mem = s;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(s == null){
			try {
				Settings dialog = new Settings(mem);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
