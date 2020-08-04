//code by Mik-el
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class SimpleFTPHandlerServer implements ProtocolHandler {

	private Socket socket;
	public static final byte GET = 1;
	public static final byte LIST = 0;
	public static final byte CLOSE = 2;
	
	public SimpleFTPHandlerServer(Socket soc) {
		socket = soc;
	}

	public void handle() {
		DataInputStream fromClient;
		DataOutputStream toClient;
		try {
			fromClient = new DataInputStream(socket.getInputStream());
			toClient = new DataOutputStream(socket.getOutputStream());
			File f = null;
			int command = LIST;
			do {
				// Il comando viene recuperato fuori dallo switch
				command = fromClient.read();
				switch (command) {
				case LIST:
					f = new File(".");// indica la directory corrente(quella che vede il server) dove si sta
										// lavorando.
					String[] list = f.list();
					toClient.writeInt(list.length);
					for (int i = 0; i < list.length; i++) {
						toClient.writeUTF(list[i]);
					}
					break;
				case GET:
					String fileName = fromClient.readUTF();

					f = new File(fileName);
					// Avendo un oggetto file posso verificare se esite il file
					if (!f.exists())
						// se il file no esiste invio al client la dimensione 0
						toClient.writeLong(0);
					else {
						// se il file esite
						long fileLen = f.length();
						toClient.writeLong(fileLen);

						FileInputStream fis = new FileInputStream(f);// Creo un oggetto fileInputStream per leggere
																		// quello che c'e' nel file
						byte[] buf = new byte[1024];
						int contatore = 0;
						// Leggo dal file
						while (contatore < fileLen) {
							int read = fis.read(buf);
							toClient.write(buf, 0, read);
							contatore += read;
						}
						fis.close();
					}
					break;
				}
			} while (command != CLOSE);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
// Quando termina l'handle il server torna in attesa