package dhmc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

    private static PreparedStatement ps;
    private static ResultSet rs;
	private static Connection connection;
	
	public DatabaseHandler() {
		connect();
	}

	/**
	 * Connect the application to the database for storage or retrieval.
	 * 
	 * @return
	 */
	public boolean connect() {
		try {
			// connect method - embedded driver
			String url = "jdbc:derby:db;create=true";
			connection = DriverManager.getConnection(url);
			if (connection != null) {
				System.out.println("Connected to Health App database");
			}
		} catch (SQLException ex) {
			System.out.println("Connection Failed! Check output console");
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * Create initial tables for application to query from when first started.
	 * 
	 * @return
	 */
	// public boolean createTables();

	/**
	 * Checks if the given username and password are known in the database.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean login(String username, String password) {
		try {
			ps = connection.prepareStatement("select * FROM login");
//			ps.setStrinag(1, username);
			rs = ps.executeQuery();
			System.out.println(rs.next());
			while (rs.next()) {
				System.out.println(rs.getString("lastname"));
				if(password.equals(rs.getString("lastname")))
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			System.out.println("error in the prepared statement\n"+e.getMessage());
		}
		return false;
	}

	//
	//
	// /**
	// * Adds patient entry into database.
	// * @param patient
	// */
	// public void storePatient(Patient patient){
	//
	// this.connect();
	// try {
	// PreparedStatement pstmt = connection.prepareStatement("insert into....");
	// ResultSet rset = pstmt.executeQuery();
	// rset.close();
	// pstmt.close();
	//
	// } catch (SQLException e) {
	// System.out.println("Get Data Failed! Check output console");
	// e.printStackTrace();
	// return ;
	// } return ;
	// }
	//
	// /**
	// * Finds patient from database given userID
	// * @param userID
	// * @return Patient object
	// */
	// public Patient getPatient(String userID){
	//
	// }
	//
	// /**
	// * Finds medicalstaff from database given name
	// * @param name
	// * @return
	// */
	// public MedicalStaff findMedicalStaff(String name){
	//
	// }
	//
	// /**
	// * Finds patient from database fiven name.
	// * @param name
	// * @return
	// */
	// public Patient findPatient(String name){
	//
	// }

}
