package controller;

import java.util.LinkedList;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.collections.ObservableList;
import model.Caregiver;
import model.MedicalStaff;
import model.Patient;

public class DatabaseHandler {
	private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

	public LinkedList<Patient> getPatientList() {
		// TODO Return patient list from new database
		return null;
	}

	public static DatabaseHandler getUniqueInstance() {
		// TODO Determine how to handle calls for the unique instance of the database
		return null;
	}

	public Caregiver getCaregiver(int caregiverID) {
		// TODO Return caregiver
		return null;
	}

	public ObservableList<Caregiver> searchPatientCaregiver(Patient patient) {
		// TODO Search caregivers
		return null;
	}

	public ObservableList<MedicalStaff> searchPatientAssignedStaff(Patient patient) {
		// TODO Search assigned staff
		return null;
	}

}
