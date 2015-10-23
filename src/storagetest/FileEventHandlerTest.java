package storagetest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import object.Event;
import object.Item;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import storage.FileEventHandler;

public class FileEventHandlerTest {

	private static String baseDirectory;
	private static FileEventHandler fEventH;	
	
	private Event event = new Event("Event1", "31 aug 2100 23:00", "1 sep 2100 02:00");
	private Event event2 = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
	
	@BeforeClass
 	public static void setUpBeforeClass() throws Exception {
		Item.setCounter(0);
		baseDirectory = System.getProperty("user.dir").toString() + "/testFiles";
		PreparationCleanUp.manualCleanUp();
		PreparationCleanUp.cleanUp(baseDirectory);
		PreparationCleanUp.setUpDirectory(baseDirectory);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Item.setCounter(0);
		PreparationCleanUp.manualCleanUp();
		PreparationCleanUp.cleanUp(baseDirectory);
	}
	
	@Test
	public void testFileEventHandlerInIdealSequence(){
		testFileEventHandlerConstructor();
		testSaveNewEventHandler();
		testRetrieveEventByDate();
		testRetrieveAllEvents();
		testChangeDirectory();
	}
	
	@Test
	public void testRetrieveAllEventsWithNoEvent(){
		fEventH = new FileEventHandler(baseDirectory);
		assertEquals("Test retrieval of all events when there are no existing events",
				new ArrayList<Event>(), fEventH.retrieveAllEvents());
	}
	
	
/**********************************************************************************************/
	
	private void testFileEventHandlerConstructor() {
		fEventH = new FileEventHandler(baseDirectory);
		
		assertEquals("Test if there are no existing events", 
				new ArrayList<Event>(), fEventH.retrieveAllEvents());
		
	}
	
	private void testSaveNewEventHandler() {
		assertEquals("Test saving null event",
				false, fEventH.saveNewEventHandler(null));
		
		assertEquals("Test saving valid event",
				true, fEventH.saveNewEventHandler(event));
		
		assertEquals("Test saving valid past event",
				true, fEventH.saveNewEventHandler(event2));
		
		//TODO: test adding of today's event.
	}
	
	private void testRetrieveEventByDate() {
		assertEquals("Test retrieving from null date",
				new ArrayList<Event>(), fEventH.retrieveEventByDate(null));
		
		assertEquals("Test retrieving from a date with no event",
				new ArrayList<Event>(), fEventH.retrieveEventByDate("12 mar 2100"));

		ArrayList<Event> expectedEventBook = new ArrayList<Event>();
		expectedEventBook.add(event2);
		ArrayList<Event> actualEventBook = fEventH.retrieveEventByDate("20 aug 2000");
	
		assertEquals("Test retrieving from a date of the past",
				expectedEventBook,actualEventBook);
	}
	
	private void testChangeDirectory(){
		PreparationCleanUp.cleanUp(baseDirectory);
		testSetNewDirectory();
		PreparationCleanUp.makeNewDirectory(baseDirectory + "/Event");
		fEventH.saveEventBook();
		
		//reset
		fEventH = new FileEventHandler(baseDirectory + "/Event");
		testRetrieveEventByDate();
		testRetrieveAllEvents();
	}
	
	private void testSetNewDirectory() {
		String newBaseDirectory = null;
		assertEquals("Test with new directory being null",
				false, fEventH.setNewDirectory(newBaseDirectory));
		
		newBaseDirectory = "this is not a directory format";
		assertEquals("Test if new directory does not exist",
				false, fEventH.setNewDirectory(newBaseDirectory));
		
		newBaseDirectory = System.getProperty("user.dir").toString() + "/alternateTestFiles";
		PreparationCleanUp.makeNewDirectory(newBaseDirectory);
		assertEquals("Test with valid new directory",
				true, fEventH.setNewDirectory(newBaseDirectory));
		baseDirectory = newBaseDirectory;
	}

	private void testRetrieveAllEvents() {
		ArrayList<Event> expectedList = new ArrayList<Event>();
		event.setId(0);
		event2.setId(1);
		expectedList.add(event2);
		expectedList.add(event);
		
		assertEquals("Test retrieval of all events",
				expectedList, fEventH.retrieveAllEvents());
			
	}
	
}




