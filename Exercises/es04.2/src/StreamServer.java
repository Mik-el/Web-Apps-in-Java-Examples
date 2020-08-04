//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;


class StreamServer { 
	
	public static void main(String argv[]) throws Exception { 
		
		
		try {
			//socket benvenuto
			ServerSocket welcomeSocket = new ServerSocket(2040); 
			
			while(true) { //devo ricevere il file dall client
				
				
				FileInputStream inFromFile = null;
				Socket socketConnessione = null; 
				PrintStream stampaSuClient = null;
				
				try {
					socketConnessione = welcomeSocket.accept(); 
					stampaSuClient = new PrintStream(socketConnessione.getOutputStream());

					inFromFile = new FileInputStream("persone.txt");
					Scanner scanFromFile = new Scanner(inFromFile);
					String line = null;

					while(scanFromFile.hasNext()){
						line = scanFromFile.nextLine();
						stampaSuClient.println(line);
					}
			} catch (IOException e) {

			}
			  finally {
					try { //chiudo scanner, printstream e socket se non lo sono
						if(stampaSuClient != null) stampaSuClient.close();
						if(inFromFile != null) inFromFile.close();
						if(socketConnessione != null) socketConnessione.close();
					} catch(IOException e) {}
			   }
			}
			
	}catch (IOException e) {
	}
	}
}