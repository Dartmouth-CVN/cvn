package model;

import java.time.LocalDate;

public class HealthInfo {
	private String date;
	private double height;
	private double weight;
	private double bmi;
	private double fat;
	private double caloriesBurned;
	private double steps;
	private double distance;
	private double floors;
	private double minSedentary;
	private double minLightlyActive;
	private double minFairlyActive;
	private double minVeryActive;
	private double activityCalories;
	private double minAsleep;
	private double minAwake;
	private double numAwakenings;
	private double timeInBed;
	
	
	public HealthInfo () {
		this.date = "";
		this.height = 0;
		this.weight = 0;
		this.bmi = 0;
		this.fat = 0;
		this.caloriesBurned = 0;
		this.steps = 0;
		this.distance = 0;
		this.floors = 0;
		this.minSedentary = 0;
		this.minLightlyActive = 0;
		this.minFairlyActive = 0;
		this.minVeryActive = 0;
		this.activityCalories = 0;
		this.minAsleep = 0;
		this.minAwake = 0;
		this.numAwakenings = 0;
		this.timeInBed = 0;
	}
	
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getBmi() {
		return bmi;
	}
	public void setBmi(double bmi) {
		this.bmi = bmi;
	}
	public double getFat() {
		return fat;
	}
	public void setFat(double fat) {
		this.fat = fat;
	}
	public double getCaloriesBurned() {
		return caloriesBurned;
	}
	public void setCaloriesBurned(double caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}
	public double getSteps() {
		return steps;
	}
	public void setSteps(double steps) {
		this.steps = steps;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getFloors() {
		return floors;
	}
	public void setFloors(double floors) {
		this.floors = floors;
	}
	public double getMinSedentary() {
		return minSedentary;
	}
	public void setMinSedentary(double minSedentary) {
		this.minSedentary = minSedentary;
	}
	public double getMinLightlyActive() {
		return minLightlyActive;
	}
	public void setMinLightlyActive(double minLightlyActive) {
		this.minLightlyActive = minLightlyActive;
	}
	public double getMinFairlyActive() {
		return minFairlyActive;
	}
	public void setMinFairlyActive(double minFairlyActive) {
		this.minFairlyActive = minFairlyActive;
	}
	public double getMinVeryACtive() {
		return minVeryActive;
	}
	public void setMinVeryActive(double minVeryActive) {
		this.minVeryActive = minVeryActive;
	}
	public double getActivityCalories() {
		return activityCalories;
	}
	public void setActivityCalories(double activityCalories) {
		this.activityCalories = activityCalories;
	}
	public double getMinAsleep() {
		return minAsleep;
	}
	public void setMinAsleep(double minAsleep) {
		this.minAsleep = minAsleep;
	}
	public double getMinAwake() {
		return minAwake;
	}
	public void setMinAwake(double minAwake) {
		this.minAwake = minAwake;
	}
	public double getNumAwakenings() {
		return numAwakenings;
	}
	public void setNumAwakenings(double numAwakenings) {
		this.numAwakenings = numAwakenings;
	}
	public double getTimeInBed() {
		return timeInBed;
	}
	public void setTimeInBed(double timeInBed) {
		this.timeInBed = timeInBed;
	}
}
