package model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Event {

	private long eventId;
	private String title;
	private LocalDateTime date;
	private String location;
	private List<AbsUser> attendees;
	private String notes;

	public Event(LocalDateTime date, String title, List<AbsUser> attendees,
	             String notes, String location) {
		this.date = date;
		this.title = title;
		this.location = location;
		this.attendees = attendees;
		this.notes = notes;
	}

	public Event(LocalDateTime date, String location, String notes) {
		this(date, "Title", new LinkedList<AbsUser>(), notes, location);
	}
	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public void setAttendees(List<AbsUser> attendees) {
		this.attendees = attendees;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDate() {
		return this.date.toString();
	}

	public String getLocation() {
		return this.location;
	}

	public List<AbsUser> getAttendees() {
		return this.attendees;
	}

	public String getNotes() {
		return this.notes;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
