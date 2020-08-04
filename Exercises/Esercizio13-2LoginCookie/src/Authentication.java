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
 * Servlet implementation class Authentication.
 */
@WebServlet("/authentication")
public class Authentication extends HttpServlet {
	/**
	 * Default constructor.
	 */
	public Authentication() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cookie c = new Cookie("authToken", "registered");

		c.setMaxAge(30);
		response.addCookie(c);

		PrintWriter pw = response.getWriter();
		pw.print("Autenticazione effettuata con successo.");
		pw.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("text/plain");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username.equals("Michele") && password.equals("Carnevale")) {
			Cookie c = new Cookie("authToken", "registered");
			c.setMaxAge(3);
			response.addCookie(c);
			pw.println("****Autenticazione avvenuta con successo****");
		} else {
			pw.println("****Utente non registrato****");
		}
		pw.flush();
	}

	private static final long serialVersionUID = 1L;
}