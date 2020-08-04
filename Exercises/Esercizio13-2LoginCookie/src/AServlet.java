//code by Mik-el
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AServlet.
 */
@WebServlet("/AServlet")
public class AServlet extends HttpServlet
{
	/**
     * Default constructor. 
     */
    public AServlet()
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter pw = response.getWriter();
		
		Cookie[] c = request.getCookies();
		
		boolean authenticated = false;
		
		if(c != null)
			for(Cookie cookie : c)
				authenticated = cookie.getName().equals("authToken") && cookie.getValue().equals("registered");
		
		if(authenticated)
			pw.print("OK, sei un utente gia'  autenticato.");
		else
			pw.print("Non sei ancora autenticato.");
		
		pw.flush();
	}
	
	private static final long serialVersionUID = 1L;
}