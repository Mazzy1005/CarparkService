package DataBaseExcange;

import java.io.Serializable;

public class UserData implements Serializable {
	private static final long serialVersionUID = 8697801447325190147L;

	private String login;
	private String password;
	
	
	public UserData() {}
	
	public UserData(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
