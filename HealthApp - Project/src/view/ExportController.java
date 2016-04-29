package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.MainApp;
import model.Patient;
import utils.*;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExportController extends AbsController {

	@FXML
	private RadioButton CSVRadioButton = new RadioButton();
	@FXML
	private RadioButton TSVRadioButton = new RadioButton();
	@FXML
	private RadioButton XMLRadioButton = new RadioButton();
	@FXML
	private RadioButton HTMLRadioButton = new RadioButton();
	@FXML
	private TitledPane personalPane = new TitledPane();
	@FXML
	private TitledPane otherPane = new TitledPane();
	@FXML
	private Button exportButton;

	List<CheckBox> personal;
	List<CheckBox> other;
	List<Boolean> fieldCheck;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public ExportController() {
		key = "export controller";
		personal = new LinkedList<>();
		other = new LinkedList<>();
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		loadCheckBoxArrays();
	}


	@Override
	public FXMLLoader getLoader() {
		loader.setLocation(MainApp.class.getResource("../view/ExportView.fxml"));
		return loader;
	}

	@FXML
	private void handlePersonalSelectButton() {
	}

	@FXML
	private void handlePersonalClearButton() {
	}

	@FXML
	private void handleHealthSelectButton() {
	}

	@FXML
	private void handleHealthClearButton() {
	}

	@FXML
	private void handlePreferenceSelectButton() {
	}

	@FXML
	private void handlePreferenceClearButton() {
	}

	@FXML
	private void handleImport(){
		SVParsingUtils utils = new CSVParsingUtils();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(new Stage());

		if(file != null){
//			List<Patient> patients = XMLParsingUtils.importData(file);
			List<Patient> patients = utils.importData(file);
			for(Patient p : patients){
				System.out.println("patient name " + p.getFirstName() );
				DBHandler.getUniqueInstance().insertPatientAlgorithm(p);
			}
			MainApp.showAlert("Finished Import");
		}
	}

	@FXML
	private void handleExport() {
		String name = "Exported" + LocalTime.now();
		getFields();
		PatientExportWrapper wrapper = new PatientExportWrapper(fieldCheck);

		if (CSVRadioButton.isSelected()) {
			SVParsingUtils utils = new CSVParsingUtils();
			utils.exportData(name + ".csv", DBHandler.getUniqueInstance().getAllFilledPatients(), wrapper);
			MainApp.showAlert("Export CSV done");
		} else if (TSVRadioButton.isSelected()) {
			SVParsingUtils utils = new TSVParsingUtils();
			utils.exportData(name + ".tsv", DBHandler.getUniqueInstance().getAllFilledPatients(), wrapper);
			MainApp.showAlert("Export TSV done");
		} else if (XMLRadioButton.isSelected()) {
			XMLParsingUtils utils = new XMLParsingUtils();
			utils.exportData(utils.getFile(name + ".xml"), DBHandler.getUniqueInstance().getAllFilledPatients(), wrapper);
			MainApp.showAlert("Export XML done");
		} else if (HTMLRadioButton.isSelected()) {
			HTMLParsingUtils utils = new HTMLParsingUtils();
			utils.exportData(utils.getFile(name + ".html"), DBHandler.getUniqueInstance().getAllFilledPatients(), wrapper);
			MainApp.showAlert("Export HTML done");
		}
	}

	private void getFields(){
		fieldCheck = new ArrayList<Boolean>();
		boolean firstname = getCheckBoxFor("First Name").isSelected();
		boolean lastname = getCheckBoxFor("Last Name").isSelected();
		boolean username = getCheckBoxFor("Username").isSelected();
		boolean birthday = getCheckBoxFor("Birthday").isSelected();
		boolean room = getCheckBoxFor("Room").isSelected();
		boolean picture = getCheckBoxFor("Picture").isSelected();
		boolean contactInfo = getCheckBoxFor("First Name").isSelected();
		boolean pets = getCheckBoxFor("Pets").isSelected();
		boolean meals = getCheckBoxFor("Meals").isSelected();
		boolean relations = getCheckBoxFor("Relations").isSelected();
		boolean assignedStaff = getCheckBoxFor("Medical Staff").isSelected();
		boolean healthProfile = getCheckBoxFor("Health Info").isSelected();

		fieldCheck.add(firstname);
		fieldCheck.add(lastname);
		fieldCheck.add(username);
		fieldCheck.add(birthday);
		fieldCheck.add(room);
		fieldCheck.add(picture);
		fieldCheck.add(contactInfo);
		fieldCheck.add(pets);
		fieldCheck.add(meals);
		fieldCheck.add(relations);
		fieldCheck.add(assignedStaff);
		fieldCheck.add(healthProfile);
	}

	private void loadCheckBoxArrays() {
		int i = 0;
		for (Node m : getAllNodes((AnchorPane) personalPane.getContent())) {
			if (m instanceof CheckBox) {
				personal.add( (CheckBox) m);
				i++;
			}
		}

		i = 0;
		for (Node n : getAllNodes((AnchorPane) otherPane.getContent())){
			if (n instanceof CheckBox) {
				other.add( (CheckBox) n);
				i++;
			}
		}
	}

	private CheckBox getCheckBoxFor(String label){
		for(CheckBox c : personal){
			if(c.getText().equals(label)){
				return c;
			}
		}

		for(CheckBox c : other){
			if(c.getText().equals(label)){
				return c;
			}
		}

		return null;
	}

	public static ArrayList<Node> getAllNodes(Parent root) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		addAllDescendents(root, nodes);
		return nodes;
	}

	private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
		for (Node node : parent.getChildrenUnmodifiable()) {
			nodes.add(node);
			if (node instanceof Parent)
				addAllDescendents((Parent)node, nodes);
		}
	}

}
