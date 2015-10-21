package serviceHandlertest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import helper.CommonHelper;
import object.Event;
import object.Item;
import object.Todo;
import service.ServiceHandler;
import storage.FileHandler;

public class serviceHandlerTest{
	 protected static ServiceHandler service;
	 private static final String DATE = "20 oct 2015";
	 private static final String EVENTNAME = "Event1";
	 private static final String TODONAME = "Normal todo1";
	 private static final String FLOATINGTODONAME = "Floating todo1";
	 private static final String NAMENOTFOUND = "random";
	 private static final String SEARCHINPUT = "1";
	 private static final String SEARCHINPUT1 = "event";
	 private static final String SEARCHINPUT2 = "normal";
	 private static final String NEWNAME = "its over 9000";
	 private static final String INCORRECTTIME = "18:00";
	 private static final String CORRECTTIME = "16:30";
	 private static final String OLDSTARTTIME = "14:00";
	 private static final String OLDENDTIME = "13:00";
	 
	 private Todo floatingTodo1 = new Todo("Floating todo1");
	 private Todo floatingTodo2 = new Todo("Floating todo2");
	 private Todo floatingTodo3 = new Todo("Floating todo3");
	 
	 private Todo todo1 = new Todo("Normal todo1", "", "20 oct 2015");
	 private Todo todo2 = new Todo("Normal todo2", "", "20 oct 2015");
	 private Todo todo3 = new Todo("Normal todo1 with time", "", "20 oct 2015", "10:00");

	 private Event event1 = new Event("Event1", "20 oct 2015 12:00", "20 oct 2015 13:00");
	 private Event event2 = new Event("Event2", "20 oct 2015 14:00", "20 oct 2015 17:00");
	 private Event event3 = new Event("Event3", "20 oct 2015 20:00", "20 oct 2014 23:00"); //end date before start date
	 
	 @Before
	public void setUp() throws Exception {
		FileHandler clear = new FileHandler();
		clear.clearAll();
		service = new ServiceHandler();		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		FileHandler clear = new FileHandler();
		clear.clearAll();
		System.out.println("All files deleted");
	}

	@Test
	public void testCreateItemSuccess() {
		try {
			assertEquals("Create event1 success",true,service.createItem(event1));
			assertEquals("Create event2 success",true,service.createItem(event2));
			assertEquals("Create todo1 success",true,service.createItem(todo1));
			assertEquals("Create todo2 success",true,service.createItem(todo2));
			assertEquals("Create floatingTodo1 success",true,service.createItem(floatingTodo1));
			assertEquals("Create floatingTodo2 success",true,service.createItem(floatingTodo2));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}

	}
	
	@Test
	public void testCreateItemFail() {
		try {
			service.createItem(event3);
		} catch (Exception e) {
			assertEquals("Create event3 fail", String.format(CommonHelper.ERROR_START_AFTER_END), e.getMessage());
		}
	}
	
