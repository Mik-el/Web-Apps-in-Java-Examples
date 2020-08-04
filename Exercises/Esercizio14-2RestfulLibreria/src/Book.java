//code by Mik-el
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
public class Book {

	private String isbn;
	private String title;
	private String author;
	
	public Book(String isbn, String title, String author) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}
	
	public Book() {}
	public String getTitle() { return this.title; }
	public String getIsbn() { return this.isbn; }
	public String getAuthor() { return this.author; }
	public void setIsbn(String isbn) { this.isbn = isbn; }
	public void setTitle(String title) { this.title = title; }
	public void setAuthor(String author) { this.author = author; }
	
	public String toString() {return "\nCodiceISBN:"+this.isbn+"\nTitolo: "+this.title+"\nAutore: "+this.author;}
	
	
}
