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
	 private ArrayList<Todo> expectedListTodo;
	 private ArrayList<Event> expectedListEvent;
	 private ArrayList<Item> expectedListItem;
	 
	 private Todo floatingTodo1 = new Todo("Floating todo1");
	 private Todo floatingTodo2 = new Todo("Floating todo2");
	 private Todo floatingTodo3 = new Todo("Floating todo3");
	 
	 private Todo todo1 = new Todo("Normal todo1", "", "20 oct 2015");
	 private Todo todo2 = new Todo("Normal todo2", "", "20 oct 2015");
	 private Todo todo3 = new Todo("Normal todo3", "", "20 oct 2015");

	 private Event event1 = new Event("Event1", "20 oct 2015 12:00", "20 oct 2015 13:00");
	 private Event event2 = new Event("Event2", "20 oct 2015 14:00", "20 oct 2015 17:00");
	 private Event event3 = new Event("Event3", "20 oct 2015 20:00", "20 oct 2014 23:00");
	 
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
			assertEquals("DeleteFloatingTodo2", false, service.deleteItem(floatingTodo3));
		} catch (Exception e) {
			fail("exception should not be thrown");
		}
	}
	
	
}
