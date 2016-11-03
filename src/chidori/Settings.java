package chidori;

import java.util.Scanner;

import javax.swing.JFrame;

public class Settings {
	
	public int width = 1100, height = 600;
	public boolean activateLineWrap = false, activateWordWrap = false, lineNumbersVisible = true, statusBarVisible = true, activateReadAsIType = false;
	
	public Settings() {
		try {
			Scanner scanner = new Scanner(Main.settingsFile);
			String[] splits = scanner.nextLine().split("#");
			scanner.close();
			
			width = Integer.parseInt(splits[0]);
			height = Integer.parseInt(splits[1]);
			activateLineWrap = Boolean.parseBoolean(splits[2]);
			activateWordWrap = Boolean.parseBoolean(splits[3]);
			lineNumbersVisible = Boolean.parseBoolean(splits[4]);
			statusBarVisible = Boolean.parseBoolean(splits[5]);
			activateReadAsIType = Boolean.parseBoolean(splits[6]);
		}
		catch (Exception exc) {
			width = 1100;
			height = 600;
			activateReadAsIType = activateWordWrap = activateLineWrap = false;
			statusBarVisible = lineNumbersVisible = true;
		}
	}
	
	public void save() {
		try {
			Main.createDirectory("Application Data");
			Main.write(toString(), Main.settingsFile);
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		switch (Main.frame.getExtendedState()) {
		case JFrame.MAXIMIZED_BOTH:
			width = 1100;
			height = 600;
			
			break;
		default:
			width = Main.frame.getWidth();
			height = Main.frame.getHeight();
			
			break;
		}
		
		return width + "#" + height + "#" + activateLineWrap + "#" + activateWordWrap + "#" +
			lineNumbersVisible + "#" + statusBarVisible + "#" + activateReadAsIType;
	}
	
}