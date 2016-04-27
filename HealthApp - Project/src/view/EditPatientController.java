package view;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;
import utils.ObjectNotFoundException;

public class EditPatientController extends AbsController {

    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    ImageView profilePic;
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
    ObservableList<AbsRelationWrapper> relations;
    ObservableList<ContactElementWrapper> relationEmails;
    ObservableList<ContactElementWrapper> relationPhones;
    ObservableList<PetWrapper> pets;
    ObservableList<MealWrapper> meals;
    DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Patient patient;

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
    private void initialize() {
        initializePersonalInfo();
        initializeRelationInfo();
        initializePetInfo();
        initializeDietaryPreferences();
    }

    public void initializePersonalInfo() {
        patientPhones = FXCollections.observableArrayList();
        patientEmails = FXCollections.observableArrayList();
        patientPhoneTable.setItems(patientPhones);
        patientEmailTable.setItems(patientEmails);
        patientPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        patientEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        patientPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        patientEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        patientPhoneColumn.setOnEditCommit((event) -> {
            int phoneIndex = patientPhoneTable.getSelectionModel().getSelectedIndex();
            long id = patientPhones.get(phoneIndex).getElementIdProperty().get();
            try {
                patientPhones.set(phoneIndex, new ContactElementWrapper(patient.getContactInfo().getElementById(id)));
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            }
        });

        patientEmailColumn.setOnEditCommit((event) -> {
            try {
                int emailIndex = patientEmailTable.getSelectionModel().getSelectedIndex();
                long id = patientEmails.get(emailIndex).getElementIdProperty().get();
                patientEmails.set(emailIndex, new ContactElementWrapper(patient.getContactInfo().getElementById(id)));
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    public void initializeRelationInfo() {
        relations = FXCollections.observableArrayList();
        relationPhones = FXCollections.observableArrayList();
        relationEmails = FXCollections.observableArrayList();
        relationTable.setItems(relations);

        relationPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        relationEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        relationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        relationPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        relationEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        relationRelationshipColumn.setCellValueFactory(cellData -> cellData.getValue().getRelationshipProperty());
    }

    public void initializePetInfo() {
        pets = FXCollections.observableArrayList();
        petTable.setItems(pets);
        petNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        petSpeciesColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeciesProperty());
        petAllergyFriendlyColumn.setCellValueFactory(cellData -> cellData.getValue().getAllergyFriendlyProperty());

    }

    public void initializeDietaryPreferences() {
        meals = FXCollections.observableArrayList();
        mealTable.setItems(meals);
        mealNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFoodProperty());
        mealCaloriesColumn.setCellValueFactory(cellData -> cellData.getValue().getCaloriesProperty());
        mealRatingColumn.setCellValueFactory(cellData -> cellData.getValue().getRatingProperty());
        mealNotesColumn.setCellValueFactory(cellData -> cellData.getValue().getNotesProperty());
    }

    public void loadFields() {
        profilePic.setImage(new Image("file:" + patient.getPicture()));
        firstName.setText(patient.getFirstName());
        lastName.setText(patient.getLastName());
        patientBirthday.setValue(patient.getBirthday());

        for (ContactElement e : patient.getContactInfo().getPhoneNumbers())
            patientPhones.add(new ContactElementWrapper(e));

        for (ContactElement e : patient.getContactInfo().getEmails())
            patientEmails.add(new ContactElementWrapper(e));

        String addresses = "";
        for (ContactElement e : patient.getContactInfo().getAddresses())
            addresses += e.getValue() + "\n";
        patientAddress.setText(addresses);

        for (AbsRelation relation : patient.getRelations())
            relations.add(new AbsRelationWrapper(relation));

        for (Pet p : patient.getPets())
            pets.add(new PetWrapper(p));

        for (Meal m : patient.getMeals())
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
        relationPhones.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void removeFamilyMemPhone() {
        relationPhones.remove(relationPhoneTable.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void addFamilyMemEmail() {
        relationEmails.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void removeFamilyMemEmail() {
        relationEmails.remove(relationEmailTable.getSelectionModel().getSelectedIndex());
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
        patient.setBirthday(patient.getBirthday());

        LinkedList<Meal> patientMeals = new LinkedList<Meal>();
        LinkedList<Pet> patientPets = new LinkedList<Pet>();
        LinkedList<AbsRelation> patientRelations = new LinkedList<AbsRelation>();

        for (MealWrapper m : meals)
            patientMeals.add(m.toMeal());

        for (PetWrapper p : pets)
            patientPets.add(p.toPet());

        for (AbsRelationWrapper relation : relations)
            patientRelations.add(relation.toAbsRelation());

        patient.setMeals(patientMeals);
        patient.setPets(patientPets);

        if (patient.getIsNewPatient() && patient.savePatient() ||
                !patient.getIsNewPatient() && patient.updatePatient()) {
            MainApp.showAlert("Update successful!");
        }
    }
}
