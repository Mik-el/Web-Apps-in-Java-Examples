//code by Mik-el
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Consumes("application/json")
@Produces("application/json")
@Path("/books")
public class BooksService {

	private HashMap<String, Book> bookCollection = new HashMap<>(); 
	private HashMap<String, List<Order>> orderCollection = new HashMap<>();
	
	public BooksService() {
		bookCollection.put("1234-1234567890", new Book("1234-1234567890", "Libro 1", "Autore 1"));
		bookCollection.put("4321-0987654321", new Book("4321-0987654321", "Libro 2", "Autore 2"));
	}
	
	@GET
	public Books getBookList() {
		ArrayList<String> code = new ArrayList<String>(bookCollection.keySet());
		return new Books(code);
	}
	
	@POST
	public Response createBook(Book book) {
		
		bookCollection.put(book.getIsbn(), book);
		URI uri = null;
		try {
			uri = new URI("/books/"+book.getIsbn());
		}catch(URISyntaxException e) {}
		return Response.created(uri).build();
	}
	
	@GET
	@Path("/{isbn}")
	public Book getBookDetail(@PathParam("isbn") String isbn) {
		return bookCollection.get(isbn);
	}
	
	@PUT
	@Path("/{isbn}")
	public void setBookDetails(@PathParam("isbn") String isbn, Book book) {
		bookCollection.put(book.getIsbn(), book);
	}
	
	@DELETE
	@Path("/{isbn}")
	public void deleteBook(@PathParam("isbn") String isbn) {
		this.bookCollection.remove(isbn);
	}
	
	@POST
	@Path("/{isbn}/orders/")
	public Response orderBook(@PathParam("isbn") String isbn) {
		List<Order> list = orderCollection.get(isbn);
		if(list == null) {
			list = new ArrayList<Order>();
			orderCollection.put(isbn, list);
		}
		int oid = orderCollection.get(isbn).size();
		
		Order order = new Order(isbn, oid);
		list.add(oid, order);
		URI uri = null;
		try {
			uri = new URI("books/"+isbn+"/orders/"+oid);
		}catch(URISyntaxException e) {}
		return Response.created(uri).build();
		
	}
	
	@GET
	@Path("/{isbn}/orders/{old}")
	public Order getOrder(String isbn, int oid) {
		List<Order> list = orderCollection.get(isbn);
		if(list != null) return list.get(oid);
		return null;
	}
}
