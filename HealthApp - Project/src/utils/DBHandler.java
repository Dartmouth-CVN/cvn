package utils;

import model.*;
import org.apache.derby.jdbc.EmbeddedDataSource;
import view.MainApp;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
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
//    public static final DateTimeZone jodaTzUTC = DateTimeZone.forID("UTC");

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

    public void populateDatabase(int number) {
        for (int i = 0; i < number; i++) {
            Patient p = RandomGenerator.getRandomPatient();
            p = RandomGenerator.fillPatient(p);
            insertPatient(p);
        }

        Administrator admin = RandomGenerator.getRandomAdmin();
        System.out.printf("admin username:%s, password:%s\n", admin.getUsername(), admin.getPassword());
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

    public boolean createUserTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE user("
                    + "user_id VARCHAR(50), firstname VARCHAR(20), lastname VARCHAR(20)," +
                    "username VARCHAR(20), password VARCHAR(50), birthday TIMESTAMP, room VARCHAR(10)," +
                    " picture VARCHAR(200), user_type VARCHAR(10), role VARCHAR(10)," +
                    "isFamily BOOLEAN, isCaregiver BOOLEAN, relationship VARCHAR(20), PRIMARY KEY(user_id) )");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
        }
        return success;
    }

    public boolean createContactTable() {
        success = false;
        if (connect()) {
            try {
                ps = connection.prepareStatement("CREATE TABLE contact( contact_id BIGINT NOT NULL, " +
                        "user_id VARCHAR(50), value VARCHAR(10), type VARCHAR(10), contact_type VARCHAR(10), " +
                        "FOREIGN KEY(user_id) REFERENCES user(user_id), PRIMARY KEY (contact_id) )");
                ps.execute();
                success = true;
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return success;
    }

    public boolean createPetTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE pet("
                    + "pet_id BIGINT NOT NULL, name VARCHAR(10), species VARCHAR(20), " +
                    "allergy_friendly BOOLEAN, user_id VARCHAR(50),"
                    + "FOREIGN KEY(user_id) REFERENCES user(user_id))");
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
                    + "meal_id BIGINT NOT NULL, name VARCHAR(20), calories INT, notes VARCHAR(200), PRIMARY KEY(meal_id) )");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//             e.printStackTrace();
        }
        return success;
    }

    public boolean createEatsTable() {
        success = false;
        if (connect()) {
            try {
                ps = connection.prepareStatement("CREATE TABLE eats(user_id VARCHAR(50), meal_id BIGINT, rating int," +
                        " FOREIGN KEY(user_id) REFERENCES user(user_id), " +
                        "FOREIGN KEY(meal_id) REFERENCES meal(meal_id))");
                ps.execute();
                success = true;
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return success;
    }

    public boolean createEventTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE event("
                    + "event_id BIGINT NOT NULL, name VARCHAR(20), date TIMESTAMP , category VARCHAR(20), "
                    + "notes LONGVARCHAR, PRIMARY KEY(event_id) )");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//            e.printStackTrace();
        }
        return success;
    }

    public boolean createHealthInfoTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE health_info("
                    + "health_id BIGINT NOT NULL, user_id VARCHAR(50), date TIMESTAMP, name VARCHAR(20), value VARCHAR(10),"
                    + "FOREIGN KEY(user_id) REFERENCES user(user_id), Primary Key(health_id) )");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//            e.printStackTrace();
        }
        return success;
    }

    public boolean createStaffAssignmentTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE staff_assignment("
                    + "patient_id VARCHAR(50), med_id VARCHAR(50), " +
                    "FOREIGN KEY(patient_id) REFERENCES user (user_id),"
                    + "FOREIGN KEY(med_id) REFERENCES user(user_id))");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//            e.printStackTrace();
        }
        return success;
    }

    public boolean createRelatedTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE related("
                    + "patient_id VARCHAR(50), relation_id VARCHAR(50), " +
                    "FOREIGN KEY(patient_id) REFERENCES user (user_id),"
                    + "FOREIGN KEY(relation_id) REFERENCES user(user_id))");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
