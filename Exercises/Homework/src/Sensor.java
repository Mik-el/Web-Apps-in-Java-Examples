//code by Mik-el
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sensor")
public class Sensor {
	private static final int N = 5;
	
	private String attribute;
	private String precision;
	private String code;
	private String samplingTime;
	private List<Sample> samplesList;
	private int parentNodeId;
	private boolean isWorking = true;
	
	public Sensor() {}
	
	public Sensor(String attribute, String precision, String code, String samplingTime, List<Sample> samplesList) {
		this.attribute = attribute;
		this.precision = precision;
		this.code = code;
		this.samplingTime = samplingTime;
		this.samplesList = samplesList;
	}
	
	public String getAttribute() { return attribute; }
	public String getPrecision() { return precision; }
	public String getCode() { return code; }
	public String getSamplingTime() { return samplingTime; }
	public List<Sample> getSamplesList() { return samplesList; }
	public int getParentNodeId() { return parentNodeId; }
	public boolean getWorkingStatus() { return isWorking; }
	
	public void setAttribute(String attribute) { this.attribute = attribute; }
	public void setPrecision(String precision) { this.precision = precision; }
	public void setCode(String code) { this.code = code; }
	public void setSamplingTime(String samplingTime) { this.samplingTime = samplingTime; }
	public void setSamplesList(List<Sample> samplesList) { this.samplesList = samplesList; }
	public void setParentNodeId(int parentNodeId) { this.parentNodeId = parentNodeId;  }
	public void setWorkingStatus(boolean isWorking) { this.isWorking = isWorking; }
	
	public String toString() {
		return "\nCode = " + code + "\nAttribute = " + attribute + "\nPrecision = " + precision + "\nSampling Time = " + samplingTime + "\nSamples List = " + samplesList.toString();
	}
	
	public String calculateSamplesAverage() {
		double average = 0;
		int i = 1;
		int lastPosition = samplesList.size() - i;
		
		if(!samplesList.isEmpty()) {
			for(i=0; i<(Math.min(N, samplesList.size())); i++) {
				average += samplesList.get(lastPosition).getValue();
				lastPosition--;
			}
		
			if(N<samplesList.size()) average = average/N;
			else average = average/i;
			
			return String.valueOf(average);
		}
		
		return null;
	}
}
