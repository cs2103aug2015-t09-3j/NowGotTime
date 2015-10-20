//package storagetest;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import storage.FileHandler;
//
//public class FileHandlerTest {
//	private static FileHandler fh = new FileHandler();
//	private static String newBaseDirectory;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		PreparationCleanUp.manualCleanUp();
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		System.out.println("Exiting, cleaning up folders");
//		if(PreparationCleanUp.cleanUp(newBaseDirectory)){
//			PreparationCleanUp.manualCleanUp();
//			System.out.println("Clean up completed. bye");
//		}
//	}
//
//	@Test
//	public void testFileHandler() {
//		String baseDirectory = System.getProperty("user.dir").toString();
//		File file = new File(baseDirectory + "/Event");
//		assertEquals("Test file existence",
//				true, file.exists());
//		
//		file = new File(baseDirectory + "/Event/All_Events.txt");
//		assertEquals("Test file existence",
//				true, file.exists());
//		
//		file = new File(baseDirectory + "/Todo");
//		assertEquals("Test file existence",
//				true, file.exists());
//		
//		file = new File(baseDirectory + "/Todo/Floating_Todo.txt");
//		assertEquals("Test file existence",
//				true, file.exists());
//		
//		file = new File(baseDirectory + "/Todo/Normal_Todo.txt");
//		assertEquals("Test file existence",
//				true, file.exists());
//		
//		file = new File(baseDirectory + "/Project");
//		assertEquals("Test file existence",
//				true, file.exists());
//		
//		file = new File(baseDirectory + "/Project/projectOverviewer.txt");
//		assertEquals("Test file existence",
//				true, file.exists());
//		
//		file = new File(baseDirectory + "/overview.txt");
//		assertEquals("Test file existence",
//				true, file.exists());
//	}
//	
//	@Test
//	public void testEvent(){
//		
//	}
//	
//	@Test
//	public void testTodo(){
//		
//	}
//	
//	@Test
//	public void testProject(){
//		
//	}
//	
////	@Test
////	public void testRetrieveEventByDate() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testRetrieveAllEvents() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testSaveNewEventHandler() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testSaveEditedEventHandler() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testRetrieveTodoByDate() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testRetrieveAllTodo() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testRetrieveUniversalTodo() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testSaveNewTodoHandler() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testSaveEditedTodoHandler() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testSaveAllEditedTodo() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testRetrieveProjectTimeLine() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testRetrieveProjectProgress() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testCreateNewProject() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testSaveEditedProjectDetails() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testGetListOfExistingProject() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testDeleteProject() {
////		fail("Not yet implemented");
////	}
////	
//	
////	@Test
////	public void testChangeBaseDirectory() {
////		newBaseDirectory = null;
////		assertEquals("Test change to null directory",
////				false, fh.changeBaseDirectory(newBaseDirectory));
////		
////		newBaseDirectory = "";
////		assertEquals("Test change to empty directory",
////				false, fh.changeBaseDirectory(newBaseDirectory));
////		
////		newBaseDirectory = "non-existing";
////		assertEquals("Test change to non-existing directory",
////				false, fh.changeBaseDirectory(newBaseDirectory));
////		
////		newBaseDirectory = System.getProperty("user.dir").toString() + "/alternateTestFiles";
////		PreparationCleanUp.makeNewDirectory(newBaseDirectory);
////		assertEquals("Test with valid new directory",
////				false, fh.changeBaseDirectory(newBaseDirectory));
////		
////		String baseDirectory = System.getProperty("user.dir").toString() + "/alternateTestFiles";
////		File file = new File(baseDirectory + "/Event");
////		assertEquals("Test with valid new directory",
////				true, file.exists());
////		
////		file = new File(baseDirectory + "/Todo");
////		assertEquals("Test with valid new directory",
////				true, file.exists());
////		
////		file = new File(baseDirectory + "/Project");
////		assertEquals("Test with valid new directory",
////				true, file.exists());
////	}
//
////	@Test
////	public void testSaveAll() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testClearAll() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testRetrieveEventById() {
////		fail("Not yet implemented");
////	}
////
////	@Test
////	public void testRetrieveTaskById() {
////		fail("Not yet implemented");
////	}
//
//}
