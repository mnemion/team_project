package sp1;
//database 환경설정

import java.sql.Connection;
import java.sql.DriverManager;


import java.sql.*;

public class dbconfig {
	public static Connection info() throws Exception{
		String db_driver = "com.mysql.jdbc.Driver";
		String db_url = "jdbc:mysql://umj7-003.cafe24.com/caffrey";
		//String db_url = "jdbc:mysql://localhost/caffrey";
		String db_user = "caffrey";
		String db_pass = "TJwl9983!";
		
		Class.forName(db_driver);
		Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
		return con;
	}
}