package model;

public class Meal {
	private String name;
	private int calories;
	private boolean like;
	private boolean dislike;
	private String notes;
	private int mealID;
	public Meal(String name, int calories, boolean like, boolean dislike, String notes){
		this.name = name;
		this.calories = calories;
		this.like = like;
		this.dislike = dislike;
		this.notes = notes;
	}

	public boolean didLike(){
		return this.like;
	}

	public int getCalories(){
		return this.calories;
	}

	public int getMealID(){
		return this.mealID;
	}

	public String getName(){
		return this.name;
	}

	public String getSpecialNotes(){
		return this.notes;
	}

	public boolean didDislike() {
		return dislike;
	}

	public void setCalories(int calories){
		this.calories = calories;
	}

	public void setDislike(boolean dislike) {
		this.dislike = dislike;
	}

	public void setDisliked(boolean disliked) {
		this.dislike = disliked;
	}
	
	public void setLike(boolean like){
		this.like = like;
	}
	
	public void setMealID(int id){
		this.mealID = id;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setSpecialNotes(String specialnotes){
		this.notes = specialnotes;
	}
}

