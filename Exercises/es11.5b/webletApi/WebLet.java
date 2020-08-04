//code by Mik-el
package webletApi;

import java.io.*;

public interface WebLet {
  // Questo metodo deve essere implementato dallo sviluppatore dell'applicazione Web
  public void service(HttpRequest req, HttpResponse res) throws IOException ;
}
