package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
	public static Connection doObj() throws SQLException {
		
		String dbname = "crudproject";
		String user = "root";
		String password = "mysql101";
		String url = "jdbc:mysql://localhost:3306/" + dbname;
		
		return DriverManager.getConnection(url, user, password);
	}
}
