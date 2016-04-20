package controller;

public class TSVParsingController extends SVParsingController{

	@Override
	String getDelimiter() {
		return "\t";
	}

}
