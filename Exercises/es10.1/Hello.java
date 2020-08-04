//code by Mik-el
import java.io.IOException;
import java.io.PrintWriter;

public class Hello  implements WebLet{
	public void service (HttpRequest req, HttpResponse res) throws IOException{
		res.setContentType("text/plain");
		res.flushHeader();
		PrintWriter pw = res.getWriter();
		pw.print("Hello WebLet world!");
		pw.flush();
	}
}
