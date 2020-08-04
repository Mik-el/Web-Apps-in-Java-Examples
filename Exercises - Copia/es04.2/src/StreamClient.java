//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;

class StreamClient { 
	
	public static void main(String argv[]) throws Exception { 
		
		/*1---socket e input dal server*/
		Socket socketClient = null;
		InputStream inDalServer = null;

		/*2---inizializzo socket e leggo il file ricevuto*/
		try {
			socketClient = new Socket("127.0.0.1", 2040);

			Scanner scanDalServer = new Scanner(socketClient.getInputStream());

			String s = null;
			
			while(scanDalServer.hasNext()){
				s = scanDalServer.nextLine();
				System.out.println(s);
			}
		} catch(IOException e) {
			
		}
		  finally {
			  
				try { //chiudo l'inputstream dal server e la socket se non lo sono
					if(inDalServer != null) inDalServer.close();
					if(socketClient != null) socketClient.close();
				} catch (IOException e) {}
		  }
	}
}