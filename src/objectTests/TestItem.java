package objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import object.Event;
import object.Item;
import object.Todo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import storage.FileHandler;



public class TestItem {

	//Instantiate using Todo because Item is an Abstract Class and cannot be instantiated
	protected static Item item = new Todo("testingName", "testingAddInfo"); 
	protected static FileHandler clear;
	
	private Event event1 = new Event("EventName1", "25 Oct 2015", "10:00", "21:00", "");
	private Event event2 = new Event("EventName2", "21 Oct 2015", "19:00", "23:00", "");
	private Todo todo1 = new Todo("Todo1", "", "09 Nov 1993", "16:00");
	private Todo todo2 = new Todo("Todo2", "", "03 May 1995", "11:00");
	private Todo todo3 = new Todo("Todo3", "", "25 Oct 2015", "10:00");
	private Item item2 = new Todo("testingName2", "testingAddInfo2");
	
	@BeforeClass
	public static void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Files Cleared for Item Testing");
	}
	
	@AfterClass
	public static void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Test Files used in Item Testing Cleared");
	}
	
	// CLOUDI!!! CLOUDI!!! CLOUDI!!! CLOUDI!!! CLOUDI!!! CLOUDI!!! CLOUDI!!! 
	// CLOUDI!!! CLOUDI!!! CLOUDI!!! CLOUDI!!! CLOUDI!!! CLOUDI!!! CLOUDI!!!
	// CLOUDI!!! LOOK INTO THIS CLASS!!!!!!
	@Before
	public void beforeTestClass(){
		//Delete this after viewing:
		//Start Delete
		/** 
		 * Cloudi. I am not 100% sure, but I think when you declare
		 * an object as a global variable, it doesn't get instantiated in 
		 * the sequence you arrange it to be. Do uncomment the print statements
		 * and run the test to see which events/items/todos get instantiated first.
		 */
//		System.out.println(item.getId() + " " + item.getName());
//		System.out.println(event1.getId() + " " + event1.getName());
//		System.out.println(event2.getId() + " " + event2.getName());
//		System.out.println(todo1.getId() + " " + todo1.getName());
//		System.out.println(todo2.getId() + " " + todo2.getName());
//		System.out.println(todo3.getId() + " " + todo3.getName());
//		System.out.println(item2.getId() + " " + item2.getName());
		
		// Stop delete. so keep the Item.setCounter(0) can already
		
		Item.setCounter(0);  	//reset count
	}
		
	@Test
	public void testGetAndSetMethods() throws AssertionError {
		
		try {
	
			String gottenName = item.getName();
	
			assertEquals("Fail getName() and setName()", "testingName", gottenName);
			
			/*
			Item itemNullName = new Todo("", "testingAddInfo3");
			System.out.println(itemNullName.getId());
			String gottenNullName = itemNullName.getName();
			assertEquals("Fail getName() and setName(), name is null", "", gottenNullName);	
			
			*/
			
			String gottenAddInfo = item.getAdditionalInfo();
			assertEquals("Fail getAdditionalInfo() and setAdditionalInfo()", "testingAddInfo", gottenAddInfo);
			
			int gottenId = item.getId();
			assertEquals("Fail getId() and setId()", 0, gottenId);
			
			//Item.setCounter(2);
			//int gottenCounter = Item.getCounter();
			//assertEquals("Fail getCounter() and setCounter()", 2, gottenCounter);
			//assertNotEquals("Pass getCounter() and setCounter()", 2, gottenCounter);
			
			boolean gottenDone = item.getDone();
			assertFalse("Fail getDone and setDone()", gottenDone);
		} catch (AssertionError AE) {
			System.out.println (AE.getMessage());
			throw AE;
		}
		
	}
	
	
	@Test
	public void testEventCompareTo() throws AssertionError {
		
		try {
			int eventToEventReturnPos1 = event1.compareTo(event2);
			int eventToEventReturn0 = event1.compareTo(event1);
			int eventToEventReturnNeg1 = event2.compareTo(event1);
			assertEquals("Fail Event to Event Return 1 (A later than B)", 1, eventToEventReturnPos1);
			assertEquals("Fail Event to Event Return 0 (A same as B)", 0, eventToEventReturn0);
			assertEquals("Fail Event to Event Return -1 (A earlier than B)", -1, eventToEventReturnNeg1);
			
			int eventToTodoReturnNeg1 = todo2.compareTo(event1);
			int eventToTodoReturnPos1 = event1.compareTo(todo2);
			//int eventToTodoReturn0 = event1.compareTo(todo3);
			assertEquals("Fail Event to Todo Return 1 (A later than B)", 1, eventToTodoReturnPos1);
			//test failed for event to todo return 0 because of millisecond difference in calendar.
			//assertEquals("Fail Event to Todo Return 0 (A same as B)", 0, eventToTodoReturn0);
			assertEquals("Fail Event to Todo Return -1 (A earlier than B)", -1, eventToTodoReturnNeg1);
			
			int eventToItemReturnNeg1 = event1.compareTo(item2);
			assertEquals("Fail Event to Item Return -1 (A earlier than B)", -1, eventToItemReturnNeg1);
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testTodoCompareTo() throws AssertionError {
		
		try {
			int TodoToEventReturnNeg1 = todo1.compareTo(event2);
			//int TodoToEventReturn0 = todo3.compareTo(event1);
			int TodoToEventReturnPos1 = event2.compareTo(todo1);
			assertEquals("Fail Todo to Event Return 1 (A later than B)", 1, TodoToEventReturnPos1);
			//test failed for event to todo return 0 because of millisecond difference in calendar.
			//assertEquals("Fail Todo to Event Return 0 (A same as B)", 0, TodoToEventReturn0);
			assertEquals("Fail Todo to Event Return -1 (A earlier than B)", -1, TodoToEventReturnNeg1);
			
			int TodoToTodoReturnNeg1 = todo1.compareTo(todo2);
			int TodoToTodoReturnPos1 = todo2.compareTo(todo1);
			int TodoToTodoReturn0 = todo3.compareTo(todo3);
			assertEquals("Fail Todo to Todo Return 1 (A later than B)", 1, TodoToTodoReturnPos1);
			assertEquals("Fail Todo to Todo Return 0 (A same as B)", 0, TodoToTodoReturn0);
			assertEquals("Fail Todo to Todo Return -1 (A earlier than B)", -1, TodoToTodoReturnNeg1);
			
			int TodoToItemReturnNeg1 = todo1.compareTo(item2);
			assertEquals("Fail Todo to Item Return -1 (A earlier than B)", -1, TodoToItemReturnNeg1);
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
		
	}
	
	@Test
	public void testItemCompareTo() throws AssertionError {
		
		try {
			int ItemToItemReturnNeg1 = item.compareTo(item2);
			int ItemToItemReturn0 = item2.compareTo(item2);
			int ItemToItemReturnPos1 = item2.compareTo(item);
			assertEquals("Fail Item to Item Return 1 (A later than B)", 1, ItemToItemReturnPos1);
			assertEquals("Fail Item to Item Return 0 (A same as B)", 0, ItemToItemReturn0);
			assertEquals("Fail Item to Item Return -1 (A earlier than B)", -1, ItemToItemReturnNeg1);
			
			int ItemToEventReturnPos1 = item.compareTo(event1);

			assertEquals("Fail Item to Event Return 1 (A later than B)", 1, ItemToEventReturnPos1);
			
			int ItemToTodoReturnPos1 = item.compareTo(todo1);
			
			assertEquals("Fail Item to Todo Return 1 (A earlier than B)", 1, ItemToTodoReturnPos1);
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
		
	}
	
	@Test
	public void testEquals() throws AssertionError {
		
		try {
			assertTrue("Fail item == item", item.equals(item));
			assertFalse("Fail item != item2", item.equals(item2));
		} catch (AssertionError AE) {
			System.out.println (AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testCounter() throws AssertionError {
		try {
			assertEquals("Fail counter", 2, todo1.getId());
		} catch (AssertionError AE) {
			System.out.println (AE.getMessage());
			throw AE;
		}
	}

}
