package stinky.cheese.gui;

/* WELCOME TO STINKY_CHEESE!
 * 
 * This project is by no means well documented, so please bare with me.
 * Stinky_Cheese is a gui-framework designed to hold Openimaj-based video chat.
 * Stinky_Cheese also supports audio calling, text chat, taking still pictures 
 * and recording video.  
 * 
 * Unfortunately, I have been unable to make Appache Maven work on my computer, 
 * and thusly have been unable to use Openimaj.
 * 
 * At the time of writing, OpenCV's lack of ARM support makes it not the ideal
 * choice for this project.
 * 
 * Stinky_Cheese also has no web integration yet, as implementation there will
 * probably depend on Openimaj, and also I have no idea where to begin with
 * Internet protocols.
 * 
 * This is the MainWindow Class.  It contains the main method as well as code 
 * for the central user interface.  Other classes also have main methods, for
 * fast debugging.  As the program comes together, those should be removed.
 * 
 * The mess of implementations of ActionListener may not seem... normal...
 * because it isn't.  It was a futile effort to keep things organized that just
 * ended up not working.  I haven't changed it because it's a lot of work to 
 * rewrite, and it works fine.
 * 
 * I've tried to include comments like this where I can to help out a bit.  Sorry
 * about the mess!
 * 
 * JA
 * 2014-07-31
 * 
 */

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JList;

import java.awt.Insets;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.openimaj.image.DisplayUtilities.ImageComponent;
import org.openimaj.image.MBFImage;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

import stinky.cheese.SettingsHost;
import stinky.cheese.contacts.Contact;
import stinky.cheese.contacts.ContactList;
import stinky.cheese.gui.listener.AddContactWindowListener;
import stinky.cheese.gui.listener.EnterKeySendListener;
import stinky.cheese.gui.listener.ExitListener;
import stinky.cheese.gui.listener.RemoveContactListener;
import stinky.cheese.gui.listener.SendListener;
import stinky.cheese.gui.listener.SettingsListener;

public class MainWindow {

	public JFrame frame;
	private JTextField txtInputtext;
	private JTextArea txtrOutputtext;
	public ContactList contactlist;
	String uName;
	private static final String CONTACTFILE = "dat/contacts.txt";
	public JList<String> list;
	Video<MBFImage> video;
	VideoDisplay<MBFImage> display;
	private JCheckBox chckbxVideo;


