//code by Mik-el
import java.io.IOException;
import java.net.Socket;

public class SimpleFTPClient {
	
	//socket del client
	private Socket clientSocket;
	
	//costruttore della classe che inizializza la socket con ip e porta che gli passiamo
	public SimpleFTPClient(String s, int port) throws Exception {
		clientSocket = new Socket(s, port);
	}
	
	public static void main(String[] args) throws Exception {

		try {
			/* creo e avvio la socket */
			SimpleFTPClient sfc = new SimpleFTPClient("127.0.0.1", 6789);
			sfc.start();
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	

	public void start() throws IOException {
		/*inizializza l'handler della socket su cui invoco handle() per gestire il protocollo di comunicaz*/
		ProtocolHandler ph = new SimpleFTPHandlerClient(clientSocket);
		ph.handle();
	}


}