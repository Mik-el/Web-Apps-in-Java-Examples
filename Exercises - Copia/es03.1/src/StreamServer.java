//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;


class StreamServer { 
	
	public static void main(String argv[]) throws Exception { 
		String clientSentence; 
		String capitalizedSentence; 
		
		ServerSocket welcomeSocket = new ServerSocket(6789); 
		
		while(true) {
			
			Socket connectionSocket = welcomeSocket.accept(); 
			
			Scanner inFromClient = new Scanner(connectionSocket.getInputStream()); 
			
			PrintStream outToClient = new PrintStream(connectionSocket.getOutputStream()); 
			
			clientSentence = inFromClient.nextLine(); 
			
			capitalizedSentence = clientSentence.toUpperCase(); 
			
			outToClient.println(capitalizedSentence);
			
			connectionSocket.close(); 
		} 
	} 
} 
