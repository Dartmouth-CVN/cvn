package utils;

import model.AbsUser;

import java.io.File;
import java.util.List;

public interface ParsingUtils {

	public File getFile(String name);

	public List<String> getFileContents(File file);

	public AbsUser getUserFromContents(String contents);
}
