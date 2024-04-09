package DataBaseExcange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class AuthenticationDB {

	public static void register(UserData user) throws Exception {
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement(
					"INSERT INTO auth (login,password) VALUES (?,?)");
			pS.setString(1, user.getLogin());
			pS.setInt(2, user.getPassword().hashCode());
			pS.executeUpdate();			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public static boolean isRegister(UserData user) {
		int pass=0;
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement(
					"SELECT password FROM auth WHERE login = ?");
			pS.setString(1, user.getLogin());
			ResultSet res = pS.executeQuery();
			res.next();
			pass = res.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return user.getPassword().hashCode() == pass;
	}

}
