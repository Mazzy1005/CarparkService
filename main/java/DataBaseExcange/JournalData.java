package DataBaseExcange;

import java.io.Serializable;

public class JournalData implements Serializable {

	private static final long serialVersionUID = -6985243669196229014L;
	
	private int id;
	private String time_out;
	private String time_in;
	private String num;
	private String route;
	
	
	
	public JournalData() {}
	public JournalData(int id, String time_out, String time_in, String num, String route) {
		super();
		this.id = id;
		this.time_out = time_out;
		this.time_in = time_in;
		this.num = num;
		this.route = route;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime_out() {
		return time_out;
	}
	public void setTime_out(String time_out) {
		this.time_out = time_out;
	}
	public String getTime_in() {
		return time_in;
	}
	public void setTime_in(String time_in) {
		this.time_in = time_in;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	
	
}
