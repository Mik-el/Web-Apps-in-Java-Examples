//code by Mik-el
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

public class NodeManager {
	private WebClient client;
		
		public NodeManager() {
			client = WebClient.create("http://localhost:8080/Homework/rest");
		}
		
		public Response postNode(Node node) {
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/nodes");
			
			Response res = client.post(node);
			System.out.println("URI della risorsa nodo creata " + node + "\n" + res.getLocation());
			
			return res;
		}
		
		public Response postSensor(Node node, Sensor sensor) {
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/nodes/" + node.getNodeId() + "/sensors");
			
			sensor.setParentNodeId(node.getNodeId());
			Response res = client.post(sensor);
			System.out.println("URI della risorsa sensore creata " + sensor + "\n" + res.getLocation());
			
			return res;
		}
		
		public Response postSample(int nodeId, Sensor sensor, Sample sample) {
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/nodes/" + nodeId + "/sensors/" + sensor.getAttribute() + "/samples");
			
			Response res = client.post(sample);
			
			System.out.println("URI della risorsa campione creata " + sample + "\n" + res.getLocation());
			
			return res;
		}
		
		public void putNode(int nodeId, Node node) {
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/nodes/" + nodeId);
			
			client.put(node);
		}
		
		public void putSensor(int nodeId, String attribute, Sensor sensor) {
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/nodes/" + nodeId + "/sensors/" + attribute);
			
			client.put(sensor);
		}
		
		public void deleteNode(int nodeId) {
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/nodes/" + nodeId);
			
			client.delete();
		}
		
		public void deleteSensor(int nodeId, String attribute) {
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/nodes/" + nodeId + "/sensors/" + attribute);
			
			client.delete();
		}
		
		public void deleteSample(int nodeId, String attribute, int sampleId) {
			client.reset();
			client.accept("application/json").type("application/json");
			client.path("/nodes/" + nodeId + "/sensors/" + attribute + "/samples/" + sampleId);
			
			client.delete();
		}
}
