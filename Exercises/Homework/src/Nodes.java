//code by Mik-el
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "nodes")
public class Nodes {
	private List<Node> nodes;
	
	public Nodes() {}
	
	public Nodes(List<Node> nodesCollection) {
		this.nodes = nodesCollection;
	}
	
	public List<Node> getNodesCollection(){ return nodes; }
	
	public void setNodesCollection(List<Node> nodesCollection) { this.nodes = nodesCollection; }
	
	public String toString() { return nodes.toString(); }
}
