//code by Mik-el
import java.io.IOException;
import java.net.*;

public class ClientsTCP {
	//variabile
	private static int n_di_threads = 0;
	
	
	public static void main(String[] argv) throws Exception{

		if(argv.length >0) try { 
											n_di_threads = Integer.parseInt(argv[0]); 
									}
		
									catch (NumberFormatException e) {
										System.err.println(e);
									}
		
			for(int i=1; i<=n_di_threads; i++) {
				ThreadClientTCPFattoriale th = new ThreadClientTCPFattoriale("127.0.0.1", 6789, i*2);
				th.start();
				System.out.println("Thread numero " + i);
			} //fine for
	} //fine if
} //fine main

class ThreadClientTCPFattoriale extends Thread {
	
	private Socket clientSocket;
	private int valore;

	public ThreadClientTCPFattoriale(String host, int port, int val) throws IOException {
		clientSocket = new Socket(host, port);
		valore = val;
	}

	public void run() {
		ProtocolHandler ph = new ClientHandler(clientSocket, valore);
		ph.handle();
	}
}// fine classe ThreadClientTCPFattoriale
