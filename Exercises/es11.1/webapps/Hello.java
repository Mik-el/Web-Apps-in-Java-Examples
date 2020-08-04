//code by Mik-el
package webapps;

import java.io.IOException;
import java.io.PrintWriter;

import webletApi.HttpRequest;
import webletApi.HttpResponse;
import webletApi.WebLet;

public class Hello  implements WebLet{
	public void service (HttpRequest req, HttpResponse res) throws IOException{
		res.setContentType("text/plain");
		res.flushHeader();
		PrintWriter pw = res.getWriter();
		pw.print("Hello WebLet world!");
		pw.flush();
	}
}
