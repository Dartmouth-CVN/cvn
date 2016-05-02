package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import model.*;
import org.controlsfx.control.Rating;
import utils.FitBitParsingUtils;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

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
    @FXML
    Accordion accord;
    @FXML
    TitledPane pane1;
    @FXML
    TitledPane pane2;
    @FXML
    TitledPane pane3;

    List<HealthAttribute<?>> healthAttributes;
    ObservableList<ContactElementWrapper> patientPhones;
    ObservableList<ContactElementWrapper> patientEmails;
    ObservableList<AbsRelationWrapper> relations;
    ObservableList<ContactElementWrapper> relationEmails;
    ObservableList<ContactElementWrapper> relationPhones;
    ObservableList<PetWrapper> pets;
    ObservableList<MealWrapper> meals;
    ObservableList<String> contactLabelValues;
    ObservableList<String> relationTypeValues;
    int relationIndex = -1;
    int petIndex = -1;
    int mealIndex = -1;
    Rating stars;
    int phoneIndex = -1;
    int emailIndex = -1;
    int relationPhoneIndex = -1;
    int relationEmailIndex = -1;
    ContactElement element;
    Callback<TableColumn<ContactElementWrapper, String>, TableCell<ContactElementWrapper, String>> cellFactory;

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
        accord.setExpandedPane(pane1);
        initializePersonalInfo();
        initializeRelationInfo();
        initializePetInfo();
        initializeDietaryPreferences();

        accord.expandedPaneProperty().addListener((ObservableValue<? extends TitledPane> observable, TitledPane oldPane, TitledPane newPane) -> {
            Boolean expand = true; // This value will change to false if there's (at least) one pane that is in "expanded" state, so we don't have to expand anything manually
            for(TitledPane pane: accord.getPanes()) {
                if(pane.isExpanded()) {
                    expand = false;
                }
            }
        /* Here we already know whether we need to expand the old pane again */
            if((expand == true) && (oldPane != null)) {
                Platform.runLater(() -> {
                    accord.setExpandedPane(oldPane);
                });
            }
        });
    }

    public void initializePersonalInfo() {
        cellFactory = new Callback<TableColumn<ContactElementWrapper, String>,
                TableCell<ContactElementWrapper, String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };
        patientPhones = FXCollections.observableArrayList();
        patientEmails = FXCollections.observableArrayList();
        contactLabelValues = FXCollections.observableArrayList();
        for(int i = 0; i < ContactElement.contactLabels.values().length; i++){
            contactLabelValues.add(ContactElement.contactLabels.values()[i].name());
        }

        patientPhoneTable.setItems(patientPhones);
        patientEmailTable.setItems(patientEmails);
        patientPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        patientPhoneLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getLabelProperty());
        patientPhoneLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        patientEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        patientEmailLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getLabelProperty());
        patientEmailLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        patientPhoneColumn.setCellFactory(cellFactory);
        patientEmailColumn.setCellFactory(cellFactory);
        patientPhoneColumn.setOnEditCommit((event) -> {
            setPhoneIndex();
            element.setValue(event.getNewValue());
            patientPhones.set(phoneIndex, new ContactElementWrapper(element));
        });
        patientPhoneLabelColumn.setOnEditCommit((event) -> {
            setPhoneIndex();
            element.setContactLabel(event.getNewValue());
            patientPhones.set(phoneIndex, new ContactElementWrapper(element));
        });

        patientEmailColumn.setOnEditCommit((event) -> {
            setEmailIndex();
            element.setValue(event.getNewValue());
            patientEmails.set(emailIndex, new ContactElementWrapper(element));
        });
        patientEmailLabelColumn.setOnEditCommit((event) -> {
            setEmailIndex();
            element.setContactLabel(event.getNewValue());
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
            if(relationIndex >= 0)
                handleRelationChange();
        });

        relationTypeValues = FXCollections.observableArrayList();
        for(int i = 0; i < AbsRelation.relationTypes.values().length; i++)
            relationTypeValues.add(AbsRelation.relationTypes.values()[i].name());
        relationType.setItems(relationTypeValues);
        relationType.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(relationType.getSelectionModel().getSelectedItem().equals(AbsRelation.relationTypes.FAMILY.name())){
                    isFamilyCheckBox.setSelected(true);
                    isFamilyCheckBox.setDisable(true);
                    isCaregiverCheckBox.setDisable(false);
                    isCaregiverCheckBox.setSelected(false);
                }else{
                    isCaregiverCheckBox.setSelected(true);
                    isCaregiverCheckBox.setDisable(true);
                    isFamilyCheckBox.setDisable(false);
                    isFamilyCheckBox.setSelected(false);
                }
            }
        });

        relationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        relationRelationshipColumn.setCellValueFactory(cellData -> cellData.getValue().getRelationshipProperty());
        relationPhoneColumn.setCellFactory(cellFactory);
        relationPhoneColumn.setOnEditCommit((event) -> {
            setRelationPhoneIndex();
            element.setValue(event.getNewValue());
            if(relationPhoneIndex < 0)
                relationPhones.add(new ContactElementWrapper(element));
            else
                relationPhones.set(relationPhoneIndex, new ContactElementWrapper(element));
        });
        relationPhoneLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        relationEmailColumn.setCellFactory(cellFactory);
        relationEmailColumn.setOnEditCommit((event) -> {
            setRelationEmailIndex();
            element.setValue(event.getNewValue());
            if(relationEmailIndex < 0)
                relationEmails.add(new ContactElementWrapper(element));
            else
                relationEmails.set(relationEmailIndex, new ContactElementWrapper(element));
        });
        relationEmailLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        relationPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        relationPhoneLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        relationEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        relationEmailLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
    }

    private void setRelationPhoneIndex(){
        relationPhoneIndex = relationPhoneTable.getSelectionModel().getSelectedIndex();
        element = relationPhoneIndex < 0? new ContactElement() : relationPhones.get(relationPhoneIndex).toContactElement();
    }

    private void setRelationEmailIndex(){
        relationEmailIndex = relationEmailTable.getSelectionModel().getSelectedIndex();
        element = relationEmailIndex < 0? new ContactElement() : relationEmails.get(relationEmailIndex).toContactElement();
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

        for(ContactElement phone : relation.getContactInfo().getPhoneNumbers())
            relationPhones.add( new ContactElementWrapper(phone));

        for(ContactElement email : relation.getContactInfo().getEmails())
            relationEmails.add( new ContactElementWrapper(email));

        if(relation.isCaregiver() && !relation.isFamily())
            relationType.getSelectionModel().select("CAREGIVER");
        else if (relation.isFamily() && !relation.isCaregiver())
            relationType.getSelectionModel().select("FAMILY");

        isFamilyCheckBox.setSelected(relation.isFamily());
        isCaregiverCheckBox.setSelected(relation.isCaregiver());
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
        try {
            if (meals.size() > 0 && mealIndex > 0) {
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
        }catch(NullPointerException e){
            MainApp.printError(e);
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
        addresses += patient.getContactInfo().getAddress().getValue() + "\n";
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
        patientEmails.add(new ContactElementWrapper(new ContactElement(Contact.contactTypes.EMAIL.name())));
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
        relationEmails.add(new ContactElementWrapper(new ContactElement(Contact.contactTypes.EMAIL.name())));
    }

    @FXML
    private void handleRemoveRelationEmail() {
        int index = relationEmailTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            relationEmails.remove(index);
    }

    @FXML
    private void handleAddPet() {
        PetWrapper pet = getPet();
        int index = pets.indexOf(pet);
        if(petIndex >= 0) //row selected
            pets.set(index, pet);
        else if(index < 0)
            pets.add(pet);
        else
            MainApp.showAlert("Pet already exists");
        resetPetFields();
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

    @FXML
    private void resetPetFields(){
        petName.clear();
        petSpecies.clear();
        allergyFriendlyCheckBox.setSelected(false);
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

    @FXML
    private void resetMealFields(){
        mealName.clear();
        mealCalories.clear();
        stars.setRating(0);
        mealNotes.clear();
    }

    @FXML
    private void handleAddMeal(){
        MealWrapper meal = getMeal();
        int index = meals.indexOf(meal);
        if(mealIndex >= 0)
            meals.set(mealIndex, meal);
        if(index < 0)
            meals.add(meal);
        else
            MainApp.showAlert("Meal already exists");
        resetMealFields();

    }

    public MealWrapper getMeal(){
        String name = mealName.getText();
        int calories;
        int rating = (int) stars.getRating();
        String notes = mealNotes.getText();

        try{
            calories = Integer.parseInt(mealCalories.getText());
        } catch (NumberFormatException e) {
            MainApp.showAlert("Calories should be a number.\nCalories reset to zero");
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
        AbsRelationWrapper relationWrapper = getRelation();
        int index = relations.indexOf(relationWrapper);
        if(index < 0 && !relations.contains(relationWrapper)) {
            relations.add(relationWrapper);
            resetRelationFields();
        }else{
//            MainApp.showAlert("Relation already exists");
            relations.set(index, relationWrapper);
        }
    }

    @FXML
    private void handleClearRelation(){
        resetRelationFields();
        relationFirstName.requestFocus();
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
            healthAttributes = FitBitParsingUtils.fitBitImport(selectedFile);
            MainApp.showAlert("FitBit import done");
            patient.getHealthProfile().addHealthInfoList(healthAttributes);
        }
    }

    @FXML
    private void save() {
        patient.setFirstName(firstName.getText());
        patient.setLastName(lastName.getText());
        patient.setBirthday(patientBirthday.getValue());

        for (MealWrapper m : meals)
            patient.addMeal(m.toMeal());

        for (PetWrapper p : pets)
            patient.addPet(p.toPet());

        for (AbsRelationWrapper relation : relations)
            patient.addRelation(relation.toAbsRelation());

        for(ContactElementWrapper phone : patientPhones) {
            patient.getContactInfo().addPhone(phone.toContactElement());
//            System.out.printf("in edit phone: type: %s, label: %s\n", phone.toContactElement().getType(), phone.toContactElement().getContactLabel() );
        }

        for(ContactElementWrapper email : patientEmails) {
            patient.getContactInfo().addEmail(email.toContactElement());
        }

        ContactElement address = new ContactElement(patientAddress.getText(),
                Contact.contactTypes.ADDRESS.name(), ContactElement.contactLabels.HOME.name());
        patient.getContactInfo().setAddress(address);

        if(healthAttributes != null)
            patient.getHealthProfile().addHealthInfoList(healthAttributes);

//        System.out.printf("patient phones size: %d patient emails size: %d\n", patientPhones.size(), patientEmails.size());

        if (patient.getIsNewPatient() && patient.savePatient() ||
                !patient.getIsNewPatient() && patient.updatePatient()) {
            MainApp.showAlert("Update successful!");
            patient.setNewPatient(false);
        }
    }
}
