package model;

/**
 * Created by mrampiah on 4/26/16.
 */
public interface IParsable {

    public String toXMLString();

    public String toSVString();

    public String toCSVString();

    public String toTSVString();

    public String toHTMLString();

    public AbsUser fromXMLString();

    public AbsUser fromCSVString();

    public AbsUser fromTSVString();

    public AbsUser fromSVString();

}
