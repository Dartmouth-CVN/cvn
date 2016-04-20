package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import model.MainApp;

public abstract class AbsDashController extends AbsController {

	public AbsDashController(MainApp mainApp) {
		super(mainApp);
	}
	
	@FXML
	private TabPane tabPane;

	@FXML
	private Tab scheduleTab = new Tab();

	@FXML
	private Tab searchTab = new Tab();

	@FXML
	private Tab exportTab = new Tab();

	@FXML
	private Tab editPatientTab = new Tab();
	@FXML
	private ImageView scheduleImage = new ImageView();

	@FXML
	private ImageView searchImage = new ImageView();

}
