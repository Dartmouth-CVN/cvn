package model;

import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class AbsRelation extends AbsUser{
    String relationship;
    String userId;

    public AbsRelation(){}

    public AbsRelation (java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password,
                        Date birthday, java.lang.String room, String picture, Contact contactInfo, java.lang.String relationship){
        super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo);
        setRelationship(relationship);
    }

    public void setRelationship(java.lang.String relationship){
        this.relationship = relationship;
    }

    public java.lang.String getRelationship(){
        return relationship;
    }
}
