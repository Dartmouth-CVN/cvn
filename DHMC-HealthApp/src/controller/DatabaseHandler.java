package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

import org.apache.derby.jdbc.EmbeddedDataSource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Administrator;
import model.Caregiver;
import model.Contact;
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
	private String[] firstNames = { "AARON", "ABDUL", "ABE", "ABEL", "ABRAHAM", "ABRAM", "ADALBERTO", "ADAM", "BARRY",
			"BART", "BARTON", "BASIL", "BEAU", "BEN", "BENEDICT", "BRANDEN", "BRANDON", "BRANT", "BRENDAN", "CEDRIC",
			"CEDRICK", "CESAR", "CHAD", "CHADWICK", "CORTEZ", "CORY", "DEREK", "DERICK", "DONNIE", "DOUGLASS", "DOYLE",
			"FRITZ", "GABRIEL", "GAIL", "GENARO", "GENE", "GEOFFREY", "GEORGE", "Henry", "HERB", "HERBERT", "HERIBERTO",
			"HERMAN", "HERSCHEL", "IRVING", "IRWIN", "ISAAC", "ISAIAH", "JEAN", "JED", "JEFFEREY", "JEFFERSON",
			"JEFFERY", "JESS", "JESS", "MOHAMED", "NED", "NEIL", "NOEL", "NOLAN", "NORBERT", "NORBERTO", "NORMAN",
			"NORMAND", "NORRIS", "ODELL", "ODIS", "OLIN", "OLIVER", "ORVILLE", "OSCAR", "OSVALDO", "OSWALDO", "RANDELL",
			"RANDOLPH", "RANDY", "RAPHAEL", "RASHAD", "WYATT", "XAVIER", "YONG", "YOUNG", "ZACHARIAH", "ZACHARY",
			"ZACHERY", "ZACK", "ZACKARY", "ZANE" };

	String[] lastNames = { "SMITH", "JOHNSON", "MARTINEZ", "THOMAS", "JACKSON", "LEE", "WALKER", "PEREZ", "HALL",
			"YOUNG", "ALLEN", "SANCHEZ", "WRIGHT", "KING", "SCOTT", "GREEN", "BAKER", "CAMPBELL", "MITCHELL", "ROBERTS",
			"CARTER", "PHILLIPS", "EVANS", "TURNER", "WARD", "COX", "DIAZ", "RICHARDSON" };

	String[] phoneNumbers = { "(741) 951-5271", "(561) 742-3921", "(784) 287-1076", "(838) 727-6573", "(500) 244-7083",
			"(943) 570-2414", "(874) 381-4941", "(367) 226-2040", "(815) 825-6662" };

	String[] emails = { "isi@tidur.org", "johu@codovo.org", "uj@orkimfah.com", "fapet@go.edu", "me@udaga.net",
			"kenna@vecbu.com", "gunob@moahu.net", "nashulu@it.gov", "opwankiw@seiteevo.io", "tifinpim@za.io",
			"nupedu@ta.edu", "juh@antof.com", "mewso@necuwnuk.io", "fuivif@daunen.gov", "hohzavi@paw.edu",
			"motjeni@muvu.io", "setosuf@oti.org", "lad@kupez.gov", "uggako@filse.net", "pivop@wewjur.com" };

	Random randomNumber;

	private DatabaseHandler() {
		randomNumber = new Random(100);
		connect();
		createTables();
		populateDatabase();
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

	public void populateDatabase() {
		addUsers(20);
		// addAdministrators();
		// addPatients();
		// addMedicalStaff();
	}

	public void addUsers(int number) {
		while (number-- > 0) {
			String firstname = getRandomFirstName();
			String lastname = getRandomLastName();
			String userID = getUserID(firstname, lastname, "Patient");
			LinkedList<String> numbers = new LinkedList<String>();
			for (int i = 0; i < 3; i++)
				numbers.add(getRandomPhoneNumber());
			LinkedList<String> emails = new LinkedList<String>();
			for (int i = 0; i < 3; i++)
				numbers.add(getRandomEmail());

			Contact contact = new Contact(userID);
			contact.setPhone(numbers);
			contact.setEmail(emails);

			insertUser(userID, firstname, lastname, contact);
			insertLogin(userID, "admin" + number, "pass" + number);
			insertPatient(userID);
		}
	}

	public String getRandomFirstName() {
		return firstNames[randomNumber.nextInt(firstNames.length)];
	}

	public String getRandomLastName() {
		return lastNames[randomNumber.nextInt(lastNames.length)];
	}

	public String getRandomPhoneNumber() {
		return phoneNumbers[randomNumber.nextInt(phoneNumbers.length)];
	}

	public String getRandomEmail() {
		return emails[randomNumber.nextInt(emails.length)];
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

	public boolean createUserAccountTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE user_account("
					+ "user_id VARCHAR(50), firstname VARCHAR(20), lastname VARCHAR(20), contact_info BLOB (10M),"
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
					+ "username VARCHAR(20), password VARCHAR(200), user_id VARCHAR(50),"
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
					+ " user_id VARCHAR(50), FOREIGN KEY(user_id) REFERENCES user_account(user_id),"
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
					+ "user_id VARCHAR(50), FOREIGN KEY(user_id) REFERENCES user_account(user_id),"
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
					+ "user_id VARCHAR(50), position VARCHAR(20), FOREIGN KEY(user_id) REFERENCES user_account(user_id),"
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
					+ " user_id VARCHAR(50), PRIMARY KEY(patient_id), "
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
					+ "patient_id INT, name VARCHAR(20), birthday DATE, isFamily? BOOLEAN, relation VARCHAR(10), contact_info BLOB (10M) "
					+ "FOREIGN KEY(patient_id) REFERENCES patient(patient_id)," + "Primary Key(caregiver_id) )");
			ps.execute();
			success = true;
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean createHealthInfoTable() {
		success = false;
		try {
			ps = connection.prepareStatement("CREATE TABLE health_info("
					+ "health_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "patient_id INT, date VARCHAR(10), height DOUBLE, weight DOUBLE, bmi DOUBLE, fat DOUBLE, caloriesBurned DOUBLE, "
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
				createUserAccountTable();

			rs = metaData.getTables(null, "APP", "LOGIN", null);
			if (!rs.next())
				createLoginTable();

			rs = metaData.getTables(null, "APP", "LOCATION", null);
			if (!rs.next())
				createLocationTable();

			rs = metaData.getTables(null, "APP", "PATIENT", null);
			if (!rs.next())
				createPatientTable();

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
	public Patient getPatient(String userID) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement(" SELECT * FROM patient NATURAL JOIN user_account WHERE user_id = ?");
				ps.setString(1, userID);
				rs = ps.executeQuery();
				if (rs.next()) {
					Blob blob = rs.getBlob("contact_info");
					ByteArrayInputStream baip = new ByteArrayInputStream(blob.getBytes(1, (int) blob.length()));
					ObjectInputStream ois = new ObjectInputStream(baip);
					Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("user_id"), rs.getInt("patient_id"), new Contact(userID) );
					connection.close();
					return patient;
				}
			}
		} catch (SQLException | IOException e) {
			MainApp.printError(e);
		}
		return null;
	}

	public ObservableList<IDisplayable> searchPatient(String name) {
		ObservableList<IDisplayable> patientList = FXCollections.observableArrayList();
		try {
			connect();
			ps = connection.prepareStatement(
					"SELECT * FROM patient Natural Join user_account" + " WHERE firstname LIKE ? OR lastname LIKE ?");
			ps.setString(1, name);
			ps.setString(2, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
						String.valueOf(rs.getInt("user_id")), rs.getInt("patient_id"));
				patientList.add(patient);
			}

			connection.close();
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return patientList;
	}

	public ObservableList<IDisplayable> searchPatient() {
		ObservableList<IDisplayable> patientList = FXCollections.observableArrayList();
		try {
			if (connect()) {
				ps = connection.prepareStatement("SELECT * FROM patient Natural Join user_account");
				rs = ps.executeQuery();
				while (rs.next()) {
					Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("user_id"), rs.getInt("patient_id"));
					patientList.add(patient);
				}

				connection.close();
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return patientList;
	}

	public LinkedList<Pet> getPets(Patient patient) {
		LinkedList<Pet> patientPets = new LinkedList<Pet>();
		try {
			ps = connection.prepareStatement("SELECT * FROM pet WHERE patient_id = ?;");
			ps.setInt(1, patient.getPatientID());
			rs = ps.executeQuery();
			while (rs.next()) {
				Pet pet = new Pet(rs.getString("name"), rs.getString("species"), rs.getBoolean("allergy_freindly"));
				patientPets.add(pet);
			}
			connection.close();
		} catch (SQLException e) {
		}
		return patientPets;
	}

	public LinkedList<Meal> getMeals(Patient patient) {
		LinkedList<Meal> patientMeals = new LinkedList<Meal>();
		try {
			ps = connection.prepareStatement("SELECT * FROM meal WHERE patient_id = ?;");
			ps.setInt(1, patient.getPatientID());
			rs = ps.executeQuery();
			while (rs.next()) {
				Meal meal = new Meal(rs.getString("name"), rs.getInt("calories"), rs.getBoolean("like"),
						rs.getBoolean("dislike"), rs.getString("notes"));
				patientMeals.add(meal);
			}
			connection.close();
		} catch (SQLException e) {
		}
		return patientMeals;
	}

	public boolean insertUser(String userID, String firstName, String lastName, Contact contactInfo) {
		success = false;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			if (connect()) {
				oos.writeObject(contactInfo);
				byte[] byteArray = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
				ps = connection
						.prepareStatement("INSERT INTO user_account (user_id, firstname, lastname, contact_info) "
								+ "VALUES(?, ?, ?, ?)");

				ps.setString(1, userID);
				ps.setString(2, firstName);
				ps.setString(3, lastName);
				ps.setBinaryStream(4, bais);
				ps.executeUpdate();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		} catch (IOException e) {
			System.err.println("Error inserting user");
			MainApp.printError(e);
		}
		return success;
	}

	public boolean insertLogin(String userID, String username, String password) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement("INSERT INTO login (user_id, username, password) VALUES (?, ?, ?)");

				ps.setString(1, userID);
				ps.setString(2, username);
				ps.setString(3, password);
				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean insertPatient(Patient p) {
		success = false;
		try {
			if (insertUser(p.getUserID(), p.getFirstName(), p.getLastName(), p.getContactInfo()) && connect()) {
				ps = connection.prepareStatement("INSERT INTO patient (user_id) VALUES (?)");

				ps.setString(1, p.getUserID());
				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean insertPatient(String userID) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement("INSERT INTO patient (user_id) VALUES (?)");

				ps.setString(1, userID);
				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean insertPet(Pet pet, Patient patient) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement(
						"INSERT INTO pet (name, species, allergy_freindly, patient_id) " + "VALUES (?, ?, ?, ?)");

				ps.setString(1, pet.getName());
				ps.setBoolean(2, pet.getAllergyFriendly());
				ps.setString(3, pet.getSpecies());
				ps.setInt(4, patient.getPatientID());
				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean insertPatients(LinkedList<Patient> patients) {
		success = true;
		for (Patient p : patients) {
			if (!insertPatient(p))
				success = false;
		}
		return success;
	}

	public boolean insertMedicalStaff(MedicalStaff staff) {
		success = false;
		try {
			if (insertUser(staff.getUserID(), staff.getFirstName(), staff.getLastName(), staff.getContactInfo())
					&& connect()) {
				ps = connection.prepareStatement("INSERT INTO medical_staff (user_id, position) VALUES (?,?)");

				ps.setString(1, staff.getUserID());
				ps.setString(2, staff.getPosition());

				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean insertAdmin(Administrator admin) {
		success = false;
		try {
			if (insertUser(admin.getUserID(), admin.getFirstName(), admin.getLastName(), admin.getContactInfo())
					&& connect()) {
				ps = connection.prepareStatement("INSERT INTO administrator (user_id) VALUES (?)");
				ps.setString(1, admin.getUserID());
				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	/**
	 * @param p
	 *            patient object
	 */
	public boolean insertMeal(Meal meal, Patient p) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement(
						"INSERT INTO meal (name, calories, like, dislike, notes, patient_id) VALUES(?, ?, ?, ?, ?, ?)");

				ps.setString(1, meal.getName());
				ps.setInt(2, meal.getCalories());
				ps.setBoolean(3, meal.didLike());
				ps.setBoolean(4, meal.didDislike());
				ps.setString(5, meal.getSpecialNotes());
				ps.setInt(6, p.getPatientID());
				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	public boolean updateUser(String userID, String firstName, String lastName, Contact contactInfo) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement(
						"UPDATE user_account SET firstname = ?, " + "lastname = ?, contact_info = ? WHERE user_id = ?");

				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setObject(3, contactInfo);
				ps.setString(4, userID);

				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return success;
	}

	public boolean updatePatient(Patient patient) {
		success = false;
		if (updateUser(patient.getUserID(), patient.getFirstName(), patient.getLastName(), patient.getContactInfo())) {
			success = true;
		}
		return success;
	}

	public boolean updateMedicalStaff(MedicalStaff staff) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement("UPDATE medical_staff SET position = ? WHERE med_id = ?");
				ps.setString(1, staff.getPosition());
				ps.setInt(2, staff.getMedID());
				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	/**
	 * 
	 * @param p
	 */
	public boolean updatePet(Pet pet, Patient p) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement(
						"UPDATE pet SET species = ?, name = ?, allergy_friendly = ? WHERE patient_id = ?");
				ps.setString(1, pet.getSpecies());
				ps.setString(2, pet.getName());
				ps.setBoolean(3, pet.getAllergyFriendly());
				ps.setInt(4, p.getPatientID());
				ps.executeUpdate();
				success = true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return success;
	}

	/**
	 * Finds meal based on patient id and updates fields with fields from meal
	 * object
	 * 
	 * @param meal
	 * @param p
	 */
	public void updateMeal(Meal meal, Patient p) {

		try {
			connect();
			ps = connection.prepareStatement("UPDATE meal SET name = ?, calories = ?, like = ?, dislike = ?, notes = ? "
					+ "WHERE patient_id = ?");
			ps.setString(1, meal.getName());
			ps.setInt(2, meal.getCalories());
			ps.setBoolean(3, meal.didLike());
			ps.setBoolean(4, meal.didDislike());
			ps.setString(5, meal.getSpecialNotes());
			ps.setInt(6, p.getPatientID());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Insert into caregiver table fields from caregiver object based on patient
	 * id
	 * 
	 * @param caregiver
	 * @param p
	 */
	public void insertCaregiver(Caregiver caregiver, Patient p) {
		try {
			ps = connection.prepareStatement(
					"INSERT INTO caregiver (name, isFamily?, relation, patient_id) VALUES(?, ?, ?, ?)");

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
	 * Updates fields of given caregiver in table with new fields from caregiver
	 * input.
	 * 
	 * @param caregiver
	 */
	public void updateCaregiver(Caregiver caregiver) {

		try {
			connect();
			ps = connection.prepareStatement(
					"UPDATE caregiver SET name = ?, isFamily? = ?, relation = ? " + "WHERE caregiver_id = ?");
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
	 * 
	 * @param info
	 * @param p
	 */
	public void insertHealthInfo(HealthInfo info, Patient p) {
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
			ps.setDouble(13, info.getMinVeryActive());
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

	public void updateHealthInfo(HealthInfo info, Patient p) {

		try {
			connect();
			ps = connection.prepareStatement("UPDATE health_info SET date = ?, height = ?, weight = ?, "
					+ "bmi = ?, fat = ?, caloriesBurned = ?, steps = ?, distance = ?, floors = ?, minSedentary = ?, "
					+ "minLightlyActive = ?, minFairlyActive = ?, minVeryActive = ?, activityCalories = ?, minAsleep = ?, "
					+ "minAwake = ?, numAwakenings = ?, timeInBed = ? " + "WHERE  patient_id = ?");
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
			ps.setDouble(13, info.getMinVeryActive());
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
			return rs.getFetchSize();
		} catch (SQLException e) {
			MainApp.printError(e);
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
