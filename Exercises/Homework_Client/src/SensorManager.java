//code by Mik-el
import java.util.Date;
import java.util.Random;

public class SensorManager extends Thread {
	private NodeManager nm;
	private Sensor sensor;
	
	public SensorManager(NodeManager nm, Sensor sensor) {
		this.nm = nm;
		this.sensor = sensor;
	}
	
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			Sample sample = getSample();
			
			synchronized (nm) {
				nm.postSample(sensor.getParentNodeId(), sensor, sample);
				sensor.getSamplesList().add(sample);
			}
			
			try {
				int samplingTime = Integer.parseInt(sensor.getSamplingTime().replaceAll("[^0-9]", ""));
				sleep(5*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public Sample getSample() {
		Sample s = null;
		
		if(sensor.getAttribute().equalsIgnoreCase("temperatura"))  s = new Sample(sensor.getSamplesList().size(), new Random().nextInt(30), "° C", new Date());
		else if(sensor.getAttribute().equalsIgnoreCase("pressione")) s = new Sample(sensor.getSamplesList().size(), new Random().nextInt(100)+950, " bar", new Date());
		else if(sensor.getAttribute().equalsIgnoreCase("umidità")) s = new Sample(sensor.getSamplesList().size(), new Random().nextInt(50)+50, "%", new Date());
		
		return s;
	}
}
