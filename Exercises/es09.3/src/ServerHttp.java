//code by Mik-el
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ServerHttp {
	
	private int port = 8080;
	private String root = ".";
	
	public ServerHttp(){
	}

	public static void main (String[] args) {
		try {
			ServerHttp sfs = new ServerHttp();
			sfs.configure();
			sfs.start();
		}catch (Exception e) {
			System.err.println(e);
		}
	}

	private void configure() {
		try {
			File config = new File("webserver.conf");
			
			if(config.exists()) {
				Scanner sc = new Scanner(config);
				String port = "";
				String root = "";
				if(sc.hasNext())port = sc.nextLine();
				if(sc.hasNext())root = sc.nextLine();
				
				StringTokenizer token1 = new StringTokenizer(port);
				if(token1.hasMoreTokens() && token1.nextToken().equals("port")) port = token1.nextToken();
				System.out.println(port);
				StringTokenizer token2 = new StringTokenizer(root);
				if(token2.hasMoreTokens() && token2.nextToken().equals("root")) root = token2.nextToken();
				System.out.println(root);
				
				try {
					this.port = Integer.parseInt(port);
				}catch(NumberFormatException e) {}
				
				if(!root.equals("")) this.root = root;
				
			}
			
		}catch(FileNotFoundException e) {
			System.err.println("File di configurazione non trovato\n");
		}
		
	}

	private void start() throws IOException {
		ServerSocket welcomeSocket = new ServerSocket(port);
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			ProtocolHandler request = new ServerHttpHandler(connectionSocket, root);
			try {
				request.handle();
			}catch (IOException e) {
				System.err.println("Errore impossibile accetare la richiesta\n");
			}
			
		}
		
	}
	
	class ServerHttpHandler extends Thread implements ProtocolHandler{
		
		private Socket socket;
		private String root;
		public static final String CRLF = "\r\n";

		public ServerHttpHandler(Socket connectionSocket, String root) {
			this.socket = connectionSocket;
			this.root = root;
		}

		@Override
		public void handle() throws IOException{
			this.start();
		}
		
		public void run() {
			try {
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				HttpRequestImpl req = new HttpRequestImpl(is, root);
				HttpResponseImpl res = new HttpResponseImpl(os);
				res.setConnection("close");
				try {
					File resurce = new File(req.getFileName());
					
					if(resurce.exists()) {
						res.setStatusLine("HTTP/1.1 200 OK");
						res.setContentType(contentType(req.getFileName()));
						res.setCacheControl("private, max-age = 60");
						
						res.flushHeader();
						res.sendFile(resurce);
					}else {
						res.setStatusLine("HTTP/1.1 404 NOT FOUND");
						res.setContentType("text/html");
						res.setCacheControl("no-cache");
						
						res.flushHeader();
						res.setEntityBody("<html><head><title>File Non Trovato</title></head><body><br>ERRORE 404<br><h1>FILE NON TROVATO</h1></body></html>");
					}
				}catch(Exception e){
					res.setStatusLine("HTTP/1.1 500 INTERNAL SERVER ERROR");
					res.setContentType("text/html");
					res.setCacheControl("no-cache");
					
					res.flushHeader();
					res.setEntityBody("<html><head><title>Server Error</title></head><body><br>ERRORE 404<br><h1>Internl server error</h1></body></html>");
				}finally {
					res.flushBody();
					if(res.getConnection().equalsIgnoreCase("close")) {
						os.close(); socket.close();
						System.out.println("Connessione Chiusa\n");
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		private String contentType(String fileName) {
			if(fileName.endsWith(".html"))return "text/html";
			
			if(fileName.endsWith(".gif"))return "image/gif";
			
			if(fileName.endsWith(".jpeg") || fileName.endsWith(".jpg"))return "image/jpeg";
			
			
				return "";
			
		}
		
	}
	class HttpRequestImpl implements HttpRequest {
		private final static String CRLF = "\r\n";
		private BufferedReader sourceReader;
		private InputStream sourceStream;
		private String connection;
		private String requestLine;
		private String fileName;
		private String contentType;
		private String method;
		private String queryString;
		private String accept;
		private String userAgent;
		private int contentLength;
		private HashMap<String, String> parameters = new HashMap<String, String>();
		// AGGIUNTA QUESTA VARIABILE
		private boolean executable;
		
		
		public HttpRequestImpl(InputStream source, String root) throws IOException {
			this.sourceStream = source;
			this.sourceReader = new BufferedReader(new InputStreamReader(source), 1);
	        
			
			requestLine = sourceReader.readLine();
			System.out.println("\n"+requestLine);
			
			StringTokenizer tokens = new StringTokenizer(requestLine);
				
			method = tokens.nextToken();
			
			String resourceName = tokens.nextToken();  
			
			while (assignHeader(sourceReader.readLine()));
			  
			if (method.equalsIgnoreCase("GET")) {
				tokens = new StringTokenizer (resourceName, "? ");
				fileName = tokens.nextToken();
				if (tokens.hasMoreElements()) 
					queryString = URLDecoder.decode(tokens.nextToken(), "Utf-8");
			}
			if (method.equalsIgnoreCase("POST")) {
				fileName = resourceName;
				if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded"))
					queryString = URLDecoder.decode(readString(contentLength), "Utf-8");
			}
			
			setParameters(queryString);
			
			
			if (fileName.equals(File.separator)) fileName = File.separator +"index.html";
			
			executable = fileName.endsWith("class"); 
			if (!executable) 
				fileName = root + fileName;
			else 
				fileName = "./bin" + fileName; // aggiunto ./bin per consentire l'esecuzione da ECLIPSE
			
			System.out.println("fileName " + fileName);
		}

		public boolean assignHeader(String line) {
			if (line.equals("")) return false; 
			else {
				String field = line.substring(0, line.indexOf(":"));
				String value = line.substring(line.indexOf(":")+1, line.length());
				
				System.out.println(field + ": "+ value);
			
				if (field.equalsIgnoreCase("Connection")) connection = value.trim();
				else if (field.equalsIgnoreCase("Content-Type")) contentType = value.trim();
				else if (field.equalsIgnoreCase("Content-Length")) contentLength = Integer.parseInt(value.trim());
				else if (field.equalsIgnoreCase("Accept")) accept = value.trim();
				else if (field.equalsIgnoreCase("User-Agent")) userAgent = value.trim();
				
				return true;
	        }  
		}
		
		public String readString (int len) throws IOException {
			int bytes = 0, pos = 0;
			char[] buf = new char[len];
			while (bytes < len ) 
				bytes += sourceReader.read(buf, pos+=bytes, len-bytes);
			return new String(buf);  
		}
		
		
		public void setParameters(String query) {
			if (query == null) return;
		
			StringTokenizer st = new StringTokenizer (query);
			while(st.hasMoreTokens()) {
				
				parameters.put(st.nextToken("=").replace('&', ' ').trim(),
							   st.nextToken("&").replace('=', ' ').trim());
			}
		}
		
		String getFileName() { return fileName; }
		boolean isExecutable() { return executable; }
		
		public String getConnection() { return connection; }
		public String getQueryString() { return queryString; }
		public String getAccept() { return accept; }
		public String getUserAgent() { return userAgent; }
		public InputStream getInputStream () { return sourceStream; }
		public BufferedReader getReader () { return sourceReader; }
		public String getParameter(String param) { return parameters.get(param); }

		
		public String getMethod() {
			return method;
		}
		
	}



	class HttpResponseImpl implements HttpResponse {
		private final static String CRLF = "\r\n";
		private DataOutputStream destStream;
		private PrintWriter	destWriter;
		
		private String statusLine;
		private String connection;
		private String contentType;
		private String entityBody;
		private String contentLength;
		private String cacheControl;
		private String lastModified;
		
		public HttpResponseImpl(OutputStream dest) throws IOException {
			this.destStream = new DataOutputStream(dest);
			this.destWriter = new PrintWriter(new OutputStreamWriter(destStream));
		}
		
		public OutputStream getOutputStream() {return destStream; }
		public PrintWriter getWriter() { return destWriter; }
		
		
		public void flushHeader() throws IOException {
			
			destStream.writeBytes(statusLine +  CRLF);
			if (connection != null) destStream.writeBytes("Connection: " + connection + CRLF);
			if (contentType != null) destStream.writeBytes("Content-Type: " + contentType + CRLF);
			if (contentLength != null) destStream.writeBytes("Content-Length: " + contentLength + CRLF);
			if (cacheControl != null) destStream.writeBytes("Cache-Control: " + cacheControl + CRLF);
			destStream.writeBytes(CRLF);
		}
		
		void setStatusLine(String sl) { statusLine = sl; }
		void setEntityBody(String body) { entityBody = body; }
		void setConnection(String con) { connection = con; }
		void setCacheControl(String cc) { cacheControl = cc; }
		void setLastModified(String lm) { lastModified = lm; }
		public void setContentType(String ct) { contentType = ct; }
		public void setContentLength(int len) { contentLength = String.valueOf(len); }

		String getConnection() {return connection; }
		
			
		void flushBody() throws IOException {
			if (entityBody != null) destStream.writeBytes(entityBody);
			destStream.flush();
		}
	
		void sendFile(File file) throws IOException {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int bytes = 0;
			while((bytes = fis.read(buffer)) != -1 )  {
				destStream.write(buffer, 0, bytes);
			}
			fis.close();
		}
	}
}
	
