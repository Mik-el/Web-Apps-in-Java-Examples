//code by Mik-el
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionIncrement.
 */
@WebServlet("/SessionIncrement")
public class ClientContacts extends HttpServlet
{       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession hs = request.getSession();
		
		int count = 0;
		
		if(!hs.isNew())
			count = (int) hs.getAttribute("count");
		
		hs.setAttribute("count", ++count);
		
		PrintWriter pw = response.getWriter();
		
		//if(count == 1)
			pw.print("Hai contattato questa componente <" + count + "> volte.");
		//else
			//pw.print("Hai contattato questa componente <" + count + "> volte.");
		
		pw.flush();
	}
	
	private static final long serialVersionUID = 1L;
}