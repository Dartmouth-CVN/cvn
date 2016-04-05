package model;

class Location {
	private int id;
	private String name;
	private String type;
	
	public Location(int id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
