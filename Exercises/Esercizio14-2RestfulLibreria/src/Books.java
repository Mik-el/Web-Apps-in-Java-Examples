//code by Mik-el
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
//classe wrapper per book
@XmlRootElement(name = "books")
public class Books {

	private List<String> collection;
	public Books() {}
	public Books(List<String> list) { this.collection = list; }
	public List<String> getCollection(){ return collection; }
	public void setCollection(List<String> collection) { this.collection = collection; }
	public String toString() { return collection.toString(); }
	
}
