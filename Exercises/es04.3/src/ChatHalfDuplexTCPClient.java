//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;

class ChatHalfDuplexTCPClient { 
	
	private Socket clientSocket;
	
	public static void main(String argv[]) throws Exception { 
		
		
			try {
				ChatHalfDuplexTCPClient chc = new ChatHalfDuplexTCPClient("127.0.0.1", 6789);
				chc.start();
			} catch(Exception e) {
				System.out.println(e);
			} 
	}//fine main

	
	
	public ChatHalfDuplexTCPClient(String s, int port) throws Exception {
		clientSocket = new Socket(s, port);
	}

	
	
	public void start() throws IOException {
		ProtocolHandler ph = new ChatHalfDuplexHandler(clientSocket, ChatHalfDuplexHandler.YOU);
		ph.handle();
	}

	
		
	
}