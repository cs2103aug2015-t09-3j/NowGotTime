//@@author A0122432X

package test.service;

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
	 private static final String DATE = "23 Oct 2015";
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
	 private static final String OLDSTARTTIMEEVENT = "23 Oct 2015 14:00";
	 private static final String OLDENDTIMEEVENT = "23 Oct 2015 13:00";
	 private static final String OLDDUETIMETODO = "23 Oct 2015 10:00";
	 private static final String TODAYDATE = "23 Oct 2015";
	 
	 private Todo floatingTodo1 = new Todo("Floating todo1");
	 private Todo floatingTodo2 = new Todo("Floating todo2");
	 private Todo floatingTodo3 = new Todo("Floating todo3");
	 
	 //todo must have end time
	 private Todo todo1 = new Todo("Normal todo1", "", "23 oct 2015", "16:00");
	 private Todo todo2 = new Todo("Normal todo2", "", "23 oct 2015", "12:00");
	 private Todo todo3 = new Todo("Normal todo3 1x good 1", "", "23 oct 2015", "10:00");
	 private Todo todo4 = new Todo("Normal todo4", "", "24 oct 2015", "12:00");
	 private Todo todo5 = new Todo("Normal todo5", "", "25 oct 2015", "12:00");
	 
	 private Event event1 = new Event("Event1", "23 oct 2015 12:00", "23 oct 2015 13:00");
	 private Event event2 = new Event("Event2", "23 oct 2015 14:00", "23 oct 2015 17:00");
	 private Event event3 = new Event("Event3", "20 oct 2015 20:00", "20 oct 2014 23:00"); //end date before start date
	 private Event event4 = new Event("Event4", "24 oct 2015 14:00", "24 oct 2015 17:00");
	 
	 @Before
	public void setUp() throws Exception {
		FileHandler clear = new FileHandler();
		clear.clearAll();
		System.out.println("ServiceHandler cleared");
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
			expectedListTodo.add(todo3);
			expectedListTodo.add(todo2);
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
			expectedListTodo.add(floatingTodo1);
			expectedListTodo.add(floatingTodo2);
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
			assertEquals("DeleteFloatingTodo2Success", true, service.deleteItem(floatingTodo2));
			assertEquals("DeleteFloatingTodo3", false, service.deleteItem(floatingTodo3));
			assertEquals("DeleteFloatingTodo2Fail", false, service.deleteItem(floatingTodo2));
			
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
			assertEquals("View specfic floating task success1", floatingTodo1, service.viewSpecificTask(FLOATINGTODONAME));
			
			service.createItem(todo1);
			assertEquals("View specfic floating task success2", todo1, service.viewSpecificTask(TODONAME));
			
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
			expectedListItem.add(todo3);
			expectedListItem.add(event1);
			expectedListItem.add(todo1);
			expectedListItem.add(floatingTodo1);
			assertEquals("Search success1", expectedListItem, service.search(SEARCHINPUT));
			
			ArrayList<Item> expectedListItem1 = new ArrayList<Item>();
			expectedListItem1.add(event1);
			expectedListItem1.add(event2);
			assertEquals("Search success2", expectedListItem1, service.search(SEARCHINPUT1));
			
			ArrayList<Item> expectedListItem2 = new ArrayList<Item>();
			assertEquals("Search empty", expectedListItem2, service.search(NAMENOTFOUND));
		} catch (Exception e) {
		    System.out.println(event2);
            e.printStackTrace();
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
			assertEquals("delete item by index success1", true, service.deleteItemByIndex(2));
			assertEquals("delete item by index success2", true, service.deleteItemByIndex(1));
			assertEquals("delete item by index success3", true, service.deleteItemByIndex(0));
			
			assertEquals("delete item by index fail", false, service.deleteItemByIndex(-1)); //cannot delete negative index
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
			assertEquals("view item by index success1", floatingTodo1, service.viewItemByIndex(3));			
			assertEquals("view item by index success2", todo1, service.viewItemByIndex(2));
			assertEquals("view item by index success3", event1, service.viewItemByIndex(1));
			assertEquals("view item by index success4", todo3, service.viewItemByIndex(0));
			assertEquals("view item by index fail", null, service.viewItemByIndex(-1)); //cannot view items of negative index
			
			service.search(SEARCHINPUT2);
			assertEquals("view item by index success5", todo3, service.viewItemByIndex(0));
			assertEquals("view item by index success6", todo1, service.viewItemByIndex(1));
			
			
		} catch (Exception e) {
			fail("exception should not be thrown");
		}	
	}
	
	@Test
	public void testEditItem() {
		try {
			service.createItem(floatingTodo1);
			service.createItem(todo3);
			service.createItem(event2);
			service.createItem(event1);
			
			assertEquals("edit item success1", FLOATINGTODONAME, service.editItem(floatingTodo1, CommonHelper.FIELD_NAME, NEWNAME));
			assertEquals("edit item success2", OLDDUETIMETODO, service.editItem(todo3, CommonHelper.FIELD_DUE, CORRECTTIME));
			
			assertEquals("edit item success3", EVENTNAME, service.editItem(event1, CommonHelper.FIELD_NAME, NEWNAME));
			assertEquals("edit item success4", OLDSTARTTIMEEVENT ,service.editItem(event2, CommonHelper.FIELD_START, CORRECTTIME));
			assertEquals("edit item success5", OLDENDTIMEEVENT, service.editItem(event1, CommonHelper.FIELD_END, CORRECTTIME));
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
	
//	chanageDirectory() not tested because already tested in storagetest
	
	@Test
	public void testMark() throws Exception {
	    service.createItem(event1);
        service.createItem(todo1);
        service.createItem(floatingTodo1);
        assertEquals("Mark event1 success", true, service.mark(event1));
		assertEquals("Mark todo1 success", true, service.mark(todo1));
		assertEquals("Mark floating todo1 success", true, service.mark(floatingTodo1));
		
		assertEquals("Mark event1 fail", false, service.mark(event1));
		assertEquals("Mark todo1 fail", false, service.mark(todo1));
		assertEquals("Mark floating todo1 fail", false, service.mark(floatingTodo1));
	}
	
	@Test
	public void testUnmark() throws Exception {
	    service.createItem(event1);
        service.createItem(todo1);
        service.createItem(floatingTodo1);
		assertEquals("Unmark event1 fail", false, service.unmark(event1));
		assertEquals("Unmark todo1 fail", false, service.unmark(todo1));
		assertEquals("Unmark floating todo1 fail", false, service.unmark(floatingTodo1));
		
		service.mark(event1);
		service.mark(todo1);
		service.mark(floatingTodo1);
		assertEquals("Unmark event1 success", true, service.unmark(event1));
		assertEquals("Unmark todo1 success", true, service.unmark(todo1));
		assertEquals("Unmark floating todo1 success", true, service.unmark(floatingTodo1));
	}
	
	@Test
	public void testViewMultipleDays() {
		try {
			service.createItem(floatingTodo1);
			service.createItem(floatingTodo2);
			service.createItem(event1);
			service.createItem(event4);
			service.createItem(event2);
			service.createItem(todo1);
			service.createItem(todo4);
			service.createItem(todo5);
			
			ArrayList<ArrayList<Item>> viewMultipleDays = new ArrayList<ArrayList<Item>>();
			ArrayList<Item> expectedListItemDayOneItems = new ArrayList<Item>();
			ArrayList<Item> expectedListItemDayTwoItems = new ArrayList<Item>();
			ArrayList<Item> expectedListItemDayThreeItems = new ArrayList<Item>();
			ArrayList<Item> expectedListItemDayOneFloatingTodo = new ArrayList<Item>();
			
			expectedListItemDayOneItems.add(event1);
			expectedListItemDayOneItems.add(event2);
			expectedListItemDayOneItems.add(todo1);
			expectedListItemDayOneFloatingTodo.add(floatingTodo1);
			expectedListItemDayOneFloatingTodo.add(floatingTodo2);
			expectedListItemDayTwoItems.add(todo4);
			expectedListItemDayTwoItems.add(event4);
			expectedListItemDayThreeItems.add(todo5);
			
			viewMultipleDays.add(expectedListItemDayOneItems);
			viewMultipleDays.add(expectedListItemDayOneFloatingTodo);
			viewMultipleDays.add(expectedListItemDayTwoItems);
			viewMultipleDays.add(expectedListItemDayThreeItems);
			
			assertEquals("View multiple days success", viewMultipleDays, service.viewMultipleDays(TODAYDATE));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}
		
	}
}