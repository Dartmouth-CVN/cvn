package view;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.converter.DefaultStringConverter;
import model.*;
import org.apache.derby.impl.tools.sysinfo.Main;
import org.controlsfx.control.Rating;
import utils.FitBitParsingUtils;

import java.io.File;
import java.time.format.DateTimeFormatter;

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
    TextField relationFirstName;
    @FXML
    TextField relationLastName;
    @FXML
    TextField relationshipField;
    @FXML
    ComboBox<String> relationType;
    @FXML
    TextField mealName;
    @FXML
    TextField mealCalories;
    @FXML
    TextField petName;
    @FXML
    TextField petSpecies;
    @FXML
    CheckBox allergyFriendlyCheckBox;
    @FXML
    AnchorPane mealRating;
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
    TableColumn<ContactElementWrapper, String> patientPhoneLabelColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> patientEmailLabelColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> relationPhoneColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> relationEmailColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> relationPhoneLabelColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> relationEmailLabelColumn;
    @FXML
    TableView<AbsRelationWrapper> relationTable;
    @FXML
    TableColumn<AbsRelationWrapper, String> relationNameColumn;
    @FXML
    TableColumn<AbsRelationWrapper, String> relationRelationshipColumn;
    @FXML
    CheckBox isFamilyCheckBox;
    @FXML
    CheckBox isCaregiverCheckBox;
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
    ObservableList<String> contactLabelValues;
    ObservableList<String> relationTypeValues;
    int relationIndex = 0;
    int petIndex = 0;
    int mealIndex = 0;
    Rating stars;
    int phoneIndex = 0;
    int emailIndex = 0;
    ContactElement element;

    DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Patient patient;

    public EditPatientController() {
        key = "edit patient";
    }

    @FXML
    public FXMLLoader getLoader() {
        loader.setLocation(MainApp.class.getResource("/view/EditPatientView.fxml"));
        return loader;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient p) {
        clearFields();

        this.patient = p;
        if(!p.getIsNewPatient())
            loadFields();

    }

    public void clearFields() {
        profilePic.setImage(new Image("file:src/view/images/blank-profile-picture.png"));
        firstName.setText("");
        lastName.setText("");
        patientBirthday.setValue(null);
        patientPhones.clear();
        patientEmails.clear();
        patientAddress.clear();
        relations.clear();
        pets.clear();
        meals.clear();
        resetRelationFields();
    }

    @FXML
    private void initialize() {
        initializePersonalInfo();
        initializeRelationInfo();
        initializePetInfo();
        initializeDietaryPreferences();
    }

