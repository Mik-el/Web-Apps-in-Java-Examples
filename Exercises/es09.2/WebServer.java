//code by Mik-el
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;

// Implementazione sequenziale di un semplice Server Web - parte I (estrazione e visualizzazione del messaggio di richiesta HTTP)
public class WebServer {
	// Valori di default per il numero di porta e per il percorso radice  
	private int port = 8000;
	private String root = ".";
	private ServerSocket welcomeSocket;
	
	public static void main(String argv[]) {
		try {
			WebServer server = new WebServer();
			server.configure();
			server.start();
			System.out.println("Server avviato");
		} catch (IOException e) {System.err.println("Il server non puo' essere lanciato" + e); }
	}
	
	public WebServer() throws IOException {
		welcomeSocket = new ServerSocket(port);
	}
	public void start() throws IOException {
		// Si crea una socket di tipo server
		
		// Elabora le richieste http in un loop infinito
		while (true) {
			// Accetta richieste di connessione
			Socket connectionSocket = welcomeSocket.accept();
			// Costruisce un oggetto per elaborare un messaggio di richiesta HTTP
			ProtocolHandler request = new ConcurrentHttpHandler(connectionSocket, root);
			try {
			   request.handle();
			} catch (IOException e) { System.err.println("La richiesta non puo' essere elaborata"); }
		 }
       }

	public void configure() {
	   	// Eventuale recupero del numero di porta e del percorso radice dal file di configurazione  
		File conf = new File("webserver.conf");
		if (conf.exists()) {
			FileInputStream fis = null;
			try {
				fis	= new FileInputStream(conf);
				Scanner scanfis = new Scanner(fis);
				while (scanfis.hasNextLine()) {
					String confLine = scanfis.nextLine();
					if (confLine.startsWith("port")) port = Integer.parseInt(confLine.substring(4).trim());
					if (confLine.startsWith("root")) root = confLine.substring(4).trim();
				}
			} catch (FileNotFoundException e) { /* never reached */ 
			} finally { 
				try {
					if (fis != null) fis.close();
				} catch (IOException e) { System.err.println("Errore durante la chiusura del file di configurazione"); }	
			}
		}	
	}
}




