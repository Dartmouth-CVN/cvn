package model;
import java.util.LinkedList;


public class Meal {
	private String name;
	private int calories;
	private boolean liked;
	private String specialnotes;
	private int mealID;
	public Meal(String name, int calories, LinkedList<String> ingredients, boolean liked, String specialnotes){
		this.name = name;
		this.calories = calories;
		this.liked = liked;
		this.specialnotes = specialnotes;
	}

	public String getName(){
		return this.name;
	}

	public int getCalories(){
		return this.calories;
	}

	public boolean isLiked(){
		return this.liked;
	}

	public String getSpecialNotes(){
		return this.specialnotes;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setCalories(int calories){
		this.calories = calories;
	}

	public void setLiked(boolean liked){
		this.liked = liked;
	}

	public void setSpecialNotes(String specialnotes){
		this.specialnotes = specialnotes;
	}
	
	public void setMealID(int id){
		this.mealID = id;
	}
	
	public int getMealID(){
		return this.mealID;
	}
}

