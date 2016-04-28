package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import model.Administrator;
import model.MainApp;

public class AdminDashController extends MedicalStaffDashController {

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
		System.out.println("loading this stuff");
		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if(newTab.equals(exportTab)) {
				loadExportTab();
				tabPane.getSelectionModel().select(exportTab);
			}
			});
	}

	public Administrator getAdmin(){
		return admin;
	}

	public void setAdmin(Administrator admin){
		this.admin = admin;
		loadAdminFields();
	}

	public void loadAdminFields(){
		super.loadUserFields(admin);
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
