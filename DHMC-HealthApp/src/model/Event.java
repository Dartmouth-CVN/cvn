package model;
import java.time.LocalDateTime;

class Event {
	private int id;
	private String name;
	private LocalDateTime date;
	private String category;
	

	public Event(int id, String name, LocalDateTime date, String category) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.category = category;
	}
		
	public int getID() {
			return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public LocalDateTime getDate() {
		return this.date;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}
