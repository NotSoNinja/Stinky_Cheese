package contacts;

/* This class is the core object behind Contacts in Stinky_Cheese.
 * It's really just a way to put all of a contact's data in one 
 * easily-accessible place.
 * 
 * This one is pretty self-explanatory.
 * 
 * ipv4 is the contact's IPv4 address
 * ipv6 is the contact's IPv6 address
 * name is a string the user will see denoting the contact
 * 
 * The only special concern here is that toString in this class has
 * special formatting for a reason: that formatting is expected in 
 * the saved file contacts.txt, and the toString method here is used 
 * in the file's generation.
 */

public class Contact {
	private String ipv4;
	private String ipv6;
	private String name;
	
	public Contact(String n, String vFour, String vSix){
		ipv4 = vFour;
		ipv6 = vSix;
		name = n;
	}
	
	public String getName(){
		return name;
	}
	public int setName(String s){
		name = s;
		return 0;
	}
	public String getIPv4(){
		return ipv4;
	}
	public int setIPv4(String s){
		ipv4 = s;
		return 0;
	}
	public String getIPv6(){
		return ipv6;
	}
	public int setIPv6(String s){
		ipv6 = s;
		return 0;
	}
	public String toString(){
		return "Name: " + name + "\n<" + ipv4 + "><" + ipv6 + ">\n";
	}

}
