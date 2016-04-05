package dhmc.view;

import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import dhmc.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class MyScheduleController {

	private Main mainApp;
	@FXML
	private AnchorPane calendarPane;

	public MyScheduleController() {
	}

	@FXML
	private void initialize() {
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public void initCalendar() {
		System.out.println("initialize schedule");
		CalendarView calendarView = new CalendarView();

		Calendar patientCalendar = new Calendar("Schedule - Patient");

		CalendarSource myCalendarSource = new CalendarSource("My Calendars");
		myCalendarSource.getCalendars().add(patientCalendar);

		calendarView.getCalendarSources().add(myCalendarSource);
		calendarView.setRequestedTime(LocalTime.now());

		Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> {
						calendarView.setToday(LocalDate.now());
						calendarView.setTime(LocalTime.now());
					});

					try {
						// update every 10 seconds
						sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};
		updateTimeThread.setPriority(Thread.MIN_PRIORITY);
		updateTimeThread.setDaemon(true);
		updateTimeThread.start();

		Scene scene = new Scene(calendarView);
	}
}
