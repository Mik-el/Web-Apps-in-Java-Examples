//code by Mik-el
package server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;
import webletApi.*;


public class WebServerWithEngine {
	// Valori di default per il numero di porta e per il percorso radice  
	private int port = 8080;
	private String root = ".";
	private String webapps = ".";
	private ServerSocket welcomeSocket;
	private WebLetEngine engine = new WebLetEngine();
	public static void main(String argv[]) {
		try {
			WebServerWithEngine server = new WebServerWithEngine();
			server.start();
		} catch (IOException e) {System.err.println("Il server non puo' essere lanciato"); }
	}
	public WebServerWithEngine() throws IOException {  
	    configure();
	    // Si crea una socket di tipo server
	    welcomeSocket = new ServerSocket(port);

        }
	public void start() throws IOException {
		// Elabora le richieste http in un loop infinito
		while (true) {
			// Accetta richieste di connessione
			Socket connectionSocket = welcomeSocket.accept();
			// Costruisce un oggetto per elaborare un messaggio di richiesta HTTP
			ProtocolHandler request = new ConcurrentHttpHandler(connectionSocket, root, webapps, engine);
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
					if (confLine.startsWith("webapps")) webapps = confLine.substring(7).trim();
				}
			} catch (FileNotFoundException e) { /* never reached */ 
			} finally { 
				try {
					if (fis != null) fis.close();
				} catch (IOException e) { System.err.println("Errore durante la chiusura del file di configurazione"); }	
			}
		} else System.out.println("Info: file di configurazione non trovato");			
	}	
}


// Classe Runnable per trasformare l'handler da sequenziale a concorrente
class ConcurrentHttpHandler extends HttpHandler implements Runnable {
	private Thread activeHandler;
	public ConcurrentHttpHandler(Socket socket, String root, String webapps, WebLetEngine engine) {
		super(socket, root, webapps, engine);
		activeHandler= new Thread(this);
	}	  
	public void handle() {
		activeHandler.start();
	}	
	public void run() {
		try {
			super.handle();
		} catch (IOException e) { System.err.println("La richiesta non puo' essere elaborata"); }	  
	}	  
}	


class WebLetEngine {
	// Contenitore di WebLet caricate su richiesta dei client
	private HashMap<String, WebLet> webLets = new HashMap<String, WebLet>(); 	
	// Il metodo che segue carica una WebLet dall'esterno (se non caricata in precedenza) 
	// la memorizza nell'engine e ne manda in esecuzione il metodo service 
	public void invokeService(File file, HttpRequest req, HttpResponse res) 
	throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		WebLet wl = null;
		// Si verifica se la WebLet è stata caricata in precedenza
		wl = webLets.get(file.getName()); 
		if (wl == null) {
			// Trasformazione del path in un nome completo di classe
			String className = file.getPath().replaceAll("\\"+File.separator, ".");
			
			// Rimozione dei due punti iniziali ed eliminazione del suffisso .class
			className = className.substring(2).replaceAll(".class", "");	
			
			Class<?> externalClass = Class.forName(className);
			// Si istanzia un oggetto usando il costruttore senza argomenti
			
			wl = (WebLet)externalClass.newInstance();
			// Si inserisce la nuova WebLet nella HashMap per successivi usi
			webLets.put(file.getName(), wl);
			// Se la WebLet avesse un metodo di inizializzazione dovrebbe essere chiamato in questo punto
		}
		// E' comodo avere l'invocazione di flushHeader in questo punto, ma la soluzione è meno
		// flessibile perchè non si dà alla componente applicativa la possibilità di modificare
		// l'intestazione. Quindi considerare l'ipotesi di spostare l'invocazione nella componente applicativa.
		//res.flushHeader();
		// Si invoca il metodo di servizio passandogli i messaggi di richiesta e di risposta
		wl.service(req, res);
	}
}	


class HttpHandler implements ProtocolHandler {
  private Socket socket;
  private String root;	
  private String webapps;
  private WebLetEngine engine;
  
  public HttpHandler(Socket socket, String root, String webapps, WebLetEngine engine) {
    this.socket = socket;
    this.root = root; 
    this.webapps = webapps; 
    this.engine = engine;
  }
  
