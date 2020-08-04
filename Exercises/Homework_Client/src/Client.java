//code by Mik-el
import java.util.ArrayList;

public class Client {
	public static void main(String[] args) {
		NodeManager nm = new NodeManager();
		
		Node n1 = new Node(0, 10.0, 10.0, 2.0, "Piazza Risorgimento", new ArrayList<Sensor>());
		Node n2 = new Node(1, 20.0, 20.0, 3.0, "Via Novara", new ArrayList<Sensor>());
		Node n3 = new Node(2, 30.0, 30.0, 3.0, "Piazza Roma", new ArrayList<Sensor>());
		Node n4 = new Node(3, 40.0, 40.0, 3.0, "Viale Atlantici", new ArrayList<Sensor>());
		Node n5 = new Node(4, 50.0, 50.0, 3.0, "Via Napoli", new ArrayList<Sensor>());
		
		nm.postNode(n1);
		nm.postNode(n2);
		nm.postNode(n3);
		nm.postNode(n4);
		nm.postNode(n5);
		
		Sensor s1 = new Sensor("temperatura", "+/- 0.1 °C", "Code1", "1 min", new ArrayList<Sample>()); 
		Sensor s2 = new Sensor("umidità", "+/- 1%", "Code2", "1 min", new ArrayList<Sample>()); 
		Sensor s3 = new Sensor("pressione", "+/- 1%", "Code3", "1 min", new ArrayList<Sample>()); 
		Sensor s4 = new Sensor("temperatura", "+/- 0.1 °C", "Code4", "1 min", new ArrayList<Sample>());
		Sensor s5 = new Sensor("umidità", "+/- 1%", "Code5", "1 min", new ArrayList<Sample>());
		Sensor s6 = new Sensor("pressione", "+/- 1%", "Code6", "1 min", new ArrayList<Sample>()); 
		Sensor s7 = new Sensor("temperatura", "+/- 0.2 °C", "Code7", "1 min", new ArrayList<Sample>()); 
		Sensor s8 = new Sensor("umidità", "+/- 3%", "Code8", "1 min", new ArrayList<Sample>()); 
		Sensor s9 = new Sensor("pressione", "+/- 1.5%", "Code9", "1 min", new ArrayList<Sample>()); 
		Sensor s10 = new Sensor("temperatura", "+/- 1 °C", "Code10", "1 min", new ArrayList<Sample>());
		
		nm.postSensor(n1, s1);
		nm.postSensor(n1, s2);
		nm.postSensor(n2, s3);
		nm.postSensor(n2, s4);
		nm.postSensor(n3, s5);
		nm.postSensor(n3, s6);
		nm.postSensor(n4, s7);
		nm.postSensor(n4, s8);
		nm.postSensor(n5, s9);
		nm.postSensor(n5, s10);
		
		SensorManager sm1 = new SensorManager(nm, s1); sm1.start();
		SensorManager sm2 = new SensorManager(nm, s2); sm2.start();
		SensorManager sm3 = new SensorManager(nm, s3); sm3.start();
		SensorManager sm4 = new SensorManager(nm, s4); sm4.start();
		SensorManager sm5 = new SensorManager(nm, s5); sm5.start();
		SensorManager sm6 = new SensorManager(nm, s6); sm6.start();
		SensorManager sm7 = new SensorManager(nm, s7); sm7.start();
		SensorManager sm8 = new SensorManager(nm, s8); sm8.start();
		SensorManager sm9 = new SensorManager(nm, s9); sm9.start();
		SensorManager sm10 = new SensorManager(nm, s10); sm10.start();
		
		try {
			Thread.sleep(15*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

		s5.setWorkingStatus(false);
		s6.setWorkingStatus(false);
		s9.setWorkingStatus(false);
		nm.putSensor(s5.getParentNodeId(), s5.getAttribute(), s5);
		nm.putSensor(s6.getParentNodeId(), s6.getAttribute(), s6);
		nm.putSensor(s9.getParentNodeId(), s9.getAttribute(), s9);
		sm5.interrupt();
		sm6.interrupt();
		sm9.interrupt();
	}
}
