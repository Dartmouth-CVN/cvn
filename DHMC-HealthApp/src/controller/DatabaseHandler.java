package controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import org.apache.derby.jdbc.EmbeddedDataSource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Administrator;
import model.Caregiver;
import model.HealthInfo;
import model.IDisplayable;
import model.MainApp;
import model.Meal;
import model.MedicalStaff;
import model.Patient;
import model.Pet;

public class DatabaseHandler {

	private static PreparedStatement ps;
	private static ResultSet rs;
	private static Connection connection;
	private static DatabaseMetaData metaData;
	private boolean success;// monitors if sql interactions result in errors

	private static DatabaseHandler uniqueInstance;

	private DatabaseHandler() {
		connect();
		createTables();
		insertDummyUser();
		insertDummyLogin();
	}

	/**
	 * Get the unique database instance
	 * 
	 * @return DatabaseHandler object
	 */
	public static DatabaseHandler getUniqueInstance() {
		if (connection != null)
			return uniqueInstance;
		else {
			uniqueInstance = new DatabaseHandler();
			return uniqueInstance;
		}
	}

	public void insertDummyLogin() {
		try {
			ps = connection.prepareStatement("INSERT INTO login (username, password, user_id) VALUES(?, ?, ?)");

			ps.setString(1, "admin");
			ps.setString(2, "pass");
			ps.setString(3, "Patient1");

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			MainApp.printError(e);
		}
	}

	public void insertDummyUser() {
		try {
			ps = connection.prepareStatement("INSERT INTO user_account (user_id, firstname, lastname) VALUES(?, ?, ?)");
			ps.setString(1, "Patient1");
			ps.setString(2, "Dummy");
			ps.setString(3, "User");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			MainApp.printError(e);
		}
	}

