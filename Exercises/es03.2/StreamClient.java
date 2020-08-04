//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;

class StreamClient { 
	
	public static void main(String argv[]) throws Exception { 
		
		int a, b;
		long somma;
		
	
		Scanner inFromUser = new Scanner(System.in); //per semplificare l' invocazione del metodo 2 volte
		//1---creo la socket 
		Socket socketClient = new Socket("127.0.0.1", 6789); 
		
		/* 2---Oggetti DataOutput(in/out) della socketclient*/
		DataOutputStream outToServer = new DataOutputStream(socketClient.getOutputStream()); 
		
		DataInputStream inFromServer = new DataInputStream(socketClient.getInputStream()); 
		
		/* 3---Acquisisco da tastiera gli interi*/
		
		System.out.println("Inserisci il primo numero da sommare: ");	
		
		a = inFromUser.nextInt();

		System.out.println("Inserisci il secondo numero da sommare: ");

		b = inFromUser.nextInt();
		
		
		/* 4---Scrivo i due numeri consecutivamente sui dati in uscita*/
		outToServer.writeInt(a); 

		outToServer.writeInt(b);
		
		/* 5---leggo il risultato somma dai dati in entrata*/
		somma = inFromServer.readLong();
		
		
		System.out.println("La somma di " + a + " e " + b + " e': "+ somma); //stampo il risultato
	
		socketClient.close(); //chiudo socket
	} 
} 

