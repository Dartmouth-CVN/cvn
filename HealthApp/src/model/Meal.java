package model;

public class Meal {
	private int mealId;
	private String food;
	private int calories;
	private int rating;
	private String notes;
	

	public Meal(){
		food = "";
		notes = "";
	}
	
	public Meal(int mealId, String food, int calories, int rating, String notes) {
		super();
		this.mealId = mealId;
		this.food = food;
		this.calories = calories;
		this.rating = rating;
		this.notes = notes;
	}
	
	public int getMealId() {
		return mealId;
	}
	public void setMealId(int mealId) {
		this.mealId = mealId;
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

