package model;

/**
 * Created by mrampiah on 4/26/16.
 */
public interface IExportable extends IParsable {

    public String toXMLString();

    public String toSVString(String delimiter);

    public String toCSVString();

    public String toTSVString();

    public String toHTMLString();
}
