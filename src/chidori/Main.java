package chidori;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.UIManager;

public class Main {
	
	public static final String title = "Chidori v0.03 (Beta)", defaultTabTitle = "New Document";
	
	public static final File settingsFile = new File("Application Data//Settings.ini"),
			backupFile = new File("Application Data//Backup.log");
	
	public static final Color DODGER_BLUE = new Color(30, 144, 255),
			ORANGE = new Color(255, 165, 0), VIOLET = new Color(178, 102, 255);
	
	private static Server server;
	public static Frame frame;
	
	public static void main(String[] directories) {
		try {
			server = new Server();
			
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			frame = new Frame(directories);
			frame.setVisible(true);
			
			new Thread(server).start();
		}
		catch (Exception exc) {
			if (directories.length > 0) {
				try {
					new Thread(new Client(directories)).start();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void createDirectory(String directory) {
		File file = new File(directory);
		
		if (!(file.exists() && file.isDirectory())) {
			file.mkdir();
		}
	}
	
	public static void write(String text, File file) throws Exception {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		bufferedWriter.write(text);
		bufferedWriter.flush();
		bufferedWriter.close();
	}
	
	public static String read(File file) throws Exception {
		int lines = 0;
		
		String text;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		while ((text = bufferedReader.readLine()) != null) {
			if (lines == 0) {
				lines++;
			}
			else {
				stringBuilder.append("\n");
			}
			
			stringBuilder.append(text);
		}
		
		bufferedReader.close();
		
		return stringBuilder.toString();
	}
	
}
