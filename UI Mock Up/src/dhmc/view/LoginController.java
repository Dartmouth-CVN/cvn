package dhmc.view;

import dhmc.Main;
import javafx.fxml.FXML;

public class LoginController {
	
	private Main mainApp;
	
	public LoginController(){
		
	}
	
	@FXML
	private void initialize(){
		
	}
	
	public void handleLogin(){
		mainApp.initRootLayout();
		mainApp.setVerticalDimensions();
		mainApp.showPatientDash();
	}
	
	public void setMainApp(Main mainApp){
		this.mainApp = mainApp;
	}

}
