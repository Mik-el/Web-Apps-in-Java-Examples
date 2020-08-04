//code by Mik-el
import java.io.*; 
import java.net.*; 

class DgramServer { 
	public static void main(String args[]) throws Exception { 

		int a,b;
		long somma;
		
		DatagramSocket socketServer = new DatagramSocket(1200); 
		
		byte[] datiRicevuti = new byte[1024]; 
		byte[] datiInviati; 
		
		while(true) { 
			
			/*1---array di byte ricevuti -> DatagramPacket che ricevo tramite socket*/
			DatagramPacket PacketEntrata = new DatagramPacket(datiRicevuti, datiRicevuti.length); 
			
			socketServer.receive(PacketEntrata); 
			
			/*2---ByteArrayInputStream ottenuto dal Datagram ricevuto
			 * DataInputStream ottenuto dal ByteArrayInputStream*/
			ByteArrayInputStream bufDalClient = new ByteArrayInputStream(PacketEntrata.getData());
			
			DataInputStream shortStreamFromClient = new DataInputStream(bufDalClient);
			
			/*3---leggo gli interi dal DataInputStream e li sommo*/
			a = shortStreamFromClient.readInt();

			b = shortStreamFromClient.readInt();

			somma = a + b;

			/*4---ByteArrayOutputStream
			 * DataOutputStream ottenuto dal ByteArrayOutputStream*/
			ByteArrayOutputStream bufAlClient = new ByteArrayOutputStream();

			DataOutputStream shortStreamToClient = new DataOutputStream(bufAlClient);
			
			/*5---scrivo la somma su DataOutputStream
			 * trasformo l' oggetto di tipo ByteArrayOut... in un array di byte che copio in quello dichiarato prima */
			shortStreamToClient.writeLong(somma);
			
			datiInviati = bufAlClient.toByteArray();
			
			/*6---ip, porta, datagrampacket in uscita dal server */
			InetAddress IP = PacketEntrata.getAddress(); 
			
			int port = PacketEntrata.getPort(); 
			
			DatagramPacket PacketUscita = new	DatagramPacket(datiInviati, datiInviati.length, IP, port); 
			
			socketServer.send(PacketUscita); 
		} 
	} 
}  
