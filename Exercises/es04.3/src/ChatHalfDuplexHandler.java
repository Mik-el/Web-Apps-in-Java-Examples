//code by Mik-el
import java.io.*; 
import java.net.*; 
import java.util.*;

//classe che implementa l' interfaccia che abbiamo creata, e quindi il suo metodo .handle()
class ChatHalfDuplexHandler implements ProtocolHandler { 
	
	private Socket socket;
	private byte status;

	public static final byte YOU=0;
	public static final byte PEER=1;
	private static final byte EXIT=2;

	private static final byte PASS = '-';
	private static final byte END = '.';

	public ChatHalfDuplexHandler(Socket soc, byte is) {
		socket = soc;
		status = is;
	}

	public void handle() {
		Scanner in_utente = null, in_peer = null;
		PrintStream out_utente = null, out_peer = null;
		try {
			in_utente = new Scanner(System.in);
			out_utente = System.out;
			in_peer = new Scanner(socket.getInputStream());
			out_peer = new PrintStream(socket.getOutputStream());
			String stringa = null;
			char ultimoCar = ' ';
			while(status != EXIT){
				switch(status) {
				
					case YOU:
						
						do{
							out_utente.print("You: ");
							stringa = in_utente.nextLine();
							out_peer.println(stringa);
							ultimoCar = stringa.charAt(stringa.length()-1);
							if (ultimoCar == PASS) status = PEER;
							else if (ultimoCar == END) status = EXIT;
						} while (status == YOU);
						break;
						
						
					case PEER:
						do {
							out_utente.print("Peer: ");
							stringa = in_peer.nextLine();
							out_utente.println(stringa);
							ultimoCar = stringa.charAt(stringa.length()-1);
							if (ultimoCar == PASS) status = YOU;
							else if (ultimoCar == END) status = EXIT;
						} while (status == PEER);
						break;
						
				}//fine switch
			}//fine while
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in_utente != null) in_utente.close();
			if(out_utente != null) out_utente.close();
			if(in_peer != null) in_peer.close();
			if(out_peer != null) out_peer.close();
			if(socket != null) try {socket.close(); } catch (IOException e) {e.printStackTrace();}
		}
	}
}