  public void handle() throws IOException { 
    // Si recuperano i riferimenti agli stream di input e di output
    InputStream  is = socket.getInputStream();
    OutputStream os = socket.getOutputStream();
    HttpRequestImpl req = null; 
    HttpResponseImpl res = new HttpResponseImpl(os);
    res.setConnection("close");
    try {
      req = new HttpRequestImpl(is, root, webapps);
      File resource = new File(req.getFileName());
      
      if (resource.exists()) {
        res.setStatusLine("HTTP/1.1 200 OK");
      
        if (req.isExecutable())  // Dynamic resource
	    try {
		System.out.println("Debug: componente da eseguire");	// Per solo scopo di debugging	
		res.setContentType("text/plain");
		res.setCacheControl("no-cache");
		engine.invokeService(resource, req, res);
            } catch (ClassNotFoundException e) { System.err.println("La risorsa richiesta non è disponibile"); throw new ServerException();
            } catch (IllegalAccessException e) { System.err.println("La risorsa non è accessibile"); throw new ServerException();
            } catch (InstantiationException e) { System.err.println("La classe non è istanziabile"); throw new ServerException(); 
	    } catch (Exception e) { System.err.println("Generico errore interno " + e); }
        else {  // Static resource
		System.out.println("Debug: risorsa statica ->" +req.getFileName());
		res.setContentType(contentType(req.getFileName()));
		res.setCacheControl("private, max-age =10");
		res.setLastModified(new Date(resource.lastModified()));
			
		res.flushHeader();
			
		res.sendFile(resource);
        }   
      } else {  // Resource not found 
        res.setStatusLine("HTTP/1.1 404 Not Found");
        res.setCacheControl("no-cache");
        res.setContentType("text/html");
        res.flushHeader();
        res.setEntityBody("<HTML>" +
            "<HEAD> <TITLE>Not Found</TITLE> </HEAD>" +
            "<BODY> Not Found </BODY> </HTML>");
		
      }
      
    } catch (ServerException e) {
        res.setStatusLine("HTTP/1.1 500 Internal Server Error");
        res.setCacheControl("no-cache");
        res.setContentType("text/html");
        res.flushHeader();
        res.setEntityBody("<HTML>" +
            "<HEAD> <TITLE>Internal Server Error</TITLE> </HEAD>" +
            "<BODY> Internal Server Error </BODY> </HTML>");
    }
    finally {
      res.flushBody();
      if (res.getConnection().equalsIgnoreCase("close")) {
         os.close(); socket.close(); }
    }
  }  


  private String contentType(String fileName) {
	  if(fileName.endsWith(".htm") || fileName.endsWith(".html"))
	      return "text/html";
	  if(fileName.endsWith(".gif"))
	      return "image/gif";
	  if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
	      return "image/jpeg";
	  try {
	  	  return Files.probeContentType(new File(fileName).toPath());  
	  } catch (IOException e) { return "application/octet-stream"; }	
  }
}




class HttpRequestImpl implements HttpRequest {
	private final static String CRLF = "\r\n";
	private BufferedReader sourceReader;
	private InputStream sourceStream;
	// Le seguenti variabili di istanza sono usate per memorizzare i valori dei campi delle linee di intestazione
	// In alternativa alle seguenti variabili di istanza è possibile usare una HashMap
	
	private HashMap<String, String> headers = new HashMap<String, String>();
	private HashMap<String, String> parameters = new HashMap<String, String>();
	
	private String method;
	private String fileName;
	private String queryString;
	private boolean executable;
	
	
	public HttpRequestImpl(InputStream source, String root, String webapps) throws IOException {
		this.sourceStream = source;
		this.sourceReader = new BufferedReader(new InputStreamReader(source), 1);
	
		String requestLine = sourceReader.readLine();
		System.out.println("\n" + requestLine);
		// Per evitare la creazione di un oggetto richiesta nel caso il messaggio sia vuoto
		if (requestLine == null) throw new IOException();
		
		StringTokenizer tokens = new StringTokenizer(requestLine);
		
		method = tokens.nextToken();
		// Estrae l'URI della risorsa (contenente anche gli eventuali parametri)
		String resourceName = tokens.nextToken();  
		// Analizza le linee di intestazione e assegna i valori dei campi alle variabili di istanza
		while (assignHeader(sourceReader.readLine()));
		
		fileName = resourceName;
		// A questo punto tutta l'intestazione del messaggio di richiesta è stata analizzata e rimossa dallo stream  
		if (method.equalsIgnoreCase("GET")) 
			if (resourceName.contains("?")) { 
			  tokens = new StringTokenizer (resourceName, "? ");
			  fileName = tokens.nextToken();
			  if (tokens.hasMoreElements()) queryString = URLDecoder.decode(tokens.nextToken(), "Utf-8");
			}	
		if (method.equalsIgnoreCase("POST")) 
			if (getContentType()!= null  &&
				getContentType().equalsIgnoreCase("application/x-www-form-urlencoded"))
					queryString = URLDecoder.decode(readString(getContentLength()), "Utf-8");
		
		// Costruzione della mappa dei parametri dalla queryString
		setParameters(queryString);
		
	
		if (fileName.equals(File.separator)) fileName = File.separator +"index.html";
	
		// Si assume che una risorsa sia identificata come eseguibile se ha estensione .class
		// In alternativa si potrebbe usare il percorso contenuto nel request-URI
		executable = fileName.endsWith("class");          
		if (!executable) {
			// Si antepone il percorso specificato nel file di configurazione
			// in modo che il file sia recuperato a partire dalla document directory del server
			fileName = root + fileName;
		} else {
			// fileName = "./bin" + fileName;  // TO EXECUTE UNDER ECLIPSE
			fileName = webapps + fileName;
		}	
		System.out.println("---------- Fine messaggio richiesta ----------------");
		System.out.println();
		System.out.println("Risorsa richiesta: "+ fileName);
	}
	
