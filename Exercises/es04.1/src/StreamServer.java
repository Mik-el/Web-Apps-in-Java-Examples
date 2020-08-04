//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;


class StreamServer { 
	
	public static void main(String argv[]) throws Exception { 
		/*Socket di benvenuto*/
		ServerSocket welcomeSocket = new ServerSocket(2040); 
		
		while(true) {
			
			Socket connectionSocket = welcomeSocket.accept(); 
			ObjectInputStream ricDalClient = new ObjectInputStream(connectionSocket.getInputStream());
			Person p = (Person)ricDalClient.readObject();
			OutputStream uscDalClient = connectionSocket.getOutputStream();
			PrintStream stampaSuFile = null;
			
			/*Scrivo sul file persone.txt la persona,
			 * se ci riesco (try) passo il byte 'o' all' ogg OutputStream
			 * se invece non ci riesco (catch) passo il bye 'k'
			 * infine chiudo ogg Printstream, ogg ObjectInputStream e la socket */
			try {
				FileOutputStream outToFile = new FileOutputStream("persone.txt");
				stampaSuFile = new PrintStream(outToFile);
				stampaSuFile.print(p);
				uscDalClient.write('o');
			} catch (IOException e) {
				uscDalClient.write('k');
			}
			finally {
				if(stampaSuFile != null) stampaSuFile.close();
				if(ricDalClient != null) ricDalClient.close();
				if(connectionSocket != null) connectionSocket.close();
			}
		} 
	} 
} 
