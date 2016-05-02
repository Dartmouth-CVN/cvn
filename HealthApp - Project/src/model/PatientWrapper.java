package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mrampiah on 4/24/16.
 */
public class PatientWrapper extends AbsUserWrapper {
    List<PetWrapper> petWrapperList;
    List<MealWrapper> mealWrapperList;
    List<AbsRelationWrapper> absRelationWrapperList;
    List<MedicalStaffWrapper> medicalStaffWrapperList;
    BooleanProperty selectedProperty;

    public PatientWrapper(Patient p) {
        super(p);
        try {
            setPetWrapperList(p.getPets());
            setMealWrapperList(p.getMeals());
            setAbsRelationWrapperList(p.getRelations());
            setSelectedProperty(false);
//        setMedicalStaffWrapperList(p.getAssignedStaff());
        }catch(NullPointerException e){

        }
    }

    public List<PetWrapper> getPetWrapperList() {
        return petWrapperList;
    }

    public void setPetWrapperList(List<Pet> petList) {
        petWrapperList = new LinkedList<PetWrapper>();
        for (Pet p : petList)
            petWrapperList.add(new PetWrapper(p));
    }

    public BooleanProperty getSelectedProperty() {
        return selectedProperty;
    }

    public void setSelectedProperty(boolean selected) {
            selectedProperty = new SimpleBooleanProperty(selected);
    }

    public List<MealWrapper> getMealWrapperList() {
        return mealWrapperList;
    }

    public void setMealWrapperList(List<Meal> mealList) {
        mealWrapperList = new LinkedList<MealWrapper>();
        for (Meal m : mealList)
            mealWrapperList.add(new MealWrapper(m));
    }

    public List<AbsRelationWrapper> getAbsRelationWrapperList() {
        return absRelationWrapperList;
    }

    public void setAbsRelationWrapperList(List<AbsRelation> absRelationList) {
        absRelationWrapperList = new LinkedList<AbsRelationWrapper>();
        for (AbsRelation relation : absRelationList)
            absRelationWrapperList.add(new AbsRelationWrapper(relation));
    }

    public List<MedicalStaffWrapper> getMedicalStaffWrapperList() {
        return medicalStaffWrapperList;
    }

    public void setMedicalStaffWrapperList(List<MedicalStaff> medicalStaffList) {
        medicalStaffWrapperList = new LinkedList<MedicalStaffWrapper>();
        for (MedicalStaff m : medicalStaffList)
            medicalStaffWrapperList.add(new MedicalStaffWrapper(m));
    }
}