	/**
	 * Create the application.
	 */
	public MainWindow() {
		try {
			contactlist = new ContactList(CONTACTFILE);
		} catch (IOException e) {
			System.out.println("IO Exception Occurred Reading Contacts");
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Stinky Cheese");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{43, 0};
		gbl_panel.rowHeights = new int[]{14, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblContacts = new JLabel("Contacts");
		GridBagConstraints gbc_lblContacts = new GridBagConstraints();
		gbc_lblContacts.insets = new Insets(0, 0, 5, 0);
		gbc_lblContacts.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblContacts.gridx = 0;
		gbc_lblContacts.gridy = 0;
		panel.add(lblContacts, gbc_lblContacts);
		
		list = new JList<String>(contactlist.getListModel());
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		panel.add(list, gbc_list);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{96, 0};
		gbl_panel_1.rowHeights = new int[]{20, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		txtrOutputtext = new JTextArea();
		txtrOutputtext.setText("");
		txtrOutputtext.setEditable(false);
		txtrOutputtext.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane (txtrOutputtext,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_txtrOutputtext = new GridBagConstraints();
		gbc_txtrOutputtext.gridheight = 2;
		gbc_txtrOutputtext.insets = new Insets(0, 0, 5, 0);
		gbc_txtrOutputtext.fill = GridBagConstraints.BOTH;
		gbc_txtrOutputtext.gridx = 0;
		gbc_txtrOutputtext.gridy = 0;
		panel_1.add(scrollPane, gbc_txtrOutputtext);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{376, 57, 0};
		gbl_panel_2.rowHeights = new int[]{23, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		txtInputtext = new JTextField();
		txtInputtext.setText("Type a message here");
		txtInputtext.addKeyListener(new EnterKeySendListener(this));
		GridBagConstraints gbc_txtInputtext = new GridBagConstraints();
		gbc_txtInputtext.insets = new Insets(0, 0, 0, 5);
		gbc_txtInputtext.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtInputtext.gridx = 0;
		gbc_txtInputtext.gridy = 0;
		panel_2.add(txtInputtext, gbc_txtInputtext);
		txtInputtext.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		btnSend.addActionListener(new SendListener(this));
		panel_2.add(btnSend, gbc_btnSend);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		chckbxVideo = new JCheckBox("Video");
		panel_3.add(chckbxVideo);
		
		JToggleButton tglbtnCall = new JToggleButton("Call");
		
		panel_3.add(tglbtnCall);
		
		JButton btnTakePicture = new JButton("Take Picture");
		panel_3.add(btnTakePicture);
		
		JToggleButton tglbtnRecordVideo = new JToggleButton("Record Video");
		panel_3.add(tglbtnRecordVideo);
		
		JToggleButton tglbtnRecordAudio = new JToggleButton("Record Audio");
		panel_3.add(tglbtnRecordAudio);
		
		
		//TODO:OPENIMAJ!
		
		video = null;
    	
    	ImageComponent screen = new ImageComponent(true, true);
    	
    	display = VideoDisplay.createVideoDisplay(video, screen);
		
		JPanel panel_4 = new JPanel();
		panel_4.add(screen);
		frame.getContentPane().add(panel_4, BorderLayout.CENTER);
		chckbxVideo.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if(chckbxVideo.isSelected()){
					((VideoCapture) video).stopCapture();
				}else{
					try {
						video = new VideoCapture(640, 320);
					} catch (VideoCaptureException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		//END OPENIMAJ
		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnStinkycheese = new JMenu("StinkyCheese");
		menuBar.add(mnStinkycheese);
		
//		JMenuItem mntmSettings = new JMenuItem("Settings");
//		mntmSettings.addActionListener(new SettingsListener(SettingsHost.getInstance()));
//		mnStinkycheese.add(mntmSettings);

		JMenuItem mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(new SettingsListener());
		mnStinkycheese.add(mntmSettings);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ExitListener(this));
		mnStinkycheese.add(mntmQuit);
		
		JMenu mnContacts = new JMenu("Contacts");
		menuBar.add(mnContacts);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new AddContactWindowListener(this, AddContactWindow.ADD_NEW));
		mnContacts.add(mntmNew);
		
		JMenuItem mntmEdit = new JMenuItem("Edit");
		mntmEdit.addActionListener(new AddContactWindowListener(this, AddContactWindow.EDIT));
		mnContacts.add(mntmEdit);
		
		JMenuItem mntmRemove = new JMenuItem("Remove");
		mntmRemove.addActionListener(new RemoveContactListener(this));
		mnContacts.add(mntmRemove);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmDocumentation = new JMenuItem("Documentation");
		mnHelp.add(mntmDocumentation);
		
		if(!SettingsHost.getInstance().getInetConnected()){
			tglbtnCall.setEnabled(false);
			chckbxVideo.setEnabled(false);
			txtrOutputtext.setEnabled(false);
			txtInputtext.setEnabled(false);
			btnSend.setEnabled(false);
			list.setEnabled(false);
			mnContacts.setEnabled(false);
		}
	}
	public Contact getListSelection(){
		try{
			return contactlist.get(list.getSelectedIndex());
		}catch(Exception e){
			return null;
		}
	}
	public void quit(){
		int code = contactlist.save(CONTACTFILE);
		if(!SettingsHost.getInstance().saveSettings()){
			System.exit(4);
		}
		System.exit(code);
	}
	public String getInputText(){
		return txtInputtext.getText();
	}
	public void addOutputLine(String s){
		if(s != null && s.length() > 0){
			txtrOutputtext.setText(txtrOutputtext.getText()+"\n"+s);
		}
		
	}
	public void clearInput(){
		txtInputtext.setText(null);
	}
}
