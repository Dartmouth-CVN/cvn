package utils;

import java.util.LinkedList;
import java.util.List;

import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
//			p = fillPatient(p);
//			updatePatient(p);
		}
	}

	public Patient fillPatient(Patient patient){
		int rand = RandomGenerator.getRandomNumber().nextInt(10);
		List<Pet> pets = new LinkedList<Pet>();
		for(int i = 0; i < rand; i++)
			pets.add(RandomGenerator.getRandomPet());


		List<Meal> meals = new LinkedList<Meal>();
		rand = RandomGenerator.getRandomNumber().nextInt(10);
		for(int i = 0; i < rand; i++)
			meals.add(RandomGenerator.getRandomMeal());

		List<AbsRelation> caregivers = new LinkedList<AbsRelation>();
		rand = RandomGenerator.getRandomNumber().nextInt(10);
		for(int i = 0; i < rand; i++)
			caregivers.add(RandomGenerator.getRandomCaregiver());

		List<MedicalStaff> medStaff = new LinkedList<MedicalStaff>();
		rand = RandomGenerator.getRandomNumber().nextInt(10);
		for(int i = 0; i < rand; i++)
			medStaff.add(RandomGenerator.getRandomMedicalStaff());

		System.out.println("Pets size: " + pets.size());
		patient.setPets(pets);
		patient.setMeals(meals);
		patient.setRelations(caregivers);
		patient.setAssignedStaff(medStaff);

		return patient;
	}

	public boolean insertPatient(Patient p) {
		startSession();
		session.save(p);
//		saveContact(p.getContactInfo());
//		savePets(p.getPets());
//		saveMeals(p.getMeals());
//		saveAssignedStaff(p.getAssignedStaff());
		session.getTransaction().commit();
		return true;
	}

	public void saveContact(Contact c){

	}

	public boolean updatePatient(Patient p){
		startSession();
		Patient update = (Patient) session.merge(p);
		session.saveOrUpdate(p);
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
return new LinkedList<Patient>();
	}

	public List<Patient> getPatientList() {
		//
		return null;
	}
}