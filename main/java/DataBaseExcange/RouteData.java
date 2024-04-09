package DataBaseExcange;

import java.io.Serializable;

public class RouteData implements Serializable {
	private static final long serialVersionUID = 8651197890261020044L;
	
	private int id;
	private String name;
	
	public RouteData() {}
	
	public RouteData(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
