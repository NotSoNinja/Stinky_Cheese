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

import contacts.Contact;

public class RemoveContactListener implements ActionListener {
	
	MainWindow parent;
	
	public RemoveContactListener(MainWindow w){
		parent = w;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Contact c = parent.getListSelection();
		System.out.println(c);
		if(c == null){
			parent.addOutputLine("No Contact Selected");
		}else{
			parent.contactlist.remove(parent.getListSelection());
			parent.contactlist.updateListNames();
		}

	}

}
