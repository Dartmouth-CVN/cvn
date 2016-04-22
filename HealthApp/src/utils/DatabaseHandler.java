package utils;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.ContactElement;
import model.Patient;

public class DatabaseHandler {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static Session session;
	
	public DatabaseHandler(){
	}

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void startSession() {
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
	}

	public void populateDatabase(int number) {
		for(int i = 0 ; i < number; i++){
			Patient p = RandomGenerator.getRandomPatient();
			insertPatient(p);
		}
	}

	public boolean insertPatient(Patient p) {
		startSession();
		
		for(ContactElement e : p.getContactInfo().getPhoneNumbers()){
			System.out.println("Phone number: " + e.getValue());
			System.out.println("before: " + e.getElementId());
			session.save(e);
			System.out.println("after: " + e.getElementId());
		}

		for(ContactElement e : p.getContactInfo().getEmails())
			session.save(e);

		for(ContactElement e : p.getContactInfo().getAddresses())
			session.save(e);
		
		session.save(p);

		session.getTransaction().commit();
		return true;
	}

	public Patient getPatient(String username) throws ObjectNotFoundException {
		startSession();
		Query q = session.createQuery("FROM Patient patient WHERE patient.username = :username");
		q.setParameter("username", username);
		Patient p = (Patient) q.uniqueResult();

		if (p == null)
			throw new ObjectNotFoundException("Patient");
		else
			return p;
	}
	
	public Patient getPatient(int userId) throws ObjectNotFoundException {
		startSession();
		
		Patient p = (Patient) session.load(Patient.class, userId);

		if (p == null)
			throw new ObjectNotFoundException("Patient with userId " + userId);
		else
			return p;
	}

	public List<Patient> getPatients() throws ObjectNotFoundException {
		startSession();
		@SuppressWarnings("unchecked")
		List<Patient> list = session.createCriteria(Patient.class).list();

		if (list.isEmpty())
			throw new ObjectNotFoundException("Patients");
		else {
			return list;
		}
	}

	public List<Patient> getPatientList() {
		//
		return null;
	}
}