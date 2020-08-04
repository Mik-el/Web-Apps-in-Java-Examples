//code by Mik-el
package webapps;

import webletApi.*;
import java.io.*;

public class Parameters implements WebLet {

	public void service (HttpRequest req, HttpResponse res) throws IOException{

		String param1= req.getParameter("firstname");
		String param2= req.getParameter("lastname");
		res.flushHeader();
		PrintWriter pw = res.getWriter();
		pw.println("nome: " + param1);
		pw.println("cognome: " + param2);
		pw.flush();
		}
}
