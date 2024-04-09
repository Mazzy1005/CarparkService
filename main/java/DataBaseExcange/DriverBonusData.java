package DataBaseExcange;

import java.io.Serializable;

public class DriverBonusData implements Serializable {

	private static final long serialVersionUID = 9153125994987529198L;
	private String name;
	private double sum;
	
	public DriverBonusData() {}
	public DriverBonusData(String name, double sum) {
		super();
		this.name = name;
		this.sum = sum;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	
}
