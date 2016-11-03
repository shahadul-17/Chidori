package chidori;

import java.net.InetAddress;
import java.net.ServerSocket;

public class Server implements Runnable {
	
	public static volatile boolean run = true;
	
	private ServerSocket serverSocket;
	
	public Server() throws Exception {
		serverSocket = new ServerSocket(60197, 500, InetAddress.getLocalHost());
	}
	
	@Override
	public void run() {
		while (run) {
			try {
				new Thread(new ServerManager(serverSocket.accept())).start();
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		
		try {
			serverSocket.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
}