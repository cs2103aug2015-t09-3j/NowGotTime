package objectTests;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import object.Todo;
import object.Item;
import storage.FileHandler;



public class TestItem {

	//Instantiate using Todo because Item is an Abstract Class and cannot be instantiated
	protected static Item item = new Todo("testingName", "testingAddInfo"); 
	protected static FileHandler clear;
	
	@BeforeClass
	public static void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Files Cleared for Testing");
	}
	
	@AfterClass
	public static void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Test Files Cleared");
	}
	
	@Test
	public void testGetAndSetMethods() throws AssertionError {
		
		try {
	
			String gottenName = item.getName();
	
			assertEquals("Fail getName() and setName()", "testingName", gottenName);
			
			String gottenAddInfo = item.getAdditionalInfo();
			assertEquals("Fail getAdditionalInfo() and setAdditionalInfo()", "testingAddInfo", gottenAddInfo);
			
			int gottenId = item.getId();
			assertEquals("Fail getId() and setId()", 0, gottenId);
			
			Item.setCounter(2);
			int gottenCounter = Item.getCounter();
			assertEquals("Fail getCounter() and setCounter()", 2, gottenCounter);
			//assertNotEquals("Pass getCounter() and setCounter()", 2, gottenCounter);
			
			boolean gottenDone = item.getDone();
			assertFalse("Fail getDone and setDone()", gottenDone);
		} catch (AssertionError AE) {
			System.out.println (AE.getMessage());
			throw AE;
		}
		
	}
	
	// CompareTo Awaiting Test
	
	@Test
	public void testEquals() throws AssertionError {
		
		try {
			assertTrue("Fail item == item", item.equals(item));
			Item item2 = new Todo("testingName2", "testingAddInfo2");
			assertFalse("Fail item != item2", item.equals(item2));
		} catch (AssertionError AE) {
			System.out.println (AE.getMessage());
			throw AE;
		}
	}

}
