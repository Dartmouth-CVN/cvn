package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class DatabaseHandlerTest {
	private static DatabaseHandler dbHandler;

	@Before
	public void setupDatabase(){
		dbHandler = DatabaseHandler.getUniqueInstance();
		dbHandler.insertDummyLogin();
	}
	
	@Test
	public void testInsertGetUser() {
		 assertEquals("pass",dbHandler.getLoginPass("admin"));
	}
	
	
}