	@Test
	public void testViewEventByDate() {
		try {
			service.createItem(event1);
			service.createItem(event2);
			ArrayList<Event> expectedListEvent = new ArrayList<Event>();
			expectedListEvent.add(event1);
			expectedListEvent.add(event2);
			assertEquals("View event by date", expectedListEvent, service.viewEventByDate(DATE) );
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}
	
	@Test
	public void testViewTaskByDate() {
		try {
			service.createItem(todo2);
			service.createItem(todo3);
			ArrayList<Todo> expectedListTodo = new ArrayList<Todo>();
			expectedListTodo.add(todo2);
			expectedListTodo.add(todo3);
			assertEquals("View task by date", expectedListTodo, service.viewTaskByDate(DATE) );
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}
	
	@Test
	public void testViewTaskNoDate() {
		try {
			service.createItem(floatingTodo2);
			service.createItem(floatingTodo1);
			ArrayList<Todo> expectedListTodo = new ArrayList<Todo>();
			expectedListTodo.add(floatingTodo2);
			expectedListTodo.add(floatingTodo1);
			assertEquals("View task by date", expectedListTodo, service.viewTaskNoDate());
		} catch (Exception e) {
			fail("exception should not be thrown");
		}
	}

	@Test
	public void testDeleteItem() {
		try {
			service.createItem(floatingTodo2);
			service.createItem(floatingTodo1);
			assertEquals("DeleteFloatingTodo2", true, service.deleteItem(floatingTodo2));
			assertEquals("DeleteFloatingTodo3", false, service.deleteItem(floatingTodo3));
			assertEquals("DeleteFloatingTodo2", false, service.deleteItem(floatingTodo2));
			
			service.createItem(event1);
			service.createItem(event2);
			assertEquals("DeleteEvent1", true, service.deleteItem(event1));
			assertEquals("DeleteEvent2", true, service.deleteItem(event2));
			
			service.createItem(todo1);
			assertEquals("DeleteTodo1", true, service.deleteItem(todo1));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}
	}

	@Test
	public void testViewSpecficEvent() {
		try {
			service.createItem(event1);
			assertEquals("View specfic event success", event1, service.viewSpecificEvent(EVENTNAME));
			assertEquals("View specific event fail", null, service.viewSpecificEvent(NAMENOTFOUND));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}
	
	@Test
	public void testViewSpecficTask() {
		try {
			service.createItem(floatingTodo1);
			assertEquals("View specfic floating task success", floatingTodo1, service.viewSpecificTask(FLOATINGTODONAME));
			
			service.createItem(todo1);
			assertEquals("View specfic floating task success", todo1, service.viewSpecificTask(TODONAME));
			
			assertEquals("View specific task fail", null, service.viewSpecificEvent(NAMENOTFOUND));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}
	
	@Test
	public void testSearch() {
		try {
			service.createItem(floatingTodo1);
			service.createItem(todo1);
			service.createItem(event2);
			service.createItem(event1);
			service.createItem(todo3);
			ArrayList<Item> expectedListItem = new ArrayList<Item>();
			expectedListItem.add(todo1);
			expectedListItem.add(todo3);
			expectedListItem.add(event1);
			expectedListItem.add(floatingTodo1);
			assertEquals("Search success", expectedListItem, service.search(SEARCHINPUT));
			
			ArrayList<Item> expectedListItem1 = new ArrayList<Item>();
			expectedListItem1.add(event1);
			expectedListItem1.add(event2);
			assertEquals("Search success", expectedListItem1, service.search(SEARCHINPUT1));
			
			ArrayList<Item> expectedListItem2 = new ArrayList<Item>();
			assertEquals("Search empty", expectedListItem2, service.search(NAMENOTFOUND));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}
	
	@Test
	public void testDeleteItemByIndex() {
		try {
			service.createItem(floatingTodo1);
			service.createItem(todo1);
			service.createItem(event2);
			service.createItem(event1);

			service.search(SEARCHINPUT); //only returns 3 result despite 4 items added
			assertEquals("delete item by index fail", false, service.deleteItemByIndex(3));
			assertEquals("delete item by index success", true, service.deleteItemByIndex(2));
			assertEquals("delete item by index success", true, service.deleteItemByIndex(1));
			assertEquals("delete item by index success", true, service.deleteItemByIndex(0));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}	
	
	@Test
	public void testViewItemByIndex() {
		try {
			service.createItem(floatingTodo1);
			service.createItem(todo1);
			service.createItem(event2);
			service.createItem(event1);
			service.createItem(todo3);

			service.search(SEARCHINPUT); //only returns 4 result despite 5 items added
			assertEquals("view item by index fail", null, service.viewItemByIndex(4));			
			assertEquals("view item by index success", floatingTodo1, service.viewItemByIndex(3));			
			assertEquals("view item by index success", event1, service.viewItemByIndex(2));
			assertEquals("view item by index success", todo3, service.viewItemByIndex(1));
			assertEquals("view item by index success", todo1, service.viewItemByIndex(0));
			
			service.search(SEARCHINPUT2);
			assertEquals("view item by index success", todo1, service.viewItemByIndex(0));
			assertEquals("view item by index success", todo3, service.viewItemByIndex(1));
			
			
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}
	
	@Test
	public void testEditItem() {
		try {
			service.createItem(floatingTodo1);
			service.createItem(todo1);
			service.createItem(event2);
			service.createItem(event1);
			service.createItem(todo3);
			
			assertEquals("edit item success", EVENTNAME, service.editItem(event1, CommonHelper.FIELD_NAME, NEWNAME));
			assertEquals("edit item success", OLDSTARTTIME ,service.editItem(event2, CommonHelper.FIELD_START, CORRECTTIME));
			assertEquals("edit item success", OLDENDTIME, service.editItem(event1, CommonHelper.FIELD_END, CORRECTTIME));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}
	
	@Test
	public void testEditItemFail() {
		try {
			service.createItem(event2);
			
			service.editItem(event2, CommonHelper.FIELD_START, INCORRECTTIME);
		} catch (Exception e) {
			assertEquals("edit event2 fail", String.format(CommonHelper.ERROR_START_AFTER_END), e.getMessage());
		}	
	}
	
}
