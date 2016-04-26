package utils;

import model.AbsUser;

import java.io.File;

public interface ParsingUtils {

	public File getFile(String name);

	public String getFileContents(File file);

	public AbsUser getUserFromContents(String contents);
}
