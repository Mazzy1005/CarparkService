package DataBaseExcange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RouteDB {
	public static ArrayList<RouteData> selectAll() {

		ArrayList<RouteData> routes = new ArrayList<RouteData>();

		try {
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT * from routes;");

			while (res.next()) {
				RouteData data = new RouteData();
				data.setId(res.getInt(1));
				data.setName(res.getString(2));
				routes.add(data);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return routes;
	}

	public static RouteData select(int id) {
		RouteData data = new RouteData();
		try {
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM routes WHERE id = " + id + ";");
			res.next();
			data.setId(res.getInt(1));
			data.setName(res.getString(2));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void update(RouteData route) {

		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement("UPDATE routes SET name = ? WHERE id = ?;");
			pS.setString(1, route.getName());
			pS.setInt(2, route.getId());

			int rows = pS.executeUpdate();
			System.out.println(rows);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insert(RouteData route) {
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement(
					"INSERT INTO routes (name)" + "VALUES (?)");
			pS.setString(1, route.getName());
			pS.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void delete(int id) {
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement("DELETE FROM routes WHERE id = ?;");
			pS.setInt(1, id);
			pS.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
