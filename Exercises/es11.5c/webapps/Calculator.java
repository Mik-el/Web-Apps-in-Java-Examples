//code by Mik-el
package webapps;

import java.io.IOException;
import java.io.PrintWriter;

import webletApi.HttpRequest;
import webletApi.HttpResponse;
import webletApi.WebLet;

public class Calculator implements WebLet{
	public void service(HttpRequest req, HttpResponse res) throws IOException {
		double firstOperand = Double.parseDouble(req.getParameter("op1"));
		double secondOperand = Double.parseDouble(req.getParameter("op2"));
		char operation = req.getParameter("operation").charAt(0);

		res.flushHeader();
		PrintWriter pw = res.getWriter();
		
		switch(operation){
			case '+': {
				pw.print(firstOperand + secondOperand);
			} break;
			
			case '-': {
				pw.print(firstOperand - secondOperand);
			} break;
			
			case '*': {
				pw.print(firstOperand * secondOperand);
			} break;
			
			case '/': {
				if(secondOperand == 0){
					pw.print("Non e' possibile la divisione per 0.");
				} else {
					pw.print(firstOperand / secondOperand);
				}
			} break;
			
			default: break;
		}
		
		pw.flush();
	}
	
}
