package model;

/**
 * Created by mrampiah on 4/26/16.
 */
public interface IParsable {

    public AbsUser fromXMLString();

    public AbsUser fromCSVString();

    public AbsUser fromTSVString();

    public AbsUser fromSVString(String delimiter);

}
