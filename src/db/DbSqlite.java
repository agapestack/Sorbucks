package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbSqlite {
	private static final String dbURL = "jdbc:sqlite:./db/database.sqlite";

	private DbSqlite() {}
	
	public static void connect() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(dbURL);
			System.out.println("Connection to SQLite has been established.");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
