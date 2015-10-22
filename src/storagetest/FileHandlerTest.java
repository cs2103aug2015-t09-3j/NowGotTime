package storagetest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import object.Event;
import object.Item;
import object.Todo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import storage.FileHandler;

public class FileHandlerTest {
	private static FileHandler fh;
	private static String newBaseDirectory;
	
	private Todo todo1 = new Todo("Floating todo");
	private Todo todo2 = new Todo("Normal todo1", "", "20 oct 2100");
	private Todo todo3 = new Todo("Past todo1", "", "20 oct 2000");
	private Todo todo4 = new Todo("Normal todo2", "", "20 oct 2100", "10:00");
	private Todo todo5 = new Todo("past todo2", "", "20 oct 2000", "10:00");
	private Todo todo6 = new Todo("Normal todo3", "", "20 oct 2100", "07:30");
	private Todo todo7 = new Todo("Past todo3", "", "20 oct 2000", "07:30");
	
	private ArrayList<Todo> expectedList;
	private ArrayList<Todo> actualList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Item.setCounter(0);
		PreparationCleanUp.manualCleanUp();
		fh = new FileHandler();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Exiting, cleaning up folders");
		Item.setCounter(0);
		PreparationCleanUp.manualCleanUp();
		PreparationCleanUp.cleanUp(newBaseDirectory);
		System.out.println("Clean up completed. bye");
	}
	
	@Test
	public void testFileHandlerInIdealSequence(){
		testFileHandler();
		testChangeBaseDirectory();
		
		testEventOperation();
		fh = new FileHandler();
		testTodoOperation();
		
	}
	
	public void testFileHandler() {
		String baseDirectory = System.getProperty("user.dir").toString();
		File file = new File(baseDirectory + "/database/Event");
		assertEquals("Test file existence",
				true, file.exists());
		
		file = new File(baseDirectory + "/database/Event/All_Events.txt");
		assertEquals("Test file existence",
				true, file.exists());
		
		file = new File(baseDirectory + "/database/Todo");
		assertEquals("Test file existence",
				true, file.exists());
		
		file = new File(baseDirectory + "/database/Todo/Floating_Todo.txt");
		assertEquals("Test file existence",
				true, file.exists());
		
		file = new File(baseDirectory + "/database/Todo/Normal_Todo.txt");
		assertEquals("Test file existence",
				true, file.exists());
		
		file = new File(baseDirectory + "/database/Project");
		assertEquals("Test file existence",
				true, file.exists());
		
		file = new File(baseDirectory + "/database/Project/projectOverviewer.txt");
		assertEquals("Test file existence",
				true, file.exists());
		
		file = new File(baseDirectory + "/overview.txt");
		assertEquals("Test file existence",
				true, file.exists());
	}
		
	public void testEventOperation(){
		testSaveNewEventHandler();
		testRetrieveAllEvents();
		testRetrieveAllEvents();
	}
	
	public void testRetrieveEventByDate() {
		assertEquals("Test retrieving from null date",
				new ArrayList<Event>(), fh.retrieveEventByDate(null));
		
		assertEquals("Test retrieving from a date with no event",
				new ArrayList<Event>(), fh.retrieveEventByDate("12 mar 2100"));
	
		Event event = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
		event.setId(1);
		ArrayList<Event> expectedEventBook = new ArrayList<Event>();
		expectedEventBook.add(event);
		ArrayList<Event> actualEventBook = fh.retrieveEventByDate("20 aug 2000");
		
//		assertEquals("Test retrieving from a date of the past",
//				true, compareEventsArrayList(expectedEventBook,actualEventBook) );
	
		assertEquals("Test retrieving from a date of the past",
				expectedEventBook,actualEventBook);
	}

	public void testRetrieveAllEvents() {
		ArrayList<Event> expectedList = new ArrayList<Event>();
		Event event = new Event("Event1", "31 aug 2100 23:00", "1 sep 2100 02:00");
		Event event2 = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
		event.setId(7);
		event2.setId(8);
		expectedList.add(event2);
		expectedList.add(event);
		
//		assertEquals("Test retrieval of all events",
//				expectedList, fh.retrieveAllEvents());
		assertEquals("Test retrieval of all events", 
				true, PreparationCleanUp.compareEventsArrayList(expectedList, fh.retrieveAllEvents()));
	}
	
	public void testSaveNewEventHandler() {
		assertEquals("Test saving null event",
				false, fh.saveNewEventHandler(null));
		
		Event event = new Event("Event1", "31 aug 2100 23:00", "1 sep 2100 02:00");
		
		assertEquals("Test saving valid event",
				true, fh.saveNewEventHandler(event));
		
		Event event2 = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
		
		assertEquals("Test saving valid past event",
				true, fh.saveNewEventHandler(event2));
	}

