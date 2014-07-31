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

public class UpdateContactListener implements ActionListener{
	
	AddContactWindow parent;
	
	
	UpdateContactListener(AddContactWindow w){
		parent = w;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(parent.contacts.contains(parent.c)){
			parent.c.setName(parent.txtName.getText());
			parent.c.setIPv4(parent.txtIpv.getText());
			parent.c.setIPv6(parent.txtIpv_1.getText());
		}else{
			parent.contacts.addContact(new Contact(parent.txtName.getText(), parent.txtIpv.getText(), parent.txtIpv_1.getText()));
		}
		parent.contacts.updateListNames();
		parent.dispose();
	}

}
