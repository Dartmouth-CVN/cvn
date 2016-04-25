package utils;

public class ObjectNotFoundException extends Exception {
	private static final long serialVersionUID = 8712841334219246248L;
	String objectName;
	
	public ObjectNotFoundException(String objectName){
		this.objectName = objectName;
	}
	
	@Override
	public String getMessage(){
		return objectName + " was not found";
	}
}
