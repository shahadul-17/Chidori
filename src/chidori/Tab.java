package chidori;

import java.awt.Dimension;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Tab {
	
	public int index = 0;
	public String filePath = "";
	private static final String[] keys = { "%918273645%", "%546372819%" };
	
	public TabStrip tabStrip;
	public TabBody tabBody;
	
	public Tab(String title) {
		tabStrip = new TabStrip(title, this);
		tabStrip.setPreferredSize(new Dimension(130, 40));
		
		tabBody = new TabBody(this);
		
		TabbedPane.hashMapTabs.put(index, this);
	}
	
	public static void backup() {
		try {
			Tab tab;
			PrintWriter printWriter = new PrintWriter(Main.backupFile);
			ArrayList<Integer> arrayListKeys = new ArrayList<Integer>(TabbedPane.hashMapTabs.keySet());
			
			for (int i=0; i<arrayListKeys.size(); i++) {
				tab = TabbedPane.hashMapTabs.get(arrayListKeys.get(i));
				
				if (tab.tabBody.saved && tab.filePath.length() != 0) {
					printWriter.print(tab.filePath + keys[1]);
				}
				else if (!tab.tabBody.saved) {
					printWriter.print(tab.tabStrip.title + keys[0] + tab.filePath + keys[0] + tab.tabBody.textArea.getText() + keys[1]);
				}
			}
			
			printWriter.flush();
			printWriter.close();
			
			Thread.sleep(20);	// though it's a bad practice but it's just for safety...
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public static void restore(TabbedPane tabbedPane) {
		if (Main.backupFile.exists()) {
			try {
				String[][] splits = { Main.read(Main.backupFile).split(keys[1]), null };
				
				for (int i=0; i<splits[0].length; i++) {
					try {
						splits[1] = splits[0][i].split(keys[0]);
						
						if (splits[1].length == 1) {
							File file = new File(splits[1][0]);
							
							if (file.exists()) {
								Main.frame.open(file);
							}
						}
						else {
							tabbedPane.createNewTab(splits[1][0]);
							tabbedPane.selectedTab.filePath = splits[1][1];
							
							if ((splits[1][1].length() != 0) && !Frame.arrayListFilePaths.contains(splits[1][1])) {
								Frame.arrayListFilePaths.add(splits[1][1]);
							}
							
							tabbedPane.selectedTab.tabBody.textArea.setText(splits[1][2]);
							tabbedPane.selectedTab.tabBody.setUnsaved();
							tabbedPane.selectedTab.tabBody.textArea.setCaretPosition(tabbedPane.selectedTab.tabBody.textArea.getText().length());
						}
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
				}
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
	
}