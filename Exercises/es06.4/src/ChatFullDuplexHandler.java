//code by Mik-el
import java.net.*;

public class ChatFullDuplexHandler implements ProtocolHandler { 
	//variabile 
	private Socket socket;
	//costruttore
	public ChatFullDuplexHandler (Socket soc) {
		socket=soc;
	}

	public void handle() {
		Reader reader = new Reader(socket);
		Writer writer = new Writer(socket);

		reader.start();
		writer.start();
	}

	
}