package model;

import java.util.LinkedList;
import java.util.List;

public class HealthProfile{
    long healthProfileId;
    List<HealthAttribute<?>> healthInfo;
    List<String> allergies;
    List<String> dietaryRestrictions;
    String userId;

    //sorted HealthAttributes
    private List weightList = new LinkedList<>();
    private List bmiList = new LinkedList<>();
    private List fatList = new LinkedList<>();
    private List caloriesBurnedList = new LinkedList<>();
    private List stepsList = new LinkedList<>();
    private List distanceList = new LinkedList<>();
    private List floorsList = new LinkedList<>();
    private List minSedentaryList = new LinkedList<>();
    private List minLightlyActiveList = new LinkedList<>();
    private List minFairlyActiveList = new LinkedList<>();
    private List minVeryActiveList = new LinkedList<>();
    private List activityCaloriesList = new LinkedList<>();
    private List minAsleepList = new LinkedList<>();
    private List minAwakeList = new LinkedList<>();
    private List numbAwakeningsList = new LinkedList<>();
    private List timeInBedList = new LinkedList<>();

    public HealthProfile() {
        this(0, "", new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
    }

    public HealthProfile(long id, String userId, List<HealthAttribute<?>> healthInfo, List<String> allergies, List<String> dietaryRestrictions) {
        this.healthProfileId = id;
        this.userId = userId;
        this.healthInfo = healthInfo;
        this.allergies = allergies;
        this.dietaryRestrictions = dietaryRestrictions;

    }

    public void addNewHealthInfo(HealthAttribute<?> info) {
        healthInfo.add(info);
    }

    public long getHealthProfileId() {
        return healthProfileId;
    }

    public void setHealthProfileId(long healthProfileId) {
        this.healthProfileId = healthProfileId;
    }

    public List<HealthAttribute<?>> getHealthInfo() {
        return healthInfo;
    }

    public void setHealthInfo(List<HealthAttribute<?>> healthInfo) {
        this.healthInfo = healthInfo;
    }

    public void addHealthInfoList(List<HealthAttribute<?>> info) {
        healthInfo.addAll(info);
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getHealthInfoAsString(){
        String info = "";
        for(HealthAttribute<?> attribute : healthInfo)
            info += attribute.toString() + ";";
        return info;
    }

    public String toString() {
        return "";
    }

    @Override
    public int hashCode() {
        return healthInfo.hashCode() * allergies.hashCode() * dietaryRestrictions.hashCode();
    }

    public void separateCategories(List<HealthAttribute<?>> healthInfo){
        for (HealthAttribute i: healthInfo){
            addToCategory(i);
        }
        return;
    }

    public void addToCategory(HealthAttribute info){
        switch(info.getName()){
            case "Weight" : weightList.add(info); return;
            case "BMI" : bmiList.add(info); return;
            case "Fat" : fatList.add(info); return;
            case "Calories Burned" : caloriesBurnedList.add(info); return;
            case "Steps" : stepsList.add(info); return;
            case "Distance" : distanceList.add(info); return;
            case "Floors" : floorsList.add(info); return;
            case "Minutes Sedentary" : minSedentaryList.add(info); return;
            case "Minutes Lightly Active" : minLightlyActiveList.add(info); return;
            case "Minutes Fairly Active" : minFairlyActiveList.add(info); return;
            case "Minutes Very Active" : minVeryActiveList.add(info); return;
            case "Activity Calories" : activityCaloriesList.add(info); return;
            case "Minutes Asleep" : minAsleepList.add(info); return;
            case "Minutes Awake" : minAwakeList.add(info); return;
            case "Number of Awakenings" : numbAwakeningsList.add(info); return;
            case "Time in Bed" : timeInBedList.add(info); return;
        }
    }


}
