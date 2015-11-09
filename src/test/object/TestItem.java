//@@author A0130445R
package test.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import object.Event;
import object.Item;
import object.Todo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import storage.FileHandler;



public class TestItem {

	private static final String MESSAGE_TEAR_DOWN = "Test Files used in Item Testing Cleared";
	private static final String MESSAGE_SET_UP = "Files Cleared for Item Testing";
	//Instantiate using Todo because Item is an Abstract Class and cannot be instantiated
	protected static Item item; 
	protected static FileHandler clear;
	
	private static Event event1;
	private static Event event2;
	private static Todo todo1;
	private static Todo todo2;
	private static Todo todo3;
	private static Item item2;
	
	@BeforeClass
	public static void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println(MESSAGE_SET_UP);
	}
	
	@AfterClass
	public static void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		Item.setCounter(0);  
		System.out.println(MESSAGE_TEAR_DOWN);
	}
	
	@BeforeClass
	public static void beforeTestClass(){
		
		Item.setCounter(0);  	//reset count
		item = new Todo("testingName", "testingAddInfo");
		event1 = new Event("EventName1", "25 Oct 2015", "10:00", "21:00", "");
		event2 = new Event("EventName2", "21 Oct 2015", "19:00", "23:00", "");
		todo1 = new Todo("Todo1", "", "09 Nov 1993", "16:00");
		todo2 = new Todo("Todo2", "", "03 May 1995", "11:00");
		todo3 = new Todo("Todo3", "", "25 Oct 2015", "10:00");
		item2 = new Todo("testingName2", "testingAddInfo2");
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

			assertEquals("Fail Event to Todo Return 1 (A later than B)", 1, eventToTodoReturnPos1);

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
			int TodoToEventReturnPos1 = event2.compareTo(todo1);
			assertEquals("Fail Todo to Event Return 1 (A later than B)", 1, TodoToEventReturnPos1);
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
			assertEquals("Fail counter", 3, todo1.getId());
		} catch (AssertionError AE) {
			System.out.println (AE.getMessage());
			throw AE;
		}
	}

}
