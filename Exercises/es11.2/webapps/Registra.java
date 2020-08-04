//code by Mik-el
package webapps;

import webletApi.*;
import java.io.*;

public class Registra implements WebLet{
	public void service(HttpRequest req, HttpResponse res) throws IOException {
		//res.setContentType("text/plain");
		String email = req.getParameter("email");
		res.flushHeader();
		PrintWriter pw = res.getWriter();
		pw.print(email);
		pw.flush();
	}
}