//code by Mik-el
package webapps;

import java.io.IOException;
import java.io.PrintWriter;

import webletApi.HttpRequest;
import webletApi.HttpResponse;
import webletApi.WebLet;

public class Reverse implements WebLet{
	public void service(HttpRequest req, HttpResponse res) throws IOException {
		String output = new StringBuffer(req.getParameter("string")).reverse().toString();

		res.flushHeader();
		PrintWriter pw = res.getWriter();
		pw.print(output);
		pw.flush();
	}
	
}
