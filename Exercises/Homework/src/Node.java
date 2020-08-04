//code by Mik-el
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "node")
public class Node {
	private int nodeId;
	private double latitude;
	private double longitude;
	private double radius;
	private List<Sensor> sensors;
	private Date lastSignal;
	private String address;
	private boolean isWorking = true;
	
	public Node() {}
	public Node(int nodeId, double latitude, double longitude, double radius, String address, List<Sensor> sensors) {
		this.nodeId = nodeId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.sensors = sensors;
		this.address = address;
	}
	
	public int getNodeId() { return nodeId; }
	public double getLatitude() { return latitude; }
	public double getLongitude() { return longitude; }
	public double getRadius() { return radius; }
	public List<Sensor> getSensors() { return sensors; }
	public Date getLastSignal() { return lastSignal; }
	public String getAddress() { return address; }
	public boolean getWorkingStatus() { return isWorking; }
	
	public void setNodeId(int nodeId) { this.nodeId = nodeId; }
	public void setLatitude(double latitude) { this.latitude = latitude; }
	public void setLongitude(double longitude) { this.longitude = longitude; }
	public void setRadius(double radius) { this.radius = radius; }
	public void setSensors(List<Sensor> sensors) { this.sensors = sensors; }
	public void setLastSignal(Date lastSignal) { this.lastSignal = lastSignal; }
	public void setAddress(String address) { this.address = address; }
	public void setWorkingStatus(boolean isWorking) { this.isWorking = isWorking; }

public String toString() {
		return "\nNode Id = " + nodeId + "\nLatitude = " + latitude + "\nLongitude = " + longitude + "\nRadius = " + radius+ "\nSensors = " + sensors.toString() ; 
	}
}
