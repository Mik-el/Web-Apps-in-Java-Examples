//code by Mik-el
package webapps;

import java.io.IOException;
import java.io.PrintWriter;

import webletApi.HttpRequest;
import webletApi.HttpResponse;
import webletApi.WebLet;

public class Conta implements WebLet {
	public void service(HttpRequest req, HttpResponse res) throws IOException {
		int output = req.getParameter("stringa").length() + Integer.parseInt(req.getParameter("numero"));

		res.flushHeader();
		PrintWriter pw = res.getWriter();
		pw.print(output);
		pw.flush();
	}

}
