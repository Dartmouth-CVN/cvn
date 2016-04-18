package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DatabaseHandlerTest2 {
	private static DatabaseHandler dbHandler2;
	@Before
	public void setupDatabase2(){
		dbHandler2 = DatabaseHandler.getUniqueInstance();
		dbHandler2.insertDummyLogin();
		dbHandler2.updateDummyLogin("admin","pass2");
	}
	
	@Test
	public void testUpdateUser() {
		 assertEquals("pass2",dbHandler2.getLoginPass("admin"));
	}

}