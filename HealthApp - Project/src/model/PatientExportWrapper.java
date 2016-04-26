package model;

/**
 * Created by mrampiah on 4/26/16.
 */
public class PatientExportWrapper implements IParsable {
    boolean userId;
    boolean firstName;
    boolean lastName;
    boolean username;



    public PatientExportWrapper(boolean userId, boolean firstName, boolean lastName, boolean username, boolean password, boolean birthday,
                                boolean room, boolean picture, boolean contactInfo, boolean pets, boolean meals, boolean relations,
                                boolean assignedStaff, boolean healthProfile){

    }
    @Override
    public String toXMLString() {
        return null;
    }

    @Override
    public String toSVString(String delimiter) {
        return null;
    }

    @Override
    public String toCSVString() {
        return null;
    }

    @Override
    public String toTSVString() {
        return null;
    }

    @Override
    public String toHTMLString() {
        return null;
    }

    @Override
    public AbsUser fromXMLString() {
        return null;
    }

    @Override
    public AbsUser fromCSVString() {
        return null;
    }

    @Override
    public AbsUser fromTSVString() {
        return null;
    }

    @Override
    public AbsUser fromSVString(String delimiter) {
        return null;
    }
}
