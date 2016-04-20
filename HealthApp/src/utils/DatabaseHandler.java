package utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Patient;

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
    	List<Patient> list = q.list();
    	
    	if(list.isEmpty())
    		throw new ObjectNotFoundException("Patient");
    	else
    		return list.get(0);
    }

    public Set<Patient> getPatients() throws ObjectNotFoundException{
    	startSession();
    	Query q = session.createQuery("FROM PATIENT patient WHERE patient.username = :username");
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
}
