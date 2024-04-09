package DataBaseExcange;

import java.util.ArrayList;
import java.sql.*;

public class DriversDB {
	public static ArrayList<DriversData> selectAll() {

		ArrayList<DriversData> drivers = new ArrayList<DriversData>();

		try {
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT * from auto_personnel;");

			while (res.next()) {
				DriversData data = new DriversData();
				data.setId(res.getInt(1));
				data.setFirstName(res.getString(2));
				data.setLastName(res.getString(3));
				data.setFatherName(res.getString(4));
				drivers.add(data);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return drivers;
	}

	public static DriversData select(int id) {
		DriversData data = new DriversData();
		try {
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT * FROM auto_personnel WHERE id = " + id + ";");
			res.next();
			data.setId(res.getInt(1));
			data.setFirstName(res.getString(2));
			data.setLastName(res.getString(3));
			data.setFatherName(res.getString(4));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void update(DriversData driver) {

		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement("UPDATE auto_personnel SET last_name = ?,"
					+ " first_name = ?, father_name = ?  WHERE id = ?;");
			pS.setString(1, driver.getLastName());
			pS.setString(2, driver.getFirstName());
			pS.setString(3, driver.getFatherName());
			pS.setInt(4, driver.getId());

			int rows = pS.executeUpdate();
			System.out.println(rows);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insert(DriversData driver) {
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement(
					"INSERT INTO auto_personnel (first_name, last_name, father_name)" + "VALUES (?, ?, ?)");
			pS.setString(1, driver.getFirstName());
			pS.setString(2, driver.getLastName());
			pS.setString(3, driver.getFatherName());
			pS.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void delete(int id) {
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement("DELETE FROM auto_personnel WHERE id = ?;");
			pS.setInt(1, id);
			pS.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
