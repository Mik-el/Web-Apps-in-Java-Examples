//code by Mik-el
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleFTPServer {

	private ServerSocket welcomeSocket;
	
	
	public static void main(String[] args) throws Exception {
		try {
			SimpleFTPServer sfs = new SimpleFTPServer(6789);
			sfs.start();
		} catch (Exception e1) {
			System.out.println(e1);
		}
	}//fine main

	
	public SimpleFTPServer(int port) throws Exception {
		welcomeSocket = new ServerSocket(port);
	}

	
	public void start() throws IOException {
		while (true) { 
			Socket connectionSocket = welcomeSocket.accept();
			ProtocolHandler ph = new SimpleFTPHandlerServer(connectionSocket);
			ph.handle();
		}

	}

	

}
