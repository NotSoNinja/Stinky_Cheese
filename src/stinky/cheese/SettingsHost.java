package stinky.cheese;

/* I sincerely apologize to whoever has to read this code.  It's a mess.
 * 
 * This behemoth handles ALL of the program's settings.  ALL OF THEM.
 * 
 * It's not done yet.  I left TODO's where stuff needs to get done. Use 
 * eclipse to see them as blue boxes by the scroll bar.
 * 
 * It's worth noting that FORMATTING IN "settings.cfg" MATTERS!!
 * 
 * It's helpful to open "settings.cfg" alongside this to see how it works.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

public final class SettingsHost {
	private static SettingsHost settingsHostReference;

	//Config file
	public static final String CONFIG_FOLDER = "cfg/";
	public static final String CONFIG_FILENAME = "settings.cfg";
	
	//General
	public static final String DEFAULT_LANGUAGE = "English";
	public static final String DEFAULT_NAME = "DEFAULT (Your IP)";
	//Video
	public static final String DEFAULT_VRES = "640x360";
	public static final int DEFAULT_VRESNUM = 0; //the position of DEFAULT_VRES in RESOLUTIONS
	public static final boolean DEFAULT_AUDIOCAP = true;
	public static final String DEFAULT_VSAVE = "%INSTALL_DIR%";
	public static final String DEFAULT_FORMAT = null; //This depends on Openimaj
	//Still Picture
	public static final String DEFAULT_SRES = "640x360";
	public static final int DEFAULT_SRESNUM = 0; //the position of DEFAULT_SRES in RESOLUTIONS
	public static final String DEFAULT_PSAVE = "%INSTALL_DIR%";
	//Audio
	public static final int DEFAULT_SVOL = 50;
	public static final int DEFAULT_RVOL = 50;
	public static final String[] RESOLUTIONS = {"640x360", "854x480", "960x540", "1024x576", "1280x720"};
	//The settings not represented above this line default to auto-detect.

	//where this file is
	String installDirectory;
	//Config file
	Path cfgPath;

	String version;
	//General
	String language;
	String name;
	//Video
	String vResolution;
	boolean audioCap;
	String vSaveLocation;
	String format;
	//Still Picture
	String sResolution;
	String pSaveLocation;
	String device;
	//Audio
	int sendVolume;
	int recieveVolume;
	String audioIn;
	String audioOut;
	//Network
	String ipv4;
	String ipv6;
	String mask;
	boolean internetConnected;

	private SettingsHost(){
		//Magic Code!  Thanks, StackOverflow!
		installDirectory = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		//setup
        String s = "";
		cfgPath = Paths.get(CONFIG_FOLDER, CONFIG_FILENAME);
		//Reading config file
		//System.out.println("Settings Host is reading files.");
		try {
			Files.readAllLines(cfgPath, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//assigning file input to variables
		//for efficiency's sake, we do this only once.
		int picture = s.indexOf("#PICTURE");
		//reading input file to variables
		if(s.contains("Language: ")){
			language = s.substring(s.indexOf("Language: ") + 10, s.indexOf('\n', s.indexOf("Language: ") + 10));
			if(!language.equals("english")&&!language.equals("nepali")){
				language = DEFAULT_LANGUAGE;
			}
		}else{
			language = DEFAULT_LANGUAGE;
		}
		if(s.contains("Name: ")){
			name = s.substring(s.indexOf("Name: ") + 6, s.indexOf('\n', s.indexOf("Name: ") + 6)).trim();
		}else{
			name = DEFAULT_NAME;
		}
		if(s.contains("Resolution: ")){
			vResolution = s.substring(s.indexOf("Resolution: ") + 11, s.indexOf('\n', s.indexOf("Resolution: ") + 11)).trim();
		}else{
			vResolution = DEFAULT_VRES;
		}
		if(s.contains("Capture Audio: ")){
			audioCap = s.substring(s.indexOf("Capture Audio: ") + 15, s.indexOf('\n', s.indexOf("Capture Audio: ") + 15)).trim().equals("false");
		}else{
			audioCap = DEFAULT_AUDIOCAP;
		}
		if(s.contains("Save Location: ")){
			vSaveLocation = s.substring(s.indexOf("Save Location: ") + 15, s.indexOf('\n', s.indexOf("Save Location: ") + 15)).trim();
			if(vSaveLocation.equals(DEFAULT_VSAVE)){
				vSaveLocation = getDefaultSavePath();
			}
		}else{
			vSaveLocation = getDefaultSavePath();
		}
		if(s.contains("Format: ")){
			format = s.substring(s.indexOf("Format: ") + 8, s.indexOf('\n', s.indexOf("Format: ") + 8)).trim();
		}else{
			format = DEFAULT_FORMAT;
		}
		if(s.contains("Resolution: ")){
			sResolution = s.substring(s.indexOf("Resolution: ", picture) + 12, s.indexOf('\n', s.indexOf("Resolution: ", picture) + 12)).trim();
		}else{
			sResolution = DEFAULT_SRES;
		}
		if(s.contains("Save Location: ")){
			pSaveLocation = s.substring(s.indexOf("Save Location: ", picture) + 14, s.indexOf('\n', s.indexOf("Save Location: ", picture) + 14)).trim();
			if(pSaveLocation.equals(DEFAULT_PSAVE)){
				pSaveLocation = getDefaultSavePath();
			}
		}else{
			pSaveLocation = getDefaultSavePath();
		}
		if(s.contains("Device: ")){
			device = s.substring(s.indexOf("Device: ", picture) + 12, s.indexOf('\n', s.indexOf("Device: ", picture) + 12)).trim();
		}else{
			device = "auto";
		}
		String voltempstr;
		if(s.contains("Send Volume: ")){
			voltempstr = s.substring(s.indexOf("Send Volume: ", picture) + 13, s.indexOf('\n', s.indexOf("Send Volume: ", picture) + 13));
			voltempstr = voltempstr.trim();			
			try{
				sendVolume = Integer.parseInt(voltempstr);
			}catch (NumberFormatException e){
				e.printStackTrace();
			}
		}else{
			sendVolume = DEFAULT_SVOL;
		}
		if(s.contains("Recieve Volume: ")){
			voltempstr = s.substring(s.indexOf("Recieve Volume: ", picture) + 16, s.indexOf('\n', s.indexOf("Recieve Volume: ", picture) + 16));
			voltempstr = voltempstr.trim();
			recieveVolume = Integer.parseInt(voltempstr);
		}else{
			recieveVolume = DEFAULT_RVOL;
		}

		//NOT DONE YET! AUDIO AUTO-CONFIGS GO HERE!

		if(s.contains("IPv4: ")){
			ipv4 = s.substring(s.indexOf("IPv4: ", picture) + 6, s.indexOf('\n', s.indexOf("IPv4: ", picture) + 6)).trim();
			if(ipv4.equalsIgnoreCase("auto") || ipv4.equals("")){
				ipv4 = detectIPv4();
			}
		}else{
			ipv4 = detectIPv4();
		}
		if(s.contains("IPv6: ")){
			ipv6 = s.substring(s.indexOf("IPv6: ", picture) + 6, s.indexOf('\n', s.indexOf("IPv6: ", picture) + 6)).trim();
			if(ipv6.equalsIgnoreCase("auto") || ipv6.equals("")){
				ipv6 = detectIPv6();
			}
		}else{
			ipv6 = detectIPv6();
		}
		if(s.contains("mask: ")){
			mask = s.substring(s.indexOf("mask: ", picture) + 12, s.indexOf('\n', s.indexOf("mask: ", picture) + 12));
		}else{
			mask = detectSubnetMask();
		}
		if(name.equals(DEFAULT_NAME)){
			if(mask == "255.255.255.0" || mask == "255.255.0.0" || mask == "255.0.0.0"){
				name = ipv4;
			}else{
				name = ipv6;
			}
		}
		internetConnected = (ipv4 != null) || (ipv6 != null);
		//System.out.println(this.toString());
	}

	public static SettingsHost getInstance(){
		if(settingsHostReference == null){
			settingsHostReference = new SettingsHost();
		}
		return settingsHostReference;
	}

	//prints toString to a file
	public boolean saveSettings(){
		FileOutputStream out;
		try {
			out = new FileOutputStream(cfgPath.toFile());
		} catch (FileNotFoundException e) {
			return false;
		}
		try {
			out.write(this.toString().getBytes());
		} catch (IOException e) {
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	//The big red button
	public void setAllDefaults(){
		language = DEFAULT_LANGUAGE;
		name = DEFAULT_NAME;
		vResolution = DEFAULT_VRES;
		audioCap = DEFAULT_AUDIOCAP;
		vSaveLocation = DEFAULT_VSAVE;
		format = DEFAULT_FORMAT;
		sResolution = DEFAULT_SRES;
		pSaveLocation = DEFAULT_PSAVE;
		//device = detectVideoDevice(); //TODO:implement this method
		sendVolume = DEFAULT_SVOL;
		recieveVolume = DEFAULT_RVOL;
		//audioIn = detectMic; //TODO: implement this method
		//audioOut = detectAudioOut; //TODO:implement this method
		ipv4 = detectIPv4();
		ipv6 = detectIPv6();
		mask = detectSubnetMask();
	}
	//returns local variables formatted for a config file
	public String toString(){
		String vtemp = "";
		String ptemp = "";
		if(vSaveLocation.equals(getDefaultSavePath())){
			vtemp = DEFAULT_VSAVE;
		}else{
			vtemp = vSaveLocation;
		}
		if(pSaveLocation.equals(getDefaultSavePath())){
			ptemp = DEFAULT_PSAVE;
		}else{
			ptemp = pSaveLocation;
		}
		return "Version: @DEV\n\n#GENERAL\nLanguage: " + language + "\nName: " + name + "\n\n#VIDEO\nResolution: " + vResolution + "\nCapture Audio: " + audioCap + "\nSave Location: " + vtemp + "\nFormat: " + format + "\n\n#PICTURE\nResolution: " + sResolution + "\nSave Location: " + ptemp + "\nDevice: " + device + "\n\n#AUDIO\nSend Volume: " + sendVolume + "\nRecieve Volume: " + recieveVolume + "\nAudio in: " + audioIn + "\nAudio out: " + audioOut + "\n\n#NETWORK\nIPv4: " + ipv4 + "\nIPv6: " + ipv6 + "\nmask: " + mask;
	}
	//Magic code!  Thanks StackOverflow!
	private static InetAddress getFirstNonLoopbackAddress(boolean preferIpv4, boolean preferIPv6) throws SocketException {
		Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements()) {
			NetworkInterface i = (NetworkInterface) en.nextElement();
			for (Enumeration<InetAddress> en2 = i.getInetAddresses(); en2.hasMoreElements();) {
				InetAddress addr = (InetAddress) en2.nextElement();
				if (!addr.isLoopbackAddress()) {
					if (addr instanceof Inet4Address) {
						if (preferIPv6) {
							continue;
						}
						return addr;
					}
					if (addr instanceof Inet6Address) {
						if (preferIpv4) {
							continue;
						}
						return addr;
					}
				}
			}
		}
		return null;
	}
	//GETTERS AND SETTERS! (nothing else beyond this point)
	public String getProgramPath(){
		return installDirectory;
	}
	public String getDefaultSavePath(){
		//TODO: update this for compiled version
		if(installDirectory.contains("bin/")){
			return installDirectory.substring(0,installDirectory.indexOf("bin/"));
		}else if(installDirectory.contains("StinkyCheese.jar")){
			return installDirectory.substring(0,installDirectory.indexOf("StinkyCheese.jar"));
		}else{
			return installDirectory;
		}
	}
	public String getVersion(){
		return version;
	}
	public String getLanguage(){
		return language;
	}
	public boolean setLanguage(String s){
		if(s.equals("english") || s.equals("nepali")){
			language = s;
			return true;
		}else{
			return false;
		}
	}
	public String getName(){
		return name;
	}
	public boolean setName(String s){
		name = s;
		return true;
	}
	public int getVidResNum(){
		for(int i = 0; i < RESOLUTIONS.length; i++){
			if(RESOLUTIONS[i] == vResolution){
				return i;
			}
		}
		return 0;
	}
	public String getVidRes(){
		for(int i = 0; i < RESOLUTIONS.length; i++){
			if(RESOLUTIONS[i] == vResolution){
				return RESOLUTIONS[i];
			}
		}
		return RESOLUTIONS[0];
	}
	public boolean setVidRes(String s){
		vResolution = s;
		return true;
	}
	public String setVidResNum(int i){
		vResolution = RESOLUTIONS[i];
		return RESOLUTIONS[i];
	}
	public boolean getAudioCapture(){
		return audioCap;
	}
	public boolean setAudioCapture(boolean s){
		audioCap = s;
		return true;
	}
	public String getVideoSave(){
		return vSaveLocation;
	}
	public boolean setVideoSave(String s){
		vSaveLocation = s;
		return true;
	}
	public String getVideoFormat(){
		return format;
	}
	public boolean setVideoFormat(String s){
		//TODO fix for allowed video formats
		format = s;
		return true;
	}
	public String getStillResolution(){
		return sResolution;
	}
	public boolean setStillResolution(String s){
		sResolution = s;
		return true;
	}
	public String getStillSaveLocation(){
		return pSaveLocation;
	}
	public boolean setStillSaveLocation(String s){
		pSaveLocation = s;
		return true;
	}
	public String getVideoDevice(){
		return device;
	}
	public boolean setVideoDevice(String s){
		device = s;
		return true;
	}
	public int getSendVolume(){
		return sendVolume;
	}
	public boolean setSendVolume(int s){
		sendVolume = s;
		return true;
	}
	public int getRecieveVolume(){
		return recieveVolume;
	}
	public boolean setRecieveVolume(int s){
		recieveVolume = s;
		return true;
	}
	public String getMic(){
		//TODO
		return audioIn;
	}
	public boolean setMic(String s){
		//TODO
		audioIn = s;
		return true;
	}
	public String detectMic(){
		//TODO make this actually detect things
		return null;
	}
	public String getAudioOut(){
		//TODO
		return audioOut;
	}
	public boolean setAudioOut(String s){
		//TODO
		audioOut = s;
		return true;
	}
	public String detectAudioOut(){
		//TODO make this actually defect things
		return null;
	}
	public String getIPv4(){
		return ipv4;
	}
	public boolean setIPv4(String s){
		ipv4 = s;
		return true;
	}
	public String detectIPv4(){
		try {
			ipv4 = getFirstNonLoopbackAddress(true, false).getHostAddress();
		} catch (SocketException e) {
			ipv4 = "null";
			e.printStackTrace();
		}
		return ipv4;
	}
	public String getIPv6(){
		return ipv6;
	}
	public boolean setIPv6(String s){
		ipv6 = s;
		return true;
	}
	public String detectIPv6(){
		try {
			ipv6 = getFirstNonLoopbackAddress(false, true).getHostAddress();
		} catch (SocketException e) {
			ipv6 = "null";
			e.printStackTrace();
		}
		return ipv6;
	}
	public String getSubnetMask(){
		return mask;
	}
	public boolean setSubnetMask(String s){
		mask = s;
		return true;
	}
	public String detectSubnetMask(){
		try {
			NetworkInterface networkInterface = NetworkInterface.getByInetAddress(getFirstNonLoopbackAddress(false, false));
			short masknum = networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
			if(masknum == 8 && !ipv4.equals("null")){
				mask = "255.0.0.0";
			}else if(masknum == 16 && !ipv4.equals("null")){
				mask = "255.255.0.0";
			}else if(masknum == 8 && !ipv4.equals("null")){
				mask = "255.255.255.0";
			}else {
				mask = "/" + masknum;
			}

		} catch (SocketException e) {
			mask = "null";
			e.printStackTrace();
		}
		return mask;
	}
	public boolean getInetConnected() {
		return internetConnected;
	}
	public boolean updateInetConnection(){
		ipv4 = detectIPv4();
		ipv6 = detectIPv6();
		mask = detectSubnetMask();
		internetConnected = (ipv4 != null) || (ipv6 != null);
		return internetConnected;
	}
}
