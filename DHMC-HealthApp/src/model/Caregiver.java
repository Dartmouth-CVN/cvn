package model;

public class Caregiver {
	private String name;
	private int age;
	private String relation;
	private Contact contactInfo;
	private boolean isFamily;
	private int caregiverID;
	public Caregiver (String name, int age, String relation, Contact contactInfo, boolean familymember){
		this.name = name;
		this.age = age;
		this.relation = relation;
		this.contactInfo = contactInfo;
		this.isFamily = familymember;
		
	}
	
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Contact getContactInfo() {
		return contactInfo;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsFamily() {
		return isFamily;
	}

	public void setIsFamily(boolean familymember) {
		this.isFamily = familymember;
	}

	public int getCaregiverID() {
		return caregiverID;
	}

	public void setCaregiverID(int caregiverID) {
		this.caregiverID = caregiverID;
	}
	

}

