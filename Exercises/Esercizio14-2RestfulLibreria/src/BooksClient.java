//code by Mik-el
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

public class BooksClient {
	public static void main(String[] args) {
		try {
			WebClient client = WebClient.create("http://localhost:8080/Esercizio14-2RestfulLibreria/rest");
			client.accept("application/json").type("application/json");
			client.path("/books");
			Books books = client.get(Books.class);
			System.out.println("Intera collezione di libri" + books);
			
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/books/1234");
			Book b = client.get(Book.class);
			System.out.println("Book: " + b);
			
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/books");
			Book book = new Book("2345", "Titolo", "Autore");
			Response res = client.post(book);
			System.out.println("URI della risorsa libro creata " + book + '\n' + res.getLocation());
			
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/books/2345");
			Book b1 = client.get(Book.class);
			System.out.println("Book: " + b1);
			
			client.reset();
			
		} catch(Exception e) {}
	}
}
