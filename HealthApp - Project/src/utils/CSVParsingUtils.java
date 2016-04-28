package utils;

import model.AbsUser;

import java.io.File;
import java.util.List;

/**
 * Created by Sean on 4/27/2016.
 */
public class CSVParsingUtils extends SVParsingUtils {

    public CSVParsingUtils() {
        super(",");
    }

    @Override
    public AbsUser getUserFromContents(String contents) {
        return null;
    }
}
