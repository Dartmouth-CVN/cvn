package model;

import java.util.Date;

/**
 * Created by mrampiah on 4/23/16.
 */
public class AbsRelation extends AbsUser{
    String relationship;

    public AbsRelation(){}

    public AbsRelation (String userId, String firstName, String lastName, String username, String password,
                        Date birthday, String room, String picture, Contact contactInfo, String relationship){
        super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo);
        setRelationship(relationship);
    }

    public void setRelationship(String relationship){
        this.relationship = relationship;
    }

    public String getRelationship(){
        return relationship;
    }
}
