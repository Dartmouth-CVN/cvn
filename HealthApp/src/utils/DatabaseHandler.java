package utils;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Contact;
import model.MainApp;
import model.Patient;

public class DatabaseHandler {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static Session session;
	
	public DatabaseHandler(){
		populateDatabase(5);
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
			Patient p = getRandomPatient();
			insertPatient(p);
		}
	}
	
	public Patient getRandomPatient(){
		String firstname = RandomGenerator.getRandomFirstName();
		String lastname = RandomGenerator.getRandomLastName();
		String username = RandomGenerator.createUsername(firstname, lastname);
		Date birthday = RandomGenerator.getRandomBirthday();
		String room = RandomGenerator.getRandomRoom();
		Contact contactInfo = RandomGenerator.getRandomContactInfo();
		return new Patient(1, firstname, lastname, username, lastname,
				birthday, room, contactInfo);
	}

	public boolean insertPatient(Patient p) {
		startSession();
		session.save(p);

		session.getTransaction().commit();
		return true;
//		try {
//			return true;
//		} catch (HibernateException e) {
//			MainApp.printError(e);
//			return false;
//		}
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