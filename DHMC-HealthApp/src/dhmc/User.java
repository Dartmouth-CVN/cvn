package dhmc;

abstract class User {
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private Contact contactInfo;
	private Schedule schedule;
	private Location room;
	private int id;
	private String role;
	// role is either "Patient", "MedicalStaff", or "Administrator"
	
	public User(String firstName, String lastName, String username, String password, int id, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.contactInfo = new Contact(id);
		this.schedule = new Schedule(id);
		this.room = null;
		this.id =id;
		this.role = role;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public int getUserid() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Contact getContactInfo() {
		return this.contactInfo;
	}
	
	public String getRole(){
		return this.role;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setUserid(int userid) {
		this.id = userid;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRole(String role){
		this.role = role;
	}
	
	public Location getRoom() {
		return this.room;
	}
	
	public void setRoom(Location room) {
		this.room = room;
	}
	
	public Schedule getSchedule() {
		return this.schedule;
	}
	
	public void login() {
		// Database access
	}
	
	public void update() {
		// Database access
	}

}
