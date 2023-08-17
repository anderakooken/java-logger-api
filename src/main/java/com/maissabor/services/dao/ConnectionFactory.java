package com.maissabor.services.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

	public static Connection getConnection(String url, String user, String pw) {
		Connection connection = null;

		try {
			if (url.contains("mysql")) {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} else if (url.contains("oracle")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} else if (url.contains("sqlserver")) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} else {
				throw new IllegalArgumentException("Unsupported database type");
			}

			connection = DriverManager.getConnection(url, user, pw);

		} catch (ClassNotFoundException e) {
			System.err.println("Database driver not found: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Error while connecting to the database: " + e.getMessage());
		}

		return connection;
	}
}
