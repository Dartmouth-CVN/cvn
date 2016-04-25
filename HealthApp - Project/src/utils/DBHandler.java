package utils;

import model.*;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mrampiah on 4/24/16.
 */
public class DBHandler {

    private static PreparedStatement ps;
    private static ResultSet rs;
    private static Connection connection;
    private static DatabaseMetaData metaData;
    private boolean success;// monitors if sql interactions result in errors
    private static DBHandler uniqueInstance;

    private DBHandler() {
        connect();
    }

    public void initDB() {
        if (connect()) {
            createTables();
            populateDatabase(100);
        } else {
            System.out.println("Couldn't connect");
        }
    }

    public void populateDatabase(int number){
        for(int i = 0 ; i < number; i++) {
            Patient p = RandomGenerator.getRandomPatient();
            p = RandomGenerator.fillPatient(p);
            insertPatient(p);
        }

        Administrator admin = RandomGenerator.getRandomAdmin();
        System.out.printf("admin username:%s, password:%s", admin.getUsername(), admin.getPassword());
        insertAdmin(admin);
    }

    /**
     * Get the unique database instance
     *
     * @return DatabaseHandler object
     */
    public static DBHandler getUniqueInstance() {
        if (connection != null)
            return uniqueInstance;
        else {
            uniqueInstance = new DBHandler();
            return uniqueInstance;
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
                    + "user_id VARCHAR(50), firstname VARCHAR(20), lastname VARCHAR(20)," +
                    "username VARCHAR(20), password VARCHAR(50), birthday TIMESTAMP, room VARCHAR(10)," +
                    " picture VARCHAR(200), contact_info BLOB (10M), Primary Key(user_id) )");
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
                    + "user_id VARCHAR(50), PRIMARY KEY(user_id), pets BLOB (10M), meals BLOB (10M)," +
                    "relations BLOB (10M), assigned_staff BLOB (10M) )");
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
                    + "user_id VARCHAR(50), PRIMARY KEY(user_id) )");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//            e.printStackTrace();
        }
        return success;
    }

    public boolean createMedicalStaffTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE medical_staff(" +
                    "user_id VARCHAR(50), PRIMARY KEY(user_id), staff_type VARCHAR(20)," +
                    " assignedPatients BLOB (10M))");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//            e.printStackTrace();
        }
        return success;
    }

    public boolean createRelationTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE relation("
                    + "relation_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "user_id VARCHAR(50), relation VARCHAR(20), discriminator VARCHAR(20), isFamily BOOLEAN," +
                    "isCaregiver BOOLEAN, FOREIGN KEY(user_id) REFERENCES user_account(user_id), Primary Key(relation_id) )");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//            e.printStackTrace();
        }
        return success;
    }

    public boolean createPetTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE pet("
                    + "pet_id BIGINT NOT NULL, name VARCHAR(10), species VARCHAR(20), " +
                    "allergy_friendly BOOLEAN, user_id VARCHAR(50),"
                    + "FOREIGN KEY(user_id) REFERENCES user_account(user_id))");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//             e.printStackTrace();
        }
        return success;
    }

    public boolean createMealTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE meal("
                    + "meal_id BIGINT NOT NULL, name VARCHAR(20), calories INT, rating INT, notes VARCHAR(200),"
                    + " user_id VARCHAR(50), FOREIGN KEY(user_id) REFERENCES user_account(user_id))");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//             e.printStackTrace();
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
//            e.printStackTrace();
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
//            e.printStackTrace();
        }
        return success;
    }

    public boolean createHealthProfileTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE health_profile("
                    + "health_id BIGINT NOT NULL ,"
                    + "user_id VARCHAR(50), allergies BLOB (10M), dietary_restrictions BLOB (10M),"
                    + "FOREIGN KEY(user_id) REFERENCES user_account(user_id),Primary Key(health_id) )");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
            e.printStackTrace();
        }
        return success;
    }

    public boolean createStaffAssignmentTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE staff_assignment("
                    + "treats_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "user_id VARCHAR(50), med_id VARCHAR(50), FOREIGN KEY(user_id) REFERENCES patient (user_id),"
                    + "FOREIGN KEY(med_id) REFERENCES medical_staff(user_id))");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
            e.printStackTrace();
        }
        return success;
    }

    public boolean createMedicationTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE medication( "
                    + "medication_id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "patient_id VARCHAR(50), med_id int VARCHAR(50), name VARCHAR(20), dosage VARCHAR(20), "
                    + "directions VARCHAR(100), refills int, nextRefillDate date, "
                    + "FOREIGN KEY(patient_id) REFERENCES patient(user_id), "
                    + "FOREIGN KEY(med_id) REFERENCES medical_staff(user_id))");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
            e.printStackTrace();
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

            rs = metaData.getTables(null, "APP", "PATIENT", null);
            if (!rs.next())
                createPatientTable();

            rs = metaData.getTables(null, "APP", "SCHEDULE", null);
            if (!rs.next())
                createScheduleTable();

            rs = metaData.getTables(null, "APP", "PET", null);
            if (!rs.next())
                createPetTable();

            rs = metaData.getTables(null, "APP", "MEAL", null);
            if (!rs.next())
                createMealTable();

            rs = metaData.getTables(null, "APP", "RELATION", null);
            if (!rs.next())
                createRelationTable();

            rs = metaData.getTables(null, "APP", "EVENT", null);
            if (!rs.next())
                createEventTable();

            rs = metaData.getTables(null, "APP", "ADMIN", null);
            if (!rs.next())
                createAdminTable();

            rs = metaData.getTables(null, "APP", "MEDICAL_STAFF", null);
            if (!rs.next())
                createMedicalStaffTable();

            rs = metaData.getTables(null, "APP", "HEALTH_PROFILE", null);
            if (!rs.next())
                createHealthProfileTable();

            rs = metaData.getTables(null, "APP", "STAFF_ASSIGNMENT", null);
            if (!rs.next())
                createStaffAssignmentTable();

