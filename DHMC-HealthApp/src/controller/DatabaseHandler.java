package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
import model.PatientProfile;
import model.Pet;
import controller.RandomGenerator;

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
		if (connect()) {
			createTables();
			populateDatabase();
		} else {
			System.out.println("Couldn't connect");
		}
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
		// addUsers(200);
		populateDatabase(1);

		// insertDummyPatient();
		// addAdministrators();
		// addPatients();
		// addMedicalStaff();
	}

	public void populateDatabase(int number) {
		for (int i = 0; i < number; i++) {
			Patient p = getRandomPatient();
			insertPatient(p);
		}
	}

	public Patient getRandomPatient() {
		RandomGenerator randGen = new RandomGenerator();
		String firstname = RandomGenerator.getRandomFirstName();
		String lastname = RandomGenerator.getRandomLastName();
		String username = RandomGenerator.createUsername(firstname, lastname);
		Date birthday = RandomGenerator.getRandomBirthday();
		String room = RandomGenerator.getRandomRoom();
		Contact contactInfo = RandomGenerator.getRandomContactInfo();

		LinkedList<Pet> petList = new LinkedList<Pet>();
		Pet pet1 = randGen.getRandomPet();
		petList.add(pet1);

		LinkedList<Meal> mealList = new LinkedList<Meal>();
		Meal meal1 = randGen.getRandomMeal();
		mealList.add(meal1);

		LinkedList<Caregiver> caregiverList = new LinkedList<Caregiver>();
		Caregiver careg1 = randGen.getRandomCaregiver();
		caregiverList.add(careg1);

		LinkedList<MedicalStaff> staffList = new LinkedList<MedicalStaff>();
		MedicalStaff staff = new MedicalStaff("testID");
		staffList.add(staff);

		return new Patient(firstname, lastname, generateUserID(firstname, lastname, "Patient"), contactInfo);
	}

	public void addUsers(int number) {
		while (number-- > 0) {
			String firstname = getRandomFirstName();
			String lastname = getRandomLastName();
			String userID = generateUserID(firstname, lastname, "Patient");
			LinkedList<String> numbers = new LinkedList<String>();
			for (int i = 0; i < 3; i++)
				numbers.add(getRandomPhoneNumber());
			LinkedList<String> emails = new LinkedList<String>();
			for (int i = 0; i < 3; i++)
				emails.add(getRandomEmail());

			Contact contact = new Contact(userID);

			for (String n : numbers) {
				contact.addPhone(n);
			}
			for (String e : emails) {
				contact.addEmail(e);
			}
			contact.addAddressList(numbers);
			contact.addEmailList(emails);
			insertUser(userID, firstname, lastname, contact);
			insertLogin(userID, "admin" + number, "pass" + number);
			insertPatient(userID);
		}
		Contact contact = new Contact("445");
		insertUser("445", "Test", "Test", contact);
		insertPatient("445");
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

	public void insertDummyPatient2() {

	}

	public void insertDummyPatient() {
		// set up objects
		Contact info = new Contact();
		PatientProfile preferences = new PatientProfile();

		LinkedList<Pet> petList = new LinkedList<Pet>();
		Pet pet1 = new Pet("billy", "dog", true);
		petList.add(pet1);

		LinkedList<Meal> mealList = new LinkedList<Meal>();
		Meal meal1 = new Meal("pizza", 22, 4, "yum");
		mealList.add(meal1);

		LinkedList<Caregiver> caregiverList = new LinkedList<Caregiver>();
		Caregiver careg1 = new Caregiver("Fred", "4-19", "cousin", info, true);
		caregiverList.add(careg1);

		LinkedList<MedicalStaff> staffList = new LinkedList<MedicalStaff>();
		MedicalStaff staff = new MedicalStaff("testID");
		staffList.add(staff);

		preferences.setCaregiver(caregiverList);
		preferences.setMenu(mealList);
		preferences.setPet(petList);

		insertDummyUser();
		insertPatient("Bob", "Barker", "111", info);
		Patient dummy = getPatient("111");

		dummy.setPreferences(preferences);
		dummy.addMedicalStaff(staff);
		updatePatient(dummy);

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
				// System.out.println("Connected to database");
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
					+ "name VARCHAR(10), species VARCHAR(20), allergy_friendly BOOLEAN, user_id VARCHAR(50), PRIMARY KEY(user_id), "
					+ "FOREIGN KEY(user_id) REFERENCES patient(user_id))");
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
					+ "name VARCHAR(20), calories INT, rating INT, notes LONG VARCHAR"
					+ " user_id VARCHAR(50), PRIMARY KEY(user_id), "
					+ "FOREIGN KEY(user_id) REFERENCES patient(user_id))");
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
					+ "user_id VARCHAR(50), name VARCHAR(20), birthday DATE, inFamily BOOLEAN, relation VARCHAR(10), contact_info BLOB (10M) "
					+ "FOREIGN KEY(user_id) REFERENCES patient(user_id)," + "Primary Key(caregiver_id) )");
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
					+ "user_id VARCHAR(50), date VARCHAR(10), height DOUBLE, weight DOUBLE, bmi DOUBLE, fat DOUBLE, caloriesBurned DOUBLE, "
					+ "steps DOUBLE, distance DOUBLE, floors DOUBLE, minSedentary DOUBLE, minLightlyActive DOUBLE,"
					+ "minFairlyActive DOUBLE, minVeryActive DOUBLE, activityCalories DOUBLE, minAsleep DOUBLE,"
					+ "minAwake DOUBLE, numAwakenings DOUBLE, timeInBed DOUBLE,"
					+ "FOREIGN KEY(user_id) REFERENCES patient(user_id),Primary Key(health_id) )");
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

	private ByteArrayInputStream baip;
	private ObjectInputStream ois;

	public Patient getFilledPatient(String userID) {
		Patient p = getPatient(userID);

		PatientProfile profile = new PatientProfile();
		profile.setCaregiver(getCaregivers(userID));
		profile.setMenu(getMeals(userID));
		profile.setPet(getPets(userID));

		p.setPreferences(profile);
		p.setHealthInfo(getHealthInfo(userID));
		p.setAssignedStaff(searchPatientAssignedStaffList(userID));

		return p;
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

				ps = connection.prepareStatement(" SELECT * FROM user_account WHERE user_id = ?");
				ps.setString(1, userID);
				rs = ps.executeQuery();
				if (rs.next()) {

					// Blob blob = rs.getBlob("contact_info");
					InputStream stream = rs.getBinaryStream("contact_info");
					System.out.println(Contact.class);
					ObjectInputStream ois = new ObjectInputStream(stream);
					Contact contact = (Contact) ois.readObject();
					
					System.out.println(contact.getPhoneList());
					Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("user_id"), contact);
					connection.close();
					System.out.println(patient.getContactInfo().getPrimaryEmail());
					return patient;
				}
			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
			MainApp.printError(e);
		}
		return null;
	}

	/**
	 * Finds MedicalStaff from database given userID.
	 * 
	 * @param userID
	 * @return MedicalStaff Object
	 */
	public MedicalStaff getMedicalStaff(String userID) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement(" SELECT * FROM medical_staff WHERE user_id = ?");
				ps.setString(1, userID);
				rs = ps.executeQuery();
				if (rs.next()) {
					Blob blob = rs.getBlob("contact_info");
					baip = new ByteArrayInputStream(blob.getBytes(1L, (int) blob.length()));
					ois = new ObjectInputStream(baip);
					MedicalStaff medicalStaff = new MedicalStaff(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("position"), rs.getString("user_id"), rs.getInt("med_id"));
					connection.close();
					return medicalStaff;
				}
			}
		} catch (SQLException | IOException e) {
			MainApp.printError(e);
		}
		return null;
	}

	/**
	 * Finds Caregiver from database given userID.
	 * 
	 * @param userID
	 * @return Caregiver Object
	 */
	public Caregiver getCaregiver(String userID) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement(" SELECT * FROM caregiver WHERE user_id = ?");
				ps.setString(1, userID);
				rs = ps.executeQuery();
				if (rs.next()) {
					Blob blob = rs.getBlob("contact_info");
					baip = new ByteArrayInputStream(blob.getBytes(1L, (int) blob.length()));
					ois = new ObjectInputStream(baip);
					Caregiver caregiver = new Caregiver(rs.getString("name"), rs.getDate("birthday").toString(),
							rs.getString("relation"), (Contact) ois.readObject(), rs.getBoolean("inFamily"));
					connection.close();
					return caregiver;
				}
			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
			MainApp.printError(e);
		}
		return null;
	}

	public ObservableList<IDisplayable> searchPatient(String name) {
		ObservableList<IDisplayable> patientList = FXCollections.observableArrayList();
		try {
			if (connect()) {
				ps = connection.prepareStatement("SELECT * FROM patient Natural Join user_account"
						+ " WHERE firstname LIKE UPPER(?) OR lastname LIKE UPPER(?)");
				ps.setString(1, "%" + name + "%");
				ps.setString(2, "%" + name + "%");
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

	public ObservableList<IDisplayable> searchPatient() {
		ObservableList<IDisplayable> patientList = FXCollections.observableArrayList();
		try {
			if (connect()) {
				ps = connection.prepareStatement("SELECT * FROM user_account");
				rs = ps.executeQuery();
				while (rs.next()) {
					Blob aBlob = rs.getBlob("contact_info");
					byte[] byteArray = aBlob.getBytes(1, (int) aBlob.length());
					baip = new ByteArrayInputStream(byteArray);
					ois = new ObjectInputStream(baip);
					Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("user_id"), (Contact) ois.readObject());
					patientList.add(patient);
				}

				connection.close();
			}
		} catch (SQLException | NullPointerException | IOException | ClassNotFoundException e) {
			// MainApp.printError(e);
			e.printStackTrace();
		}
		return patientList;
	}

	/**
	 * Search for list of patients assigned to medical staff member
	 * 
	 * @param medstaff
	 *            object
	 * @return
	 */
	public ObservableList<Patient> searchMedStaffAssignedPatient(MedicalStaff staff) {
		ObservableList<Patient> patientList = FXCollections.observableArrayList();
		try {
			if (connect()) {
				ps = connection.prepareStatement("SELECT a.firstname, a.lastname, a.user_id " + "FROM user_account a "
						+ "JOIN patient p ON a.user_id = p.user_id "
						+ "JOIN staff_assignment s ON p.patient_id = s.patient_id " + "WHERE s.med_id = ? ");

				rs = ps.executeQuery();
				while (rs.next()) {
					Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("user_id"), 0);
					patientList.add(patient);
				}

				connection.close();
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return patientList;
	}

	/**
	 * returns observable list of MedicalStaff objects given a patient
	 * 
	 * @param p
	 *            patient object
	 * @return observable list of IDisplayable medstaff
	 */
	public ObservableList<MedicalStaff> searchPatientAssignedStaff(String userID) {
		ObservableList<MedicalStaff> medStaffList = FXCollections.observableArrayList();
		try {
			if (connect()) {
				ps = connection.prepareStatement(
						"SELECT * FROM staff_assignment RIGHT JOIN medical_staff ON staff_assignment.med_id = medical_staff.med_id"
								+ "JOIN patient ON staff_assignment.patient_id = patient.patient_id"
								+ " WHERE patient.user_id = ?");
				ps.setString(1, userID);
				rs = ps.executeQuery();
				while (rs.next()) {
					MedicalStaff medicalStaff = new MedicalStaff(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("position"), rs.getString("user_id"), rs.getInt("med_id"));
					medStaffList.add(medicalStaff);
				}

				connection.close();
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return medStaffList;
	}

	/**
	 * returns observable list of meal objects given a patient
	 * 
	 * @param p
	 *            patient object
	 * @return observable list of meal
	 */
	public ObservableList<Meal> searchPatientMeal(Patient p) {
		ObservableList<Meal> mealList = FXCollections.observableArrayList();
		try {
			if (connect()) {
				ps = connection.prepareStatement("SELECT * FROM meal" + " WHERE patient_id = ?");
				ps.setInt(1, p.getPatientID());
				rs = ps.executeQuery();
				while (rs.next()) {
					Meal meal = new Meal(rs.getString("name"), rs.getInt("calories"), rs.getInt("rating"),
							rs.getString("notes"));
					mealList.add(meal);
				}

				connection.close();
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return mealList;
	}

	/**
	 * returns observable list of caregiver objects given a patient
	 * 
	 * @param p
	 *            patient object
	 * @return observable list of caregiver
	 */
	public ObservableList<Caregiver> searchPatientCaregiver(Patient p) {
		ObservableList<Caregiver> caregiverList = FXCollections.observableArrayList();
		try {
			if (connect()) {
				ps = connection.prepareStatement("SELECT * FROM caregiver" + " WHERE patient_id = ?");
				ps.setInt(1, p.getPatientID());
				rs = ps.executeQuery();
				while (rs.next()) {
					Caregiver caregiver = new Caregiver(rs.getString("name"),
							asLocalDate(rs.getDate("birthday")).toString(), rs.getString("relation"),
							(Contact) rs.getObject("contact_info"), rs.getBoolean("inFamily"));
					caregiverList.add(caregiver);
				}

				connection.close();
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return caregiverList;
	}

	/**
	 * returns observable list of pet objects given a patient
	 * 
	 * @param patient
	 *            object
	 * @return observable list of pets
	 */
	public ObservableList<Pet> searchPatientPet(Patient p) {
		ObservableList<Pet> petList = FXCollections.observableArrayList();
		try {
			if (connect()) {
				ps = connection.prepareStatement("SELECT * FROM pet" + " WHERE patient_id = ?");
				ps.setInt(1, p.getPatientID());
				rs = ps.executeQuery();
				while (rs.next()) {
					Pet pet = new Pet(rs.getString("name"), rs.getString("species"), rs.getBoolean("allergy_friendly"));
					petList.add(pet);
				}

				connection.close();
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return petList;
	}

	public LinkedList<MedicalStaff> searchPatientAssignedStaffList(String userID) {
		LinkedList<MedicalStaff> medStaffList = new LinkedList<MedicalStaff>();
		try {
			if (connect()) {
				ps = connection.prepareStatement(
						"SELECT * FROM staff_assignment RIGHT JOIN medical_staff ON staff_assignment.med_id = medical_staff.med_id"
								+ "JOIN patient ON staff_assignment.patient_id = patient.patient_id"
								+ " WHERE patient.user_id = ?");
				ps.setString(1, userID);
				rs = ps.executeQuery();
				while (rs.next()) {
					MedicalStaff medicalStaff = new MedicalStaff(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("position"), rs.getString("user_id"), rs.getInt("med_id"));
					medStaffList.add(medicalStaff);
				}

				connection.close();
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return medStaffList;
	}

	public LinkedList<Patient> getPatientList() {
		LinkedList<Patient> patientList = new LinkedList<Patient>();
		try {
			if (connect()) {
				ps = connection.prepareStatement("SELECT * FROM user_account");
				rs = ps.executeQuery();
				while (rs.next()) {
					Patient patient = new Patient(rs.getString("firstname"), rs.getString("lastname"),
							rs.getString("user_id"), 0);
					patientList.add(patient);
				}

				connection.close();
			}
		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return patientList;
	}

	public LinkedList<Pet> getPets(String userId) {
		LinkedList<Pet> patientPets = new LinkedList<Pet>();
		try {
			ps = connection.prepareStatement("SELECT * FROM pet WHERE user_id = ?;");
			ps.setString(1, userId);
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

	public LinkedList<HealthInfo> getHealthInfo(String userId) {
		LinkedList<HealthInfo> patientHealthInfo = new LinkedList<HealthInfo>();
		try {
			ps = connection.prepareStatement("SELECT * FROM health_info WHERE user_id = ?;");
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				HealthInfo info = new HealthInfo(rs.getString("date"), rs.getDouble("height"), rs.getDouble("weight"),
						rs.getDouble("bmi"), rs.getDouble("fat"), rs.getDouble("caloriesBurned"), rs.getDouble("steps"),
						rs.getDouble("distance"), rs.getDouble("floors"), rs.getDouble("minSedentary"),
						rs.getDouble("minLightlyActive"), rs.getDouble("minFairlyActive"),
						rs.getDouble("minVeryActive"), rs.getDouble("activityCalories"), rs.getDouble("minAsleep"),
						rs.getDouble("minAwake"), rs.getDouble("numAwakenings"), rs.getDouble("timeInBed"));
				patientHealthInfo.add(info);
			}
			connection.close();
		} catch (SQLException e) {
		}
		return patientHealthInfo;
	}

	public LinkedList<Meal> getMeals(String userId) {
		LinkedList<Meal> patientMeals = new LinkedList<Meal>();
		try {
			ps = connection.prepareStatement("SELECT * FROM meal WHERE user_id = ?;");
			ps.setString(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Meal meal = new Meal(rs.getString("name"), rs.getInt("calories"), rs.getInt("rating"),
						rs.getString("notes"));
				patientMeals.add(meal);
			}
			connection.close();
		} catch (SQLException e) {
		}
		return patientMeals;
	}

	public LinkedList<Caregiver> getCaregivers(String userID) {
		LinkedList<Caregiver> patientCaregivers = new LinkedList<Caregiver>();
		try {
			ps = connection.prepareStatement("SELECT * FROM caregiver WHERE user_id = ?;");
			ps.setString(1, userID);
			rs = ps.executeQuery();
			while (rs.next()) {
				Caregiver care = new Caregiver(rs.getString("name"), asLocalDate(rs.getDate("birthday")).toString(),
						rs.getString("relation"), (Contact) rs.getObject("contact_info"), rs.getBoolean("inFamily"));
				patientCaregivers.add(care);
			}
			connection.close();
		} catch (SQLException e) {
		}
		return patientCaregivers;
	}
	
	public static byte[] serialize(Object obj) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(obj);
	    return out.toByteArray();
	}
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}
	
	public void myCode(){
//		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//		ObjectOutputStream oos = new ObjectOutputStream(buffer);
//		oos.writeObject(contactInfo);
//		oos.close();
//		byte[] byteArray = buffer.toByteArray();
//		for(byte b : byteArray)
//			System.out.print( (char) b);
//		System.out.println();
//		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
//		ObjectInputStream ois = new ObjectInputStream(bais);
	}

	public boolean insertUser(String userID, String firstName, String lastName, Contact contactInfo) {
		success = false;
		try {
			if (connect()) {
				byte[] objectBytes = serialize(contactInfo);
				Contact desel  = (Contact) deserialize(objectBytes);
				System.out.println("Contact info: " + desel.getPhoneList());
				ps = connection
						.prepareStatement("INSERT INTO user_account (user_id, firstname, lastname, contact_info) "
								+ "VALUES(?, ?, ?, ?)");

				ps.setString(1, userID);
				ps.setString(2, firstName);
				ps.setString(3, lastName);
				ps.setBinaryStream(4, new ByteArrayInputStream(objectBytes), objectBytes.length);
				ps.executeUpdate();
				success = true;
			}
		} catch (SQLException | IOException | ClassNotFoundException e) {
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

	public boolean insertPatient(String first, String last, String userID, Contact info) {
		success = false;
		try {

			if (insertUser(userID, first, last, info) && connect()) {
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

	/**
	 * Insert single Patient into user_account and patient table
	 * 
	 * @param p
	 * @return
	 */
	public boolean insertPatient(Patient p) {
		success = false;
		try {
			if (p.getUserID().equals(""))
				p.setUserID(generateUserID(p.getFirstName(), p.getLastName(), "Patient"));

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

	/**
	 * Insert userID into patient table
	 * 
	 * @param userID
	 * @return
	 */
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

	/**
	 * Insert multiple patients
	 * 
	 * @param patients
	 * @return
	 */
	public boolean insertPatients(LinkedList<Patient> patients) {
		success = true;
		for (Patient p : patients) {
			if (!insertPatient(p))
				success = false;
		}
		return success;
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
					"INSERT INTO caregiver (name, inFamily, relation, patient_id) VALUES(?, ?, ?, ?)");

			ps.setString(1, caregiver.getName());
			ps.setBoolean(2, caregiver.getInFamily());
			ps.setString(3, caregiver.getRelation());
			ps.setInt(4, p.getPatientID());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
		}
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

	/**
	 * Insert single medicalstaff into user_account and medstaff table
	 * 
	 * @param staff
	 * @return
	 */
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

	/**
	 * insert assigned staff relationship into staff_assignment
	 * 
	 * @param staff
	 * @param p
	 * @return
	 */
	public boolean insertAssignedStaff(MedicalStaff staff, Patient p) {
		success = false;
		try {
			connect();

			ps = connection.prepareStatement("INSERT INTO staff_assignment (med_id, patient_id) VALUES (?,?)");

			ps.setInt(1, staff.getMedID());
			ps.setInt(2, p.getPatientID());

			ps.executeUpdate();
			ps.close();
			success = true;

		} catch (SQLException e) {
			MainApp.printError(e);
		}
		return success;
	}

	/**
	 * Insert multiple medical staff members as assigned staff of patient
	 * 
	 * @param patients
	 * @return
	 */
	public boolean insertAssignedMedicalStaffMembers(LinkedList<MedicalStaff> staffList, Patient p) {
		success = true;
		for (MedicalStaff staff : staffList) {
			if (!insertAssignedStaff(staff, p))
				success = false;
		}
		return success;
	}

	/**
	 * Insert single Admin ito user_account and admin table
	 * 
	 * @param admin
	 * @return
	 */
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
	 * Insert single Meal into Meal table with patient userID
	 * 
	 * @param meal
	 *            object patient object
	 * 
	 */
	public boolean insertMeal(Meal meal, Patient p) {
		success = false;
		try {
			if (connect()) {
				ps = connection.prepareStatement(
						"INSERT INTO meal (name, calories, raing, notes, patient_id) VALUES(?, ?, ?, ?, ?)");

				ps.setString(1, meal.getFood());
				ps.setInt(2, meal.getCalories());
				ps.setInt(3, meal.getRating());
				ps.setString(4, meal.getNotes());
				ps.setInt(5, p.getPatientID());
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
	 * Insert multiple meals
	 * 
	 * @param patients
	 * @return
	 */
	public boolean insertMeals(LinkedList<Meal> meals, Patient p) {
		success = true;
		for (Meal m : meals) {
			if (!insertMeal(m, p))
				success = false;
		}
		return success;
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

	/**
	 * Update single user located based on user_id in tables
	 * 
	 * @param userID
	 * @param firstName
	 * @param lastName
	 * @param contactInfo
	 * @return
	 */
	public boolean updateUser(String userID, String firstName, String lastName, Contact contactInfo) {
		success = false;
		try {
			if (connect()) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(contactInfo);
				byte[] byteArray = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);

				ps = connection.prepareStatement(
						"UPDATE user_account SET firstname = ?, " + "lastname = ?, contact_info = ? WHERE user_id = ?");

				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setBinaryStream(3, bais, byteArray.length);
				ps.setString(4, userID);

				ps.executeUpdate();
				ps.close();
				success = true;
			}
		} catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
		return success;
	}

	/**
	 * Updates sinlge patient found in Patient table based on patient ID
	 * 
	 * @param patient
	 * @return
	 */
	public boolean updatePatient(Patient patient) {
		success = false;
		if (updateUser(patient.getUserID(), patient.getFirstName(), patient.getLastName(), patient.getContactInfo())) {
			success = true;
		}
		return success;
	}

	/**
	 * Update single MedicalStaff in user_account table and MedicalStaff table
	 * 
	 * @param staff
	 * @return
	 */
	public boolean updateMedicalStaff(MedicalStaff staff) {
		success = false;
		try {
			updateUser(staff.getUserID(), staff.getFirstName(), staff.getLastName(), staff.getContactInfo());
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
	 * Update single Pet int pet table, based on patient id
	 * 
	 * @param pet
	 *            object
	 * @param patient
	 *            object
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

	public boolean updatePets(LinkedList<Pet> pets, Patient p) {
		return true;
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
			ps = connection.prepareStatement(
					"UPDATE meal SET name = ?, calories = ?, rating = ?, notes = ? " + "WHERE patient_id = ?");
			ps.setString(1, meal.getFood());
			ps.setInt(2, meal.getCalories());
			ps.setInt(3, meal.getRating());
			ps.setString(4, meal.getNotes());
			ps.setInt(5, p.getPatientID());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateMeals(LinkedList<Meal> meals, Patient p) {

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
					"UPDATE caregiver SET name = ?, inFamily = ?, relation = ? " + "WHERE caregiver_id = ?");
			ps.setString(1, caregiver.getName());
			ps.setBoolean(2, caregiver.getInFamily());
			ps.setString(3, caregiver.getRelation());
			ps.setInt(4, caregiver.getCaregiverID());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateCaregivers(LinkedList<Caregiver> caregivers, Patient p) {

	}

	/**
	 * Update single HealthInfo Object in
	 * 
	 * @param info
	 * @param p
	 */

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

	public String generateUserID(String firstname, String lastname, String type) {
		int position = getUserIDHelper(firstname, lastname);
		int code = Math.abs((firstname.hashCode() + position) + (lastname.hashCode() + position));
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

	public String getLoginPass(String username) {
		try {
			connect();
			ps = connection.prepareStatement("Select * FROM login WHERE username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString("password"));
				return rs.getString("password");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void updateDummyLogin(String username, String pass) {
		try {
			connect();
			ps = connection.prepareStatement("UPDATE login SET password = ? WHERE username = ?");
			ps.setString(1, pass);
			ps.setString(2, username);
			ps.executeUpdate();
			System.out.println("finish update");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return;
	}

	/**
	 * converts local date to date
	 * 
	 * @param localDate
	 * @return converted date
	 */
	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * converts date to local date
	 * 
	 * @param date
	 * @return converted local date
	 */
	public static LocalDate asLocalDate(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}

}
