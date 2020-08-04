//code by Mik-el
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
public class Order {

	private String isbn;
	private int id;
	
	public Order(String isbn, int id) {
		this.isbn = isbn;
		this.id = id;
	}
	
	public Order() {}

	public String getIsbn() { return isbn; }
	public void setIsbn(String isbn) { this.isbn = isbn; }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String toString() { return "\nOrdine n°: "+this.id+"\nCodiceISBN: "+this.isbn; }
	
	
}
