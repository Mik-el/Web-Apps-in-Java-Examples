//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;

class DgramClient { 
	public static void main(String args[]) throws Exception { 
		
		//0---variabili per la somma e per il flusso di byte
		int a,b;
		long sum;
		
		byte[] datiInviati; 
		byte[] datiRicevuti = new byte[1024]; 
		
		Scanner inFromUser = new Scanner(System.in); //
		
		/*1---inizializzo socket del client (di tipo udp stavolta)*/
		DatagramSocket clientSocket = new DatagramSocket(); 
		
		/*2---Indirizzo ip client*/
		InetAddress IP = InetAddress.getLocalHost(); 
	
		//----
		
		/*3---Acquisisco gli interi da tastiera*/
		System.out.println("Inserisci il primo numero da sommare: ");
		a = inFromUser.nextInt(); 

		System.out.println("Inserisci il secondo numero da sommare: ");
		b = inFromUser.nextInt(); 
		
		/*4---creo 2 variabili per lavorare con i dati in uscita
		 * "bufAlServer" di tipo ByteArrayOutputStream
		 *  "shortStreamtoServer" di tipo DataOutputStream, derivata dalla precedente*/
		
		ByteArrayOutputStream bufAlServer = new ByteArrayOutputStream();

		DataOutputStream shortStreamToServer = new DataOutputStream(bufAlServer);
		
		/*5--- scrivo i numeri a e b sul DataOutputStream*/
		shortStreamToServer.writeInt(a);

		shortStreamToServer.writeInt(b);
		
		/*6--- converto la variabile di tipo ByteArrayOutputStream in un array di byte */
		datiInviati = bufAlServer.toByteArray();         
		
		/*7--- creo i DatagramPacket in entrata e in uscita per poi inviarli e riceverli*/
		DatagramPacket packetUscita = new DatagramPacket(datiInviati, datiInviati.length, IP, 1200); 
		
		clientSocket.send(packetUscita); 
		
		DatagramPacket packetEntrata = new DatagramPacket(datiRicevuti, datiRicevuti.length); 
		
		clientSocket.receive(packetEntrata); 
		
		
		/*8--- ricaviamo l' array di byte in entrata  dai dati nel pacchetto in entrata ( packetEntrata.getData())
		 * 	   ricaviamo il flusso di dati in entrata dall' array di byte
		 * 	   ricaviamo la somma dal flusso di dati ( .readLong() ) */
		ByteArrayInputStream bufFromServer = new ByteArrayInputStream(packetEntrata.getData());

		DataInputStream shortStreamFromServer = new DataInputStream(bufFromServer);

		sum = shortStreamFromServer.readLong();
		
		
		
		System.out.println("La somma di " + a + " e " + b + " e': " + sum); 
		
		clientSocket.close(); 
	} 
} 
