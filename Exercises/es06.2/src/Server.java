//code by Mik-el
import java.io.IOException;
import java.net.*;

public class Server {
	
	//variabile
	private ServerSocket welcomeSocket;
	
	//costruttore
	public Server(int port) throws IOException {
		welcomeSocket = new ServerSocket(port);
	}

	public static void main(String[] args) throws Exception {
		try {
			Server cfs = new Server(6789);
			cfs.start();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	

	public void start() throws IOException {
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			//ProtocolHandler ph = new ServerHandler(connectionSocket);
			//se invece voglio usare il server concorrente:
			ProtocolHandler ph = new ServerHandlerConcorrente(connectionSocket);
			
			ph.handle();
		}

	}

	

}
