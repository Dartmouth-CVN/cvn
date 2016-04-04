package model;
import java.util.LinkedList;
import java.time.LocalDateTime;

class Schedule {
	private int id;
	private LinkedList<Event> events;

	public Schedule(int id) {
		this.id = id;
		this.events = new LinkedList<Event>();
	}
	
	public int getID() {
		return this.id;
	}
	
	public void addEvent(int id, String name, LocalDateTime date, String category) {
		Event newEvent = new Event(id, name, date, category);
		this.events.add(newEvent);
	}
	
	public void removeEvent(int id) {
		int i;
		for (i = 0; i < this.events.size(); i++) {
			if (this.events.get(i).getID() == id) {
				this.events.remove(i);
			}
		}
	}

}
