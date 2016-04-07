package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import model.MainApp;
import model.Patient;

public class EditPatientController {
	
	private Patient p;
	
	public EditPatientController(Patient p) {this.p=p;}
	public EditPatientController(){};
	
	@FXML
	private TextField firstNameTF;

	@FXML
	private TextField lastNameTF;

	@FXML
	private TextField emailTF;

	@FXML
	private TextField phoneTF;
	
	@FXML
	private TextArea savedInfo;
	
	@FXML
	private ScrollPane sp;

	@FXML
	private VBox box;
	
	@FXML
	private Button add;

	@FXML
	private Button delete;
	
	MainApp mainApp;
	
	@FXML
	public void initialize(){

	}

	@FXML
	public void handleNames(KeyEvent event){

		if(!event.getCode().equals(KeyCode.ENTER))
			return;
		else {
			String first = firstNameTF.getText();
			String last = lastNameTF.getText();
			String email = emailTF.getText();
			String phone = phoneTF.getText();
			
			p.setFirstName(first);
			p.setLastName(last);
			p.getContactInfo().makePrimaryEmail(email);
			p.getContactInfo().makePrimaryPhone(phone);
			
			savedInfo.setText("Saved Information:\n" +
					"First Name: " + p.getFirstName() + "\n" +
					"Last Name: " + p.getLastName() + "\n" +
					"Email: " + p.getContactInfo().getPrimaryEmail() + "\n" +
					"Phone: " + p.getContactInfo().getPrimaryPhone()); 
		}
	}
	
	
	public void setMainApp(MainApp app){
		this.mainApp = app;
	}
	
	@FXML
	public void addFamily() {
		TextField newFamily = new TextField();
		newFamily.setPromptText("Family Member Name");
		box.getChildren().add(box.getChildren().size()-2, newFamily);
	}
	
	@FXML
	public void deleteFamily() {
		if(box.getChildren().size() > 2) {
			box.getChildren().remove(box.getChildren().size()-2);
		}
	}
	
	public void update() {
		mainApp.getDatabseHandler().updatePatient(p);
	}
}
