//code by Mik-el
package webapps;

import webletApi.*;
import java.io.*;

public class Registra1 implements WebLet{
	public void service(HttpRequest req, HttpResponse res) throws IOException {
		FileOutputStream fos = new FileOutputStream("database", true);
		PrintStream ps = new PrintStream(fos);
		ps.println("nome    : " + req.getParameter("firstname"));
		ps.println("cognome : " + req.getParameter("lastname"));
		ps.println("email   : " + req.getParameter("email"));
		ps.println("sex     : " + req.getParameter("sex"));
		ps.println("username: " + req.getParameter("username"));
		ps.println("password: " + req.getParameter("password"));
		
		//res.setContentType("text/html");
		res.flushHeader();
		
		PrintWriter pw = res.getWriter();
		String output = req.getParameter("firstname") + ", la tua registrazione e' avvenuta con successo";

		pw.print(output);
		pw.flush();
		
	}

}
