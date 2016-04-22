package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Meal {
	private StringProperty food;
	private IntegerProperty calories;
	private IntegerProperty rating;
	private StringProperty notes;
	private int mealID;
	
	public Meal(String food, Integer calories, Integer rating, String notes){
		this.food = new SimpleStringProperty(food);
		this.calories = new SimpleIntegerProperty(calories);
		this.rating = new SimpleIntegerProperty(rating);
		this.notes = new SimpleStringProperty(notes);
	}
	
	public Meal() {
		this.food.set("");
		this.calories.set(0);
		this.rating.set(0);
		this.notes.set("");
	}

	public int getMealID(){
		return this.mealID;
	}
	
	public void setMealID(int id){
		this.mealID = id;
	}
	
	public StringProperty foodProperty() {
		return food;
	}
	
    public final String getFood() {
        return foodProperty().get();
    }

    public final void setFood(String food) {
        foodProperty().set(food);
    }
    
	public IntegerProperty caloriesProperty() {
		return calories;
	}
	
    public final int getCalories() {
        return caloriesProperty().get();
    }

    public final void setCalories(int calories) {
        caloriesProperty().set(calories);
    }
    
    public IntegerProperty ratingProperty() {
    	
    	return rating;
    }
	
    public final int getRating() {
        return ratingProperty().get();
    }

    public final void setRating(int rating) {
        ratingProperty().set(rating);
    }
    
	public StringProperty notesProperty() {
		return notes;
	}
	
    public final String getNotes() {
        return notesProperty().get();
    }

    public final void setNotes(String notes) {
        notesProperty().set(notes);
    }
}

