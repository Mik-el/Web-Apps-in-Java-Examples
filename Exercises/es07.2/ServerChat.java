//code by Mik-el
import java.util.*;
import java.io.*;
import java.net.*;

public class ServerChat {
	
	private ServerSocket welcomeSocket;
	
	public ServerChat(int port) throws Exception {
		welcomeSocket = new ServerSocket(port);
	}
	
	public static void main(String args[]) {
		try {
			ServerChat cs = new ServerChat(8000);
			cs.start();
		} 
		catch(Exception e) {System.err.println("Errore");}
	    }

	
	public void start() throws IOException {
		Room room = new Room();
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			//room.add(connectionSocket);
			ProtocolHandler ph = new ChatServerHandler(connectionSocket, room);
			ph.handle();
	}	
}

}

//------------------------------
class ChatServerHandler extends Thread implements ProtocolHandler {
	
	private Socket mySock;
	private Room room;
	
	public ChatServerHandler(Socket s, Room r) throws IOException {
		mySock = s;
		room = r;
		room.add(mySock);
	}

	public void handle() {
		start();
	}

	public void run() {
		try {
			Scanner input = new Scanner(mySock.getInputStream());
			boolean end = false;
			do {
				String stringa = input.nextLine();
				end = stringa.endsWith(".");
				room.broadcast(!end ? stringa : stringa.substring(0, stringa.lastIndexOf(".")));
			} while(!end);
			System.out.println("ServerChatHandler terminato");
		
		}
		catch(IOException e){
			System.err.println("Errori di input/output "); 
		}
		
	}//end run
}//end class







