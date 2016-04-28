package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.AbsUser;
import model.ContactElement;
import model.Patient;
import sun.applet.Main;
import utils.ObjectNotFoundException;

public abstract class AbsDashController extends AbsController {

	@FXML
	protected TabPane tabPane;
	@FXML
	protected Tab scheduleTab;
	@FXML
	protected Tab searchTab;
	@FXML
	protected Tab exportTab;
	@FXML
	protected Tab editPatientTab;
	@FXML
	protected Tab addPatientTab;
	@FXML
	protected ImageView scheduleImage;
	@FXML
	protected ImageView searchImage;

	@FXML
	protected ImageView profilePic;
	@FXML
	protected Label name;
	@FXML
	protected Label email;
	@FXML
	protected Label room;
	@FXML
	protected Label number;

	@FXML
	private AnchorPane iconPane;

	public AbsDashController() {
		key = "abs dash";
	}

	@FXML
	private void initialize() {
	}

	public void setIconPane(AnchorPane pane){
		iconPane = pane;
	}

	public void initializeTabs(){
		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if(newTab.equals(searchTab)) {
				loadSearchTab();
				tabPane.getSelectionModel().select(searchTab);
			}
			else if(newTab.equals(addPatientTab)) {
				loadAddPatientTab();
				tabPane.getSelectionModel().select(addPatientTab);
			}
		});
	}

	public void loadUserFields(AbsUser user){
		try {
			if(user != null) {
				profilePic.setImage(new Image("file:" + user.getPicture()));
				name.setText(user.getFirstName() + " " + user.getLastName());
				ContactElement mail = user.getContactInfo().getPrimaryEmail();
				ContactElement phone = user.getContactInfo().getPrimaryPhone();

				email.setText(mail.getValue() + " (" + mail.getType() + ")");
				room.setText(user.getRoom());
				number.setText(phone.getValue() + " (" + phone.getType() + ")");
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void loadSearchTab() {
		LoadedScene scene = MainApp.getLoadedSceneOfType(new SearchController());
		searchTab.setContent(scene.getPane());
	}

	public void loadAddPatientTab(){
		LoadedScene scene = MainApp.getLoadedSceneOfType(new EditPatientController());
		((EditPatientController) scene.getController()).setPatient(new Patient());
		addPatientTab.setContent(scene.getPane());
	}

	public void loadEditPatientTab(Patient p) {
		LoadedScene scene = MainApp.getLoadedSceneOfType(new EditPatientController());
		((EditPatientController) scene.getController()).setPatient(p);
		editPatientTab.setContent(scene.getPane());
		tabPane.getSelectionModel().select(editPatientTab);
	}

	public void loadExportTab(){
		LoadedScene scene = MainApp.getLoadedSceneOfType(new ExportController());
		exportTab.setContent(scene.getPane());

	}

	/**
	 * Clicking search image opens the search tab.
	 */
	@FXML
	protected void handleSearchImage() {
		loadSearchTab();
		tabPane.getSelectionModel().select(searchTab);
	}

	/**
	 * Clicking schedule image opens the schedule tab.
	 */
	@FXML
	protected void handleScheduleImage() {
		tabPane.getSelectionModel().select(scheduleTab);
	}

}
