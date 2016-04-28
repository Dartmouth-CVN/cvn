package view;

import javafx.fxml.FXMLLoader;
import model.MainApp;

public abstract class AbsController {
	
	MainApp mainApp;
	FXMLLoader loader;
	String key;

	public AbsController() {
		this.loader = new FXMLLoader();
		key = "abs controller";
	}

	public FXMLLoader getLoader() {
		return this.loader;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}

	@Override
	public int hashCode(){
		return key.hashCode();
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof AbsController){
			return ((AbsController) o).getKey().equals(this.key);
		}
		return false;
	}
}
