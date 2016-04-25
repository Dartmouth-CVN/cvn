package view.controller;

import java.io.File;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import model.*;
import utils.ObjectNotFoundException;

public class EditPatientController extends AbsController{

	// General functions and attributes to interact with main application

	private Patient patient;
	private File curCSV;
	private LinkedList<HealthInfo> info;

	public EditPatientController() {
	}


	@FXML
	public FXMLLoader getLoader() {
		loader.setLocation(MainApp.class.getResource("../view/EditPatientView.fxml"));
		return loader;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient p) {
		this.patient = p;
		loadFields();
	}

	@FXML
	public void importFitBitCSV() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select FitBit CSV to import");
		curCSV = fc.showOpenDialog(null);
		if (curCSV != null && curCSV.exists()) {
//			info = utils.FitBitParsingUtils.fitBitImport(curCSV);
//			patient.getHealthProfile().addHealthInfoList(info);
//			MainApp.showAlert("FitBit import successful!");
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
	TextField relationName;
	@FXML
	TextField relationshipField;
	@FXML
	TextField mealName;
	@FXML
	TextField mealCalories;
	@FXML
	TextField mealRating;
	@FXML
	TextArea mealNotes;
	@FXML
	DatePicker patientBirthday;
	@FXML
	DatePicker relationBirthday;
	@FXML
	TableView<ContactElementWrapper> patientPhoneTable;
	@FXML
	TableView<ContactElementWrapper> patientEmailTable;
	@FXML
	TableView<ContactElementWrapper> relationPhoneTable;
	@FXML
	TableView<ContactElementWrapper> relationEmailTable;
	@FXML
	TableColumn<ContactElementWrapper, String> patientPhoneColumn;
	@FXML
	TableColumn<ContactElementWrapper, String> patientEmailColumn;
	@FXML
	TableColumn<ContactElementWrapper, String> relationPhoneColumn;
	@FXML
	TableColumn<ContactElementWrapper, String> relationEmailColumn;
	@FXML
	TableView<AbsRelationWrapper> relationTable;
	@FXML
	TableColumn<AbsRelationWrapper, String> relationNameColumn;
	@FXML
	TableColumn<AbsRelationWrapper, String> relationRelationshipColumn;
	@FXML
	TableView<PetWrapper> petTable;
	@FXML
	TableColumn<PetWrapper, String> petNameColumn;
	@FXML
	TableColumn<PetWrapper, String> petSpeciesColumn;
	@FXML
	TableColumn<PetWrapper, Boolean> petAllergyFriendlyColumn;
	@FXML
	TableView<MealWrapper> mealTable;
	@FXML
	TableColumn<MealWrapper, String> mealNameColumn;
	@FXML
	TableColumn<MealWrapper, Number> mealCaloriesColumn;
	@FXML
	TableColumn<MealWrapper, Number> mealRatingColumn;
	@FXML
	TableColumn<MealWrapper, String> mealNotesColumn;

	ObservableList<ContactElementWrapper> patientPhones;
	ObservableList<ContactElementWrapper> patientEmails;

	ObservableList<AbsRelationWrapper> familyMembers;
	ObservableList<ContactElementWrapper> familyEmails;
	ObservableList<ContactElementWrapper> familyPhones;

	ObservableList<PetWrapper> pets;
	ObservableList<MealWrapper> meals;

	@FXML
	private void initialize() {
		patientPhones = FXCollections.observableArrayList();
		patientEmails = FXCollections.observableArrayList();
		familyMembers = FXCollections.observableArrayList();
		familyPhones = FXCollections.observableArrayList();
		familyEmails = FXCollections.observableArrayList();
		pets = FXCollections.observableArrayList();
		meals = FXCollections.observableArrayList();

		patientPhoneTable.setItems(patientPhones);
		patientEmailTable.setItems(patientEmails);
		relationTable.setItems(familyMembers);
		petTable.setItems(pets);
		mealTable.setItems(meals);

		patientPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		patientEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		relationPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		relationEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		patientPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
		patientEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
		relationPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
		relationEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
		relationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		relationRelationshipColumn.setCellValueFactory(cellData -> cellData.getValue().getRelationshipProperty());
		petNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		petSpeciesColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeciesProperty());
		petAllergyFriendlyColumn.setCellValueFactory(cellData -> cellData.getValue().getAllergyFriendlyProperty());
		mealNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFoodProperty());
		mealCaloriesColumn.setCellValueFactory(cellData -> cellData.getValue().getCaloriesProperty());
		mealRatingColumn.setCellValueFactory(cellData -> cellData.getValue().getRatingProperty());
		mealNotesColumn.setCellValueFactory(cellData -> cellData.getValue().getNotesProperty());

