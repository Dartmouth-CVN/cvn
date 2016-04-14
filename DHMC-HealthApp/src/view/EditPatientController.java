package view;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.Caregiver;
import model.MainApp;
import model.Patient;

public class EditPatientController {
	
	// General functions and attributes to interact with main application
	
	private Patient p;
	MainApp mainApp;
	
	public EditPatientController(Patient p) {
		this.p = p;
		
	}
	
	public EditPatientController(){};
	
	
	public void setMainApp(MainApp app){
		this.mainApp = app;
	}	
	
	public void setPatient (Patient p) {
		this.p = p;
	}
	
	public void update() {
		mainApp.getDatabaseHandler().updatePatient(p);
	}
	
	// Functions and attributes to communicate with EditPatient.fxml
	
	@FXML
	TableView<Caregiver> familyTable;
	
}
