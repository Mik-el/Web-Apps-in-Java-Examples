//code by Mik-el
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Calculator
 */
@WebServlet("/calculator")
public class Calculator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Calculator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();

		try {

			float op1 = Float.parseFloat(request.getParameter("op1"));
			float op2 = Float.parseFloat(request.getParameter("op2"));
			String c =  request.getParameter("operatorList");
			float result = 0;
	
			switch (c) {
			case "+":
				result = op1 + op2;
				break;

			case "-":
				result = op1 - op2;
				break;

			case "*":
				result = op1 * op2;
				break;

			case "/":
				result = op1 / op2;
				break;
			}

			pw.println("Il risultato dell'operazione e'" + result);

		} catch (NumberFormatException e) {
			pw.println("L'operazione non puo' essere eseguita");
		} finally {
			pw.flush();
		}

	}

}
