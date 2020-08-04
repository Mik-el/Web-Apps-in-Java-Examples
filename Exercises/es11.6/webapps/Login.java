//code by Mik-el
package webapps;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import webletApi.HttpRequest;
import webletApi.HttpResponse;
import webletApi.WebLet;

public class Login implements WebLet{

	public void service(HttpRequest req, HttpResponse res) throws IOException {
		
		//res.setContentType("text/html");
		res.flushHeader();
		
		PrintWriter pw = res.getWriter();
		
		Scanner sc = new Scanner(new File("database"));

		String username;
		String password;
		StringTokenizer token;
		int loginStatus = INCORRECT_USERNAME;
		
		while(sc.hasNextLine() && (loginStatus == INCORRECT_USERNAME)){
			
			for(int i = 0; i < 4; i++)
				sc.nextLine();

			username = sc.nextLine();
			token = new StringTokenizer(username);
			token.nextToken(": ");
			username = token.nextToken();
			password = sc.nextLine();
			token = new StringTokenizer(password);
			token.nextToken(": ");
			password = token.nextToken();
			
			if(username.equals(req.getParameter("username"))){
				loginStatus = INCORRECT_PASSWORD;
				if(password.equals(req.getParameter("password"))){
					loginStatus = CORRECT_CREDENTIALS;
				}
			}
		}
		
		if(loginStatus == CORRECT_CREDENTIALS){
			pw.println("Accesso effettuato, username e password corrette.");
		} else if(loginStatus == INCORRECT_PASSWORD){
			pw.println("Accesso non effettuato, password errata.");
		} else {
			pw.println("Accesso non effettuato, username non trovato.");
		}

		pw.flush();
		
	}

	private final int CORRECT_CREDENTIALS = 2;
	private final int INCORRECT_PASSWORD = 1;
	private final int INCORRECT_USERNAME = 0;

}