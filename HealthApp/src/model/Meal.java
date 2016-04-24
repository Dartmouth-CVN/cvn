package model;

import javafx.beans.property.*;

import javax.persistence.*;

public class Meal {
	 long mealId;
	String food;
	int calories;
	int rating;
	String notes;

	Patient patientKey;

	LongProperty mealIdProperty;
	StringProperty foodProperty;
	IntegerProperty caloriesProperty;
	IntegerProperty ratingProperty;
	StringProperty notesProperty;
	

	public Meal(){
	}
	
	public Meal(long mealId, String food, int calories, int rating, String notes) {
		setMealId(mealId);
		setFood(food);
		setCalories(calories);
		setRating(rating);
		setNotes(notes);
	}
	
	public Meal(String food, int calories, int rating, String notes) {
		this (0, food, calories, rating, notes);
	}
	
	public long getMealId() {
		return mealId;
	}
	public void setMealId(long mealId) {
		this.mealId = mealId;
		setMealIdProperty(mealId);
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
		setFoodProperty(food);
	}
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
		setCaloriesProperty(calories);
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
		setRatingProperty(rating);
	}
	public String getNotes() {
		return notes;
	}

	public LongProperty getMealIdProperty() {
		return mealIdProperty;
	}

	public StringProperty getFoodProperty() {
		return foodProperty;
	}

	public IntegerProperty getCaloriesProperty() {
		return caloriesProperty;
	}

	public IntegerProperty getRatingProperty() {
		return ratingProperty;
	}

	public StringProperty getNotesProperty() {
		return notesProperty;
	}

	public void setNotes(String notes) {
		this.notes = notes;
		setNotesProperty(notes);
	}

	public void setMealIdProperty(long mealId) {
		mealIdProperty = new SimpleLongProperty(mealId);
	}

	public void setFoodProperty(String food) {
		foodProperty = new SimpleStringProperty(food);
	}

	public void setCaloriesProperty(int calories) {
		caloriesProperty = new SimpleIntegerProperty(calories);
	}

	public void setRatingProperty(int rating) {
		ratingProperty = new SimpleIntegerProperty(rating);
	}

	public void setNotesProperty(String notes) {
		notesProperty = new SimpleStringProperty(notes);
	}


	public Patient getPatientKey(){
		return patientKey;
	}

	public void setPatientKey(Patient p){
		patientKey = p;
	}
}

