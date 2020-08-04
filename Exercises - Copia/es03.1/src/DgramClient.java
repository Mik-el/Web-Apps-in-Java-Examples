//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;

class DgramClient { 
	public static void main(String args[]) throws Exception { 
		
		Scanner inFromUser = new Scanner(System.in); 
		
		DatagramSocket clientSocket = new DatagramSocket(); 
		
		InetAddress IPAddress = InetAddress.getLocalHost(); 
	        
		// Show the local address 
		System.out.println(IPAddress);
	
		byte[] sendData; // = new byte[1024]; 
		byte[] receiveData = new byte[1024]; 
		
		System.out.println("Inserisi una stringa in minuscolo");
		
		String sentence = inFromUser.nextLine(); 
		sendData = sentence.getBytes();         
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1200); 
		
		clientSocket.send(sendPacket); 
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
		
		clientSocket.receive(receivePacket); 
		
		String modifiedSentence = new String(receivePacket.getData()); 
		
		System.out.println("FROM SERVER:" + modifiedSentence); 
		
		clientSocket.close(); 
	} 
} 
