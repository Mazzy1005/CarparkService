package DataBaseExcange;

import java.io.Serializable;

public class RouteAutoData implements Serializable {

	private static final long serialVersionUID = 6723687710583014062L;
	private String rName;
	private String aNum;
	
	
	
	public RouteAutoData() {}
	public RouteAutoData(String rName, String aNum) {
		super();
		this.rName = rName;
		this.aNum = aNum;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public String getaNum() {
		return aNum;
	}
	public void setaNum(String aNum) {
		this.aNum = aNum;
	}
	
	
}
