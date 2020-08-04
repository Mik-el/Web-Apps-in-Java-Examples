//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;


class StreamServer { 
	
	public static void main(String argv[]) throws Exception { 
		
		int a,b;	//addendi
		long somma; //la somma e' di 8 byte!
		
		//creo socket accettazione
		ServerSocket socketBenvenuto = new ServerSocket(6789); 
		
		while(true) {
			//creo la socket di connessione accettando la connessione in entrata nella socket di benvenuto
			Socket socketConnessione = socketBenvenuto.accept(); 
			
			/*i flussi di dati in entrata e uscita dalla socket di connessione 
			 * li inizializziamo come oggetti della classe DataInputStream e DataOutputStream rispettivamente*/
			DataInputStream inputDalClient = new DataInputStream(socketConnessione.getInputStream()); 
			
			DataOutputStream outputDalClient = new DataOutputStream(socketConnessione.getOutputStream()); 
			
			/*leggiamo i numeri a, b in entrata dal client tramite 
			 * una readInt() che legge i primi  4 byte del DataInputStream che sta arrivando*/
			a = inputDalClient.readInt();

			b = inputDalClient.readInt();
			
			/*sommiamo i numeri mettendoli nella variabile somma, che scriveremo sul 
			 * nostro DataOutputStream in uscita tramite una writeLong() 
			 * */
			somma = a + b;
			
			outputDalClient.writeLong(somma);
			
			socketConnessione.close(); //chiudo la socket di connessione
		} 
	} 
} 
