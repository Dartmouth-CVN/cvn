package dhmc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			String url = "jdbc:derby:healthapp2DB;create=true";
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
	
	public void insertLoginUser(){
		try {
			ps = connection.prepareStatement("INSERT INTO healthapp2.Login (username, password, user_id, role) VALUES (?, ?, ?, ?)");
			ps.setString(1, "admin2");
			ps.setString(2, "pass");
			ps.setInt(3, 1);
			ps.setString(4, "Admin");
			ps.execute();
			System.out.println("Inserted login user");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void getLoginUsers(){
		try {
			ps = connection.prepareStatement("SELECT * FROM healthapp2.Login");
			
			rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("username"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void insertUser(){
		try{
			ps = connection.prepareStatement("INSERT INTO healthapp2.User_Account (user_id, firstname, lastname) VALUES (?, ?, ?)");
			ps.setString(2, "firstname");
			ps.setString(3, "lastname");
			ps.setInt(1, 1);
			ps.execute();
			System.out.println("inserted user");
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dropTables(){

		try {
			ps = connection.prepareStatement("drop table healthapp2.Login");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.Contact");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.Location");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.Event");
			ps.execute();
			
			ps = connection.prepareStatement("drop table healthapp2.Schedule");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.Administrator");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.MedicalStaff");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.Patient");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.Caregiver");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.HealthInfo");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.Treats");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.Medication");
			ps.execute();

			ps = connection.prepareStatement("drop table healthapp2.User_Account");
			ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTables() {
		try {
			ps = connection.prepareStatement("CREATE TABLE healthapp2.User_Account("
					+ "user_id int NOT NULL, firstname VARCHAR(20), lastname VARCHAR(20)," 
					+"username VARCHAR(20), password VARCHAR(20), role VARCHAR(20), Primary Key(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.Login("
					+ "id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"
					+ ", username VARCHAR(20), password VARCHAR(200), user_id int,"
					+ "FOREIGN KEY(user_id) REFERENCES healthapp2.User_Account(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.Contact("
					+ "user_id int, phone VARCHAR(20), email VARCHAR(20), address VARCHAR(100), "
					+ "FOREIGN KEY(user_id) REFERENCES healthapp2.User_Account(user_id))");
			ps.execute();

			ps = connection.prepareStatement(
					"CREATE TABLE healthapp2.Location(" + "name VARCHAR(20),location_type VARCHAR(20),User_ID int,"
							+ "FOREIGN KEY(user_id) REFERENCES healthapp2.User_Account(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.Schedule("
					+ "Schedule_ID int Primary Key, User_ID int, FOREIGN KEY(user_id) "
					+ "REFERENCES healthapp2.User_Account(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.Event("
					+ "Event_ID int, name VARCHAR(20), Event_Date DATE, category VARCHAR(20), "
					+ "Schedule_ID int, FOREIGN KEY(Schedule_ID) " + "REFERENCES healthapp2.Schedule(Schedule_ID))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.Administrator("
					+ "Administrator_ID int Primary KEY, user_id int, FOREIGN KEY(user_id) "
					+ "REFERENCES healthapp2.User_Account(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.MedicalStaff("
					+ "Medical_ID int Primary Key, user_id int, role VARCHAR(20), FOREIGN KEY(user_id)"
					+ " REFERENCES healthapp2.User_Account(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.Patient(Patient_ID int NOT NULL, User_ID int,"
					+ " PRIMARY KEY(Patient_ID), FOREIGN KEY(user_id) REFERENCES healthapp2.User_Account(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.Caregiver("
					+ "User_ID int, name VARCHAR(20), phone VARCHAR(20), email VARCHAR(20),"
					+ " address VARCHAR(100), FOREIGN KEY(user_id) " + "REFERENCES healthapp2.Patient(user_id) )");
			ps.execute();

			ps = connection
					.prepareStatement("CREATE TABLE healthapp2.HealthInfo( User_ID int,height int, weight double, "
							+ "excerciseFrequency int, dateRecorded date, FOREIGN KEY(user_id) "
							+ "REFERENCES healthapp2.Patient(user_id))");
			ps.execute();

			ps = connection.prepareStatement(
					"CREATE TABLE healthapp2.Treats(" + "User_ID int,Medical_ID int,FOREIGN KEY(user_id) "
							+ "REFERENCES healthapp2.Patient(user_id),FOREIGN KEY(Medical_ID) "
							+ "REFERENCES healthapp2.MedicalStaff(Medical_ID))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE healthapp2.Medication( "
					+ "User_ID int, Medical_ID int, name VARCHAR(20), dosage VARCHAR(20), "
					+ "directions VARCHAR(100), refills int, nextRefillDate date, "
					+ "FOREIGN KEY(user_id) REFERENCES healthapp2.Patient(user_id), "
					+ "FOREIGN KEY(Medical_ID) REFERENCES healthapp2.MedicalStaff(Medical_ID))");
			ps.execute();
			System.out.println("Tables created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Create initial tables for healthapp2lication to query from when first started.
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
	public String login(String username, String password) {
		try {
			ps = connection.prepareStatement("SELECT * FROM healthapp2.login WHERE username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				if(password.equals(rs.getString("password")))
					return rs.getString("role");
				else
					return null;
			}
		} catch (SQLException e) {
			System.out.println("error in the prepared statement\n"+e.getMessage());
		}
		return null;
	}

//	/**
//	 * Finds patient from database given userID.
//	 * @param userID
//	 * @return Patient Object
//	 */
//	 public Patient findPatient(int userID){
//		 try {
//			 ps = connection.prepareStatement("SELECT * FROM Patient Natural Join User_Account WHERE User_ID = ?;");
//			 ps.setInt(1, userID);
//			 rs = ps.executeQuery();
//			 if (rs.next()) {
//				 Patient patient = new Patient(rs.getString("firstname"),
//						 rs.getString("lastname"), rs.getString("username"), rs.getString("password"), rs.getInt("User_ID")); 
//				 connection.close();
//				 return patient;
//			 }
//		 } catch (SQLException e) {
//		 }
//		 return null;
//	 }
//	/**
//	 * Finds MedicalStaff from database given userID.
//	 * @param userID
//	 * @return MedicalStaff Object
//	 */
//	 public MedicalStaff findMedicalStaff(int userID){
//		 try {
//			 ps = connection.prepareStatement("SELECT * FROM MedicalStaff Natural Join User_Account WHERE WHERE User_ID = ?;");
//			 ps.setInt(1, userID);
//			 rs = ps.executeQuery();
//			 if (rs.next()) {
//				 MedicalStaff MedicalStaff = new MedicalStaff(rs.getString("firstname"),
//						 rs.getString("lastname"), rs.getString("username"), rs.getString("password"), rs.getInt("User_ID")); 
//				 connection.close();
//				 return MedicalStaff;
//			 }
//		 } catch (SQLException e) {
//		 }
//		 return null;
//	 }
//	 /**
//	  * Finds Administrator from database given userID.
//	  * @param userID
//	  * @return Administrator Object
//	  */
//	 public Administrator findAdministrator(int userID){
//		 try {
//			 ps = connection.prepareStatement(
//					 "SELECT * FROM Administrator Natural Join User_Account WHERE User_ID = ?;");
//			 ps.setInt(1, userID);
//			 rs = ps.executeQuery();
//			 if (rs.next()) {
//				 Administrator Administrator = new Administrator(rs.getString("firstname"),
//						 rs.getString("lastname"), rs.getString("username"), rs.getString("password"), rs.getInt("User_ID")); 
//				 connection.close();
//				 return Administrator;
//			 }
//		 } catch (SQLException e) {
//		 }
//		 return null;
//	 }
	 
	 
	 
	 
	
//	 public void storePatient(Patient patient){
//	
//	 this.connect();
//	 try {
//	 PreparedStatement pstmt = connection.prepareStatement("insert into....");
//	 ResultSet rset = pstmt.executeQuery();
//	 rset.close();
//	 pstmt.close();
//	
//	 } catch (SQLException e) {
//	 System.out.println("Get Data Failed! Check output console");
//	 e.printStackTrace();
//	 return ;
//	 } return ;
//	 }
//	
//	 /**
//	 * Finds patient from database given userID
//	 * @param userID
//	 * @return Patient object
//	 */
//	 public Patient getPatient(String userID){
//	
//	 }
//	
//	 /**
//	 * Finds medicalstaff from database given name
//	 * @param name
//	 * @return
//	 */
//	 public MedicalStaff findMedicalStaff(String name){
//	
//	 }
//	
//	 /**
//	 * Finds patient from database fiven name.
//	 * @param name
//	 * @return
//	 */
//	 public Patient findPatient(String name){
//	
//	 }

}
