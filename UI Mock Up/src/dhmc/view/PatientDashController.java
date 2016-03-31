package dhmc.view;

import application.Main;

public class PatientDashController {
	private Main mainApp;
	
	public PatientDashController(Main mainApp){
		this.mainApp = mainApp;
	}
	
	public void handleTabSwitch(){
		mainApp.getPrimaryStage().setWidth(800);
		mainApp.getPrimaryStage().setHeight(600);
	}

}
