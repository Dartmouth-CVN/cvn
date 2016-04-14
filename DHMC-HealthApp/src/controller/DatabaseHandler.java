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
import model.IDisplayable;
import model.MainApp;
import model.MedicalStaff;
import model.Patient;
<<<<<<< HEAD
import model.PatientProfile;
=======
>>>>>>> 6f1f71384988bf8bfb3acd9eb6363fa5ddea9235
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
					+ "species VARCHAR(20), quantity INT, allergy_friendly BOOLEAN, patient_id INT, PRIMARY KEY(patient_id), "
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
					+ "patient_id INT, name VARCHAR(20), isFamily? BOOLEAN, relation VARCHAR(10),"
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

<<<<<<< HEAD
=======
	public void insertPatient2(Patient p) {
		// PatientProfile preferences = p.getPreferences();
		// Object family = p.getFamily();
		// Object pets = p.getPets();
		// Object liked_meals = p.getLikedMeals();
		// Object disliked_meals = p.getDislikedMeals();
		// Object fitness_info = p.getFitness();
		//
		// try {
		// int userID = insertUser(p.getFirstName(), p.getLastName(),
		// p.getRole());
		// ps = connection.prepareStatement("INSERT INTO patient (user_id,
		// family, pets, liked_meals, disliked_meals, fitness_info) "
		// + "VALUES (?, ?, ?, ?, ?, ?)");
		//
		// ps.setInt(1, userID);
		// ps.setObject(2, family);
		// ps.setObject(3, pets);
		// ps.setObject(4, liked_meals);
		// ps.setObject(5, disliked_meals);
		// ps.setObject(6, fitness_info);
		//
		// System.out.printf("inserted patient: %s %s\n", p.getFirstName(),
		// p.getLastName());
		//
		// ps.executeUpdate();
		// ps.close();
		// } catch (SQLException e) {
		// }
	}

>>>>>>> 6f1f71384988bf8bfb3acd9eb6363fa5ddea9235
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
					"UPDATE user_account SET firstname = ?, lastname = ?, role = ? " + "WHERE user_id = ?");

			ps.setString(1, p.getFirstName());
			ps.setString(2, p.getLastName());
			ps.setInt(4, Integer.parseInt(p.getUserID()));
			System.out.println("updated patient: " + p.getFirstName());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateMedicalStaff(MedicalStaff staff) {
		try {
			ps = connection.prepareStatement("UPDATE user_account SET firstname = ?, lastname = ?, role = ?"
					+ "FROM user_account" + "WHERE user_id = ?");

			ps.setString(1, staff.getFirstName());
			ps.setString(2, staff.getLastName());
			ps.setInt(4, Integer.parseInt(staff.getUserID()));

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
			ps.setInt(4, Integer.parseInt(admin.getUserID()));

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
	}

	/**
	 * 
	 * @param p
	 */
	public void removeAllPets(Patient p) {

		try {
<<<<<<< HEAD
			connect();
			ps = connection.prepareStatement("DELETE FROM pet WHERE patient_id = ?");
			ps.setInt(1, p.getPatientID());
			int rset = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
=======
				connect();
				ps = connection.prepareStatement("DELETE FROM pet WHERE patient_id = ?");
				ps.setInt(1, p.getPatientID());
				ps.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
>>>>>>> 6f1f71384988bf8bfb3acd9eb6363fa5ddea9235
	}

	/**
	 * 
	 * @param p
	 */
	public void updatePets(Patient p){
		LinkedList<Pet> patientPets = p.getPreferences().getPets();
		try {
				connect();
				//remove all current patient pets
				removeAllPets(p);
				//update with new list of pets
				ps = connection.prepareStatement("INSERT INTO pet (patient_id, species, quantity, allergy_friendly) VALUES(?,?,?,?)");
				for (Pet pet: patientPets){	
					ps.setInt(1, p.getPatientID());
					ps.setString(2, pet.getSpecies());
					ps.setInt(3, pet.getQuantity());
					ps.setBoolean(4, pet.getAllergyFriendly());
					ps.executeUpdate();
					}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public LinkedList<Pet> searchPatientPets(Patient p) {
		LinkedList<Pet> patientPets = new LinkedList<Pet>();
		try {
			connect();
			ps = connection.prepareStatement("SELECT * FROM pet WHERE patient_id = ?");
			ps.setInt(1, p.getPatientID());
			rs = ps.executeQuery();
			while (rs.next()) {
				Pet pet = new Pet(rs.getString("species"), rs.getInt("quantity"), rs.getBoolean("allergy_friendly"));
				patientPets.add(pet);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return patientPets;
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
