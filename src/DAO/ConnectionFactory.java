package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection getConnection() {
		System.out.println("Conectando ao banco");
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver()); 
			System.out.println("conectou!");
			return DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}

	}
}
