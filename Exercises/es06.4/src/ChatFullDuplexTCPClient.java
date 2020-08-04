//code by Mik-el
import java.io.IOException; 
import java.net.*;

public class ChatFullDuplexTCPClient { 
	
	public static void main(String argv[]) throws Exception { 
		try {
			ChatFullDuplexTCPClient cfc = new ChatFullDuplexTCPClient("127.0.0.1", 6789);
			cfc.start();
		} catch(Exception e) {
			System.out.println(e);
		} 
	}

	public ChatFullDuplexTCPClient(String s, int port) throws IOException {
		clientSocket = new Socket(s, port);
	}

	public void start() throws IOException {
		ProtocolHandler ph = new ChatFullDuplexHandler(clientSocket);
		ph.handle();
	}

	private Socket clientSocket;
}