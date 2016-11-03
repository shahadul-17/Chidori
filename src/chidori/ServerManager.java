package chidori;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class ServerManager implements Runnable {
	
	private Socket socket;
	private volatile ArrayList<String> arrayListDirectories = new ArrayList<String>();
	
	public ServerManager(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(socket.getInputStream());
			
			while (scanner.hasNextLine()) {
				arrayListDirectories.add(scanner.nextLine());
			}
			
			scanner.close();
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					for (int i=0; i<arrayListDirectories.size(); i++) {
						Main.frame.open(new File(arrayListDirectories.get(i)));
					}
				}
			});
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		try {
			socket.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}