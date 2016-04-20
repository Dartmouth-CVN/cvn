package controller;

import javafx.fxml.FXMLLoader;
import model.MainApp;

public abstract class AbsController {
	
	MainApp mainApp;
	FXMLLoader loader;

	public AbsController(MainApp mainApp) {
		this.mainApp = mainApp;
		this.loader = new FXMLLoader();
	}

	public FXMLLoader getLoader() {
		return this.loader;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
