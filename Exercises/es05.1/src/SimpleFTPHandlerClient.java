//code by Mik-el
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SimpleFTPHandlerClient implements ProtocolHandler {
	
	private Socket socket;
	
	public static final byte GET = 1;
	public static final byte LIST = 0;
	public static final byte CLOSE = 2;
	
	
	
	public SimpleFTPHandlerClient(Socket soc) {
		socket = soc;
	}

	
	
	public void handle() {
		DataInputStream dalServer = null;
		DataOutputStream versoIlServer = null;
		Scanner scanUtente = null;
		PrintStream printUtente = null;
		try {
			dalServer = new DataInputStream(socket.getInputStream());
			versoIlServer = new DataOutputStream(socket.getOutputStream());
			scanUtente = new Scanner(System.in);
			printUtente = System.out;
			int command = LIST;
			do {
				// Acquisisco comando e lancio i blocchi di istruz corrispondenti
				printUtente.print("Inserisci un comando tra GET LIST e CLOSE: ");
				String commandLine = scanUtente.nextLine();

				if (commandLine.equalsIgnoreCase("get"))
					command = GET;
				else if (commandLine.equalsIgnoreCase("list"))
					command = LIST;
				else if (commandLine.equalsIgnoreCase("close"))
					command = CLOSE;
				versoIlServer.write(command);
				
				switch (command) {
				
					case LIST:
						int listLength = dalServer.readInt();
						for (int i = 0; i < listLength; i++)
							printUtente.println(dalServer.readUTF());//legge stringhe unicode
						break;
						
					case GET:
						// Nel buffer di spedizione inserisco anche il nome del file
						printUtente.print("Inserisci il nome del file: ");
						String fileName = scanUtente.nextLine();
						versoIlServer.writeUTF(fileName);
	
						long lunghezzaFile = dalServer.readLong();
						if (lunghezzaFile == 0)    break;
						// Codice necessario per effettuare la lettura dal file
						int contatore = 0;
						FileOutputStream f = new FileOutputStream(fileName + ".copy");// estensione diversa per evitare di sovrascrivere
																						
						byte[] buffer = new byte[1024];
	
						// I dati letti dal canale vengono scritti sul buffer
						while (contatore < lunghezzaFile) {
							int leggi = dalServer.read(buffer);
							f.write(buffer, 0, leggi);
							contatore += leggi;
						}
						System.out.println("File trasferito correttamente");
						break;
				}//fine switch
				
				
			} while (command != CLOSE);
			printUtente.println("Programma terminato");
			
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {}
			
			scanUtente.close(); printUtente.close();
			
			try{
				if(dalServer != null) dalServer.close();
				if(versoIlServer != null) versoIlServer.close();
			} catch (IOException e) {}
		}//fine finally
	}//fine handle
}
