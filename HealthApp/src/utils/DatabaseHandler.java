package utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.collections.ObservableList;
import model.Caregiver;
import model.IDisplayable;
import model.Meal;
import model.MedicalStaff;
import model.Patient;
import model.Pet;

public class DatabaseHandler {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static Session session;

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
    
    public void startSession(){
    	session = sessionFactory.getCurrentSession();
    	session.beginTransaction();
    }
    
    public Patient getPatient(String username) throws ObjectNotFoundException{
    	startSession();
    	Query q = session.createQuery("FROM PATIENT patient WHERE patient.username = :username");
    	q.setString("username", username);
    	@SuppressWarnings("unchecked")
		List<Patient> list = q.list();
    	
    	if(list.isEmpty())
    		throw new ObjectNotFoundException("Patient");
    	else
    		return list.get(0);
    }

    public Set<Patient> getPatients() throws ObjectNotFoundException{
    	startSession();
    	Query q = session.createQuery("FROM PATIENT patient WHERE patient.username = :username");
    	@SuppressWarnings("unchecked")
		List<Patient> list = q.list();
    	Set<Patient> patientSet = new HashSet<Patient>();
    	
    	if(list.isEmpty())
    		throw new ObjectNotFoundException("Patients");
    	else{
    		Iterator<Patient> i = list.iterator();
    		while(i.hasNext()){
    			patientSet.add(i.next());
    		}
    	}
    	return patientSet;
    }
    
	public LinkedList<Patient> getPatientList() {
		// 
		return null;
	}

	public static DatabaseHandler getUniqueInstance() {
		// TODO getUniqueInstance
		return null;
	}

	public Caregiver getCaregiver(int caregiverID) {
		// TODO getCaregiver
		return null;
	}

	public ObservableList<Caregiver> searchPatientCaregiver(Patient patient) {
		// TODO searchPatientCaregiver
		return null;
	}

	public ObservableList<MedicalStaff> searchPatientAssignedStaff(Patient patient) {
		// TODO searchPatientAssignedStaff
		return null;
	}

	public ObservableList<Meal> searchPatientMeal(Patient patient) {
		// TODO searchPatientMeal
		return null;
	}

	public ObservableList<Pet> searchPatientPet(Patient patient) {
		// TODO searchPatientPet
		return null;
	}

	public MedicalStaff getMedicalStaff(String userID) {
		// TODO getMedicalStaff
		return null;
	}

	public ObservableList<IDisplayable> searchPatient(String name) {
		// TODO searchPatient
		return null;
	}

	public ObservableList<IDisplayable> searchPatient() {
		// TODO searchPatient (no params, so return all patients)
		return null;
	}
}
