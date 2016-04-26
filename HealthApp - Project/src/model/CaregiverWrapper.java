package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by mrampiah on 4/24/16.
 */
public class CaregiverWrapper extends AbsRelationWrapper {

    BooleanProperty isFamilyProperty;

    public CaregiverWrapper(Caregiver caregiver) {
        super(caregiver);
        setIsFamilyProperty(caregiver.getIsFamily());
    }

    public BooleanProperty getIsFamilyProperty() {
        return isFamilyProperty;
    }

    public void setIsFamilyProperty(boolean isFamily) {
        isFamilyProperty = new SimpleBooleanProperty(isFamily);
    }


}
