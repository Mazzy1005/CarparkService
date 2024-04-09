package DataBaseExcange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

public class JournalDB {
	public static ArrayList<JournalData> selectAll() {

		ArrayList<JournalData> journal = new ArrayList<JournalData>();

		try {
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT journal.id, time_out, time_in, auto.num, routes.name "
					+ "FROM journal, auto, routes WHERE auto_id=auto.id AND route_id = routes.id;");

			while (res.next()) {
				JournalData data = new JournalData();
				data.setId(res.getInt(1));
				String s = res.getString(2);
				int in = s.lastIndexOf('.');
				if (in != -1) 
				{
					data.setTime_out(s.substring(0, in));
				}
				else
				{
					data.setTime_out(s);
				}
				s = res.getString(3);
				if (s != null)
				{

					in = s.lastIndexOf('.');
					if (in != -1)
					{
						data.setTime_in(s.substring(0, in));
					} 
					else
					{
						data.setTime_in(s);
					}
				}
				else
				{
					data.setTime_in("В пути");
				}
				data.setNum(res.getString(4));
				data.setRoute(res.getString(5));
				journal.add(data);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return journal;
	}

	public static JournalData select(int id) {
		JournalData data = new JournalData();
		try {
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT journal.id, time_out, time_in, auto.num, routes.name FROM journal, auto, routes WHERE auto_id=auto.id AND route_id = routes.id AND journal.id = "+id);
			res.next();
			data.setId(res.getInt(1));
			data.setTime_out(res.getString(2));
			data.setTime_in(res.getString(3));
			data.setNum(res.getString(4));
			data.setRoute(res.getString(5));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void update(JournalData record) throws Exception {

		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement("SELECT id FROM auto WHERE num = ?;");
			
			pS.setString(1, record.getNum());
			ResultSet res = pS.executeQuery();
			res.next();
			int cId=res.getInt(1);	

			pS = conn.prepareStatement("SELECT id FROM routes WHERE name = ?;");
			pS.setString(1, record.getRoute());
			res = pS.executeQuery();
			res.next();
			int rId=res.getInt(1);
			
			pS = conn.prepareStatement(
					"UPDATE journal SET time_out = ?, time_in = ?, auto_id = ?, route_id = ?  WHERE id = ?;");
			pS.setTimestamp(1, Timestamp.valueOf((record.getTime_out()).replace('T', ' ')));
			if ((record.getTime_in() != "")) {
				pS.setTimestamp(2, Timestamp.valueOf((record.getTime_in()).replace('T', ' ')));
			} else {
				pS.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);
			}
			pS.setInt(3, cId);
			pS.setInt(4, rId);
			pS.setInt(5, record.getId());

			int rows = pS.executeUpdate();
			System.out.println(rows);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void insert(JournalData record) throws Exception {
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement("SELECT id FROM auto WHERE num =  ?;");
			pS.setString(1, record.getNum());
			ResultSet res = pS.executeQuery();
			res.next();
			int cId = res.getInt(1);
			pS = conn.prepareStatement("SELECT id FROM routes WHERE name =  ?;");
			pS.setString(1, record.getRoute());
			res = pS.executeQuery();
			res.next();
			int rId = res.getInt(1);

			pS = conn.prepareStatement(
					"INSERT INTO journal (time_out, time_in, auto_id, route_id)" + "VALUES (?, ?, ?, ?)");
			System.out.println(record.getTime_out().replace('T', ' '));
			pS.setTimestamp(1, Timestamp.valueOf((record.getTime_out()).replace('T', ' ')));
			if ((record.getTime_in() != "")) {
				pS.setTimestamp(2, Timestamp.valueOf((record.getTime_in()).replace('T', ' ')));
			} else {
				pS.setNull(2, Types.TIMESTAMP_WITH_TIMEZONE);
			}
			pS.setInt(3, cId);
			pS.setInt(4, rId);

			int rows = pS.executeUpdate();
			System.out.println(rows);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}

	}

	public static void delete(int id) {
		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement("DELETE FROM journal WHERE id = ?;");
			pS.setInt(1, id);
			pS.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
