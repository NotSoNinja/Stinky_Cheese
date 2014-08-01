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

import stinky.cheese.contacts.ContactList;
import stinky.cheese.gui.AddContactWindow;
import stinky.cheese.gui.MainWindow;

public class AddContactWindowListener implements ActionListener{
	
	ContactList contacts;
	MainWindow parent;
	boolean mode;
	
	public AddContactWindowListener(MainWindow w, boolean type){
		super();
		parent = w;
		contacts = parent.contactlist;
		mode = type;//TRUE = ADD NEW, FALSE = EDIT
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(parent.list.getSelectedValue() == null && mode == AddContactWindow.EDIT){
			parent.addOutputLine("No Contact Selected");
		}else{
			try {
				AddContactWindow dialog = new AddContactWindow(contacts, parent, mode);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
