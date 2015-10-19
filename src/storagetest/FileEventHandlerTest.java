package storagetest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import object.Event;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import storage.FileEventHandler;

public class FileEventHandlerTest {

	private static String baseDirectory;
	private static FileEventHandler fEventH;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		baseDirectory = System.getProperty("user.dir").toString() + "/testFiles";
		System.out.println("This is the base directory: \n" + baseDirectory);
		
		preparationCleanUp.cleanUp(baseDirectory);
		preparationCleanUp.setUpDirectory(baseDirectory);
	}
	
	@Test
	public void testFileEventHandlerInIdealSequence(){
		testFileEventHandlerConstructor();
		testSaveNewEventHandler();
		testRetrieveEventByDate();
		testSetNewDirectory();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Exiting, cleaning up folders");
		if(preparationCleanUp.cleanUp(baseDirectory)){
			System.out.println("Clean up completed. bye");
		}
	}

	public void testFileEventHandlerConstructor() {
		fEventH = new FileEventHandler(baseDirectory);
		
		assertEquals("Test if there are no existing events", 
				new ArrayList<Event>(), fEventH.retrieveEventsToDelete());
		
//		assertEquals("Test if there are no existing passed events", 
//				new ArrayList<Event>(), fEventH.retrieveEventsToDelete());
	}
	
	public void testSaveNewEventHandler() {
		assertEquals("Test saving null event",
				false, fEventH.saveNewEventHandler(null));
		
		Event event = new Event("Event1", "31 aug 2100 23:00", "1 sep 2100 02:00");
		
		assertEquals("Test saving valid event",
				true, fEventH.saveNewEventHandler(event));
		
		Event event2 = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
		
		assertEquals("Test saving valid past event",
				true, fEventH.saveNewEventHandler(event2));
	}
	
	public void testRetrieveEventByDate() {
		assertEquals("Test retrieving from null date",
				new ArrayList<Event>(), fEventH.retrieveEventByDate(null));
		
		assertEquals("Test retrieving from a date with no event",
				new ArrayList<Event>(), fEventH.retrieveEventByDate("12 mar 2100"));
	
//		Event event = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
//		event.setId(1);
//		ArrayList<Event> expectedEventBook = new ArrayList<Event>();
//		expectedEventBook.add(event);
//		ArrayList<Event> actualEventBook = fEventH.retrieveEventByDate("20 aug 2000");
//		
//		assertEquals("Test retrieving from a date of the past",
//				true, expectedEventBook.equals(actualEventBook) );
//		assertEquals("Test retrieving from a date of the past",
//				expectedEventBook, actualEventBook);
	}
	
	public void testSetNewDirectory() {
		assertEquals("Test with new directory being null",
				false, fEventH.setNewDirectory(null));
		
		assertEquals("Test if new directory does not exist",
				false, fEventH.setNewDirectory("this is not a directory format"));
		
		assertEquals("Test with valid new directory",
				false, fEventH.setNewDirectory(baseDirectory + "/alternateTestFiles"));
	}

//	@Test
//	public void testRetrieveEventsToDelete() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testUpdateHistory() {
//		fail("Not yet implemented");
//	}
	

//	public void testSaveEventBook() {
//		fail("Not yet implemented");
//	}	
}
