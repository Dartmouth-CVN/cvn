package controller;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import model.Patient;
import utils.DatabaseHandler;

public class DBTest {
	Patient patient = new Patient("Ebenezer", "Ampiah", "ekampiah", "admin", "01/07/1993");

	@Test
	public void testInsert() {
		Session session = DatabaseHandler.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.save(patient);
        session.getTransaction().commit();
	}
	
	@Test
	public void testSelect(){
		Session session = DatabaseHandler.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        List patients = session.createQuery("from Patient").list();
        Patient loadPatient = session.load(Patient.class, 201);
        assertTrue(patients.size() > 0);
        assertEquals(loadPatient.getFirstName(), "Ebenezer");
	}

}
