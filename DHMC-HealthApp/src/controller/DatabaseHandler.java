package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Administrator;
import model.MedicalStaff;
import model.Patient;

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
			String url = "jdbc:derby:HealthAppDB;create=true";
			connection = DriverManager.getConnection(url);
			if (connection != null) {
				System.out.println("Connected to Health App database");
				
//				dropTables();
//				createTables();
//				insertUser();
//				insertLoginUser();
//				getLoginUsers();
				
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
			ps = connection.prepareStatement("INSERT INTO app.login (username, password, user_id) VALUES (?, ?, ?)");
			ps.setString(1, "admin");
			ps.setString(2, "pass");
			ps.setInt(3, 1);
			ps.execute();
			System.out.println("Inserted login user");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getLoginUsers(){
		try {
			ps = connection.prepareStatement("SELECT * FROM app.login");
			
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
			ps = connection.prepareStatement("INSERT INTO app.user_account (firstname, lastname, role) VALUES (?, ?, ?)");
			ps.setString(1, "firstname");
			ps.setString(2, "lastname");
			ps.setString(3, "role");
			ps.execute();
			System.out.println("inserted user");
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dropTables(){

		try {
			ps = connection.prepareStatement("drop table app.login");
			ps.execute();

			ps = connection.prepareStatement("drop table app.contact");
			ps.execute();

			ps = connection.prepareStatement("drop table app.location");
			ps.execute();

			ps = connection.prepareStatement("drop table app.event");
			ps.execute();
			
			ps = connection.prepareStatement("drop table app.schedule");
			ps.execute();

			ps = connection.prepareStatement("drop table app.administrator");
			ps.execute();

			ps = connection.prepareStatement("drop table app.medication");
			ps.execute();
			
			ps = connection.prepareStatement("drop table app.treats");
			ps.execute();

			ps = connection.prepareStatement("drop table app.medical_staff");
			ps.execute();

			ps = connection.prepareStatement("drop table app.patient");
			ps.execute();

			ps = connection.prepareStatement("drop table app.caregiver");
			ps.execute();

			ps = connection.prepareStatement("drop table app.health_info");
			ps.execute();

			ps = connection.prepareStatement("drop table app.user_account");
			ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTables() {
		try {
//			ps = connection.prepareStatement("create schema healthapp");
//			ps.execute();
			
			ps = connection.prepareStatement("CREATE TABLE app.user_account("
					+ "user_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
					+ "firstname VARCHAR(20), lastname VARCHAR(20), role VARCHAR(20), Primary Key(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.login("
					+ "login_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "username VARCHAR(20), password VARCHAR(200), user_id int,"
					+ "FOREIGN KEY(user_id) REFERENCES app.user_account(user_id), Primary Key(login_id) )");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.contact("
					+ "contact_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ " user_id int, phone VARCHAR(20), email VARCHAR(20), address VARCHAR(100), "
					+ "FOREIGN KEY(user_id) REFERENCES app.user_account(user_id), Primary Key(contact_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.location(" 
					+ "location_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
					+ "name VARCHAR(20), location_type VARCHAR(20), Primary Key(location_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.schedule("
					+ "schedule_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ " user_id int, FOREIGN KEY(user_id) REFERENCES app.user_account(user_id),"
					+ "Primary Key(schedule_id) )");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.event("
					+ "event_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "name VARCHAR(20), event_date DATE, category VARCHAR(20), "
					+ "schedule_id int, FOREIGN KEY(schedule_id) " + "REFERENCES app.Schedule(schedule_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.administrator("
					+ "admin_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "user_id int, FOREIGN KEY(user_id) REFERENCES app.user_account(user_id),"
					+ "Primary Key(admin_id) )");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.medical_staff("
					+ "med_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "user_id int, role VARCHAR(20), FOREIGN KEY(user_id) REFERENCES app.user_account(user_id),"
					+ "Primary Key(med_id) )");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.patient("
					+ "patient_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ " user_id int, PRIMARY KEY(patient_id), "
					+ "FOREIGN KEY(user_id) REFERENCES app.user_account(user_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.caregiver("
					+ "caregiver_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "patient_id int, name VARCHAR(20), phone VARCHAR(20), email VARCHAR(20),"
					+ " address VARCHAR(100), FOREIGN KEY(patient_id) REFERENCES app.patient(patient_id),"
					+ "Primary Key(caregiver_id) )");
			ps.execute();

			ps = connection
					.prepareStatement("CREATE TABLE app.health_info("
							+ "health_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
							+ "patient_id int, height int, weight double, excerciseFrequency int, dateRecorded date, "
							+ "FOREIGN KEY(patient_id) REFERENCES app.patient(patient_id), "
							+ "Primary Key(health_id) )");
			ps.execute();

			ps = connection.prepareStatement(
					"CREATE TABLE app.treats("
					+ "treats_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "patient_id int, med_id int, "
					+ "FOREIGN KEY(patient_id) REFERENCES app.patient(patient_id),"
					+ "FOREIGN KEY(med_id) REFERENCES app.medical_staff(med_id))");
			ps.execute();

			ps = connection.prepareStatement("CREATE TABLE app.medication( "
					+ "medication_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "patient_id int, med_id int, name VARCHAR(20), dosage VARCHAR(20), "
					+ "directions VARCHAR(100), refills int, nextRefillDate date, "
					+ "FOREIGN KEY(patient_id) REFERENCES app.patient(patient_id), "
					+ "FOREIGN KEY(med_id) REFERENCES app.medical_staff(med_id))");
			ps.execute();
			System.out.println("Tables created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the given username and password are known in the database.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public String login(String username, String password) {
		try {
			ps = connection.prepareStatement("SELECT * FROM app.login WHERE username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				if(password.equals(rs.getString("password")))
					return "username: " + rs.getString("username");
				else
					return null;
			}
		} catch (SQLException e) {
			System.out.println("error in the prepared statement\n " + e.getMessage());
		}
		return null;
	}

	
	
	/**
	 * Finds patient from database given userID.
	 * @param userID
	 * @return Patient Object
	 */
	public Patient findPatient(int userID){
		try {
			ps = connection.prepareStatement("SELECT * FROM Patient Natural Join User_Account WHERE User_ID = ?;");
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				Patient patient = new Patient(rs.getString("firstname"),
						rs.getString("lastname"), rs.getString("username"), rs.getString("user_ID"), rs.getInt("patient_ID")); 
				connection.close();
				return patient;
			}
		} catch (SQLException e) {
		}
		return null;
	}
	/**
	 * Finds MedicalStaff from database given userID.
	 * @param userID
	 * @return MedicalStaff Object
	 */
	public MedicalStaff findMedicalStaff(int userID){
		try {
			ps = connection.prepareStatement("SELECT * FROM MedicalStaff Natural Join User_Account WHERE User_ID = ?;");
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				MedicalStaff MedicalStaff = new MedicalStaff(rs.getString("firstname"),
						rs.getString("lastname"), rs.getString("username"), rs.getString("User_ID"), rs.getInt("med_id")); 
				connection.close();
				return MedicalStaff;
			}
		} catch (SQLException e) {
		}
		return null;
	}
	/**
	 * Finds Administrator from database given userID.
	 * @param userID
	 * @return Administrator Object
	 */
	public Administrator findAdministrator(int userID){
		try {
			ps = connection.prepareStatement(
					"SELECT * FROM Administrator Natural Join User_Account WHERE User_ID = ?;");
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				Administrator Administrator = new Administrator(rs.getString("firstname"),
						rs.getString("lastname"), rs.getString("username"), rs.getString("User_ID"), rs.getInt("admin_id")); 
				connection.close();
				return Administrator;
			}
		} catch (SQLException e) {
		}
		return null;
	}

}
