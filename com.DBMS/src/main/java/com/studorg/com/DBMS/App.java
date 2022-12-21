package com.studorg.com.DBMS;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String args[]) throws SQLException, IOException, 
	ClassNotFoundException {

		System.out.println("Beginning");
		
		// Load the PostgreSQL driver
		Class.forName("org.postgresql.Driver");

		// Connect to the default database with credentials

		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "root");

		// For atomicity
		conn.setAutoCommit(false);

		// For isolation
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		// Create statement object
		PreparedStatement stmt1 = null;
		try {
			
			String updateInDepot = "update depot set dep_id = ? where dep_id = ?";
			stmt1 = conn.prepareStatement(updateInDepot);
			stmt1.setString(1, "dd1");
			stmt1.setString(2, "d1");
			stmt1.executeUpdate();
			
			//No need to update in Stock table it will get auto updated
			//As while creating foreign key constraints we had ON UPDATE CASCADE added

			

		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			e.printStackTrace();
			// For atomicity
			conn.rollback();
			stmt1.close();
			conn.close();
			return;
		}
		conn.commit();
		stmt1.close();
		conn.close();
		System.out.println("End");
	}

}
