//code by Mik-el
import java.io.*; 
import java.net.*;


public class ChatFullDuplexTCPServer { 
	
	
	private ServerSocket welcomeSocket;
	
	//costruttore
	public ChatFullDuplexTCPServer (int port) throws IOException {
		welcomeSocket = new ServerSocket(port);
	}
	
	public static void main(String argv[]) throws Exception { 
		try {	
			ChatFullDuplexTCPServer cfs = new ChatFullDuplexTCPServer(6789);
			cfs.start();
		} catch(IOException e) {
			System.out.println(e);
		}
	}

	
	//invocato su un oggetto ChatFullDuplexTCPServer
	public void start() throws IOException {
		Socket connectionSocket = welcomeSocket.accept(); //la welcome socket di benvenuto accetta
		ProtocolHandler ph = new ChatFullDuplexHandler(connectionSocket); //oggetto dell' interfaccia protocol handler sarà istanziato creando un oggetto della classe ChatFullDuxplexHandler che implementa quell' interfaccia
		ph.handle(); //in questo caso il metodo istanza 2 oggetti, Writer e Reader che estendono la classe Thread (quei due oggetti verranno costruiti a partire dalla connection socket)
	}

	
}