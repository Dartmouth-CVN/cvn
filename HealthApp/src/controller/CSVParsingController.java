package controller;

public class CSVParsingController extends SVParsingController{

	@Override
	String getDelimiter() {
		return ",";
	}

}
