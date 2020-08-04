//code by Mik-el
import java.io.*;
import java.net.*;

class ServerHandler implements ProtocolHandler {
	//variabile
	private Socket socket;
	
	//costruttore
	public ServerHandler(Socket soc) {
		socket = soc;
	}

	public void handle() {
		try {
			System.out.println("Sta eseguendo il thread " + Thread.currentThread());
			DataInputStream fromClient = new DataInputStream(socket.getInputStream());
			DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
			int n = 0; //intero ricevuto
			int f = 0; //fattoriale calcolato
			
			do {
				n = fromClient.readInt();
				f = fact(n);
				toClient.writeInt(f);
			} while(n>0);
			
		}	//end try
		
		//catch
		catch (Exception e) {
			System.out.println(e);
		} 
		
		//finally
		finally { 
			if (socket == null) try { 
												socket.close(); 
												} catch (IOException e) {
												}
		}
		
	}//fine metodo handle()

	//classico metodo per calcolare fattoriale
	private int fact(int n){
		if(n==0 || n==1) return 1;
		else return n*fact(n-1);
	}

	
}