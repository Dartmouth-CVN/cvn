package model;
public class Meal {
	private int mealID;
	private String food;
	private int calories;
	private int rating;
	private String notes;
	
	public Meal(String food, int calories, int rating, String notes){
		this.food = food;
		this.calories = calories;
		this.rating = rating;
		this.notes = notes;
	}
	
	public Meal() {
		this.food = "New Meal";
		this.calories = 0;
		this.rating = 0;
		this.notes = "";
	}

	public int getMealID() {
		return mealID;
	}

	public void setMealID(int mealID) {
		this.mealID = mealID;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}

