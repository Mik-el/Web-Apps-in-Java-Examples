//code by Mik-el
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements ProtocolHandler {
	
	//variabile
	private Socket socket;
	
	//costruttore
	public ClientHandler(Socket soc) {
		socket = soc;
	}

	
	
	
	public void handle() {
		
		Scanner scanUtente = null;
		
		try {	
			scanUtente = new Scanner(System.in);
			PrintStream toUser = new PrintStream(System.out);
			
			DataInputStream fromServer = new DataInputStream(socket.getInputStream());
			DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
			int n = 0; //numero inserito 
			
			
			//blocco do-while nel try
			do {
				toUser.print("Inserisci il numero di cui vuoi calcolare il fattoriale (deve essere un intero ): ");
				try{
					n = Integer.parseInt(scanUtente.next() );
					if(n<=0) {
						toUser.print("Hai inserito un numero negativo, verrà considerato positivo \n");
						n = -n;
					}
				}
				catch(NumberFormatException e) {
					toUser.print("Errore nel parsare l' inserimento, il valore da te inserito verrà considerato un 1 \n");
					n=1;
				}
				toServer.writeInt(n);
				int f = fromServer.readInt();
				toUser.println("Il fattoriale di " + n + " e' " + f);
			} while(n>0);
		} //fine try
		
		//catch
		catch (Exception e) { 
					}
		
		//finally
		finally {
						if (socket != null) try { socket.close(); } 
													catch (IOException e) {
														//System.out.println("socket nulla");
													}
						if (scanUtente != null) scanUtente.close();
					}
		}//fine finally
	
}
