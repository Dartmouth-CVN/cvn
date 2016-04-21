package model;

import java.util.LinkedList;

public class Schedule {
	
	int scheduleId;
	private LinkedList<Event> events;
	
	public Schedule () {
		this.events = new LinkedList<Event>();
	}
	
	public Schedule (LinkedList<Event> events) {
		this.events = events;
	}
	
	public int getScheduleId(){
		return scheduleId;
	}
	
	public void setScheduleId(int scheduleId){
		this.scheduleId = scheduleId;
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