	// Assegna il valore del campo alla relativa variabile di istanza se line contiene un header. 
	// Restituisce true se la linea contiene un header false altrimenti (stringa vuota)
	public boolean assignHeader(String line) {
		if (line.equals("")) return false; 
		else {
			String field = line.substring(0, line.indexOf(":"));
			String value = line.substring(line.indexOf(":")+1, line.length());
			
			System.out.println(field +":"+ value); // per debugging
			headers.put(field.trim(), value.trim());
			return true;
        	}  
	}
	
	// Legge dallo stream di input (body) una sequenza di len caratteri (ASCII) e restituisce una stringa che li contiene
	public String readString (int len) throws IOException {
		int bytes = 0;
		char[] buf = new char[len];
		while (bytes < len ) 
			bytes += sourceReader.read(buf, bytes, len-bytes);
		return new String(buf);  
	}
	
	// Assegna il contenuto della stringa query alla hashMap dei parametri
	public void setParameters(String query) {
		if (query == null) return;

		StringTokenizer st = new StringTokenizer (query);
		while(st.hasMoreTokens()) 
			parameters.put(st.nextToken("=").replace('&', ' ').trim(),
						   st.nextToken("&").replace('=', ' ').trim());
	}
	
	// Se si utilizza una HashMap i metodi get sono operazioni di accesso alla HashMap
	String getFileName() { return fileName; }
	boolean isExecutable() { return executable; }
	
	public String getConnection() { return headers.get("Connection"); }
	public String getAccept() { return headers.get("Accept"); }
	public String getUserAgent() { return headers.get("User-Agent"); }
	public String getContentType() { return headers.get("Content-Type"); }
	public int getContentLength() { return Integer.parseInt(headers.get("Content-Length")); }
	
	public InputStream getInputStream () { return sourceStream; }
	public BufferedReader getReader () { return sourceReader; }
	public String getQueryString() { return queryString; }
	public String getParameter(String param) { return parameters.get(param); }
	
}



class HttpResponseImpl implements HttpResponse {
	private final static String CRLF = "\r\n";
	private DataOutputStream destStream;
	private PrintWriter	destWriter;
	private HashMap<String, String> headers = new HashMap<String, String>(); 
	// Anche in questo caso si può utilizzare una HashMap
	private String statusLine;
	private String entityBody;

	public HttpResponseImpl(OutputStream dest) throws IOException {
		this.destStream = new DataOutputStream(new BufferedOutputStream(dest));
		this.destWriter = new PrintWriter(new OutputStreamWriter(destStream));
	}
	
	void setStatusLine(String sl) { statusLine = sl; }
	void setEntityBody(String body) { entityBody = body; }
	void setConnection(String con) { headers.put("Connection", con); }
	void setCacheControl(String cc) { headers.put("Cache-Control", cc); }
	void setLastModified(Date d) { headers.put("LastModified", ""+d); }
	
	public void setContentType(String ct) { headers.put("Content-Type", ct); }
	public void setContentLength(int len) { headers.put("Content-Length",  ""+len); }

	String getConnection() {return headers.get("Connection"); }
	
	public OutputStream getOutputStream() {return destStream; }
	public PrintWriter getWriter() { return destWriter; }
	
	// Il metodo che segue invia sullo stream di output l'intestazione del messaggio
	public void flushHeader() throws IOException {
		// E' possibile usare anche destWriter in alternativa 
		System.out.println("\nDebug: response message");  			// Debug
		destStream.writeBytes(statusLine +  CRLF);
		System.out.print(statusLine + CRLF);			  		// Debug
		
		Iterator<String> iter = headers.keySet().iterator();
		
		while (iter.hasNext()) {
			String header = iter.next();
			String line = header + ": " + headers.get(header) + CRLF; 
			destStream.writeBytes(line);
			System.out.print(line);						// Debug
		}	

		destStream.writeBytes(CRLF);
	}
	// Il metodo che segue effettua il flush dello stream di output per inviare il body	
	void flushBody() throws IOException {
		if (entityBody != null) destStream.writeBytes(entityBody);
		destStream.flush();
	}
	
	// Spedisce un file al client attraverso il messaggio di risposta
	void sendFile(File file) throws IOException {
		// Impiega un buffer di 1KB per recuperare blocchi di
		// byte dal file ed inviarli nello stream di rete
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int bytes = 0;
		// Copia i blocchi dal file allo stream di output.
		while((bytes = fis.read(buffer)) != -1 )  {
			destStream.write(buffer, 0, bytes);
		}
		fis.close();
	}  
	
	
}


class ServerException extends Exception { 
}
