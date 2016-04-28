package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Administrator;
import model.ContactElement;
import utils.ObjectNotFoundException;

public class AdminDashController extends MedicalStaffDashController {

	@FXML
	private ImageView profilePic;
	@FXML
	private Label name;
	@FXML
	private Label email;
	@FXML
	private Label room;
	@FXML
	private Label number;

	@FXML
	protected Tab addPatientTab;

	private Administrator admin;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public AdminDashController() {
		key = "admin dash";
	}

	@FXML
	private void initialize() {
		loadAdminFields();
		super.initializeTabs();
	}

	public Administrator getAdmin(){
		return admin;
	}

	public void setAdmin(Administrator admin){
		this.admin = admin;
		loadAdminFields();
	}

	public void loadAdminFields(){
		try {
			if(admin != null) {
				profilePic.setImage(new Image("file:" + admin.getPicture()));
				name.setText(admin.getFirstName() + " " + admin.getLastName());
				ContactElement mail = admin.getContactInfo().getPrimaryEmail();
				ContactElement phone = admin.getContactInfo().getPrimaryPhone();

				email.setText(mail.getValue() + " (" + mail.getType() + ")");
				room.setText(admin.getRoom());
				number.setText(phone.getValue() + " (" + phone.getType() + ")");
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public FXMLLoader getLoader(){
		loader.setLocation(MainApp.class.getResource("../view/AdminDash.fxml"));
		return loader;
	}

	/**
	 * Loads and sets content of the schedule tab.
	 */
	@FXML
	private void loadScheduleTab() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/MySchedule.fxml"));
			AnchorPane mySchedule = (AnchorPane) loader.load();

			scheduleTab.setContent(mySchedule);

			MyScheduleController controller = loader.getController();
			controller.setMainApp(mainApp);

		} catch (IOException e) {
			MainApp.printError(e);
		}
	}
	
}
