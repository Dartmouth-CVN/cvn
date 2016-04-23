package view;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import model.Caregiver;
import model.DisplayString;
import model.HealthInfo;
import model.MainApp;
import model.Meal;
import model.Patient;
import model.Pet;

public class EditPatientController {

	// General functions and attributes to interact with main application

	private Patient p;
	MainApp mainApp;
	private File curCSV;
	private LinkedList<HealthInfo> info;


	public EditPatientController(Patient p) {
		this.p = p;
//		Pane parent = exportfields;
//		for(Node n : parent.getChildren()){
//			if(n instanceof Accordion){
//				//create array
//			}
//			if(n instanceof CheckBox){
//				
//			}
//		}
	}


	public EditPatientController() {
	}

	public void setMainApp(MainApp app) {
		this.mainApp = app;
	}
	
	public Patient getPatient() {
		return this.p;
	}

	public void setPatient(Patient p) {
		this.p = p;
	}

	@FXML
	public void importFitBitCSV() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select FitBit CSV to import");
		curCSV = fc.showOpenDialog(null);
		if (curCSV != null && curCSV.exists()) {
			info = controller.FitBitParsingUtils.fitBitImport(curCSV);
			p.addHealthInfoList(info);
			MainApp.showAlert("FitBit import successful!");
		}
	}
	
	@FXML
	public void exportFitBitCSV() {
		curCSV = new File(p.getFirstName()+p.getLastName()+"-fitbitexport.csv");
		if (curCSV != null && curCSV.exists()) {
			controller.FitBitParsingUtils.fitbitExport(curCSV, p);
			MainApp.showAlert("FitBit export successful!");
		}
	}

	// Functions and attributes to communicate with EditPatient.fxml

	@FXML
	TextField firstName;
	@FXML 
	TextField lastName;
	@FXML
	TextArea patientAddress;
	@FXML
	TextField familyMemName;
	@FXML
	TextField familyMemRel;
	@FXML
	TextField mealName;
	@FXML
	TextField mealCal;
	@FXML
	TextArea mealNotes;
	@FXML
	DatePicker patientBirthday;
	@FXML
	DatePicker familyMemBirthday;
	@FXML
	TableView<DisplayString> patientPhoneTable;
	@FXML
	TableView<DisplayString> patientEmailTable;
	@FXML
	TableView<DisplayString> familyPhoneTable;
	@FXML
	TableView<DisplayString> familyEmailTable;
	@FXML
	TableColumn<DisplayString, String> patientPhone;
	@FXML
	TableColumn<DisplayString, String> patientEmail;
	@FXML
	TableColumn<DisplayString, String> familyPhone;
	@FXML
	TableColumn<DisplayString, String> familyEmail;
	@FXML
	TableView<Caregiver> familyTable;
	@FXML
	TableColumn<Caregiver, String> familyNames;
	@FXML
	TableColumn<Caregiver, String> familyRels;
	@FXML
	TableView<Pet> petTable;
	@FXML
	TableColumn<Pet, String> petNames;
	@FXML
	TableColumn<Pet, String> petSpecies;
	@FXML
	TableColumn<Pet, Boolean> petAllergyFriendly;
	@FXML
	TableView<Meal> mealTable;
	@FXML
	TableColumn<Meal, String> mealNames;
	@FXML
	TableColumn<Meal, Number> mealCals;
	@FXML
	TableColumn<Meal, Number> rating;
	@FXML
	TableColumn<Meal, String> mealNotesCol;
	@FXML
	TextField mealRating;
	
	

	ObservableList<DisplayString> patientPhones;
	ObservableList<DisplayString> patientEmails;

	ObservableList<Caregiver> familyMembers;
	ObservableList<DisplayString> familyEmails;
	ObservableList<DisplayString> familyPhones;
	ObservableList<Caregiver> famNames;
	ObservableList<Caregiver> famRels;
	
	ObservableList<Pet> pets;
	ObservableList<Meal> meals;

	@FXML
	private void initialize() {
	}

	public void loadFields() {
		patientPhones = FXCollections.observableArrayList();
		patientEmails = FXCollections.observableArrayList();
		familyMembers = FXCollections.observableArrayList();
		pets = FXCollections.observableArrayList();
		meals = FXCollections.observableArrayList();
		famNames = FXCollections.observableArrayList();
		famRels = FXCollections.observableArrayList();
		familyPhones = FXCollections.observableArrayList();
		familyEmails = FXCollections.observableArrayList();

		patientPhoneTable.setItems(patientPhones);
		patientEmailTable.setItems(patientEmails);
		familyTable.setItems(familyMembers);
		petTable.setItems(pets);
		mealTable.setItems(meals);

		patientPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		patientEmail.setCellFactory(TextFieldTableCell.forTableColumn());
		familyPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		familyEmail.setCellFactory(TextFieldTableCell.forTableColumn());

		patientPhone.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		patientEmail.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		familyPhone.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		familyEmail.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		familyNames.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		familyRels.setCellValueFactory(cellData -> cellData.getValue().relationProperty());
		mealNames.setCellValueFactory(cellData -> cellData.getValue().foodProperty());
		mealCals.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty());
		rating.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());
		mealNotesCol.setCellValueFactory(cellData -> cellData.getValue().notesProperty());

		patientPhone.setOnEditCommit(new EventHandler<CellEditEvent<DisplayString, String>>() {
			@Override
			public void handle(CellEditEvent<DisplayString, String> t) {
				int phoneIndex = patientPhoneTable.getSelectionModel().getSelectedIndex();
				patientPhones.set(phoneIndex, new DisplayString(t.getNewValue()) );
			}
		});

		patientEmail.setOnEditCommit(new EventHandler<CellEditEvent<DisplayString, String>>() {
			@Override
			public void handle(CellEditEvent<DisplayString, String> t) {
				int emailIndex = patientEmailTable.getSelectionModel().getSelectedIndex();
				patientEmails.set(emailIndex, new DisplayString(t.getNewValue()) );
			}
		});
		
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		patientBirthday.setValue(p.getBirthday());
		patientAddress.setText(p.getContactInfo().getPrimaryAddress());

		for (String phoneNum : p.getContactInfo().getPhoneList())
			patientPhones.add(new DisplayString(phoneNum));

		for (String email : p.getContactInfo().getEmailList())
			patientEmails.add(new DisplayString(email));
		
		for(Meal m : p.getPreferences().getMenu())
			meals.add(m);
		
		for(Caregiver c : p.getPreferences().getCaregivers())
			familyMembers.add(c);
		
		for(Pet pet : p.getPreferences().getPets())
			pets.add(pet);
	}

	@FXML
	private void addPatientPhone() {
		patientPhones.add(new DisplayString("Enter New Number..."));
	}

	@FXML
	private void removePatientPhone() {
		patientPhones.remove(patientPhoneTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addPatientEmail() {
		patientEmails.add(new DisplayString("Enter new email..."));
	}

	@FXML
	private void removePatientEmail() {
		patientEmails.remove(patientEmailTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addFamilyMemPhone() {
		familyPhones.add(new DisplayString("Enter New Number..."));
	}

	@FXML
	private void removeFamilyMemPhone() {
		familyPhones.remove(familyPhoneTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addFamilyMemEmail() {
		familyEmails.add(new DisplayString("Enter new email..."));
	}

	@FXML
	private void removeFamilyMemEmail() {
		familyEmails.remove(familyEmailTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addFamilyMember() {
	}

	@FXML
	private void removeFamilyMember() {
		int selectionIndex = familyTable.getSelectionModel().getSelectedIndex();

		if (selectionIndex >= 0) {
			familyTable.getItems().remove(selectionIndex);
		}
	}

	@FXML
	private void addPet() {
		petTable.getItems().add(new Pet());
	}

	@FXML
	private void removePet() {
		int selectionIndex = petTable.getSelectionModel().getSelectedIndex();

		if (selectionIndex >= 0) {
			petTable.getItems().remove(selectionIndex);
		}
	}

	@FXML
	private void addMeal() {
		meals.add(new Meal(mealName.getText(), 
				Integer.parseInt(mealCal.getText()), Integer.parseInt(mealRating.getText()), mealNotes.getText()));
		mealName.setText("");
		mealCal.setText("");
		mealRating.setText("");
		mealNotes.setText("");
	}

	@FXML
	private void removeMeal() {
		int selectionIndex = mealTable.getSelectionModel().getSelectedIndex();

		if (selectionIndex >= 0) {
			mealTable.getItems().remove(selectionIndex);
		}
	}

	@FXML
	private void save() {
		saveInfo();
	}
	
	public void saveNames() {
		p.setFirstName(firstName.getText());
		p.setLastName(lastName.getText());
	}
	
	public void saveInfo() {
		ArrayList<String> array = new ArrayList<String>();
		saveNames();
		p.setBirthday(patientBirthday.getValue());

		p.getContactInfo().addAddress(patientAddress.getText());


		for (DisplayString phoneNumber : patientPhones) {
			p.getContactInfo().addPhone(phoneNumber.getString());
			array.add(phoneNumber.getString());
		}
		
		for (String phone : p.getContactInfo().getPhoneList()) {
			if (!array.contains(phone)) {
				p.getContactInfo().removePhone(phone);
			}
		}
		
		array = new ArrayList<String>();

		for (DisplayString email : patientEmails) {
			p.getContactInfo().addEmail(email.getString());
			array.add(email.getString());
		}
		
		for (String email : p.getContactInfo().getEmailList()) {
			if (!array.contains(email)) {
				p.getContactInfo().removePhone(email);
			}
		}
		
		for(Meal m : meals){
			p.getPreferences().addMeal(m);
		}
	
		p.getContactInfo().addAddress(patientAddress.getText());


		if ( (p.getNewPatient() && p.save()) || (!p.getNewPatient() && p.update())) {
			MainApp.showAlert("Update successful!");
		}
	}

	public void setMain(MainApp mainApp) {
		
		this.mainApp = mainApp;
		
	}
	
}
