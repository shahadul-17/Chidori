package chidori;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {
	
	private String[] directories;
	private Socket socket;
	
	public Client(String[] directories) throws Exception {
		this.directories = directories;
		socket = new Socket(InetAddress.getLocalHost(), 60197);
	}
	
	@Override
	public void run() {
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			
			for (int i=0; i<directories.length; i++) {
				printWriter.println(directories[i]);
				printWriter.flush();
			}
			
			printWriter.close();
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