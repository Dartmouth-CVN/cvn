package model;

import java.io.Serializable;

public class Meal{
    long mealId;
    String food;
    int calories;
    int rating;
    String notes;


    public Meal() {
        this(0, "Enter food", 0, 0, "Enter notes...");
    }

    public Meal(long mealId, String food, int calories, int rating, String notes) {
        setMealId(mealId);
        setFood(food);
        setCalories(calories);
        setRating(rating);
        setNotes(notes);
    }


    public Meal(long mealId, String food, int calories, String notes) {
        setMealId(mealId);
        setFood(food);
        setCalories(calories);
        setNotes(notes);
    }

    public Meal(String food, int calories, int rating, String notes) {
        this(0, food, calories, rating, notes);
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
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

    public String toString(){
        return String.format("%s, %d, %d, %s", food, calories, rating, notes);
    }


    public static Meal fromSVString(String line){
        String[] values = line.split(",");
        String food = "";
        int calories = 0;
        int rating = 0;
        String notes = "";
        if(values.length > 0) {
            food = values[0];
            calories = Integer.parseInt(values[1]);
            rating = Integer.parseInt(values[2]);
            notes = values[3];
        }
        return new Meal(food, calories, rating, notes);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof  Meal){
            Meal other = (Meal) o;
            return food.equals(other.getFood()) ;
        }
        return false;
    }
}

