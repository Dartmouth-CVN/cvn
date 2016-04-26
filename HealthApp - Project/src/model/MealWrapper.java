package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by mrampiah on 4/24/16.
 */
public class MealWrapper {
    StringProperty foodProperty;
    IntegerProperty caloriesProperty;
    IntegerProperty ratingProperty;
    StringProperty notesProperty;
    Meal meal;

    public MealWrapper(Meal meal) {
        this.meal = meal;
        setCaloriesProperty(meal.getCalories());
        setFoodProperty(meal.getFood());
        setRatingProperty(meal.getRating());
        setNotesProperty(meal.getNotes());
    }

    public StringProperty getFoodProperty() {
        return foodProperty;
    }

    public void setFoodProperty(String food) {
        foodProperty = new SimpleStringProperty(food);
    }

    public IntegerProperty getCaloriesProperty() {
        return caloriesProperty;
    }

    public void setCaloriesProperty(int calories) {
        caloriesProperty = new SimpleIntegerProperty(calories);
    }

    public IntegerProperty getRatingProperty() {
        return ratingProperty;
    }

    public void setRatingProperty(int rating) {
        ratingProperty = new SimpleIntegerProperty(rating);
    }

    public StringProperty getNotesProperty() {
        return notesProperty;
    }

    public void setNotesProperty(String notes) {
        notesProperty = new SimpleStringProperty(notes);
    }

    public Meal toMeal() {
        return this.meal;
    }
}
