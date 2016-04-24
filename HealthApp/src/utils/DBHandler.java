package utils;

import model.MainApp;
import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;

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

    private DatabaseHandler() {
        connect();
    }

    public void initDB() {
        if (connect()) {
            createTables();
//            populateDatabase();
        } else {
            System.out.println("Couldn't connect");
        }
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
                    " contact_info BLOB (10M), Primary Key(user_id) )");
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
                    + "user_id VARCHAR(50) PRIMARY KEY(user_id), pets BLOB (10M), meals BLOB (10M)," +
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
                    + "user_id VARCHAR(50) PRIMARY KEY(user_id) )");
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
            ps = connection.prepareStatement("CREATE TABLE medical_staff(" +
                    "user_id VARCHAR(50) PRIMARY KEY(user_id), staff_type VARCHAR(20)," +
                    " assignedPatients BLOB (10M))");
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
            // e.printStackTrace();
        }
        return success;
    }

    public boolean createMealTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE meal("
                    + "meal_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "name VARCHAR(20), calories INT, rating INT, notes LONG VARCHAR,"
                    + " user_id VARCHAR(50), FOREIGN KEY(user_id) REFERENCES user_account(user_id))");
            ps.execute();
            success = true;
        } catch (SQLException e) {
            MainApp.printError(e);
            // e.printStackTrace();
        }
        return success;
    }

    public boolean createCaregiverTable() {
        success = false;
        try {
            ps = connection.prepareStatement("CREATE TABLE caregiver("
                    + "caregiver_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "user_id VARCHAR(50), name VARCHAR(20), birthday DATE, inFamily BOOLEAN, relation VARCHAR(10), contact_info BLOB (10M), "
                    + "FOREIGN KEY(user_id) REFERENCES user_account(user_id), Primary Key(caregiver_id) )");
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
                    + "FOREIGN KEY(user_id) REFERENCES user_account(user_id),Primary Key(health_id) )");
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
                    + "user_id VARCHAR(50), med_id int, FOREIGN KEY(user_id) REFERENCES user_account (user_id),"
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

            rs = metaData.getTables(null, "APP", "PET", null);
            if (!rs.next())
                createPetTable();

            rs = metaData.getTables(null, "APP", "MEAL", null);
            if (!rs.next())
                createMealTable();

            rs = metaData.getTables(null, "APP", "CAREGIVER", null);
            if (!rs.next())
                createCaregiverTable();

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


}
