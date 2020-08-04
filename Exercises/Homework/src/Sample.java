//code by Mik-el
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sample")
public class Sample {
	private int id;
	private double value;
	private String unitMeasure;
	private Date date;
	
	public Sample() {}
	
	public Sample(int id, double value, String unitMeasure, Date date) {
		this.id = id;
		this.value = value;
		this.unitMeasure = unitMeasure;
		this.date = date;
	}
	
	public int getId() { return id; }
	public double getValue() { return value; }
	public String getUnitMeasure() { return unitMeasure; }
	public Date getDate() { return date; }
	
	public void setId(int id) { this.id = id; }
	public void setValue(double value) { this.value = value; }
	public void setUnitMeasure(String unitMeasure) { this.unitMeasure = unitMeasure; }
	public void setDate(Date date) { this.date = date; }
	
	public String toString() {
		return "\nId = " + id + "\nValue = " + value + " " + unitMeasure + "\nDate = " + date.toString();
	}
}
