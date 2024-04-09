package DataBaseExcange;

import java.io.Serializable;

public class RecordTimeData implements Serializable {

	private static final long serialVersionUID = -4344844611371534061L;

	private String rName;
	private String time;
	private String aNum;
	
	public RecordTimeData() {}
	public RecordTimeData(String rName, String time, String aNum) {
		super();
		this.rName = rName;
		this.time = time;
		this.aNum = aNum;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		int in = time.lastIndexOf('.');
		if (in != -1) 
		{
			this.time =time.substring(0, in);
		}
		else
		{
			this.time = time;
		}		
	}
	public String getaNum() {
		return aNum;
	}
	public void setaNum(String aNum) {
		this.aNum = aNum;
	}
	
	
}
