//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;


class ChatHalfDuplexTCPServer { 
	
	private ServerSocket welcomeSocket;
	
	
	
	public static void main(String argv[]) throws Exception {
		
			
			try {	
				ChatHalfDuplexTCPServer chs = new ChatHalfDuplexTCPServer(6789);
				chs.start();
			} catch(Exception e) {
				System.out.println(e);
			}
	}//fine main

	
	
	public ChatHalfDuplexTCPServer (int port) throws Exception {
		welcomeSocket = new ServerSocket(port);
	}

	
	
	public void start() throws IOException {
		Socket connectionSocket = welcomeSocket.accept();
		ProtocolHandler ph = new ChatHalfDuplexHandler(connectionSocket, ChatHalfDuplexHandler.PEER);
		ph.handle();
	}

	
	
	
}