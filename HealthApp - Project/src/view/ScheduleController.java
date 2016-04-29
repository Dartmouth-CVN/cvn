package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import model.AbsUser;
import model.Event;
import model.MainApp;

import java.time.LocalDateTime;

public class ScheduleController extends AbsController {
	@FXML
	TableView<Event> myEvents;
	@FXML
	TableView<Event> upcomingEvents;
	@FXML
	TableColumn<Event, LocalDateTime> myTime;
	@FXML
	TableColumn<Event, String> myTitle;
	@FXML
	TableColumn<Event, String> myLocation;
	@FXML
	TableColumn<Event, LocalDateTime> upcomingTime;
	@FXML
	TableColumn<Event, String> upcomingTitle;
	@FXML
	TableColumn<Event, String> upcomingLocation;
	@FXML
	TextArea myNotes;
	@FXML
	Label myNotesLabel;
	@FXML
	TextArea upcomingNotes;
	@FXML
	Label upcomingNotesLabel;

	private AbsUser user;
	private ObservableList<Event> myEventList;
	private ObservableList<Event> upcomingEventList;


	public ScheduleController() {

	}

	@FXML
	public FXMLLoader getLoader() {
		loader.setLocation(MainApp.class.getResource("../view/ScheduleView.fxml"));
		return loader;
	}

	@FXML
	private void initialize() {
//		myEventList.addAll(user.getSchedule().getEvents());
		//myEvents.setItems(myEventList);
		myTime.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
		myTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		myLocation.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
		//myNotes.setText(user.getSchedule().);
	}

	public AbsUser getUser() {
		return user;
	}

	public void setUser(AbsUser user) {
		this.user = user;
	}
}
