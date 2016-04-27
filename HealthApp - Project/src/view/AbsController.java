package view;

import javafx.fxml.FXMLLoader;

public abstract class AbsController {
	
	MainApp mainApp;
	FXMLLoader loader;

	public AbsController() {
		this.loader = new FXMLLoader();
	}

	public FXMLLoader getLoader() {
		return this.loader;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
