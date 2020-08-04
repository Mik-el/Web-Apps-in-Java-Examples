//code by Mik-el
import java.io.*; 
import java.net.*; 

class DgramServer { 
	public static void main(String args[]) throws Exception { 
		
		DatagramSocket serverSocket = new DatagramSocket(1200); 
		
		byte[] receiveData = new byte[1024]; 
		byte[] sendData;//  = new byte[1024]; 
		
		while(true) { 
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
			serverSocket.receive(receivePacket); 
			String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength()); 
			
			String capitalizedSentence = sentence.toUpperCase(); 
			sendData = capitalizedSentence.getBytes();
			
			InetAddress IPAddress = receivePacket.getAddress(); 
			
			int port = receivePacket.getPort(); 
			
			DatagramPacket sendPacket = new	DatagramPacket(sendData, sendData.length, IPAddress, port); 
			
			serverSocket.send(sendPacket); 
		} 
	} 
}  