//    List<TextFieldTableCell<ContactElementWrapper, String>> textFieldList;

    public void initializePersonalInfo() {
        patientPhones = FXCollections.observableArrayList();
        patientEmails = FXCollections.observableArrayList();
        contactLabelValues = FXCollections.observableArrayList();
        for(int i = 0; i < ContactElement.contactLabel.values().length; i++){
            contactLabelValues.add(ContactElement.contactLabel.values()[i].name());
        }

        patientPhoneTable.setItems(patientPhones);
        patientEmailTable.setItems(patientEmails);
        patientPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        patientPhoneLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        patientPhoneLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        patientEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        patientEmailLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        patientEmailLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        patientPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        patientEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        patientPhoneColumn.setOnEditCommit((event) -> {
            setPhoneIndex();
            element.setValue(event.getNewValue());
            patientPhones.set(phoneIndex, new ContactElementWrapper(element));
        });
        patientPhoneLabelColumn.setOnEditCommit((event) -> {
            setPhoneIndex();
            element.setType(event.getNewValue());
            patientPhones.set(phoneIndex, new ContactElementWrapper(element));
        });

        patientEmailColumn.setOnEditCommit((event) -> {
            setEmailIndex();
            element.setValue(event.getNewValue());
            patientEmails.set(emailIndex, new ContactElementWrapper(element));
        });
        patientEmailLabelColumn.setOnEditCommit((event) -> {
            setEmailIndex();
            element.setType(event.getNewValue());
            patientEmails.set(emailIndex, new ContactElementWrapper(element));
        });

    }

    private void setPhoneIndex(){
        phoneIndex = patientPhoneTable.getSelectionModel().getSelectedIndex();
        element = patientPhones.get(phoneIndex).toContactElement();
    }

    private void setEmailIndex(){
        emailIndex = patientEmailTable.getSelectionModel().getSelectedIndex();
        element = patientEmails.get(emailIndex).toContactElement();
    }

    public void initializeRelationInfo() {
        relations = FXCollections.observableArrayList();
        relationPhones = FXCollections.observableArrayList();
        relationEmails = FXCollections.observableArrayList();
        relationTable.setItems(relations);

        relationPhoneTable.setItems(relationPhones);
        relationEmailTable.setItems(relationEmails);
        relationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            relationIndex = relationTable.getSelectionModel().getSelectedIndex();
            handleRelationChange();
        });

        relationTypeValues = FXCollections.observableArrayList();
        for(int i = 0; i < AbsRelation.relationTypes.values().length; i++)
            relationTypeValues.add(AbsRelation.relationTypes.values()[i].name());
        relationType.setItems(relationTypeValues);

        relationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        relationRelationshipColumn.setCellValueFactory(cellData -> cellData.getValue().getRelationshipProperty());
        relationPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        relationPhoneLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        relationEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        relationEmailLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        relationPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        relationPhoneLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        relationEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        relationEmailLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
    }

    private void resetRelationFields(){
        relationFirstName.setText("");
        relationLastName.setText("");
        relationBirthday.setValue(LocalDate.now());
        relationshipField.setText("");
        isFamilyCheckBox.setSelected(false);
        isCaregiverCheckBox.setSelected(false);
        relationPhones.clear();
        relationEmails.clear();
        relationType.getSelectionModel().selectFirst();
    }

    private void handleRelationChange() {
        AbsRelation relation = relationTable.getSelectionModel().getSelectedItem().toAbsRelation();
        resetRelationFields();
        relationFirstName.setText(relation.getFirstName());
        relationLastName.setText(relation.getLastName());
        relationBirthday.setValue(relation.getBirthday());
        relationshipField.setText(relation.getRelationship());
        isFamilyCheckBox.setSelected(relation.isFamily());
        isCaregiverCheckBox.setSelected(relation.isCaregiver());

        for(ContactElement phone : relation.getContactInfo().getPhoneNumbers())
            relationPhones.add( new ContactElementWrapper(phone));

        for(ContactElement email : relation.getContactInfo().getEmails())
            relationEmails.add( new ContactElementWrapper(email));

        if(relation.isCaregiver() && !relation.isFamily())
            relationType.getSelectionModel().select("CAREGIVER");
        else if (relation.isFamily() && !relation.isCaregiver())
            relationType.getSelectionModel().select("FAMILY");
    }

    public void initializePetInfo() {
        pets = FXCollections.observableArrayList();
        petTable.setItems(pets);
        petTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            petIndex = petTable.getSelectionModel().getSelectedIndex();
            handlePetChange();
        });

        petNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        petSpeciesColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeciesProperty());
        petAllergyFriendlyColumn.setCellValueFactory(cellData -> cellData.getValue().getAllergyFriendlyProperty());
    }

    private void handlePetChange(){
        Pet p;
        if(pets.size() > 0) {
            p = pets.get(petIndex).toPet();
            petName.setText(p.getName());
            petSpecies.setText(p.getSpecies());
            allergyFriendlyCheckBox.setSelected(p.isAllergyFriendly());
        } else {
            petName.clear();
            petSpecies.clear();
            allergyFriendlyCheckBox.setSelected(false);
        }
    }

    public void initializeDietaryPreferences() {
        meals = FXCollections.observableArrayList();
        mealTable.setItems(meals);
        mealTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mealIndex = mealTable.getSelectionModel().getSelectedIndex();
            handleMealChange();
        });

        mealNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFoodProperty());
        mealCaloriesColumn.setCellValueFactory(cellData -> cellData.getValue().getCaloriesProperty());
        mealRatingColumn.setCellValueFactory(cellData -> cellData.getValue().getRatingProperty());
        mealNotesColumn.setCellValueFactory(cellData -> cellData.getValue().getNotesProperty());
        stars = new Rating(10, 0);
        stars.setUpdateOnHover(true);
        mealRating.getChildren().add(stars);
    }

    private void handleMealChange(){
        Meal m;
        if(meals.size() > 0) {
            m = meals.get(mealIndex).toMeal();
            mealName.setText(m.getFood());
            mealCalories.setText(String.valueOf(m.getCalories()));
            mealNotes.setText(m.getNotes());
            stars.setRating(m.getRating());
        } else {
            mealName.clear();
            mealCalories.clear();
            mealNotes.clear();
            stars.setRating(0);
        }
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
    private void handleAddPatientPhone() {
        patientPhones.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void handleRemovePatientPhone() {
        int index = patientPhoneTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            patientPhones.remove(index);
    }

    @FXML
    private void handleAddPatientEmail() {
        patientEmails.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void handleRemovePatientEmail() {
        int index = patientEmailTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            patientEmails.remove(index);
    }

    @FXML
    private void handleAddRelationPhone() {
        relationPhones.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void handleRemoveRelationPhone() {
        int index = relationPhoneTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            relationPhones.remove(index);
    }

    @FXML
    private void handleAddRelationEmail() {
        relationEmails.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void handleRemoveRelationEmail() {
        int index = relationEmailTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            relationEmails.remove(index);
    }

    @FXML
    private void handleAddPet() {
        resetPetFields();
        petName.requestFocus();
    }

    @FXML
    private void handleSavePet(){
        PetWrapper pet = getPet();
        int index = pets.indexOf(pet);
        if(index < 0)
            pets.add(pet);
        else {
//            MainApp.showAlert("Pet already exists");
            pets.set(index, pet);
        }
    }

    @FXML
    private void handleRemovePet() {
        int selectionIndex = petTable.getSelectionModel().getSelectedIndex();

        if(pets.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Remove pet");
            alert.setContentText("Are you sure you want to remove this pet?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.CANCEL) {
                    return;
                } else if (rs == ButtonType.OK) {
                    if (selectionIndex >= 0) {
                        petTable.getItems().remove(selectionIndex);
                    }
                    resetPetFields();
                }
            });
        }
    }

    private void resetPetFields(){
        petName.clear();
        petSpecies.clear();
        allergyFriendlyCheckBox.setSelected(false);
    }

    @FXML
    private void handleAddMeal() {
        resetMealFields();
        mealName.requestFocus();
    }

    @FXML
    private void handleRemoveMeal() {
        int selectionIndex = mealTable.getSelectionModel().getSelectedIndex();

        if(meals.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Remove meal");
            alert.setContentText("Are you sure you want to remove this meal?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.CANCEL) {
                    return;
                } else if (rs == ButtonType.OK) {
                    if (selectionIndex >= 0) {
                        mealTable.getItems().remove(selectionIndex);
                    }
                    resetMealFields();
                }
            });
        }
    }

    private void resetMealFields(){
        mealName.clear();
        mealCalories.clear();
        stars.setRating(0);
        mealNotes.clear();
    }

    @FXML
    private void handleSaveMeal(){
        MealWrapper meal = getMeal();
        int index = meals.indexOf(meal);
        if(index < 0)
            meals.add(meal);
        else{
            meals.set(index, meal);
        }

    }

    public MealWrapper getMeal(){
        String name = mealName.getText();
        int calories;
        int rating = (int) stars.getRating();
        String notes = mealNotes.getText();

        try{
            calories = Integer.parseInt(mealCalories.getText());
        } catch (NumberFormatException e) {
            calories = 0;
        }

       return new MealWrapper(new Meal(name, calories, rating, notes));
    }

    public PetWrapper getPet(){
        String name = petName.getText();
        String species = petSpecies.getText();
        Boolean allergyFriendly = allergyFriendlyCheckBox.isSelected();

        return new PetWrapper(new Pet(name, species, allergyFriendly));
    }

    public AbsRelationWrapper getRelation(){
        AbsRelation relation = null;
        String firstName = relationFirstName.getText();
        String lastName = relationLastName.getText();
        String relationship = relationshipField.getText();
        LocalDate birthday = relationBirthday.getValue();
        boolean isFamily = isFamilyCheckBox.isSelected();
        boolean isCaregiver = isCaregiverCheckBox.isSelected();
        List<ContactElement> elements = new LinkedList<>();

        for(ContactElementWrapper elementWrapper : relationPhones)
            elements.add(elementWrapper.toContactElement());

        for(ContactElementWrapper elementWrapper : relationEmails)
            elements.add(elementWrapper.toContactElement());
        Contact contactInfo = new Contact(elements);
        try {
            if (relationType.getSelectionModel().getSelectedItem().equals(AbsRelation.relationTypes.FAMILY)) {
                relation = new Family(firstName, lastName, "N/A", "N/A", birthday, "N/A", "N/A", contactInfo,
                        relationship, isCaregiver);
            } else {
                relation = new Caregiver(firstName, lastName, "N/A", "N/A", birthday, "N/A", "N/A", contactInfo,
                        relationship, isFamily);
            }
        }catch(NullPointerException e){
            MainApp.printError(e);
        }
        return new AbsRelationWrapper(relation);
    }

    @FXML
    private void handleAddRelation(){
        resetRelationFields();
        relationFirstName.requestFocus();
    }

    @FXML
    private void handleSaveRelation(){
        AbsRelationWrapper relationWrapper = getRelation();
        int index = relations.indexOf(relationWrapper);
        if(index < 0) {
            relations.add(relationWrapper);
            resetRelationFields();
        }else{
//            MainApp.showAlert("Relation already exists");
            relations.set(index, relationWrapper);
        }
    }

    @FXML
    private void handleRemoveRelation(){
        relations.remove(relationIndex);
        resetRelationFields();
    }

    @FXML
    private void handleFitBitImport() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            patient.getHealthProfile().setHealthInfo(FitBitParsingUtils.fitBitImport(selectedFile));
            MainApp.showAlert("FitBit import done");
        }

    }

    @FXML
    private void save() {
        patient.setFirstName(firstName.getText());
        patient.setLastName(lastName.getText());
        patient.setBirthday(patient.getBirthday());

        for (MealWrapper m : meals)
            patient.addMeal(m.toMeal());

        for (PetWrapper p : pets)
            patient.addPet(p.toPet());

        for (AbsRelationWrapper relation : relations)
            patient.addRelation(relation.toAbsRelation());

        for(ContactElementWrapper phone : patientPhones)
            patient.getContactInfo().addPhone(phone.toContactElement());

        for(ContactElementWrapper email : patientEmails)
            patient.getContactInfo().addEmail(email.toContactElement());

//        System.out.printf("patient phones size: %d patient emails size: %d\n", patientPhones.size(), patientEmails.size());

        if (patient.getIsNewPatient() && patient.savePatient() ||
                !patient.getIsNewPatient() && patient.updatePatient()) {
            MainApp.showAlert("Update successful!");
        }
    }
}
