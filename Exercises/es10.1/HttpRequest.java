//code by Mik-el
import java.io.*;

public interface HttpRequest {
    // Restituisce lo stream di input associato al messaggio
    public InputStream getInputStream() throws IOException;
   // Restituisce lo stream orientato ai caratteri associato al messaggio
    public BufferedReader getReader() throws IOException;
    // Restituisce la stringa contenente i parametri di Query estratti da un form
    public String getQueryString();
    // Restituisce i formati MIME accettati
    public String getAccept();
    // Restituisce il tipo e la versione dello User Agent (Browser) usato dal lato client
    public String getUserAgent();
    // Restituisce il valore del parametro specificato
    public String getParameter(String param);
}
