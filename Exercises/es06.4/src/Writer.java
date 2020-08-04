//code by Mik-el
import java.io.IOException; 
import java.io.PrintStream; 
import java.net.Socket; 
import java.util.Scanner;

public class Writer extends Thread { 
	
	//variabile
	private Socket socket;
	
	//costruttore
	public Writer(Socket s) {
		socket = s;
	}

	public void run() {
		Scanner dallUtente = null;
		PrintStream VersoIlPeer = null;
		try {
			dallUtente = new Scanner(System.in);
		
			VersoIlPeer = new PrintStream(socket.getOutputStream());
		
			String stringa = null;
		
						do{
							System.out.print("Tu: ");
							stringa = dallUtente.nextLine();
							VersoIlPeer.println(stringa);
						} while (!stringa.endsWith("."));
						
						System.out.println("Tu: terminato");
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
						try { socket.shutdownOutput(); 
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
		  if(dallUtente != null) dallUtente.close();
		  //if(VersoIlPeer != null) VersoIlPeer.close();
		}
	}

	
}