// Gestore del protocollo HTTP. Versione in grado di elaborare solo richieste con metodo GET
  class HttpHandler implements ProtocolHandler {
  private final static String CRLF = "\r\n";
  private Socket socket;
  private String root;	
  
  
  // Costruttore
  // I parametri socket e root sono usati rispettivamente per recuperare gli stream associati al canale e per specificare la directory radice 
  public HttpHandler(Socket socket, String root) {
    this.socket = socket;
	this.root = root;  
  }
  // Interpreta il messaggio di richiesta HTTP per produrre il relativo messaggio di risposta HTTP 	
  public void handle() throws IOException {
    System.out.println("Richiesta ricevuta dal Browser");  // per debug
	  
    // Si recuperano i riferimenti agli stream di input e di output
    InputStream is = socket.getInputStream();
    // E' possibile impiegare in alternativa PrintStream  
    DataOutputStream os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    // Si costruisce uno stream filtro per leggere a linee
    // E' possibile impiegare in alternativa uno Scanner  
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    // Si recupera dallo stream la prima linea (la request line del messaggio di richiesta)  
	System.out.println(new Date()); // Per capire se la richiesta è nuova
	  
    String requestLine = br.readLine(); 
    System.out.println("\n"+requestLine);
    
    // Si estrae il nome del file dalla linea di richiesta
    // Se si usa uno Scanner e' possibile estrarre direttamente i token con il metodo next()  
    StringTokenizer tokens = new StringTokenizer(requestLine);
    // Si recupera il metodo dal primo token della request line
    String method = tokens.nextToken();
    // Si recupera il nome della risorsa richiesta dal secondo token della request line  
    String fileName = tokens.nextToken();
    // Visualizza sullo standard output il resto del contenuto del messaggio di richiesta
    String line = "";
    
    
    // Questo codice salta tutte le linee di intestazione visualizzandole sullo stdout
	do {
      line = br.readLine();
      System.out.println(line);
    } while (!line.equals(""));

if (fileName.equals(File.separator)) fileName = File.separator +"index.html";
    // Si antepone root in modo che il file sia recuperato da una posizione valida del file system
    // root rappresenta la radice per il file system gestito dal Web server   
    fileName = root + fileName;
    //System.out.println(fileName);
    // Prepara il messaggio di risposta
    File file = new File(fileName);
    String statusLine = "";
	String dateLine = "";
    // Vogliamo forzare l'uso delle connessioni non permanenti in HTTP/1.1  
    String connectionLine = "Connection: close" + CRLF;
    String contentTypeLine = "";
    //String contentLengthLine = "";
	String cacheControlLine = "";
    String lastModified = ""; 
    //String eTag = ""; 
	//String expires = "";  

    String entityBody = "";
	// Diversa costruzione del messaggio di risposta in dipendenza della presenza o meno del file richiesto  
    if (file.exists()) {
      statusLine = "HTTP/1.1 200 OK" + CRLF;
      contentTypeLine = "Content-Type: " + contentType(fileName) + CRLF;
      //contentLengthLine = "Content-Length: " + file.length() + CRLF;
	  //cacheControlLine = "Cache-Control: private, max-age=60" + CRLF; 
	  cacheControlLine = "Cache-Control: no-cache" + CRLF;
	  lastModified = "Last-Modified: " + new Date() + CRLF; 	
	  //eTag = "ETag: " + genETag() + CRLF;  // added	
	  //expires = "Expires: " + "Tue Nov 08 22:02:35 CET 2016" + CRLF;
    } else {
      statusLine = "HTTP/1.1 404 Not Found" + CRLF;
      contentTypeLine = "Content-Type: text/html" + CRLF;
      entityBody = "<HTML>" +
            "<HEAD> <TITLE>Not Found</TITLE> </HEAD>" +
            "<BODY> File Not Found </BODY> </HTML>";
	  //contentLengthLine = "Content-Length: " + entityBody.length() + CRLF;
    }
    dateLine = "Date: " + new Date() + CRLF;
	// Costruzione del messaggio di risposta con le informazioni precalcolate 

	// Invia la linea di stato (status line)
    os.writeBytes(statusLine);
    // Invia le linee di intestazione
    os.writeBytes(connectionLine);
	os.writeBytes(dateLine);
	  
	if (file.exists()) {
	  os.writeBytes(cacheControlLine);  
	  os.writeBytes(lastModified);  
	  //os.writeBytes(eTag);
	  //os.writeBytes(expires);	
	}
	  
    os.writeBytes(contentTypeLine);
    // os.writeBytes(contentLenghtLine);
    // Invia una linea vuota per indicare la fine delle
    // linee di intestazione
    os.writeBytes(CRLF);

    // Invia il body
    if (file.exists()) {
      try {
        sendFile(file, os);
      }  catch (IOException e) { System.err.println("Errore durante l'accesso al file " + file.getName()); }
    } else { os.writeBytes(entityBody); }
    // Eventuale chiusura della connessione nel caso di connessioni non permanenti
    if (connectionLine.equals("Connection: close" + CRLF)) {
      os.close(); socket.close(); 
      System.out.println("Connessione chiusa");
    }
  }

  // Restituisce il formato MIME associato ad un file attraverso l'analisi dell'estensione
  // E' possibile usare probeContentType
  private String contentType(String fileName) {
	// Il corpo di questo metodo puo' essere esteso per gestire altri formati MIME  
    if(fileName.endsWith(".htm") || fileName.endsWith(".html"))
      return "text/html";
    if(fileName.endsWith(".gif"))
      return "image/gif";
    if(fileName.endsWith(".jpg") || (fileName.endsWith(".jpeg")))
      return "image/jpeg";

	try {
		return Files.probeContentType(new File(fileName).toPath());
	} catch(IOException e) {
	} finally { return "application/octet-stream"; }
  }
	
  // Copia il file sullo stream di output os
  private void sendFile(File file, OutputStream os) throws IOException {
    // Impiega un buffer di 1KB per recuperare blocchi di
    // byte dal file ed inviarli sullo stream di rete
    FileInputStream fis = null;  
    try {
      fis = new FileInputStream(file);
      byte[] buffer = new byte[1024];
      int bytes = 0;
      // Copia i blocchi dal file allo stream di output fino alla fine del file
      while((bytes = fis.read(buffer)) != -1 )  
        os.write(buffer, 0, bytes);
    }
    finally {  
      if (fis != null) fis.close();
    }	
  }
}

class ConcurrentHttpHandler extends HttpHandler implements Runnable{
	private Thread activeHandler;
	public ConcurrentHttpHandler(Socket socket, String root){
		super(socket, root);
		activeHandler= new Thread(this);
	}
	
	public void run(){
		try{
			super.handle();
		}catch(IOException e){
			System.err.println("La richiesta non puo' essere elaborata.");
		}
	}
	
	public void handle(){
		activeHandler.start();
	}
}