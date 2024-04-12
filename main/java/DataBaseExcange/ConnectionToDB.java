package DataBaseExcange;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDB {
	
	private static Connection conn;
	private static boolean isInit=false; 
	private static void init() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Properties props = new Properties();
		String url=null;
		try(FileInputStream fin=new FileInputStream("/usr/local/tomcat/webapps/config.txt"))
        {    
			props.load(fin);
			url=props.getProperty("url");
        }
        catch(IOException ex){
              
            System.out.println(ex.getMessage());
        } 
		conn = DriverManager.getConnection(url, props);
		isInit = true;
	}
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		if(!isInit)
		{
			init();
		}
		return conn;
	}
}