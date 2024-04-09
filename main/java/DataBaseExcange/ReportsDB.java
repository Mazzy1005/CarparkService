package DataBaseExcange;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class ReportsDB {
	public static ArrayList<RouteAutoData> numOfAutoOnRoute() {

		ArrayList<RouteAutoData> records = new ArrayList<RouteAutoData>();

		try {
			
			Connection conn = ConnectionToDB.getConnection();
			Statement statement = conn.createStatement();
			ResultSet res = statement.executeQuery("SELECT * from auto_on_routes;");

			while (res.next()) {
				RouteAutoData data = new RouteAutoData();
				data.setrName(res.getString(1));
				data.setaNum(res.getString(2));
				records.add(data);
			}
			CopyManager c = new CopyManager((BaseConnection) conn);
			FileOutputStream fos = new FileOutputStream(
					"C:\\Users\\yarma\\Study\\Базы данных\\Курсовая\\auto_on_routes.txt");
			c.copyOut("copy (select * from auto_on_routes) to stdout;", fos);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return records;
	}

	public static ArrayList<DriverBonusData> driversBonus(int sum) {

		ArrayList<DriverBonusData> records = new ArrayList<DriverBonusData>();

		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement(
					"SELECT * from drivers_bonus(?);");
			pS.setInt(1, sum);
			ResultSet res = pS.executeQuery();

			while (res.next()) {
				DriverBonusData data = new DriverBonusData();
				data.setName(res.getString(1));
				data.setSum(res.getDouble(2));
				records.add(data);
			}
			CopyManager c =new CopyManager((BaseConnection) conn);
			FileOutputStream fos=new FileOutputStream("C:\\Users\\yarma\\Study\\Базы данных\\Курсовая\\drivers_bonus.txt");
			c.copyOut("copy (SELECT * from drivers_bonus("+sum+")) to stdout;", fos);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return records;
	}
	
	public static ArrayList<RecordTimeData> recordTime() {

		ArrayList<RecordTimeData> records = new ArrayList<RecordTimeData>();

		try {
			Connection conn = ConnectionToDB.getConnection();
			PreparedStatement pS = conn.prepareStatement(
					"SELECT * FROM records_on_routes();");
			ResultSet res = pS.executeQuery();

			while (res.next()) {
				RecordTimeData data = new RecordTimeData();
				data.setrName(res.getString(1));
				data.setTime(res.getString(2));
				data.setaNum(res.getString(3));
				records.add(data);
			}
			CopyManager c =new CopyManager((BaseConnection) conn);
			FileOutputStream fos=new FileOutputStream("C:\\Users\\yarma\\Study\\Базы данных\\Курсовая\\record_time_on_routes.txt");
			c.copyOut("copy (SELECT * FROM records_on_routes()) to stdout;", fos);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return records;
	}
}
