package model;

import java.util.LinkedList;

public class Schedule {
	
	int scheduleID;
	private LinkedList<Event> events;
	
	public Schedule () {
		this.events = new LinkedList<Event>();
	}
	
	public Schedule (LinkedList<Event> events) {
		this.events = events;
	}
	
	//The setters
	public void addEvent(Event e) {
		if(!this.events.contains(e)) {
			this.events.add(e);
		}
	}
	
	public void removeEvent(Event e) {
		if(this.events.contains(e)) {
			this.events.remove(e);
		}
	}
	
	//The getters
	public LinkedList<Event> getEvents() {
		return this.events;
	}
}
