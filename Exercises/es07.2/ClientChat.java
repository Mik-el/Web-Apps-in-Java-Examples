//code by Mik-el
import java.util.*;
import java.io.*;
import java.net.*;

public class ClientChat {
	
	public static void main(String args[]) {
		try {
			ClientChat cfc = new ClientChat("127.0.0.1", 8000);
			cfc.start();
		} catch(Exception e) {System.out.println(e);}
	}

	public ClientChat(String s, int port) throws Exception {
		clientSocket = new Socket(s, port);
	}
	
	public void start() throws IOException {
		ProtocolHandler ph = new ChatClientHandler(clientSocket);
		ph.handle();
	}	
	
private Socket clientSocket;
}


//--------------------------------------
class Writer extends Thread {
		private Socket socket;
		public Writer(Socket s) {
		socket = s;}
	
	
	public void run() {
		Scanner inFromUser = null;
		try {
			inFromUser = new Scanner(System.in);
			PrintStream outToServer = new PrintStream(socket.getOutputStream());
			String line;
						do{
							line = inFromUser.nextLine();
							outToServer.println(line);
						} while (!line.endsWith("."));
						System.out.println("Client->writer : terminating");
		}
		catch (IOException e) {}
		
		finally {
					try { if(socket != null) socket.shutdownOutput(); } 
					catch(IOException e) {}
					inFromUser.close();
		}//end finally 
	  }//end run()
	}//end chat


//-------------------------------------------------
class Reader extends Thread {
	private Socket socket;
	public Reader(Socket s) {
		socket = s;}
	
	public void run() {
		Scanner inFromServer = null;
		PrintStream outToUser = null;
		try {
			inFromServer = new Scanner(socket.getInputStream());
			outToUser = System.out;
			String line;
						do{
							line = inFromServer.nextLine();
							outToUser.println(line);
						} while (!line.endsWith("."));
						System.out.println("Client->reader : terminating");
		} 
		
		catch (IOException e) {}
		
		finally {
					try { if(socket != null) socket.shutdownInput(); }
					catch(IOException e) {}
		  inFromServer.close();
		}//end finally 
		
	  }//end run
	}//end class


//--------------------------------------------
class ChatClientHandler implements ProtocolHandler {
	
	private Socket socket;
	
	public ChatClientHandler(Socket s) {
		socket = s;
	}

	public void handle() {
		Writer w = new Writer(socket);
		Reader r = new Reader(socket);

		w.start();
		r.start();
	}
}