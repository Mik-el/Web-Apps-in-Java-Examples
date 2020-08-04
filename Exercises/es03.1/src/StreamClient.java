//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;

class StreamClient { 
	
	public static void main(String argv[]) throws Exception { 
		String sentence; 
		String modifiedSentence; 
		
		Scanner inFromUser = new Scanner(System.in); 
		
		Socket clientSocket = new Socket("127.0.0.1", 6789); 
		
		PrintStream outToServer = new PrintStream(clientSocket.getOutputStream()); 
		
		Scanner inFromServer = new Scanner (clientSocket.getInputStream()); 
		
		System.out.println("Inserisci una linea in minuscolo");	
		
		sentence = inFromUser.nextLine(); 
		
		outToServer.println(sentence); 
		
		modifiedSentence = inFromServer.nextLine(); 
		
		System.out.println("FROM SERVER: " + modifiedSentence); 
	
		clientSocket.close(); 
	} 
} 

