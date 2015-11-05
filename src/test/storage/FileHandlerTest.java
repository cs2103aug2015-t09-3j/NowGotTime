//@@author A0124402Y
package test.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
	private static final String DATABASE = "database";
	
	private Todo todo1 = new Todo("Floating todo");
	private Todo todo2 = new Todo("Normal todo1", "", "20 Oct 2100");
	private Todo todo3 = new Todo("Past todo1", "", "20 Oct 2000");
	private Todo todo4 = new Todo("Normal todo2", "", "20 Oct 2100", "10:00");
	private Todo todo5 = new Todo("past todo2", "", "20 Oct 2000", "10:00");
	private Todo todo6 = new Todo("Normal todo3", "", "20 Oct 2100", "07:30");
	private Todo todo7 = new Todo("Past todo3", "", "20 Oct 2000", "07:30");
	
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
		Item.setCounter(0);
		PreparationCleanUp.manualCleanUp();
		PreparationCleanUp.cleanUp(newBaseDirectory);
	}
	
	@Test
	public void testFileHandlerInIdealSequence(){
		testFileHandler();
		testChangeBaseDirectory();
		
		testEventOperation();
		fh = new FileHandler();
		testTodoOperation();
		fh = new FileHandler();
		testProjectOperation();
		
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

/****************************************************************************/		

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
		
		assertEquals("Test retrieval of all events", 
				expectedList, fh.retrieveAllEvents());
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

/****************************************************************************/
	
	public void testTodoOperation(){
		testSaveNewTodoHandler();
		testRetrieveUniversalTodo();
		testRetrieveAllTodo();
		testRetrieveTodoByDate();
		testSaveEditedTodoHandler();
		testSaveAllEditedTodo();
	}
	
	//tested with null, past and future dates
	public void testRetrieveTodoByDate() {
		ArrayList<Todo> expectedList = new ArrayList<Todo>();
		
		ArrayList<Todo> actualList = fh.retrieveTodoByDate(null);
		assertEquals("Test retrieval of todo with null", 
				expectedList, actualList);
		
		actualList = fh.retrieveTodoByDate("01 Jan 2000");
		assertEquals("Test retrieval of todo on date with no todo", 
				expectedList, actualList);
		
		expectedList.add(todo7);
		expectedList.add(todo5);
		expectedList.add(todo3);
		
		actualList = fh.retrieveTodoByDate("20 oct 2000");
		assertEquals("Test retrieval of passed todo by date", 
				expectedList, actualList);
		
		expectedList.clear();
		expectedList.add(todo6);
		expectedList.add(todo4);
		expectedList.add(todo2);
		
		actualList = fh.retrieveTodoByDate("20 oct 2100");
		assertEquals("Test retrieval of future todo by date", 
				expectedList, actualList);
	}

	public void testRetrieveAllTodo() {
		ArrayList<Todo> expectedList = new ArrayList<Todo>();
		
		expectedList.add(todo7);
		expectedList.add(todo5);
		expectedList.add(todo3);
		
		expectedList.add(todo6);
		expectedList.add(todo4);
		expectedList.add(todo2);
		expectedList.add(todo1);
		
		ArrayList<Todo> actualList = fh.retrieveAllTodo();
		
		assertEquals("Test retrieval of all todo", 
				expectedList, actualList);
	}

	public void testRetrieveUniversalTodo() {
		expectedList = new ArrayList<Todo>();		
		expectedList.add(todo1);
		actualList = fh.retrieveUniversalTodo();
		
		assertEquals("Test retrieval of all floating todo", 
				expectedList, actualList);
	}

	public void testSaveNewTodoHandler() {
		
		assertEquals("Test adding of floating todo", 
				false, fh.saveNewTodoHandler(null));
		
		assertEquals("Test adding of floating todo", 
				true, fh.saveNewTodoHandler(todo1));
		
		assertEquals("Test adding of normal todo with no time", 
				true, fh.saveNewTodoHandler(todo2));
		
		assertEquals("Test adding of passed todo with no time", 
				true, fh.saveNewTodoHandler(todo3));
		
		assertEquals("Test adding of normal todo", 
				true, fh.saveNewTodoHandler(todo4));
		
		assertEquals("Test adding of passed todo", 
				true, fh.saveNewTodoHandler(todo5));
		
		//change bottom todo to current date?
		
		assertEquals("Test adding of normal todo", 
				true, fh.saveNewTodoHandler(todo6));
		
		assertEquals("Test adding of passed todo", 
				true, fh.saveNewTodoHandler(todo7));	
	}
	
	public void testSaveEditedTodoHandler() {
		
		assertEquals("Test save when there are no change done",
				true, fh.saveEditedTodoHandler());
		
		ArrayList<Todo> todolist = fh.retrieveAllTodo();
		Todo todo = todolist.get(0);
		todo.setName("eventful");
		assertEquals("Test if save is successful after editing the event", 
				true, fh.saveEditedTodoHandler());
		
		todolist = fh.retrieveAllTodo();
		todo2 = todolist.get(0);
		assertEquals("Test if changes are saved",
				todo, todo2);		
	}

	public void testSaveAllEditedTodo() {
		assertEquals("Test save when there are no change done",
				true, fh.saveAllEditedTodo());
		
		ArrayList<Todo> todolist = fh.retrieveAllTodo();
		Todo todo = todolist.get(0);
		todo.setName("todo7");
		assertEquals("Test if save is successful after editing the event", 
				true, fh.saveAllEditedTodo());
		
		todolist = fh.retrieveAllTodo();
		todo2 = todolist.get(0);
		assertEquals("Test if changes are saved",
				todo, todo2);		
	}
	
/***************************************************************************/
	
	public void testProjectOperation(){
		testGetListOfExistingProjectWithNoProject();
		testCreateNewProject();
		testGetListOfExistingProject();
		testSaveEditedProjectDetails();
		testDeleteProject();
		
	}
	
	@Test
	public void testRetrieveProjectTimeLine() {
		assertEquals("Test retrieval of project with null",
				null, fh.retrieveProjectTimeLine(null));
		
		assertEquals("Test retrieval of project with empty string",
				null, fh.retrieveProjectTimeLine(""));
		
		assertEquals("Test retrieval of project with non-existing project name",
				null, fh.retrieveProjectTimeLine("Non-existing"));
		
		fh.createNewProject("new project");
		ArrayList<Integer> projectBook = new ArrayList<Integer>();
		projectBook.add(0);
		fh.saveEditedProjectDetails(projectBook, new HashMap<Integer, String>(), "new project");
		
		assertEquals("Test retrieval of existing project",
				projectBook, fh.retrieveProjectTimeLine("new project"));
		
	}

	@Test
	public void testRetrieveProjectProgress() {
		assertEquals("Test retrieval without have to first retrieve project",
				new HashMap<Integer, String>(), fh.retrieveProjectProgress());
	}
	
	public void testCreateNewProject() {
		
		assertEquals("adding of project with null name",
				false, fh.createNewProject(null));
		
		assertEquals("adding of project with symbol name", 
				false, fh.createNewProject("!@#$%^&*()"));
		
		assertEquals("adding of project with empty string name",
				false, fh.createNewProject(""));
		
		assertEquals("adding of project", 
				true, fh.createNewProject("proj1"));
		
		assertEquals("adding of project with the same name",
				false, fh.createNewProject("proj1"));
		
		assertEquals("adding of project with similar name",
				true, fh.createNewProject("proj12"));
		
		assertEquals("adding of project with number name",
				true, fh.createNewProject("1234567890"));
			
	}

	public void testSaveEditedProjectDetails() {
				
		HashMap<Integer, String> progressBook  = new HashMap<Integer, String>();
		progressBook.put(1, "progress1");
		progressBook.put(4, "this index should not exist");
		ArrayList<Integer> projectBook = new ArrayList<Integer>();
		projectBook.add(1);
		String projectName = "proj1";
		
		assertEquals("Testing of editing with null projectBook",
				false, fh.saveEditedProjectDetails(null, progressBook, projectName) );
		
		assertEquals("Testing of editing with null progressBook",
				false, fh.saveEditedProjectDetails(projectBook, null, projectName) );
		
		assertEquals("Testing of editing with null name",
				false, fh.saveEditedProjectDetails(projectBook, progressBook, null) );
		
		assertEquals("Testing of editing with empty name",
				false, fh.saveEditedProjectDetails(projectBook, progressBook, "") );
		
		assertEquals("Testing of editing with non-existing project name",
				false, fh.saveEditedProjectDetails(projectBook, progressBook, "non-existing"));
		
		assertEquals("Testing of editing with valid parameters",
				true, fh.saveEditedProjectDetails(projectBook, progressBook, projectName) );
		
		progressBook.put(4, "this index should not exist");
		
		//In this case, FileHandler does not check if the id is valid, Logic will handle it.
		assertEquals("Testing of editing with hashmap having ID that does not exist in projectBook",
				true, fh.saveEditedProjectDetails(projectBook, progressBook, projectName) );
		
		Item.setCounter(0); 	//reset Counter for other test cases
	}

	public void testGetListOfExistingProjectWithNoProject() {	
		assertEquals("Test the retrieval of list when there is no existing project",
				new ArrayList<String>(), fh.getListOfExistingProject());
	}
	
	public void testGetListOfExistingProject() {
		ArrayList<String> expectedList = new ArrayList<String>();
		expectedList.add("proj1");
		expectedList.add("proj12");
		expectedList.add("1234567890");
		assertEquals("Test the retrieval of list when there is no existing project",
				expectedList, fh.getListOfExistingProject());
		
	}

	public void testDeleteProject() {
		assertEquals("Test deleting of project with null",
				false, fh.deleteProject(null));
		assertEquals("Test deleting of project with empty string",
				false, fh.deleteProject(""));
		assertEquals("Test deleting of non-existing project",
				false, fh.deleteProject("non-existing project name"));
			
		assertEquals("Test deleting of existing project with number names",
				true, fh.deleteProject("1234567890"));
		assertEquals("Test deleting of project with similar names",
				true, fh.deleteProject("proj12"));
		assertEquals("Test deleting of project",
				true, fh.deleteProject("proj1"));
		

		testGetListOfExistingProjectWithNoProject(); 
		// test if the projects names are also deleted from the project overview file
		
	}

/***************************************************************************/
	
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
		
		File file = new File(newBaseDirectory);
		assertEquals("Test if event directory exist",
				true, file.exists());
		
		file = new File(newBaseDirectory + "/database");
		assertEquals("Test if event directory exist",
				true, file.exists());
		
		file = new File(newBaseDirectory + "/database/Event");
		assertEquals("Test if event directory exist",
				true, file.exists());
		
		file = new File(newBaseDirectory + "/database/Todo");
		assertEquals("Test if todo directory exist",
				true, file.exists());
		
		file = new File(newBaseDirectory + "/database/Project");
		assertEquals("Test if project directory exist",
				true, file.exists());
	}

