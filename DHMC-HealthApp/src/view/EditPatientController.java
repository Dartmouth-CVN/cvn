package view;

import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Caregiver;
import model.MainApp;
import model.Meal;
import model.Patient;
import model.Pet;

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
	
	@FXML TextField firstName;
	@FXML TextField lastName;
	@FXML TextArea patientAddress;
	@FXML TextField familyMemName;
	@FXML TextField familyMemRel;
	@FXML TextField mealName;
	@FXML TextField mealCal;
	@FXML TextArea mealNotes;
	@FXML DatePicker patientBirthday;
	@FXML DatePicker familyMemBirthday;
	@FXML VBox patientPhone;
	@FXML VBox patientEmail;
	@FXML VBox familyMemPhone;
	@FXML VBox familyMemEmail;
	@FXML TableView<Caregiver> familyTable;
	@FXML TableColumn<Caregiver, String> familyNames;
	@FXML TableColumn<Caregiver, String> familyRels;
	@FXML TableView<Pet> petTable;
	@FXML TableColumn<Pet, String> petNames;
	@FXML TableColumn<Pet, String> petSpecies;
	@FXML TableColumn<Pet, Boolean> petAllergyFriendly;
	@FXML TableView<Meal> mealTable;
	@FXML TableColumn<Meal, String> mealNames;
	@FXML TableColumn<Meal, Integer> mealCals;
	@FXML TableColumn<Meal, Boolean> mealLiked;
	@FXML TableColumn<Meal, Boolean> mealDiskliked;
	@FXML TableColumn<Meal, String> mealNotesCol;
	@FXML RadioButton mealLikeButton;
	@FXML RadioButton mealDislikeButton;
	@FXML RadioButton mealNeutralButton;
	
	
	@FXML private void initialize() {
		TextField temp;
		int i;
		LinkedList<String> phoneNums = p.getContactInfo().getPhone();
		LinkedList<String> emails = p.getContactInfo().getEmail();
		
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		patientBirthday.setValue(p.getBirthday());
		patientAddress.setText(p.getContactInfo().getPrimaryAddress());
		
		for (i = 0; i < phoneNums.size(); i++) {
			if (i > 0) {
				addPatientPhone();
			}
			temp = (TextField) patientPhone.getChildren().get(i);
			temp.setText(phoneNums.get(i));
		}
		
		for (i = 0; i < emails.size(); i++) {
			if (i > 0) {
				addPatientEmail();
			}
			temp = (TextField) patientEmail.getChildren().get(i);
			temp.setText(emails.get(i));
		}
	}
	
	@FXML private void addPatientPhone() {
		TextField newPhone = new TextField();
		newPhone.setPromptText("Phone Number");
		patientPhone.getChildren().add(newPhone);
	}
	
	@FXML private void removePatientPhone() {
		int size = patientPhone.getChildren().size();
		
		if (size > 1) {
			patientPhone.getChildren().remove(size - 1);
		}
	}
	
	@FXML private void addPatientEmail() {
		TextField newEmail = new TextField();
		newEmail.setPromptText("Email Address");
		patientEmail.getChildren().add(newEmail);
	}
	
	@FXML private void removePatientEmail() {
		int size = patientEmail.getChildren().size();
		
		if (size > 1) {
			patientEmail.getChildren().remove(size - 1);
		}
	}
	
	@FXML private void addFamilyMemPhone() {
		TextField newPhone = new TextField();
		newPhone.setPromptText("Phone Number");
		familyMemPhone.getChildren().add(newPhone);
	}
	
	@FXML private void removeFamilyMemPhone() {
		int size = familyMemPhone.getChildren().size();
		
		if (size > 1) {
			familyMemPhone.getChildren().remove(size - 1);
		}
	}
	
	@FXML private void addFamilyMemEmail() {
		TextField newEmail = new TextField();
		newEmail.setPromptText("Email Address");
		familyMemEmail.getChildren().add(newEmail);
	}
	
	@FXML private void removeFamilyMemEmail() {
		int size = familyMemEmail.getChildren().size();
		
		if (size > 1) {
			familyMemEmail.getChildren().remove(size - 1);
		}
	}
	
	@FXML private void addFamilyMember() {
		
	}
	
	@FXML private void removeFamilyMember() {
		
	}
	
	@FXML private void addPet() {
		
	}
	
	@FXML private void removePet() {
		
	}
	
	@FXML private void addMeal() {
		
	}
	
	@FXML private void removeMeal() {
		
	}
	
	@FXML private void likeMeal() {
		mealDislikeButton.setSelected(false);
		mealNeutralButton.setSelected(false);
	}
	
	@FXML private void dislikeMeal() {
		mealLikeButton.setSelected(false);
		mealNeutralButton.setSelected(false);
	}
	
	@FXML private void unlikeMeal() {
		mealDislikeButton.setSelected(false);
		mealLikeButton.setSelected(false);
	}
	
	@FXML private void switchFamilyMember() {
		
	}
	
	@FXML private void switchMeal() {
		
	}
	
	@FXML private void save() {
		update();
	}
}