		patientPhoneColumn.setOnEditCommit(new EventHandler<CellEditEvent<ContactElementWrapper, String>>() {
			@Override
			public void handle(CellEditEvent<ContactElementWrapper, String> t) {
				try {
					int phoneIndex = patientPhoneTable.getSelectionModel().getSelectedIndex();
					long id = patientPhoneTable.getSelectionModel().getSelectedItem().getElementIdProperty().get();
					patientPhones.set(phoneIndex, new ContactElementWrapper(patient.getContactInfo().getElementById(id)) );
				} catch (ObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		patientEmailColumn.setOnEditCommit(new EventHandler<CellEditEvent<ContactElementWrapper, String>>() {
			@Override
			public void handle(CellEditEvent<ContactElementWrapper, String> t) {
				try {
					int emailIndex = patientEmailTable.getSelectionModel().getSelectedIndex();
					long id = patientPhoneTable.getSelectionModel().getSelectedItem().getElementIdProperty().get();
					patientEmails.set(emailIndex, new ContactElementWrapper(patient.getContactInfo().getElementById(id)) );
				} catch (ObjectNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadFields() {

		firstName.setText(patient.getFirstName());
		lastName.setText(patient.getLastName());
//			patientBirthday.setValue(patient.getBirthday().getTime());

		for(ContactElement e: patient.getContactInfo().getPhoneNumbers())
			patientPhones.add(new ContactElementWrapper(e));

		for(ContactElement e: patient.getContactInfo().getEmails())
			patientEmails.add(new ContactElementWrapper(e));

		String addresses = "";
		for(ContactElement e: patient.getContactInfo().getAddresses())
			addresses += e.getValue() + "\n";
		patientAddress.setText(addresses);

		for(AbsRelation relation : patient.getRelations())
			familyMembers.add(new AbsRelationWrapper(relation));

		for (Pet p : patient.getPets())
			pets.add(new PetWrapper(p));

		for(Meal m: patient.getMeals())
			meals.add(new MealWrapper(m));
	}

	@FXML
	private void addPatientPhone() {
		patientPhones.add(new ContactElementWrapper(new ContactElement()));
	}

	@FXML
	private void removePatientPhone() {
		patientPhones.remove(patientPhoneTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addPatientEmail() {
		patientEmails.add(new ContactElementWrapper(new ContactElement()));
	}

	@FXML
	private void removePatientEmail() {
		patientEmails.remove(patientEmailTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addFamilyMemPhone() {
		familyPhones.add(new ContactElementWrapper(new ContactElement()));
	}

	@FXML
	private void removeFamilyMemPhone() {
		familyPhones.remove(relationPhoneTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addFamilyMemEmail() {
		familyEmails.add(new ContactElementWrapper(new ContactElement()));
	}

	@FXML
	private void removeFamilyMemEmail() {
		familyEmails.remove(relationEmailTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addPet() {
		petTable.getItems().add(new PetWrapper(new Pet()));
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
		mealTable.getItems().add(new MealWrapper(new Meal()));
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
	
	public void saveInfo() {
		patient.setFirstName(firstName.getText());
		patient.setLastName(lastName.getText());
//		patient.setBirthday(new Date(patientBirthday.getValue().toString()));

		LinkedList<Meal> patientMeals = new LinkedList<Meal>();
		LinkedList<Pet> patientPets = new LinkedList<Pet>();
		LinkedList<AbsRelation> patientRelations = new LinkedList<AbsRelation>();

		for(MealWrapper m : meals)
			patientMeals.add(m.toMeal());

		for(PetWrapper p : pets)
		patientPets.add(p.toPet());

		for(AbsRelationWrapper relation : familyMembers)
		patientRelations.add(relation.toAbsRelation());

		patient.setMeals(patientMeals);
		patient.setPets(patientPets);

		if ( patient.getIsNewPatient() && patient.savePatient() ||
				!patient.getIsNewPatient() && patient.updatePatient() ) {
			MainApp.showAlert("Update successful!");
		}
	}
}
