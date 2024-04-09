package DataBaseExcange;

import java.io.Serializable;

public class AutoData implements Serializable{
	private static final long serialVersionUID = 2493828260482331709L;
	
	private int id;
	private String num;
	private String color;
	private String mark;
	private String driver;
	
	
	public AutoData(){}
	
	public AutoData(int id, String num, String color, String mark, String driver) {
		super();
		this.id = id;
		this.num = num;
		this.color = color;
		this.mark = mark;
		this.driver = driver;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
}
