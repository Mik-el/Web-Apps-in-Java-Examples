//code by Mik-el
package webletApi;

import java.io.*;

public interface HttpResponse {
    // Restituisce lo stream associato al messaggio 
    public OutputStream getOutputStream() throws IOException;
    // Restituisce il PrintWriter associato al messaggio
    public PrintWriter getWriter() throws IOException;
    // Invia nello stream di output le intestazioni del messaggio 
    public void flushHeader() throws IOException;
    // Specifica il tipo di contenuto del messaggio
    public void setContentType(String cType);
    // Specifica la lunghezza del body del messaggio
    public void setContentLength(int cLength);
}