//	public void PreparationCleanUp.testSaveEditedEventHandler() {
//		fail("Not yet implemented");
//	}
	
	public void testTodoOperation(){
		testSaveNewTodoHandler();
		testRetrieveUniversalTodo();
		testRetrieveAllTodo();
		testRetrieveTodoByDate();		
	}
	
	public void testRetrieveTodoByDate() {
		ArrayList<Todo> expectedList = new ArrayList<Todo>();
		expectedList.add(todo3);
		expectedList.add(todo7);
		expectedList.add(todo5);
		
		
		ArrayList<Todo> actualList = fh.retrieveTodoByDate("20 oct 2000");
		
//		assertEquals("Test retrieval of todo by date", 
//				true, PreparationCleanUp.compareTodoArrayList(expectedList, actualList));
	}

	public void testRetrieveAllTodo() {
//		ArrayList<Todo> expectedList = new ArrayList<Todo>();
//		expectedList.add(todo3);
//		expectedList.add(todo7);
//		expectedList.add(todo5);
//		
//		expectedList.add(todo2);
//		expectedList.add(todo6);
//		expectedList.add(todo4);
//		
//		expectedList.add(todo1);
//		
//		ArrayList<Todo> actualList = fh.retrieveAllTodo();
//		
//		assertEquals("Test retrieval of all todo", 
//				true, PreparationCleanUp.compareTodoArrayList(expectedList, actualList));
		
//		assertEquals("Test retrieval of all todo", 
//				expectedList, actualList);
	}

	public void testRetrieveUniversalTodo() {
		expectedList = new ArrayList<Todo>();
		expectedList.add(todo1);
		actualList = fh.retrieveUniversalTodo();
		
		assertEquals("Test retrieval of all floating todo", 
				true, PreparationCleanUp.compareTodoArrayList(expectedList, actualList));
	
//		assertEquals("Test retrieval of all floating todo", expectedList, actualList);
	}

	public void testSaveNewTodoHandler() {
		assertEquals("Test if there are no existing tasks", 
				true, fh.saveNewTodoHandler(todo1));
		
		assertEquals("Test if there are no existing tasks", 
				true, fh.saveNewTodoHandler(todo2));
		
		assertEquals("Test if there are no existing tasks", 
				true, fh.saveNewTodoHandler(todo3));
		
		assertEquals("Test if there are no existing tasks", 
				true, fh.saveNewTodoHandler(todo4));
		
		assertEquals("Test if there are no existing tasks", 
				true, fh.saveNewTodoHandler(todo5));
		
		assertEquals("Test if there are no existing tasks", 
				true, fh.saveNewTodoHandler(todo6));
		
		assertEquals("Test if there are no existing tasks", 
				true, fh.saveNewTodoHandler(todo7));	
	}
	
//	public void testSaveEditedTodoHandler() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSaveAllEditedTodo() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRetrieveProjectTimeLine() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRetrieveProjectProgress() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCreateNewProject() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSaveEditedProjectDetails() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetListOfExistingProject() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteProject() {
//		fail("Not yet implemented");
//	}

	public void testChangeBaseDirectory() {
		newBaseDirectory = null;
		assertEquals("Test change to null directory",
				false, fh.changeBaseDirectory(newBaseDirectory));
		
		newBaseDirectory = "";
		assertEquals("Test change to empty directory",
				false, fh.changeBaseDirectory(newBaseDirectory));
		
		newBaseDirectory = "non-existing";
		assertEquals("Test change to non-existing directory",
				false, fh.changeBaseDirectory(newBaseDirectory));
		
		newBaseDirectory = System.getProperty("user.dir").toString() + "/alternateTestFiles";
		PreparationCleanUp.makeNewDirectory(newBaseDirectory);
		
		
		assertEquals("Test with valid new directory",
				true, fh.changeBaseDirectory(newBaseDirectory));
		
		File file = new File(newBaseDirectory + "/Event");
		assertEquals("Test if event directory exist",
				true, file.exists());
		
		file = new File(newBaseDirectory + "/Todo");
		assertEquals("Test if todo directory exist",
				true, file.exists());
		
		file = new File(newBaseDirectory + "/Project");
		assertEquals("Test if project directory exist",
				true, file.exists());
	}

//	@Test
//	public void testSaveAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testClearAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRetrieveEventById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRetrieveTaskById() {
//		fail("Not yet implemented");
//	}

}