//            rs = metaData.getTables(null, "APP", "MEDICATION", null);
//            if (!rs.next())
//                createMedicationTable();

            if (ps != null) {
                ps.close();
                System.out.println("Tables created");
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static byte[] serializeImage(String image){
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            ObjectOutputStream os = new ObjectOutputStream(out);
//            os.writeObject();
//            image.writeObject(os);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public static Object deserializeImage(byte[] data) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        ObjectInputStream is = new ObjectInputStream(in);
//        String image = new String();
//        image.readObject(is);
//        return image;
        return null;
    }

    public boolean insertPatient(Patient p) {
        success = false;
        try {
            if (insertAbsUser(p.getUserId(), p.getFirstName(), p.getLastName(), p.getUsername(), p.getPassword(),
                    p.getRoom(), p.getPicture(), p.getBirthday(), p.getContactInfo()) && connect()) {
                byte[] petBytes = serialize(p.getPets());
                byte[] mealBytes = serialize(p.getMeals());
                byte[] relations = serialize(p.getRelations());
                byte[] staffBytes = serialize(p.getAssignedStaff());
                ps = connection.prepareStatement("INSERT INTO patient (user_id, pets, meals, relations," +
                        " assigned_staff) VALUES (?, ?, ?, ?, ?)");
                ps.setString(1, p.getUserId());
                ps.setBinaryStream(2, new ByteArrayInputStream(petBytes), petBytes.length);
                ps.setBinaryStream(3, new ByteArrayInputStream(mealBytes), mealBytes.length);
                ps.setBinaryStream(4, new ByteArrayInputStream(relations), relations.length);
                ps.setBinaryStream(5, new ByteArrayInputStream(staffBytes), staffBytes.length);
                ps.executeUpdate();
                ps.close();
                success = true;
            }
        } catch (SQLException | IOException e) {
            MainApp.printError(e);
        }
        return success;
    }

    public boolean insertAdmin(Administrator admin) {
        success = false;
        try {
            if (insertAbsUser(admin.getUserId(), admin.getFirstName(), admin.getLastName(), admin.getUsername(), admin.getPassword(),
                    admin.getRoom(), admin.getPicture(), admin.getBirthday(), admin.getContactInfo()) && connect()) {
                ps = connection.prepareStatement("INSERT INTO admin (user_id) VALUES (?)");
                ps.setString(1, admin.getUserId());
                ps.executeUpdate();
                ps.close();
                success = true;
            }
        } catch (SQLException e) {
//            MainApp.printError(e);
            e.printStackTrace();
        }
        return success;
    }

    public boolean insertAbsUser(String userId, String firstName, String lastName, String username, String password,
                                 String room, String picture, Date birthday, Contact contactInfo) {
        success = false;
        try {
            if (connect()) {
                byte[] objectBytes = serialize(contactInfo);
                ps = connection
                        .prepareStatement("INSERT INTO user_account (user_id, firstname, lastname," +
                                "username, password, room, picture, birthday, contact_info) "
                                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, userId);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, username);
                ps.setString(5, password);
                ps.setString(6, room);
                ps.setString(7, picture);
                ps.setTimestamp(8, new Timestamp(birthday.getTime()));
                ps.setBinaryStream(9, new ByteArrayInputStream(objectBytes), objectBytes.length);
                ps.executeUpdate();
                success = true;
            }
        } catch (SQLException | IOException e) {
            MainApp.printError(e);
        }
        return success;
    }

    public Patient getPatientByUsername(java.lang.String username){
        try {
            if (connect()) {

                ps = connection.prepareStatement(" SELECT * FROM patient NATURAL JOIN user_account WHERE username = ?");
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Contact contact = (Contact) deserialize(rs.getBytes("contact_info"));
                    List<Pet> pets = (List<Pet>) deserialize(rs.getBytes("pets"));
                    List<Meal> meals = (List<Meal>) deserialize(rs.getBytes("meals"));
                    List<AbsRelation> relations = (List<AbsRelation>) deserialize(rs.getBytes("relations"));
                    List<MedicalStaff> assignedStaff = (List<MedicalStaff>) deserialize(rs.getBytes("assigned_staff"));

                    Patient patient = new Patient(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            rs.getTimestamp("birthday"), rs.getString("room"), rs.getString("picture"), contact, pets, meals, relations,
                            assignedStaff, new HealthProfile());
                    connection.close();
                    return patient;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            MainApp.printError(e);
        }
        return null;
    }

    public Patient getPatientById(java.lang.String userId){
        try {
            if (connect()) {

                ps = connection.prepareStatement(" SELECT * FROM patient NATURAL JOIN user_account WHERE user_id = ?");
                ps.setString(1, userId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Contact contact = (Contact) deserialize(rs.getBytes("contact_info"));
                    List<Pet> pets = (List<Pet>) deserialize(rs.getBytes("pets"));
                    List<Meal> meals = (List<Meal>) deserialize(rs.getBytes("meals"));
                    List<AbsRelation> relations = (List<AbsRelation>) deserialize(rs.getBytes("relations"));
                    List<MedicalStaff> assignedStaff = (List<MedicalStaff>) deserialize(rs.getBytes("assigned_staff"));

                    Patient patient = new Patient(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            rs.getTimestamp("birthday"), rs.getString("room"), rs.getString("picture"), contact, pets, meals, relations,
                            assignedStaff, new HealthProfile());
                    connection.close();
                    return patient;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            MainApp.printError(e);
        }
        return new Patient();
    }

    public List<Patient> getAllPatients(){
        LinkedList<Patient> patientList = new LinkedList<Patient>();
        try {
            if (connect()) {

                ps = connection.prepareStatement(" SELECT * FROM patient NATURAL JOIN user_account");
                rs = ps.executeQuery();
                while (rs.next()) {
                    Contact contact = (Contact) deserialize(rs.getBytes("contact_info"));
                    List<Pet> pets = (List<Pet>) deserialize(rs.getBytes("pets"));
                    List<Meal> meals = (List<Meal>) deserialize(rs.getBytes("meals"));
                    List<AbsRelation> relations = (List<AbsRelation>) deserialize(rs.getBytes("relations"));
                    List<MedicalStaff> assignedStaff = (List<MedicalStaff>) deserialize(rs.getBytes("assigned_staff"));

                    Patient patient = new Patient(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            rs.getTimestamp("birthday"), rs.getString("room"), rs.getString("picture"), contact, pets, meals, relations,
                            assignedStaff, new HealthProfile());
                    patientList.add(patient);
                }
                connection.close();
            }
        } catch (SQLException | IOException | ClassNotFoundException | NullPointerException e) {
            MainApp.printError(e);
        }
        return patientList;

    }

    public AbsUser getAbsUser(java.lang.String username){
        if(connect()){
            try {
                ps = connection.prepareStatement("SELECT * FROM user_account WHERE username = ? ");
                ps.setString(1, username);
                rs = ps.executeQuery();

                if(rs.next()){
                    AbsUser user = getUserType(rs.getString("user_id"));
                    if(user instanceof Patient)
                        return getPatientByUsername(username);
                    else if(user instanceof Administrator)
                        return getAdministrator(username);
                }
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return new Patient();
    }

    public Administrator getAdministrator(java.lang.String username){
        try {
            if (connect()) {

                ps = connection.prepareStatement(" SELECT * FROM admin NATURAL JOIN user_account WHERE username = ?");
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Contact contact = (Contact) deserialize(rs.getBytes("contact_info"));

                    Administrator admin = new Administrator(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            rs.getTimestamp("birthday"), rs.getString("room"), rs.getString("picture"), contact);
                    connection.close();
                    return admin;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            MainApp.printError(e);
        }
        return new Administrator();
    }

    public boolean updateAbsUser(String userId, String firstName, String lastName, String username, String password,
                                 String room, String picture, Date birthday, Contact contactInfo){
        success = false;
        try {
            if (connect()) {
                byte[] objectBytes = serialize(contactInfo);
                ps = connection
                        .prepareStatement("UPDATE user_account SET firstname = ?, lastname = ?," +
                                "username =?, password = ?, room = ?, picture = ?, birthday = ?, contact_info = ? " +
                                "WHERE user_id = ?");
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, username);
                ps.setString(4, password);
                ps.setString(5, room);
                ps.setString(6, picture);
                ps.setTimestamp(7, new Timestamp(birthday.getTime()));
                ps.setBinaryStream(8, new ByteArrayInputStream(objectBytes), objectBytes.length);
                ps.setString(9, userId);
                ps.executeUpdate();
                success = true;
            }
        } catch (SQLException | IOException e) {
            MainApp.printError(e);
        }
        return success;
    }

    public boolean updatePatient(Patient p){
        success = false;
        try {
            if (updateAbsUser(p.getUserId(), p.getFirstName(), p.getLastName(), p.getUsername(), p.getPassword(),
                    p.getRoom(), p.getPicture(), p.getBirthday(), p.getContactInfo()) && connect()) {
                byte[] petBytes = serialize(p.getPets());
                byte[] mealBytes = serialize(p.getMeals());
                byte[] relations = serialize(p.getRelations());
                byte[] staffBytes = serialize(p.getAssignedStaff());
                ps = connection.prepareStatement("UPDATE  patient SET pets = ?, meals = ?, relations = ?," +
                        " assigned_staff = ? WHERE user_id = ?");
                ps.setBinaryStream(1, new ByteArrayInputStream(petBytes), petBytes.length);
                ps.setBinaryStream(2, new ByteArrayInputStream(mealBytes), mealBytes.length);
                ps.setBinaryStream(3, new ByteArrayInputStream(relations), relations.length);
                ps.setBinaryStream(4, new ByteArrayInputStream(staffBytes), staffBytes.length);
                ps.setString(5, p.getUserId());
                ps.executeUpdate();
                ps.close();
                success = true;
            }
        } catch (SQLException | IOException e) {
            MainApp.printError(e);
        }
        return success;
    }

    public AbsUser getUserType(String userid){
        String type = userid.substring(0, 2);
        switch(type){
            case "RE": return new Caregiver();
            case "AD": return new Administrator();
            case "MS": return new MedicalStaff();
            default: return new Patient();
        }
    }
}
