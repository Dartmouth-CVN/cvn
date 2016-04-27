package utils;

import model.AbsUser;

/**
 * Created by Sean on 4/27/2016.
 */
public class TSVParsingUtils extends SVParsingUtils {

    public TSVParsingUtils() {
        super("\t");
    }

    @Override
    public AbsUser getUserFromContents(String contents) {
        return null;
    }
}
