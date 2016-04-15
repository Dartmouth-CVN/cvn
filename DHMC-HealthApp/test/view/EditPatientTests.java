package view;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import model.Patient;

public class EditPatientTests {
	
	EditPatientController editPatient;

	@Before
	public void setUp() throws Exception {
		new JFXPanel();
		this.editPatient = new EditPatientController(new Patient());
		editPatient.firstName = new TextField();
		editPatient.lastName = new TextField();
	}

	@Test
	public void testInit() {
		assertTrue(editPatient.getPatient().getFirstName().equals("First Name"));
		assertTrue(editPatient.getPatient().getLastName().equals("Last Name"));
	}
	
	@Test
	public void testSaveNames() {
		editPatient.firstName.setText("Test");
		editPatient.lastName.setText("User");
		editPatient.saveNames();
		assertTrue(editPatient.getPatient().getFirstName().equals("Test"));
		assertTrue(editPatient.getPatient().getLastName().equals("User"));
	}

}
