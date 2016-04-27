package utils;

import model.*;
import org.apache.derby.impl.tools.sysinfo.Main;
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
                    + "health_id BIGINT NOT NULL,"
                    + "user_id VARCHAR(50), name VARCHAR(20), value VARCHAR(10),"
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

    public boolean insertAdmin(Administrator admin) {
        return connect() && insertAbsUser(admin);
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

    public Patient getPatientByUsername(String username) {
        try {
            if (connect()) {
                ps = connection.prepareStatement(" SELECT user_id, firstname, lastname, username, password, " +
                        "birthday, room, picture FROM  user_account WHERE username = ?");
                ps.setString(1, username);
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
                        "birthday, room, picture FROM  user_account WHERE user_id = ?");
                ps.setString(1, userId);
                return getPatient(ps.executeQuery());
            }
            return getPatient(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

                ps = connection.prepareStatement(" SELECT * FROM user_id WHERE user_type = ?");
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

    public AbsUser getAbsUser(String username) {
        if (connect()) {
            try {
                ps = connection.prepareStatement("SELECT * FROM user_account WHERE username = ? ");
                ps.setString(1, username);
                rs = ps.executeQuery();

                if (rs.next()) {
                    String userType = rs.getString("user_type");
                    if (userType.equals(Patient.getUserType()))
                        return getPatientByUsername(username);
                    else if (userType.equals(Administrator.getUserType())) ;
                    return getAdministrator(username);
                }
            } catch (SQLException e) {
//                e.printStackTrace();
                MainApp.printError(e);
            }
        }
        return null;
    }

    public Administrator getAdministrator(String username) {
        try {
            if (connect()) {

                ps = connection.prepareStatement(" SELECT * FROM admin NATURAL JOIN user_account WHERE username = ?");
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Contact contact = (Contact) deserialize(rs.getBytes("contact_info"));

                    Administrator admin = new Administrator(rs.getString("user_id"), rs.getString("firstname"),
                            rs.getString("lastname"), rs.getString("username"), rs.getString("password"),
                            timestampToLocalDate(rs.getTimestamp("birthday")), rs.getString("room"),
                            rs.getString("picture"), contact);
                    connection.close();
                    return admin;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            MainApp.printError(e);
        }
        return new Administrator();
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
