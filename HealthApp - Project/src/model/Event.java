package model;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
	private LongProperty eventId;
	private StringProperty title;
	private ObjectProperty<LocalDateTime> date;
	private StringProperty location;
	private ListProperty<AbsUser> attendees;
	private StringProperty notes;

	public Event(LocalDateTime date, String title, ListProperty<AbsUser> attendees,
	             String notes, String location) {
		this.date = new SimpleObjectProperty<LocalDateTime>(date);
		this.title = new SimpleStringProperty(title);
		this.location = new SimpleStringProperty(location);
		this.attendees = new SimpleListProperty(attendees);
		this.notes = new SimpleStringProperty(notes);
	}

	public Event(LocalDateTime date, String title,
				 String notes, String location) {
		this.date = new SimpleObjectProperty<LocalDateTime>(date);
		this.title = new SimpleStringProperty(title);
		this.location = new SimpleStringProperty(location);
		this.notes = new SimpleStringProperty(notes);
	}

	public Event(LocalDateTime date, String location, String notes) {
		this(date, "Title", new SimpleListProperty<AbsUser>(), notes, location);
	}
	public long getEventId() {
		return eventId.get();
	}

	public void setEventId(LongProperty eventId) {
		this.eventId = eventId;
	}

	public void setAttendees(ListProperty<AbsUser> attendees) {
		this.attendees = attendees;
	}

	public void setDate(ObjectProperty<LocalDateTime> date) {
		this.date = date;
	}

	public void setLocation(StringProperty location) {
		this.location = location;
	}

	public void setNotes(StringProperty notes) {
		this.notes = notes;
	}

	public String getDate() {
		return this.date.toString();
	}

	public LocalDateTime getDateTime() {
		return this.date.get();
	}

	public String getLocation() {
		return this.location.get();
	}

	public List<AbsUser> getAttendees() {
		return this.attendees;
	}

	public String getNotes() {
		return this.notes.get();
	}

	public void addAttendee(AbsUser user){
		attendees.add(user);
	}

	public void removeAttendee(AbsUser user){
		if(attendees.contains(user))
			attendees.remove(user);
	}

	@Override
	public int hashCode(){
		return location.hashCode() * notes.hashCode();
	}

	public String getTitle() {return title.get(); }

	public void setTitle(StringProperty title) {
		this.title = title;
	}

	public StringProperty getTitleProperty() {return this.title;}
	public ObjectProperty<LocalDateTime> getDateProperty() {return this.date;}
	public StringProperty getLocationProperty() {return this.location;}
	public StringProperty getNotesProperty() {return this.notes;}

}
