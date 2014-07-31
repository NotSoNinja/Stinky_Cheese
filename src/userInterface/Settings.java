package userInterface;

/* This is the current work-in-progress.  WARNING: DOES NOT SAVE SETTINGS YET!
 * 
 * I'll get to this.  Be patient.
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SpinnerListModel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;

import javax.swing.JProgressBar;

import utility.SettingsHost;

public class Settings extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6632899323933356803L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtSavelocation;
	private JTextField txtSavelocation_1;
	private JTextField txtCurrentipv;
	private JTextField txtCurrentipv_1;
	private JTextField txtSubnetmask;
	private JTextField txtName;
	private JSlider vslider;
	private JSpinner vspinner;
	private JSlider pslider;
	private JSpinner pspinner;
	private JCheckBox checkBox;
	private JSlider svolslider;
	private JSlider rvolslider;
	private JComboBox aSendComboBox;
	private JComboBox aRecComboBox;
	private JComboBox comboBox;
	private JRadioButton rdbtnEnglish;
	private JToggleButton btnTest;
	private JToggleButton toggleButton;
	SettingsHost mem;
	Settings thiswindow;
	

	/**
	 * Create the dialog.
	 */
	public Settings(SettingsHost s) {
		thiswindow = this;
		mem = s;
		setBounds(100, 100, 450, 300);
		setTitle("Settings");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane);
			{
				JPanel panel = new JPanel();
				tabbedPane.addTab("General", null, panel, null);
				panel.setLayout(new MigLayout("", "[47px][grow]", "[14px][][][][][][]"));
				
				JLabel lblLanguage = new JLabel("Language");
				panel.add(lblLanguage, "cell 0 0,alignx left,aligny top");
				
				ButtonGroup languageGroup = new ButtonGroup();
				
				rdbtnEnglish = new JRadioButton("English");
				panel.add(rdbtnEnglish, "cell 0 1");
				languageGroup.add(rdbtnEnglish);
				
				JRadioButton rdbtnNepali = new JRadioButton("Nepali");
				panel.add(rdbtnNepali, "cell 0 2");
				languageGroup.add(rdbtnNepali);
				
				if(mem.getLanguage() == "nepali"){
					rdbtnNepali.setSelected(true);
				}else{
					rdbtnEnglish.setSelected(true);
				}
				
				
				JLabel lblYourContactName = new JLabel("Your Contact Name:");
				panel.add(lblYourContactName, "cell 0 3,alignx trailing");
			
			
				txtName = new JTextField();
				txtName.setText(mem.getName());
				panel.add(txtName, "cell 1 3,growx");
				txtName.setColumns(10);
			
			
				JButton btnResetAllTo = new JButton("Reset ALL Settings to Default");
				btnResetAllTo.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						mem.setAllDefaults();
						rdbtnEnglish.setSelected(mem.getLanguage().equalsIgnoreCase(SettingsHost.DEFAULT_LANGUAGE));
						txtName.setText(mem.getName());
						txtSavelocation_1.setText(mem.getVideoSave());
						txtSavelocation.setText(mem.getStillSaveLocation());
						vslider.setValue(mem.getVidResNum());
						checkBox.setSelected(mem.getAudioCapture());
						svolslider.setValue(mem.getSendVolume());
						rvolslider.setValue(mem.getRecieveVolume());
						//TODO: set aSendComboBox
						//TODO: set aRecComboBox
						//TODO: set comboBox
						btnTest.setSelected(false);
						toggleButton.setSelected(false);
					}
				});
				panel.add(btnResetAllTo, "cell 1 6");
				
			}
			{
				JPanel panel = new JPanel();
				tabbedPane.addTab("Video", null, panel, null);
				panel.setLayout(new MigLayout("", "[grow][][]", "[][][][][][][]"));
				{
					vslider = new JSlider(0,SettingsHost.RESOLUTIONS.length - 1);
					vslider.setMajorTickSpacing(1);
					vslider.setSnapToTicks(true);
					vslider.setValue(mem.getVidResNum());
					
					panel.add(vslider, "flowx,cell 0 0");
				
					SpinnerListModel vResolutions = new SpinnerListModel(SettingsHost.RESOLUTIONS);
					vspinner = new JSpinner(vResolutions);
					vspinner.setValue((mem.getVidRes()));
					panel.add(vspinner, "cell 1 0,growx,aligny center");
					
					vslider.addChangeListener(new ChangeListener(){
						public void stateChanged(ChangeEvent arg0) {
							vspinner.setValue(SettingsHost.RESOLUTIONS[vslider.getValue()]);
						}
					});
					vspinner.addChangeListener(new ChangeListener(){
						public void stateChanged(ChangeEvent e) {
							int i;
							for(i = 0; i < SettingsHost.RESOLUTIONS.length; i++){
								if(SettingsHost.RESOLUTIONS[i] == vspinner.getValue()){
									vslider.setValue(i);
								}
							}
						}
					});
				
					JLabel lblVideoCaptureResolution = new JLabel("Video Capture Resolution");
					panel.add(lblVideoCaptureResolution, "cell 2 0");
				}
				{
					checkBox = new JCheckBox("Capture Audio with Video");
					checkBox.setSelected(mem.getAudioCapture());
					panel.add(checkBox, "cell 0 1");
				}
				{
					JLabel lblVideoSaveLocation = new JLabel("Video Save Location");
					panel.add(lblVideoSaveLocation, "cell 0 2");
				}
				{
					txtSavelocation_1 = new JTextField();
					txtSavelocation_1.setText(mem.getVideoSave());
					panel.add(txtSavelocation_1, "cell 0 3,growx");
					txtSavelocation_1.setColumns(10);
				}
				{
					JButton btnDefault_1 = new JButton("Default");
					btnDefault_1.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent arg0) {
							txtSavelocation_1.setText(mem.getDefaultSavePath());
						}
					});
					panel.add(btnDefault_1, "cell 1 3,growx");
				}
				{
					JButton btnBrowse_1 = new JButton("Browse");
					btnBrowse_1.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							JFileChooser fc = new JFileChooser(mem.getVideoSave());
							fc.setDialogTitle("Browse");
							fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							fc.setMultiSelectionEnabled(false);
							if(fc.showOpenDialog(thiswindow) == JFileChooser.APPROVE_OPTION){
								txtSavelocation_1.setText(fc.getSelectedFile().getPath());
							}
							
						}
					});
					panel.add(btnBrowse_1, "cell 2 3");
				}
				//TODO: Video format will likely depend on Openimaj
				{
					JLabel lblVideoFormat = new JLabel("Video Format");
					panel.add(lblVideoFormat, "cell 0 4");
				}
				{
					JSpinner spinner = new JSpinner();
					panel.add(spinner, "cell 0 5");
				}
				//TODO: No Advanced features yet.
				{
					JButton btnAdvanced = new JButton("Advanced");
					panel.add(btnAdvanced, "cell 1 5");
				}
				//TODO: should reset the video format, this should be added to the "tab only" and "overall" resets
				{
					JButton btnDefault_2 = new JButton("Default");
					panel.add(btnDefault_2, "cell 2 5");
				}
				//Tab-only reset button
				{
					JButton btnResetToDefaults = new JButton("Reset to Defaults");
					btnResetToDefaults.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent arg0) {
							txtSavelocation_1.setText(mem.getDefaultSavePath());
							vslider.setValue(SettingsHost.DEFAULT_VRESNUM);
							checkBox.setSelected(SettingsHost.DEFAULT_AUDIOCAP);
						}
					});
					panel.add(btnResetToDefaults, "cell 0 6");
				}
			}
			{
				JPanel panel = new JPanel();
				tabbedPane.addTab("Picture", null, panel, null);
				panel.setLayout(new MigLayout("", "[200px,grow][29px][111px]", "[26px][][][][][][][]"));
				{
					pslider = new JSlider(0,SettingsHost.RESOLUTIONS.length - 1);
					pslider.setMajorTickSpacing(1);
					pslider.setSnapToTicks(true);
					pslider.setValue(mem.getVidResNum());
					panel.add(pslider, "cell 0 0,growx,aligny center");
				
					SpinnerListModel pResolutions = new SpinnerListModel(SettingsHost.RESOLUTIONS);
					pspinner = new JSpinner(pResolutions);
					pspinner.setValue((mem.getVidRes()));
					panel.add(pspinner, "cell 1 0,growx,aligny center");
					
					pslider.addChangeListener(new ChangeListener(){
						public void stateChanged(ChangeEvent arg0) {
							pspinner.setValue(SettingsHost.RESOLUTIONS[pslider.getValue()]);
						}
					});
					pspinner.addChangeListener(new ChangeListener(){
						public void stateChanged(ChangeEvent e) {
							int i;
							for(i = 0; i < SettingsHost.RESOLUTIONS.length; i++){
								if(SettingsHost.RESOLUTIONS[i] == pspinner.getValue()){
									pslider.setValue(i);
								}
							}
						}
					});
				
					JLabel lblStillCaptureResolution = new JLabel("Still Capture Resolution");
					panel.add(lblStillCaptureResolution, "cell 2 0,alignx left,aligny center");
				}
				{
					JSeparator separator = new JSeparator();
					panel.add(separator, "cell 0 1 3 1");
				}
				{
					JLabel lblStillPictureSave = new JLabel("Still Picture Save Location");
					panel.add(lblStillPictureSave, "cell 0 1 3 1");
				}
				{
					JSeparator separator = new JSeparator();
					panel.add(separator, "flowx,cell 0 3 3 1");
				}
				{
					txtSavelocation = new JTextField();
					txtSavelocation.setText(mem.getStillSaveLocation());
					panel.add(txtSavelocation, "cell 0 2,growx");
					txtSavelocation.setColumns(10);
				}
				{
					JButton btnDefault = new JButton("Default");
					btnDefault.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent arg0) {
							txtSavelocation.setText(mem.getDefaultSavePath());
						}
					});
					panel.add(btnDefault, "cell 1 2,growx");
				}
				{
					JButton btnBrowse = new JButton("Browse");
					btnBrowse.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							JFileChooser fc = new JFileChooser(mem.getVideoSave());
							fc.setDialogTitle("Browse");
							fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							fc.setMultiSelectionEnabled(false);
							if(fc.showOpenDialog(thiswindow) == JFileChooser.APPROVE_OPTION){
								txtSavelocation_1.setText(fc.getSelectedFile().getPath());
							}
							
						}
					});
					panel.add(btnBrowse, "cell 2 2");
				}
				{
					//TODO DEFINATELY depends on openimaj
					comboBox = new JComboBox();
					panel.add(comboBox, "cell 0 4,growx");
				}
				{
					JLabel lblCaptureDevice = new JLabel("Capture Device");
					panel.add(lblCaptureDevice, "cell 1 4");
				}
				{
					JButton btnResetToDefaults_1 = new JButton("Reset to Defaults");
					pslider.setValue(SettingsHost.DEFAULT_SRESNUM);
					txtSavelocation.setText(mem.getDefaultSavePath());
					panel.add(btnResetToDefaults_1, "cell 0 7");
				}
			}
			{
				JPanel panel = new JPanel();
				tabbedPane.addTab("Audio", null, panel, null);
				panel.setLayout(new MigLayout("", "[grow][]", "[][][][][][][]"));
				{
//					pslider.setMajorTickSpacing(1);
//					pslider.setSnapToTicks(true);
					
					svolslider = new JSlider(1,100);
					svolslider.setMajorTickSpacing(1);
					svolslider.setSnapToTicks(true);
					svolslider.setValue(mem.getSendVolume());
					panel.add(svolslider, "cell 0 0,growx");
				}
				{
					JLabel lblSendVolume = new JLabel("Send Volume");
					panel.add(lblSendVolume, "cell 1 0");
				}
				{
					rvolslider = new JSlider(1,100);
					rvolslider.setMajorTickSpacing(1);
					rvolslider.setSnapToTicks(true);
					rvolslider.setValue(mem.getRecieveVolume());
					panel.add(rvolslider, "cell 0 1,growx");
				}
				{
					JLabel lblRecieveVolume = new JLabel("Recieve Volume");
					panel.add(lblRecieveVolume, "cell 1 1");
				}
				{
					//TODO: depends on openimaj
					JComboBox aSendComboBox = new JComboBox();
					panel.add(aSendComboBox, "cell 0 2,growx");
				}
				{
					JLabel lblAudioOutDevice = new JLabel("Audio Out Device");
					panel.add(lblAudioOutDevice, "cell 1 2");
				}
				{
					//TODO: depends on openimaj
					aRecComboBox = new JComboBox();
					panel.add(aRecComboBox, "cell 0 3,growx");
				}
				{
					JLabel lblAudioInDevice = new JLabel("Audio in Device");
					panel.add(lblAudioInDevice, "cell 1 3");
				}
				{
					JProgressBar progressBar = new JProgressBar();
					panel.add(progressBar, "cell 0 4,growx,aligny center");
				
					btnTest = new JToggleButton("Test");
					btnTest.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							//manipulate progress bar according to send volume
							//play output according to both
						}
					});
					panel.add(btnTest, "cell 1 4,growx");
				
					JButton btnResetToDefaults_2 = new JButton("Reset to Defaults");
					btnResetToDefaults_2.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							btnTest.setSelected(false);
							svolslider.setValue(SettingsHost.DEFAULT_SVOL);
							rvolslider.setValue(SettingsHost.DEFAULT_RVOL);
							//TODO reset combo box
						}
					});
					panel.add(btnResetToDefaults_2, "cell 0 6");
				}
			}
			{
				JPanel panel = new JPanel();
				tabbedPane.addTab("Network", null, panel, null);
				panel.setLayout(new MigLayout("", "[grow][]", "[][][][][][][]"));
				{
					txtCurrentipv = new JTextField();
					txtCurrentipv.setText(mem.getIPv4());
					txtCurrentipv.setEditable(false);
					panel.add(txtCurrentipv, "cell 0 0,growx");
					txtCurrentipv.setColumns(10);
				
					JLabel lblCurrentIpvAddress = new JLabel("Current IPv4 Address");
					panel.add(lblCurrentIpvAddress, "cell 1 0");
				
					txtCurrentipv_1 = new JTextField();
					txtCurrentipv_1.setText(mem.getIPv6());
					txtCurrentipv_1.setEditable(false);
					panel.add(txtCurrentipv_1, "cell 0 1,growx");
					txtCurrentipv_1.setColumns(10);
				
					JLabel lblCurrentIpvAddress_1 = new JLabel("Current IPv6 Address");
					panel.add(lblCurrentIpvAddress_1, "cell 1 1");
				
					txtSubnetmask = new JTextField();
					txtSubnetmask.setText(mem.getSubnetMask());
					txtSubnetmask.setEditable(false);
					panel.add(txtSubnetmask, "cell 0 2,growx");
					txtSubnetmask.setColumns(10);
				
					JLabel lblSubnetMask = new JLabel("Subnet Mask");
					panel.add(lblSubnetMask, "cell 1 2");
				
					JButton btnDetectSettings = new JButton("Detect Settings");
					btnDetectSettings.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							txtCurrentipv.setText(mem.detectIPv4());
							txtCurrentipv_1.setText(mem.detectIPv6());
							txtSubnetmask.setText(mem.detectSubnetMask());
						}
					});
					panel.add(btnDetectSettings, "cell 0 4");
				
					toggleButton = new JToggleButton("Enable Editing");
					toggleButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							if(toggleButton.isSelected()){
								txtCurrentipv.setEditable(true);
								txtCurrentipv_1.setEditable(true);
								txtSubnetmask.setEditable(true);
							}else{
								txtCurrentipv.setEditable(false);
								txtCurrentipv_1.setEditable(false);
								txtSubnetmask.setEditable(false);
							}
						}
					});
					panel.add(toggleButton, "cell 1 4");
				
					JButton btnResetToDefaults_3 = new JButton("Reset to Defaults");
					btnResetToDefaults_3.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							txtCurrentipv.setText(mem.detectIPv4());
							txtCurrentipv_1.setText(mem.detectIPv6());
							txtSubnetmask.setText(mem.detectSubnetMask());
						}
					});
					panel.add(btnResetToDefaults_3, "cell 0 6");
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
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
	//If you got this far, you'll realize that this whole thing is just a constructor.
}