	public boolean connect() {
		boolean connected = false;
		try {
			// connect method - embedded driver
			EmbeddedDataSource ds = new EmbeddedDataSource();
			ds.setDatabaseName("HealthApp");
			ds.setCreateDatabase("create");
			connection = ds.getConnection();
			if (connection != null) {
				System.out.println("Connected to database");
				metaData = connection.getMetaData();
				connected = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return connected;
	}

	public boolean createUserAccountsTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE user_account("
					+ "user_id VARCHAR(10), firstname VARCHAR(20), lastname VARCHAR(20), contact BLOB (10M),"
					+ "Primary Key(user_id) )");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createLoginTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE login("
					+ "login_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "username VARCHAR(20), password VARCHAR(200), user_id VARCHAR(10),"
					+ "FOREIGN KEY(user_id) REFERENCES user_account(user_id), Primary Key(login_id) )");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createLocationTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE location("
					+ "location_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
					+ "name VARCHAR(20), location_type VARCHAR(20), Primary Key(location_id))");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createScheduleTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE schedule("
					+ "schedule_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ " user_id VARCHAR(10), FOREIGN KEY(user_id) REFERENCES user_account(user_id),"
					+ "Primary Key(schedule_id) )");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createEventTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE event("
					+ "event_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "name VARCHAR(20), event_date DATE, category VARCHAR(20), "
					+ "schedule_id INT, FOREIGN KEY(schedule_id) " + "REFERENCES Schedule(schedule_id))");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createAdminTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE admin("
					+ "admin_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "user_id VARCHAR(10), FOREIGN KEY(user_id) REFERENCES user_account(user_id),"
					+ "Primary Key(admin_id) )");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createMedicalStaffTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE medical_staff("
					+ "med_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "user_id VARCHAR(10), position VARCHAR(20), FOREIGN KEY(user_id) REFERENCES user_account(user_id),"
					+ "Primary Key(med_id) )");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createPatientTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE patient("
					+ "patient_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ " user_id VARCHAR(10), PRIMARY KEY(patient_id), "
					+ "FOREIGN KEY(user_id) REFERENCES user_account(user_id))");
			ps.execute();

			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createPetTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE pet("
					+ "pet_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "name VARCHAR(10), species VARCHAR(20), allergy_friendly BOOLEAN, patient_id INT, PRIMARY KEY(patient_id), "
					+ "FOREIGN KEY(patient_id) REFERENCES patient(patient_id))");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createMealTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE meal("
					+ "meal_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "name VARCHAR(20), calories INT, like BOOLEAN, dislike BOOLEAN, notes LONG VARCHAR"
					+ " patient_id INT, PRIMARY KEY(patient_id), "
					+ "FOREIGN KEY(patient_id) REFERENCES patient(patient_id))");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createCaregiverTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE caregiver("
					+ "caregiver_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "patient_id INT, name VARCHAR(20), birthday DATE isFamily? BOOLEAN, relation VARCHAR(10),"
					+ "FOREIGN KEY(patient_id) REFERENCES patient(patient_id)," + "Primary Key(caregiver_id) )");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	/*
	 * add fields: string date, double [height, weight, bmi, fat,
	 * caloriesBurned, steps, distance, floors, minSedentary, minLightlyActive,
	 * minFairlyActive, minVeryActive, activityCalories, minAsleep, minAwake,
	 * numAwakenings, timeInBed]
	 */
	public boolean createHealthInfoTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE health_info("
					+ "health_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "patient_id INT, date DATE, height DOUBLE, weight DOUBLE, bmi DOUBLE, fat DOUBLE, caloriesBurned DOUBLE, "
					+ "steps DOUBLE, distance DOUBLE, floors DOUBLE, minSedentary DOUBLE, minLightlyActive DOUBLE,"
					+ "minFairlyActive DOUBLE, minVeryActive DOUBLE, activityCalories DOUBLE, minAsleep DOUBLE,"
					+ "minAwake DOUBLE, numAwakenings DOUBLE, timeInBed DOUBLE,"
					+ "FOREIGN KEY(patient_id) REFERENCES patient(patient_id),Primary Key(health_id) )");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createStaffAssignmentTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE staff_assignment("
					+ "treats_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "patient_id int, med_id int, " + "FOREIGN KEY(patient_id) REFERENCES patient(patient_id),"
					+ "FOREIGN KEY(med_id) REFERENCES medical_staff(med_id))");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createMedicationTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE medication( "
					+ "medication_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "patient_id int, med_id int, name VARCHAR(20), dosage VARCHAR(20), "
					+ "directions VARCHAR(100), refills int, nextRefillDate date, "
					+ "FOREIGN KEY(patient_id) REFERENCES patient(patient_id), "
					+ "FOREIGN KEY(med_id) REFERENCES medical_staff(med_id))");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public void createTables() {
		try {
			connect();
			// check if the tables exist in our schema, create if they dont
			rs = metaData.getTables(null, "APP", "USER_ACCOUNT", null);
			if (!rs.next())
				createUserAccountsTable();

			rs = metaData.getTables(null, "APP", "LOGIN", null);
			if (!rs.next())
				createLoginTable();

			rs = metaData.getTables(null, "APP", "LOCATION", null);
			if (!rs.next())
				createLocationTable();

			rs = metaData.getTables(null, "APP", "SCHEDULE", null);
			if (!rs.next())
				createScheduleTable();

			rs = metaData.getTables(null, "APP", "EVENT", null);
			if (!rs.next())
				createEventTable();

			rs = metaData.getTables(null, "APP", "ADMIN", null);
			if (!rs.next())
				createAdminTable();

			rs = metaData.getTables(null, "APP", "MEDICAL_STAFF", null);
			if (!rs.next())
				createMedicalStaffTable();

			rs = metaData.getTables(null, "APP", "PATIENT", null);
			if (!rs.next())
				createPatientTable();

			rs = metaData.getTables(null, "APP", "HEALTH_INFO", null);
			if (!rs.next())
				createHealthInfoTable();

			rs = metaData.getTables(null, "APP", "STAFF_ASSIGNMENT", null);
			if (!rs.next())
				createStaffAssignmentTable();

			rs = metaData.getTables(null, "APP", "MEDICATION", null);
			if (!rs.next())
				createMedicationTable();
			if (ps != null) {
				ps.close();
				System.out.println("Tables created");
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
	}

	/**
	 * Validates the input login credentials.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public String login(String username, String password) {
		String userID = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM login NATURAL JOIN user_account  WHERE username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (password.equals(rs.getString("password")))
					userID = rs.getString("user_id");
			}
		} catch (SQLException e) {
			System.out.printf("Unable to validate login\n%s ", e.getMessage());
		}
		return userID;
	}

	/**
	 * Finds patient from database given userID.
	 * 
	 * @param userID
	 * @return Patient Object
	 */
	public Patient findPatient(int userID) {
		try {
			connect();
			ps = connection.prepareStatement("SELECT * FROM patient NATURAL JOIN user_account WHERE user_id = ?");
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("user_id"), rs.getInt("patient_id"));
				connection.close();
				return patient;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ObservableList<IDisplayable> searchPatient(String name) {
		ObservableList<IDisplayable> patientList = FXCollections.observableArrayList();
		try {
			connect();
			ps = connection
					.prepareStatement("SELECT * FROM patient Natural Join user_account " + "WHERE firstname = ?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
						String.valueOf(rs.getInt("user_id")), rs.getInt("patient_id"));
				patientList.add(patient);
			}

			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return patientList;
	}

	public ObservableList<IDisplayable> searchPatient() {
		ObservableList<IDisplayable> personData = FXCollections.observableArrayList();
		try {
			ps = connection.prepareStatement("SELECT * FROM patient NATURAL JOIN user_account");
			rs = ps.executeQuery();
			while (rs.next()) {
				Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
						String.valueOf(rs.getInt("user_id")), rs.getInt("patient_id"));
				personData.add(patient);
			}

			connection.close();
		} catch (SQLException e) {
		}
		return personData;
	}

	public LinkedList<Patient> getPTS() {
		LinkedList<Patient> personData = new LinkedList<Patient>();
		try {
			connect();
			ps = connection.prepareStatement("SELECT * FROM patient NATURAL JOIN user_account");
			rs = ps.executeQuery();
			while (rs.next()) {
				Patient user = new Patient(rs.getString("firstname"), rs.getString("lastname"),
						String.valueOf(rs.getInt("user_id")), rs.getInt("patient_id"));
				personData.add(user);
			}

			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return personData;
	}

	/**
	 * Finds MedicalStaff from database given userID.
	 * 
	 * @param userID
	 * @return MedicalStaff Object
	 */
	public MedicalStaff findMedicalStaff(int userID) {
		try {
			ps = connection.prepareStatement("SELECT * FROM MedicalStaff Natural Join User_Account WHERE User_ID = ?;");
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				MedicalStaff medicalStaff = new MedicalStaff(rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("position"), rs.getString("user_id"), rs.getInt("med_id"));
				connection.close();
				return medicalStaff;
			}
		} catch (SQLException e) {
		}
		return null;
	}

	/**
	 * Finds Administrator from database given userID.
	 * 
	 * @param userID
	 * @return Administrator Object
	 */
	public Administrator findAdministrator(int userID) {
		try {
			ps = connection
					.prepareStatement("SELECT * FROM administrator Natural Join User_Account WHERE user_id = ?;");
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				Administrator Administrator = new Administrator(rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("user_id"), rs.getInt("admin_id"));
				connection.close();
				return Administrator;
			}
		} catch (SQLException e) {
		}
		return null;
	}

	public int findUser(int userID) {
		try {
			ps = connection.prepareStatement("SELECT * FROM user_account WHERE user_id = ?");
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("user_id");
			}
		} catch (SQLException e) {
		}
		return -1;
	}

	public int findAnyUser() {
		try {
			ps = connection.prepareStatement("SELECT * FROM user_account");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("user_id");
			}
		} catch (SQLException e) {
		}
		return -1;
	}

	public int insertUser(String firstName, String lastName) {
		try {
			connect();
			ps = connection.prepareStatement("INSERT INTO user_account (firstname, lastname, role) VALUES(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.executeUpdate();
			int userID = 0;

			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				userID = rs.getInt(1);
			} else {
				System.out.println("Couldn't return any id");
			}
			return userID;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public int insertUser() {
		try {
			connect();
			ps = connection.prepareStatement("INSERT INTO user_account (firstname, lastname, role) VALUES(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, "Dummy");
			ps.setString(2, "User");
			ps.setString(3, "Patient");
			ps.executeUpdate();
			int userID = 0;

			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next()) {
				userID = rs.getInt(1);
			} else {
				System.out.println("Couldn't return any id");
			}
			return userID;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public void insertPatient(Patient p) {
		try {
			int userID = insertUser(p.getFirstName(), p.getLastName());
			ps = connection.prepareStatement("INSERT INTO patient (user_id) VALUES (?)");

			ps.setInt(1, userID);
			System.out.printf("inserted patient: %s %s\n", p.getFirstName(), p.getLastName());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}

	public void insertPatients(LinkedList<Patient> patients) {
		try {
			for (Patient p : patients) {
				int userID = insertUser(p.getFirstName(), p.getLastName());
				ps = connection.prepareStatement("INSERT INTO patient (user_id) VALUES (?)");

				ps.setInt(1, userID);
				System.out.printf("inserted patient: %s %s\n", p.getFirstName(), p.getLastName());

				ps.executeUpdate();
			}
			ps.close();
		} catch (SQLException e) {
		}
	}

	public void insertMedicalStaff(MedicalStaff staff) {
		try {
			ps = connection.prepareStatement("INSERT INTO user_account (firstname, lastname, user_id) VALUES(?, ?, ?);"
					+ "INSERT INTO medical_staff (user_id) VALUES (?,?)");

			ps.setString(1, staff.getFirstName());
			ps.setString(2, staff.getLastName());
			ps.setInt(3, Integer.parseInt(staff.getUserID()));

			ps.setInt(6, Integer.parseInt(staff.getUserID()));
			ps.setInt(7, staff.getMedID());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}

	public void insertAdmin(Administrator admin) {
		try {
			ps = connection.prepareStatement("INSERT INTO user_account (firstname, lastname, user_id) VALUES(?, ?, ?);"
					+ "INSERT INTO administrator (user_id) VALUES (?)");

			ps.setString(1, admin.getFirstName());
			ps.setString(2, admin.getLastName());
			ps.setInt(3, Integer.parseInt(admin.getUserID()));

			ps.setInt(6, Integer.parseInt(admin.getUserID()));
			ps.setInt(7, admin.getAdminID());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}

	public void updatePatient(Patient p) {
		try {
			connect();
			ps = connection.prepareStatement(
					"UPDATE user_account SET firstname = ?, lastname = ? WHERE user_id = ?");

			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setString(3, p.getUserID());
			System.out.println("updated patient: " + p.getFirstName());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateMedicalStaff(MedicalStaff staff) {
		try {
			ps = connection.prepareStatement("UPDATE user_account SET firstname = ?, lastname = ?, "
					+ "FROM user_account" + "WHERE user_id = ?");

			ps.setString(1, staff.getFirstName());
			ps.setString(2, staff.getLastName());
			ps.setString(4, staff.getUserID());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}

	public void updateAdmin(Administrator admin) {
		try {
			ps = connection.prepareStatement("UPDATE user_account SET firstname = ?, lastname = ?, role = ?"
					+ "FROM user_account" + "WHERE user_id = ?");

			ps.setString(1, admin.getFirstName());
			ps.setString(2, admin.getLastName());
			ps.setString(4, admin.getUserID());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}


	/**
	 * 
	 * @param p
	 */
	public void updatePet(Pet pet, Patient p){
		
		try {
				connect();
				ps = connection.prepareStatement("UPDATE pet SET species = ?, quantity = ?, allergy_friendly = ? WHERE patient_id = ?");
				ps.setString(1, pet.getSpecies());
				ps.setInt(2, pet.getQuantity());
				ps.setBoolean(3, pet.getAllergyFriendly());
				ps.setInt(4, p.getPatientID());
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}

	/**
	 * Insert a meal object into table based on patient
	 * @param meal object
	 * @param p patient object
	 */
	public void insertMeal(Meal meal, Patient p){
		try {
			ps = connection.prepareStatement("INSERT INTO meal (name, calories, like, dislike, notes, patient_id) VALUES(?, ?, ?, ?, ?, ?)");

			ps.setString(1, meal.getName());
			ps.setInt(2, meal.getCalories());
			ps.setBoolean(3, meal.getLiked());
			ps.setBoolean(4, meal.getDisliked());
			ps.setString(5, meal.getSpecialNotes());
			ps.setInt(6, p.getPatientID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}
	/**
	 * Finds meal based on patient id and updates fields with fields from meal object
	 * @param meal
	 * @param p
	 */
	public void updateMeal(Meal meal, Patient p){

		try {
			connect();
			ps = connection.prepareStatement("UPDATE meal SET name = ?, calories = ?, like = ?, dislike = ?, notes = ? " 
					+ "WHERE patient_id = ?");
			ps.setString(1, meal.getName());
			ps.setInt(2, meal.getCalories());
			ps.setBoolean(3, meal.getLiked());
			ps.setBoolean(4, meal.getDisliked());
			ps.setString(5, meal.getSpecialNotes());
			ps.setInt(6, p.getPatientID());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Insert into caregiver table fields from caregiver object based on patient id
	 * @param caregiver
	 * @param p
	 */
	public void insertCaregiver(Caregiver caregiver, Patient p){
		try {
			ps = connection.prepareStatement("INSERT INTO caregiver (name, isFamily?, relation, patient_id) VALUES(?, ?, ?, ?)");

			ps.setString(1, caregiver.getName());
			ps.setBoolean(2, caregiver.getIsFamily());
			ps.setString(3, caregiver.getRelation());
			ps.setInt(4, p.getPatientID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
}
	/**
	 * Updates fields of given caregiver in table with new fields from caregiver input.
	 * @param caregiver
	 */
	public void updateCaregiver(Caregiver caregiver){
		
		try {
				connect();
				ps = connection.prepareStatement("UPDATE caregiver SET name = ?, isFamily? = ?, relation = ? " 
					+ "WHERE caregiver_id = ?");
				ps.setString(1, caregiver.getName());
				ps.setBoolean(2, caregiver.getIsFamily());
				ps.setString(3, caregiver.getRelation());
				ps.setInt(4, caregiver.getCaregiverID());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
	/**
	 * Insert into HealthInfo table healthinfo fields by new object
	 * @param info
	 * @param p
	 */
	public void insertHealthInfo(HealthInfo info, Patient p){
		try {
			ps = connection.prepareStatement("INSERT INTO health_info (date, height, weight, bmi, fat, caloriesBurned, "
			+ "steps, distance, floors, minSedentary, minLightlyActive, minFairlyActive, minVeryActive, activityCalories, "
			+ " minAsleep, minAwake, numAwakenings, timeInBed, patient_id) " 
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setString(1, info.getDate());
			ps.setDouble(2, info.getHeight());
			ps.setDouble(3, info.getWeight());
			ps.setDouble(4, info.getBmi());
			ps.setDouble(5, info.getFat());
			ps.setDouble(6, info.getCaloriesBurned());
			ps.setDouble(7, info.getSteps());
			ps.setDouble(8, info.getDistance());
			ps.setDouble(9, info.getFloors());
			ps.setDouble(10, info.getMinSedentary());
			ps.setDouble(11, info.getMinLightlyActive());
			ps.setDouble(12, info.getMinFairlyActive());
			ps.setDouble(13, info.getMinVeryACtive());
			ps.setDouble(14, info.getActivityCalories());
			ps.setDouble(15, info.getMinAsleep());
			ps.setDouble(16, info.getMinAwake());
			ps.setDouble(17, info.getNumAwakenings());
			ps.setDouble(18, info.getTimeInBed());
			ps.setInt(19, p.getPatientID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
}
	public void updateHealthInfo(HealthInfo info, Patient p){

		try {
				connect();
				ps = connection.prepareStatement("UPDATE health_info SET date = ?, height = ?, weight = ?, " 
					+ "bmi = ?, fat = ?, caloriesBurned = ?, steps = ?, distance = ?, floors = ?, minSedentary = ?, "
					+ "minLightlyActive = ?, minFairlyActive = ?, minVeryActive = ?, activityCalories = ?, minAsleep = ?, "
					+ "minAwake = ?, numAwakenings = ?, timeInBed = ? "
					+ "WHERE  patient_id = ?");
				ps.setString(1, info.getDate());
				ps.setDouble(2, info.getHeight());
				ps.setDouble(3, info.getWeight());
				ps.setDouble(4, info.getBmi());
				ps.setDouble(5, info.getFat());
				ps.setDouble(6, info.getCaloriesBurned());
				ps.setDouble(7, info.getSteps());
				ps.setDouble(8, info.getDistance());
				ps.setDouble(9, info.getFloors());
				ps.setDouble(10, info.getMinSedentary());
				ps.setDouble(11, info.getMinLightlyActive());
				ps.setDouble(12, info.getMinFairlyActive());
				ps.setDouble(13, info.getMinVeryACtive());
				ps.setDouble(14, info.getActivityCalories());
				ps.setDouble(15, info.getMinAsleep());
				ps.setDouble(16, info.getMinAwake());
				ps.setDouble(17, info.getNumAwakenings());
				ps.setDouble(18, info.getTimeInBed());
				ps.setDouble(18, p.getPatientID());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}

	public void dropTables() {
		try {
			connect();
			ps = connection.prepareStatement("drop table login");
			ps.execute();

			ps = connection.prepareStatement("drop table contact");
			ps.execute();

			ps = connection.prepareStatement("drop table location");
			ps.execute();

			ps = connection.prepareStatement("drop table event");
			ps.execute();

			ps = connection.prepareStatement("drop table schedule");
			ps.execute();

			ps = connection.prepareStatement("drop table administrator");
			ps.execute();

			ps = connection.prepareStatement("drop table medication");
			ps.execute();

			ps = connection.prepareStatement("drop table treats");
			ps.execute();

			ps = connection.prepareStatement("drop table medical_staff");
			ps.execute();

			ps = connection.prepareStatement("drop table patient");
			ps.execute();

			ps = connection.prepareStatement("drop table caregiver");
			ps.execute();

			ps = connection.prepareStatement("drop table health_info");
			ps.execute();

			ps = connection.prepareStatement("drop table user_account");
			ps.execute();

			ps.close();
		} catch (SQLException e) {
			MainApp.printError(e);
		}
	}

	public int getUserIDHelper(String firstname, String lastname) {
		try {
			connect();
			ps = connection
					.prepareStatement("SELECT firstname FROM user_account WHERE firstname = ? AND lastname = ? ");
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			rs = ps.executeQuery();
			rs.last();
			return rs.getRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public String getUserID(String firstname, String lastname, String type) {
		int position = getUserIDHelper(firstname, lastname);
		int code = (firstname.hashCode() + position) + (lastname.hashCode() + position);
		switch (type) {
		case "Patient":
			return "P" + code;
		case "Medical Staff":
			return "M" + code;
		case "Admin":
			return "M" + code;
		case "Caregiver":
			return "C" + code;
		default:
			return null;
		}
	}

}
