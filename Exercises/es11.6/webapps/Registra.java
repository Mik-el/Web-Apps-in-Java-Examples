//code by Mik-el
package webapps;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import webletApi.HttpRequest;
import webletApi.HttpResponse;
import webletApi.WebLet;

public class Registra implements WebLet{

	public void service(HttpRequest req, HttpResponse res) throws IOException {
		FileOutputStream fos = new FileOutputStream("database", true);
		PrintStream ps = new PrintStream(fos);
		ps.println("nome    :" + req.getParameter("firstname"));
		ps.println("cognome :" + req.getParameter("lastname"));
		ps.println("email   :" + req.getParameter("email"));
		ps.println("sex     :" + req.getParameter("sex"));
		ps.println("username:" + req.getParameter("username"));
		ps.println("password:" + req.getParameter("password"));
		
		//res.setContentType("text/html");
		res.flushHeader();
		
		PrintWriter pw = res.getWriter();
		String output = "<HTML>" +
			"<HEAD>" +
				"<TITLE>" +
					"Registrazione" +
				"</TITLE>" +
			"</HEAD>" +
			"<BODY>" +
				"<H2>" +
					req.getParameter("firstname") + ", la tua registrazione &egrave avvenuta con successo" +
				"</H2>" +
			"</BODY>" +
		"</HTML>";
		
		pw.print(output);
		pw.flush();
		
	}

}
