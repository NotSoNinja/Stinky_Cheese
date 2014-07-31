package contacts;

/* Yes, I know ArrayLists are inefficient.  Yes, I used one anyway.
 * 
 * This class is a little unorthodox: it's a list with a bunch of extra parameters.
 * 
 * Basically all this does is store contacts.
 * 
 * Not-basically, it also generates itself from the contacts.txt file, and also is
 * responsible for writing the file (when the save() method is called).
 * 
 * It can also generate a list model based on itself for displaying contact names to
 * the user.
 * 
 * This class in particular is more frankenstien's monster than any other class in 
 * this project, and it's already been rewritten a couple of times.
 * 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class ContactList extends ArrayList<Contact>{
	/**
	 * Auto-generated serial ID!
	 * (If you know what these do, feel free to change.)
	 */
	private static final long serialVersionUID = 5509261432427498341L;
	FileInputStream in;
	FileOutputStream out;
	String s;
	int c;
	int start;
	int mid1;
	int mid2;
	int end;
	DefaultListModel<String> L;
	
	public ContactList(){
		super();
	}
	//This constructor is the one you should call.
	public ContactList(String path) throws IOException{
		//It reads the contacts file!
		try {
            in = new FileInputStream(path);
            while((c = in.read()) != -1){
            	s = s + (char) c;
            }
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
            if (in != null) {
                in.close();
            }
        }
		L = new DefaultListModel<String>();
		//It generates itself!
		if(s != null && s != "null"){
			while(s.length() > 5){
				start = s.indexOf("Name: ") + 6;
				mid1 = s.indexOf('\n');
				mid2 = s.indexOf("><");
				end = s.indexOf(">",mid2 + 1);
				this.add(new Contact(s.substring(start, mid1 - 1),s.substring(mid1 + 2,mid2),s.substring(mid2 + 2,end)));
				s = s.substring(end + 3);
			}
			//LOLWHAT?  (this part generates a ListModel to display to the user in a JList on the main window.)
			for(Contact c: this){
				L.addElement(c.getName());
			}
		}
	}
	//method names pretty self-explanatory from here down
	public int addContact(Contact c){
		this.add(c);
		L.addElement(c.getName());
		return 0;
	}
	public int removeContact(Contact c){
		if(this.contains(c)){
			L.removeElement(c.getName());
			this.remove(c);
			return 0;
		}else{
			System.out.println("Contact does not exist.");
			return 1;
		}
		
	}
	public DefaultListModel<String> getListModel(){
		return L;
	}
	public void updateListNames(){
		L.removeAllElements();
		for(Contact c: this){
			L.addElement(c.getName());
		}
	}
	public int save(String path){
		for(Contact cont : this){
			try {
				out = new FileOutputStream(path);
			} catch (FileNotFoundException e) {
				System.out.println("Contacts File Not Found.");
				return 1;
			}
			try {
				out.write(cont.toString().getBytes());
			} catch (IOException e) {
				System.out.println("Error Generating Byte Array");
				e.printStackTrace();
				return 2;
			}
			try {
				out.close();
			} catch (IOException e) {
				System.out.println("Problem Closing Contact FileOutputStream.");
				e.printStackTrace();
				return 3;
			}
		}
		return 0;
	}
}
