package DataBaseExcange;

import java.util.ArrayList;
import java.sql.*;

public class AutoDB {

	public static ArrayList<AutoData> selectAll() {

		ArrayList<AutoData> auto = new ArrayList<AutoData>();

		try {
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT auto.id, num, color, mark, "
					+ "(last_name || ' ' || first_name || ' ' || father_name) AS driver"
					+ " FROM auto, auto_personnel WHERE auto.personnel_id = auto_personnel.id;");

			while (res.next()) {
				AutoData data = new AutoData();
				data.setId(res.getInt(1));
				data.setNum(res.getString(2));
				data.setColor(res.getString(3));
				data.setMark(res.getString(4));
				data.setDriver(res.getString(5));
				auto.add(data);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return auto;
	}

	public static AutoData select(int id) {
		AutoData data = new AutoData();
		try {
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT auto.id, num, color, mark, "
					+ "(last_name || ' ' || first_name || ' ' || father_name) AS driver"
					+ " FROM auto, auto_personnel WHERE auto.personnel_id = auto_personnel.id" + " AND auto.id =" + id
					+ ";");
			res.next();
			data.setId(res.getInt(1));
			data.setNum(res.getString(2));
			data.setColor(res.getString(3));
			data.setMark(res.getString(4));
			data.setDriver(res.getString(5));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void update(AutoData auto) {


		try {
			Connection conn = ConnectionToDB.getConnection();
			String[] words=auto.getDriver().split(" ");
			String lname = words[0];
			String finame = words[1];
			String faname = words[2];
			PreparedStatement pS = conn.prepareStatement(
					"SELECT id FROM auto_personnel WHERE last_name =  ? AND first_name = ?"
					+ " AND father_name = ?;");
            pS.setString(1, lname);
            pS.setString(2, finame);
            pS.setString(3, faname);
			ResultSet res = pS.executeQuery();
			res.next();
			int dId = res.getInt(1);
			pS=conn.prepareStatement("UPDATE auto SET num = ?, color = ?, mark = ?, personnel_id = ?  WHERE id = ?;");
			pS.setString(1, auto.getNum());
            pS.setString(2, auto.getColor());
            pS.setString(3, auto.getMark());
            pS.setInt(4, dId);
            pS.setInt(5, auto.getId());
            
			int rows = pS.executeUpdate();
			System.out.println(rows);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void insert(AutoData auto) {
		try {
			Connection conn = ConnectionToDB.getConnection();
			String[] words=auto.getDriver().split(" ");
			String lname = words[0];
			String finame = words[1];
			String faname = words[2];
			PreparedStatement pS = conn.prepareStatement(
					"SELECT id FROM auto_personnel WHERE last_name =  ? AND first_name = ?"
					+ " AND father_name = ?;");
            pS.setString(1, lname);
            pS.setString(2, finame);
            pS.setString(3, faname);
			ResultSet res = pS.executeQuery();
			res.next();
			int dId = res.getInt(1);
			pS=conn.prepareStatement("INSERT INTO auto (Num, Color, Mark, Personnel_Id)"
					+ "VALUES (?, ?, ?, ?)");
			pS.setString(1, auto.getNum());
            pS.setString(2, auto.getColor());
            pS.setString(3, auto.getMark());
            pS.setInt(4, dId);
            
			pS.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void delete(int id) {
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement("DELETE FROM auto WHERE id = ?;");
			pS.setInt(1, id);
			pS.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
