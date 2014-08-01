package stinky.cheese.gui;

/* Guess what this class does!  (It's a gui for adding contacts...)
 * 
 * This window lets you edit contacts, add contacts, and either save or discard your edits/additions
 * 
 * That's about it.
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import stinky.cheese.contacts.Contact;
import stinky.cheese.contacts.ContactList;
import stinky.cheese.gui.listener.CancelListener;
import stinky.cheese.gui.listener.UpdateContactListener;

public class AddContactWindow extends JDialog {
	/**
	 * Auto-generated serial ID!
	 * (If you know what these do, feel free to change.)
	 */
	private static final long serialVersionUID = -3570799706029631830L;
	private final JPanel contentPanel = new JPanel();
	public static final boolean ADD_NEW = true;
	public static final boolean EDIT = false;
	public JTextField txtName;
	public JTextField txtIpv;
	public JTextField txtIpv_1;
	public Contact c;
	public ContactList contacts;
	private MainWindow parent;

	/**
	 * Launch this window only.
	 * (Why would you want to?)
	 * 
	 * ...I know, debugging.  That's why this is still here.
	 */
	public static void main(String[] args) {
		try {
			AddContactWindow dialog = new AddContactWindow(null, null, true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddContactWindow(ContactList cons, MainWindow w, boolean type) {
		//This is the part where it figures out whether to create or edit a contact
		parent = w;
		if(type == ADD_NEW){
			c = null;
		}else{
			c = parent.getListSelection();
		}
		//System.out.println(c);
		contacts = cons;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[][][][][][][]"));
		{
			JLabel lblContactName = new JLabel("Contact Name");
			contentPanel.add(lblContactName, "cell 0 0");
		}
		{
			txtName = new JTextField();
			if(c == null){
				txtName.setText("");
			}else{
				txtName.setText(c.getName());
			}
			contentPanel.add(txtName, "cell 0 1,growx");
			txtName.setColumns(10);
		}
		{
			JLabel lblContactIpvAddress = new JLabel("Contact IPv4 Address");
			contentPanel.add(lblContactIpvAddress, "cell 0 2");
		}
		{
			txtIpv = new JTextField();
			if(c == null){
				txtIpv.setText("");
			}else{
				txtIpv.setText(c.getIPv4());
			}
			contentPanel.add(txtIpv, "cell 0 3,growx");
			txtIpv.setColumns(10);
		}
		{
			JLabel lblContactIpvAddress_1 = new JLabel("Contact IPv6 Address");
			contentPanel.add(lblContactIpvAddress_1, "cell 0 4");
		}
		{
			txtIpv_1 = new JTextField();
			if(c == null){
				txtIpv_1.setText("");
			}else{
				txtIpv_1.setText(c.getIPv6());
			}
			contentPanel.add(txtIpv_1, "cell 0 5,growx");
			txtIpv_1.setColumns(10);
		}
		{
			JLabel lblItIsOnly = new JLabel("It is only necessary to enter one IP address per contact.");
			contentPanel.add(lblItIsOnly, "cell 0 6");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//While we're here, I should point out that there's no safeguard against duplicates.
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new UpdateContactListener(this));
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new CancelListener(this));
				buttonPane.add(cancelButton);
			}
		}
	}

}
