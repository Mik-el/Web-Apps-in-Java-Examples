//code by Mik-el
import java.io.*; 
import java.net.Socket; 
import java.util.Scanner;

public class Reader extends Thread { 
	
	
	private Socket socket;
	
	//costruttore
	public Reader(Socket s) {
		socket = s;
	}

	public void run() {
		Scanner dalPeer = null;
		PrintStream versoUtente = null;
		
		try {
			dalPeer = new Scanner(socket.getInputStream()); //acquisisce l'input dalla Socket 
		
			versoUtente = System.out; //per stampare a video
		
			String stringa = null;
		
						do{
							stringa = dalPeer.nextLine();
							versoUtente.print("Peer: ");
							versoUtente.println(stringa);
						} while (!stringa.endsWith("."));
						
						versoUtente.println("Peer: terminato");
		}
		
		catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		
		finally {
						try { 
								socket.shutdownInput(); 
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
						
		  if(versoUtente != null) versoUtente.close();
		  //if(dalPeer != null) dalPeer.close();
		}
		
	}

	
}