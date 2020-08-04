//code by Mik-el
import java.io.IOException;
import java.net.*;

public class ClientTCP {
	
	//costruttore
	public ClientTCP(String s, int port) throws IOException {
		clientSocket = new Socket(s, port);
	}
	
	//variabile
	private Socket clientSocket;
	
	
	public static void main(String[] args) {

		try {
			ClientTCP cfc = new ClientTCP("127.0.0.1", 6789);
			cfc.start();
		} 
		catch (IOException e) {
			System.out.println(e);
		}

	}

	

	public void start() {
		ProtocolHandler ph = new ClientHandler(clientSocket);
		ph.handle();
	}

	
}