package storagetest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import object.Todo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import storage.FileTodoHandler;

public class FileTodoHandlerTest {

	private static String baseDirectory;
	private static FileTodoHandler fTodoH;
	
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
		baseDirectory = System.getProperty("user.dir").toString() + "\\testFiles";
		System.out.println("This is the base directory: \n" + baseDirectory);
		
		PreparationCleanUp.cleanUp(baseDirectory);
		PreparationCleanUp.setUpDirectory(baseDirectory);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Exiting, cleaning up folders");
		if(PreparationCleanUp.cleanUp(baseDirectory)){
			System.out.println("Clean up completed. bye");
		}
	}
	
	@Test
	public void testFileTodoHandlerInIdealSequence(){
		testFileTodoHandlerConstructor();
		testSaveNewTodoHandler();
		testRetrieveAllTodo();
		testRetrieveTodoByDate();
		testRetrieveFloatingTodo();
		testSaveToDoList();
		testSaveUniversalToDoList();
		testChangeDirectory();
//		testSeparateTodoTypes();
//		testSetNewDirectory();
	}
	
//	@Test
//	public void testRetrievalWithNoTask(){
//		preparationCleanUp.cleanUp(baseDirectory);
//		fTodoH = new FileTodoHandler(baseDirectory);
//		
//		ArrayList<Todo> expectedList = new ArrayList<Todo>();
//		ArrayList<Todo> actualList = fTodoH.retrieveAllTodo();
//		
//		assertEquals("Test retrieval of all todo", 
//				true, compareTodoArrayList(expectedList, actualList));
//	}
	
	public void testFileTodoHandlerConstructor() {
		fTodoH = new FileTodoHandler(baseDirectory);
		
		assertEquals("Test if there are no existing tasks", 
				new ArrayList<Todo>(), fTodoH.retrieveAllTodo());
		
		//TODO: check existence of files
	}

	public void testSaveNewTodoHandler() {
		assertEquals("Test if there are no existing tasks", 
				true, fTodoH.saveNewTodoHandler(todo1));
		
		assertEquals("Test if there are no existing tasks", 
				true, fTodoH.saveNewTodoHandler(todo2));
		
		assertEquals("Test if there are no existing tasks", 
				true, fTodoH.saveNewTodoHandler(todo3));
		
		assertEquals("Test if there are no existing tasks", 
				true, fTodoH.saveNewTodoHandler(todo4));
		
		assertEquals("Test if there are no existing tasks", 
				true, fTodoH.saveNewTodoHandler(todo5));
		
		assertEquals("Test if there are no existing tasks", 
				true, fTodoH.saveNewTodoHandler(todo6));
		
		assertEquals("Test if there are no existing tasks", 
				true, fTodoH.saveNewTodoHandler(todo7));	
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
		
		ArrayList<Todo> actualList = fTodoH.retrieveAllTodo();
		
//		assertEquals("Test retrieval of all todo", 
//				expectedList, actualList);
		
		assertEquals("Test retrieval of all todo", 
				true, compareTodoArrayList(expectedList, actualList));
	}
	
	public void testSaveToDoList() {
		assertEquals("Test saving", 
				true, fTodoH.saveToDoList());
	}

	public void testRetrieveTodoByDate() {
		ArrayList<Todo> expectedList = new ArrayList<Todo>();
		expectedList.add(todo7);
		expectedList.add(todo5);
		expectedList.add(todo3);
		
		ArrayList<Todo> actualList = fTodoH.retrieveTodoByDate("20 oct 2000");
		
		assertEquals("Test retrieval of todo by date", 
				true, compareTodoArrayList(expectedList, actualList));
	}

	public void testRetrieveFloatingTodo() {
		expectedList = new ArrayList<Todo>();
		expectedList.add(todo1);
		actualList = fTodoH.retrieveFloatingTodo();
		
		assertEquals("Test retrieval of all floating todo", 
				true, compareTodoArrayList(expectedList, actualList));
	
//		assertEquals("Test retrieval of all floating todo", expectedList, actualList);
	}

	public void testSetNewDirectory() {
		String newBaseDirectory = null;
		assertEquals("Test with new directory being null",
				false, fTodoH.setNewDirectory(newBaseDirectory));
		
		newBaseDirectory = "this is not a directory format";
		assertEquals("Test if new directory does not exist",
				false, fTodoH.setNewDirectory(newBaseDirectory));
		
		newBaseDirectory = System.getProperty("user.dir").toString() + "/alternateTestFiles";
		PreparationCleanUp.makeNewDirectory(newBaseDirectory);
		assertEquals("Test with valid new directory",
				true, fTodoH.setNewDirectory(newBaseDirectory));
		baseDirectory = newBaseDirectory;
	}
	
	public void testChangeDirectory(){
		PreparationCleanUp.cleanUp(baseDirectory);
		testSetNewDirectory();
		PreparationCleanUp.makeNewDirectory(baseDirectory + "/Todo");
		testSaveToDoList();
		testSaveUniversalToDoList();
		
		fTodoH = new FileTodoHandler(baseDirectory + "/Todo");
		testRetrieveAllTodo();
		testRetrieveTodoByDate();
		testRetrieveFloatingTodo();
	}
	
	public void testSaveUniversalToDoList() {
		assertEquals("Test saving floating task", 
				true, fTodoH.saveUniversalToDoList());
	}

//	public void testSeparateTodoTypes() {
//	}
	
	private boolean compareTodoArrayList(ArrayList<Todo> list1, ArrayList<Todo> list2){
		
		if(list1.size() != list2.size()){
			return false;
		}else{
			for(int i=0; i<list1.size(); i++){
				if(!compareTodo(list1.get(i), list2.get(i))){
					return false;
				}
			}
			return true;
		}		
	}

	private boolean compareTodo(Todo todo1, Todo todo2){	
		return (todo1.getId() == todo2.getId());
	}
	
}