//	@Test
//	public void testSaveAll() {
//		assertEquals("testing of saving all",
//				true, fh.saveAll());
//	}
			
	@Test
	public void testClearAll() {
		fh.clearAll();
		checkIfDirectoriesExist(false);	// expect false since fh has clear all files
		fh = new FileHandler();	
		checkIfDirectoriesExist(true);	// fh will re-create files
	}

	@Test
	public void testRetrieveEventById() {
		
		Event event = new Event("Event1", "31 aug 2100 23:00", "1 sep 2100 02:00");
		assertEquals("Test save of new event",
				true, fh.saveNewEventHandler(event));
		
		assertEquals("Test retrieval of event with a valid index",
				event, fh.retrieveEventById(event.getId()));
		
		assertEquals("Test retrieval of event with negative index number",
				null, fh.retrieveEventById(-1));
		
		assertEquals("Test retrieval of event with an invalid index",
				null, fh.retrieveEventById(1000));
		
		Item.setCounter(0);
	}

	@Test
	public void testRetrieveTaskById() {
		Item.setCounter(0);
		Todo todo1 = new Todo("Floating todo");
		Todo todo2 = new Todo("Normal todo1", "", "20 Oct 2100");		
		
		assertEquals("Test creation of new floating todo",
				true, fh.saveNewTodoHandler(todo1));
		
		assertEquals("Test creation of new normal todo",
				true, fh.saveNewTodoHandler(todo2));
		
		assertEquals("Test retrieval of floating todo with valid index",
				todo1, fh.retrieveTaskById(todo1.getId()));
		
		assertEquals("Test retrieval of normal todo with valid index",
				todo2, fh.retrieveTaskById(todo2.getId()));
		
		assertEquals("Test retrieval of todo with negative index",
				null, fh.retrieveTaskById(-1));
		
		assertEquals("Test retrieval of todo with invalid index",
				null, fh.retrieveTaskById(1000));
		
		
	}
	
	public void checkIfDirectoriesExist(boolean expected){
		String baseDirectory = System.getProperty("user.dir").toString();
		
		File file = new File(baseDirectory + "/" + DATABASE + "/Event");
		assertEquals("Test if event storage directory exist",
				expected, file.exists());
		
		file = new File(baseDirectory + "/" + DATABASE + "/Todo");
		assertEquals("Test if todo storage directory exist",
				expected, file.exists());
		
		file = new File(baseDirectory + "/" + DATABASE + "/Project");
		assertEquals("Test if project directory directory exist",
				expected, file.exists());

	}
}