//            e.printStackTrace();
        }
        return success;
    }

    public void createTables() {
        try {
            connect();
            // check if the tables exist in our schema, create if they dont
            rs = metaData.getTables(null, "APP", "USER", null);
            if (!rs.next())
                createUserTable();

            rs = metaData.getTables(null, "APP", "CONTACT", null);
            if (!rs.next())
                createContactTable();

            rs = metaData.getTables(null, "APP", "PET", null);
            if (!rs.next())
                createPetTable();

            rs = metaData.getTables(null, "APP", "MEAL", null);
            if (!rs.next())
                createMealTable();

            rs = metaData.getTables(null, "APP", "EATS", null);
            if (!rs.next())
                createEatsTable();

            rs = metaData.getTables(null, "APP", "EVENT", null);
            if (!rs.next())
                createEventTable();

            rs = metaData.getTables(null, "APP", "HEALTH_INFO", null);
            if (!rs.next())
                createHealthInfoTable();

            rs = metaData.getTables(null, "APP", "RELATED", null);
            if (!rs.next())
                createRelatedTable();

            rs = metaData.getTables(null, "APP", "STAFF_ASSIGNMENT", null);
            if (!rs.next())
                createStaffAssignmentTable();

            if (ps != null) {
                ps.close();
                System.out.println("Tables created");
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
    }

    public boolean insertAbsUser(AbsUser user) {
        success = false;
        try {
            if (connect()) {
                String query = "INSERT INTO user_account (user_id, firstname, lastname," +
                        "username, password, birthday, room, picture, user_type) "
                        + "VALUES(:userId, :firstname, :lastname, :username, :password, :birthday, :room, :picture, :userType)";

                NamedParameterStatement nps = setBasicParameters(new NamedParameterStatement(connection, query), user);
                nps.executeUpdate();
                nps.close();
                success = true;
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
        return success;
    }

    public boolean insertPatient(Patient p) {
        return connect() && insertAbsUser(p);
    }

    public boolean insertMedicalStaff(MedicalStaff med){
        return connect() && insertAbsUser(med);
    }

    public boolean insertAdmin(Administrator admin) {
        return connect() && insertAbsUser(admin);
    }

    public boolean insertRelation(AbsRelation rel){
        return connect() && insertAbsUser(rel);
    }

    public boolean insertContact(ContactElement c, String userId) {
        success = false;
        if (connect()) {
            try {
                String query = "INSERT INTO contact (contact_id, user_id, value, type, contact_type) "
                        + "VALUES(:contactId, :userId, :value :type, :contactType)";

                NamedParameterStatement nps = new NamedParameterStatement(connection, query);
                nps.setLong("contactId", c.getElementId());
                nps.setString("userId", userId);
                nps.setString("value", c.getValue());
                nps.setString("type", c.getType());
                nps.setString("contactType", c.getContactType());
                nps.executeUpdate();
                nps.close();
                success = true;
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return success;
    }

    public boolean insertPet(Pet pet, String userId) {
        success = false;
        if (connect()) {
            try {
                ps = connection.prepareStatement("INSERT INTO pet (pet_id, user_id, name, species, allergy_friendly) "
                        + "VALUES(?, ?, ?, ?, ?)");
                int i = 1;
                ps.setLong(i++, pet.getPetId());
                ps.setString(i++, userId);
                ps.setString(i++, pet.getName());
                ps.setString(i++, pet.getSpecies());
                ps.setBoolean(i++, pet.isAllergyFriendly());
                ps.executeUpdate();
                ps.close();
                success = true;
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return success;
    }

    public boolean insertMeal(Meal meal){
        success = false;
        if (connect()) {
            try {
                ps = connection.prepareStatement("INSERT INTO meal (meal_id, name, calories, notes) "
                        + "VALUES(?, ?, ?, ?)");
                int i = 1;
                ps.setLong(i++, meal.getMealId());
                ps.setString(i++, meal.getFood());
                ps.setInt(i++, meal.getCalories());
                ps.setString(i++, meal.getNotes());
                ps.executeUpdate();
                ps.close();
                success = true;
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return success;
    }

    public boolean insertEats(Meal meal, String userId){
        success = false;
        if (connect()) {
            try {
                ps = connection.prepareStatement("INSERT INTO eats (user_id, meal_id, rating) "
                        + "VALUES(?, ?, ?)");
                int i = 1;
                ps.setString(i++, userId);
                ps.setLong(i++, meal.getMealId());
                ps.setInt(i++, meal.getRating());
                ps.executeUpdate();
                ps.close();
                success = true;
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return success;
    }

    public boolean insertRelated(String patientId, String relationId){
        success = false;
        if (connect()) {
            try {
                ps = connection.prepareStatement("INSERT INTO related (patient_id, relation_id) "
                        + "VALUES(?, ?, ?)");
                int i = 1;
                ps.setString(i++, patientId);
                ps.setString(i++, relationId);
                ps.executeUpdate();
                ps.close();
                success = true;
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return success;
    }

    public boolean insertHealthInfo(HealthAttribute<?> healthInfo, String userId){
        success = false;
        if (connect()) {
            try {
                ps = connection.prepareStatement("INSERT INTO health_info (health_id, user_id, date, name, value) "
                        + "VALUES(?, ?, ?, ?, ?)");
                int i = 1;
                ps.setLong(i++, healthInfo.getHealthAttributeId());
                ps.setString(i++, userId);
                ps.setTimestamp(i++, localDateToTimestamp(healthInfo.getDate()));
                ps.setString(i++, healthInfo.getName());
                ps.setString(i++, healthInfo.getStringValue());
                ps.executeUpdate();
                ps.close();
                success = true;
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return success;
    }

    public AbsUser getAbsUser(String username) {
        if (connect()) {
            try {
                ps = connection.prepareStatement("SELECT * FROM user WHERE username = ? ");
                ps.setString(1, username);
                rs = ps.executeQuery();

                if (rs.next()) {
                    String userType = rs.getString("user_type");
                    if (userType.equals(Patient.getUserType()))
                        return getPatientByUsername(username);
                    else if (userType.equals(Administrator.getUserType())) ;
                    return getAdministratorByUsername(username);
                }
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return null;
    }

    public Administrator getAdministratorByUsername(String username) {
        try {
            if (connect()) {

                ps = connection.prepareStatement(" SELECT * FROM user WHERE username = ? AND user_type = ?");
                ps.setString(1, username);
                ps.setString(2, Administrator.getUserType());
                rs = ps.executeQuery();
                if (rs.next()) {
                    Administrator admin = new Administrator(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            timestampToLocalDate(rs.getTimestamp("birthday")), rs.getString("room"),
                            rs.getString("picture"));
                    connection.close();
                    return admin;
                }
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
        return new Administrator();
    }

    public Administrator getAdministratorById(String userId) {
        try {
            if (connect()) {

                ps = connection.prepareStatement(" SELECT * FROM user WHERE user_id = ? AND user_type = ?");
                ps.setString(1, userId);
                ps.setString(2, Administrator.getUserType());
                rs = ps.executeQuery();
                if (rs.next()) {
                    Administrator admin = new Administrator(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            timestampToLocalDate(rs.getTimestamp("birthday")), rs.getString("room"),
                            rs.getString("picture"));
                    connection.close();
                    return admin;
                }
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
        return new Administrator();
    }

    public Patient getPatientByUsername(String username) {
        try {
            if (connect()) {
                ps = connection.prepareStatement(" SELECT user_id, firstname, lastname, username, password, " +
                        "birthday, room, picture FROM  user WHERE username = ? AND user_type = ?");
                ps.setString(1, username);
                ps.setString(2, Patient.getUserType());
                return getPatient(ps.executeQuery());
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
        return null;
    }

    public Patient getPatientById(String userId) {
        try {
            if (connect()) {
                ps = connection.prepareStatement(" SELECT user_id, firstname, lastname, username, password, " +
                        "birthday, room, picture FROM  user WHERE user_id = ? AND user_type = ?");
                ps.setString(1, userId);
                ps.setString(2, Patient.getUserType());
                return getPatient(ps.executeQuery());
            }
            return getPatient(ps.executeQuery());
        } catch (SQLException e) {
//            e.printStackTrace();
            MainApp.printError(e);
        }
        return null;
    }

    public MedicalStaff getMedicalStaffByUsername(String username) {
        try {
            if (connect()) {
                ps = connection.prepareStatement(" SELECT user_id, firstname, lastname, username, password, " +
                        "birthday, room, picture FROM  user WHERE username = ? AND user_type = ?");
                ps.setString(1, username);
                ps.setString(2, MedicalStaff.getUserType());
                rs = ps.executeQuery();
                if(rs.next()) {
                    MedicalStaff medicalStaff = new MedicalStaff(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            timestampToLocalDate(rs.getTimestamp("birthday")), rs.getString("room"),
                            rs.getString("picture"));
                    return medicalStaff;
                }
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
        return null;
    }

    public MedicalStaff getMedicalStaffById(String userId) {
        try {
            if (connect()) {
                ps = connection.prepareStatement(" SELECT user_id, firstname, lastname, username, password, " +
                        "birthday, room, picture FROM  user WHERE user_id = ? AND user_type = ?");
                ps.setString(1, userId);
                ps.setString(2, MedicalStaff.getUserType());
                rs = ps.executeQuery();
                if(rs.next()) {
                    MedicalStaff medicalStaff = new MedicalStaff(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            timestampToLocalDate(rs.getTimestamp("birthday")), rs.getString("room"),
                            rs.getString("picture"));
                    return medicalStaff;
                }
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            MainApp.printError(e);
        }
        return null;
    }

    public AbsRelation getRelationByUsername(String username) {
        try {
            if (connect()) {
                ps = connection.prepareStatement(" SELECT user_id, firstname, lastname, username, password, " +
                        "birthday, room, picture FROM user WHERE username = ? AND user_type = ?");
                ps.setString(1, username);
                ps.setString(2, AbsRelation.getUserType());
                return getAbsRelation(ps.executeQuery());
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
        return null;
    }

    public AbsRelation getRelationById(String userId) {
        try {
            if (connect()) {
                ps = connection.prepareStatement(" SELECT user_id, firstname, lastname, username, password, " +
                        "birthday, room, picture FROM  user WHERE user_id = ?");
                ps.setString(1, userId);
                ps.setString(2, AbsRelation.getUserType());
                return getAbsRelation(ps.executeQuery());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AbsRelation getAbsRelation(ResultSet rs){
        try {
            if(rs.next()) {
                AbsRelation relation;
                if (rs.getString("role") == Caregiver.getRole()) {
                    relation = new Caregiver(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            timestampToLocalDate(rs.getTimestamp("birthday")), rs.getString("room"),
                            rs.getString("picture"), rs.getString("relationship"), rs.getBoolean("isFamily"));
                } else {
                    relation = new Family(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            timestampToLocalDate(rs.getTimestamp("birthday")), rs.getString("room"),
                            rs.getString("picture"), rs.getString("relationship"), rs.getBoolean("isCaregiver"));

                }
                connection.close();
                return relation;
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
    }

    public Patient getPatient(ResultSet rs) {
        try {
            if (rs.next()) {
                Patient patient = new Patient(rs.getString("user_id"), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                        timestampToLocalDate(rs.getTimestamp("birthday")), rs.getString("room"),
                        rs.getString("picture"));
                connection.close();
                return patient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        LinkedList<Patient> patientList = new LinkedList<Patient>();
        try {
            if (connect()) {
                ps = connection.prepareStatement(" SELECT * FROM user WHERE user_type = ?");
                ps.setString(1, Patient.getUserType());
                rs = ps.executeQuery();
                while (rs.next()) {
                    Patient patient = getPatient(rs);
                    patientList.add(patient);
                }
                connection.close();
            }
        } catch (SQLException | NullPointerException e) {
            MainApp.printError(e);
        }
        return patientList;
    }

    public List<ContactElement> getContactInfo(String userId){
        List<ContactElement> elements = new LinkedList<>();
        if(connect()){
            try {
                ps = connection.prepareStatement(" SELECT * FROM contact WHERE user_id = ?");
                ps.setString(1, userId);
                while(rs.next()){
                    ContactElement element;
                    String type = rs.getString("contact_type");
                    if(type.equals(Phone.getContactType()))
                        element = new Phone(rs.getLong("contact_id"), rs.getString("value"), rs.getString("type"));
                    else if(type.equals(Email.getContactType()))
                        element = new Email(rs.getLong("contact_id"), rs.getString("value"), rs.getString("type"));
                    else
                        element = new Address(rs.getLong("contact_id"), rs.getString("value"), rs.getString("type"));

                    elements.add(element);
                }
            } catch (SQLException e) {
                MainApp.printError(e);
            }
        }
        return elements;
    }

    public Meal getMeal(long mealId){
        
    }

    public boolean updatePatient(Patient p) {
        success = false;
        try {
            if (connect()) {
                String query = "UPDATE user_account SET user_id=:userId, firstname =:firstname, lastname =:lastname," +
                        "username =:username, password =:password, birthday =:birthday, room =:room, picture =:picture," +
                        "user_type =:userType) ";
                NamedParameterStatement nps = setBasicParameters(new NamedParameterStatement(connection, query), p);
                nps.executeUpdate();
                nps.close();
                success = true;
            }
        } catch (SQLException e) {
            MainApp.printError(e);
        }
        return success;
    }

    public NamedParameterStatement setBasicParameters(NamedParameterStatement nps, AbsUser user) {
        try {
            nps.setString("userId", user.getUserId());
            nps.setString("firstname", user.getFirstName());
            nps.setString("lastname", user.getLastName());
            nps.setString("username", user.getUsername());
            nps.setString("password", user.getPassword());
            nps.setString("room", user.getRoom());
            nps.setString("picture", user.getPicture());
            nps.setTimestamp("birthday", localDateToTimestamp(user.getBirthday()));
            nps.setString("userType", user.getUserType());
        } catch (SQLException e) {
//            e.printStackTrace();
            MainApp.printError(e);
        }

        return nps;
    }

    public static LocalDate timestampToLocalDate(Timestamp timestamp) {
        return timestamp.toLocalDateTime().toLocalDate();
    }

    public static Timestamp localDateToTimestamp(LocalDate localDate) {
        return Timestamp.valueOf(localDate.atStartOfDay());
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
}
