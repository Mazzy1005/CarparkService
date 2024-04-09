package DataBaseExcange;

import java.io.Serializable;

public class DriversData implements Serializable {

	private static final long serialVersionUID = 3471151749998208600L;
	private int id;
	private String firstName;
	private String lastName;
	private String fatherName;
	
	public DriversData() {}
	public DriversData(int id, String firstName, String lastName, String fatherName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fatherName = fatherName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	
}
