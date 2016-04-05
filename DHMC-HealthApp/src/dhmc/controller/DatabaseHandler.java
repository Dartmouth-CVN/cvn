package dhmc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

	private static Connection conn1 = null;
	
	/**
	 * Connect the application to the database for storage or retrieval.
	 * 
	 * @return
	 */
	public boolean connect(){

		try {
			// connect method - embedded driver
			String dbURL1 = "jdbc:derby:C:/Users/Matt/MyDB;create=true";
			conn1 = DriverManager.getConnection(dbURL1);
			if (conn1 != null) {
				System.out.println("Connected to database #1");
			}
		} catch (SQLException ex) {
			System.out.println("Connection Failed! Check output console");
			ex.printStackTrace();
			return false;
		}return true;
	}
	
	/**
	 *  Create initial tables for application to query from when first started.
	 * 
	 * @return
	 */
	public boolean createTables(){
		
	
	}
	
	
	/**
	 * Checks if the given username and password are known in the database.
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean verifyDetails(String username, String password){

		boolean found = false;
		this.connect();
		try {
			PreparedStatement pstmt = conn1.prepareStatement("select username, password from app.posts where username = '" + username + "' and password = '" + password + "'");
			ResultSet rset = pstmt.executeQuery();	 
			// Process the results
			if (rset != null){
				found = true;
			}
			rset.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Get Data Failed! Check output console");
			e.printStackTrace();
			return false;   		 
		} return found;
	}
	
	/**
	 * Adds patient entry into database.
	 * @param patient
	 */
	public void storePatient(Patient patient){
		
		this.connect();
		try {
			PreparedStatement pstmt = conn1.prepareStatement("insert into....");
			ResultSet rset = pstmt.executeQuery();	
			rset.close();
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.println("Get Data Failed! Check output console");
			e.printStackTrace();
			return ; 
		} return ;
	}
	
	/**
	 * Finds patient from database given userID
	 * @param userID
	 * @return Patient object
	 */
	public Patient getPatient(String userID){
		
	}
	
	/**
	 * Finds medicalstaff from database given name
	 * @param name
	 * @return
	 */
    public MedicalStaff findMedicalStaff(String name){
    	
    }
    
    /**
     * Finds patient from database fiven name.
     * @param name
     * @return
     */
    public Patient findPatient(String name){
		
	}
    
}
