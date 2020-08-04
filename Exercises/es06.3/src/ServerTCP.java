//code by Mik-el
import java.io.IOException;
import java.net.*;

public class ServerTCP {
	
	//variabile
	private ServerSocket welcomeSocket;
	
	//costruttore
	public ServerTCP(int port) throws IOException {
		welcomeSocket = new ServerSocket(port);
	}

	public static void main(String[] args) throws Exception {
		try {
			ServerTCP cfs = new ServerTCP(6789);
			cfs.start();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	

	public void start() throws IOException {
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			ProtocolHandler ph = new ServerHandler(connectionSocket);
			ph.handle();
		}

	}

	

}
