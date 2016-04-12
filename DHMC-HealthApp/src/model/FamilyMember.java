package model;

public class FamilyMember {
	private String name;
	private int age;
	private String relation;
	private Contact contactInfo;

	public FamilyMember (String name, int age, String relation, Contact contactInfo){
		this.name = name;
		this.age = age;
		this.relation = relation;
		this.contactInfo = contactInfo;
		
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
	

}
