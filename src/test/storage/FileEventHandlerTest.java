//@@author A0124402Y
package test.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import object.Event;
import object.Item;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import storage.FileEventHandler;

public class FileEventHandlerTest {

	private static String baseDirectory;
	private static FileEventHandler fEventH;	
	
	private Event event = new Event("Event1", "31 aug 2100 23:00", "1 sep 2100 02:00");
	private Event event2 = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
	
	
	@Before
	public void setUpBeforeMethod() throws Exception{
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
	public void testRetrieveEventByDateWithNoExistingEvent(){
		fEventH = new FileEventHandler(baseDirectory);
		ArrayList<Event> actualEventBook = fEventH.retrieveEventByDate("20 aug 2000");
		
		assertEquals("Test retrieving from a date with no event",
				new ArrayList<Event>(), actualEventBook);
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
		
		assertEquals("Test if the event txt file has been created",
				true, new File(baseDirectory + "/All_Events.txt").exists());
		
		assertEquals("Test if there are no existing events", 
				new ArrayList<Event>(), fEventH.retrieveAllEvents());
		
	}
	
	private void testSaveNewEventHandler() {
		
//		assertEquals("Test saving null event",
//				false, fEventH.saveNewEventHandler(null));
		
		assertEquals("Test saving valid event",
				true, fEventH.saveNewEventHandler(event));
		
		assertEquals("Test saving valid passed event",
				true, fEventH.saveNewEventHandler(event2));
	}
	
	private void testRetrieveEventByDate() {
				
		assertEquals("Test retrieving from a date with wrong format: numbers",
				null, fEventH.retrieveEventByDate("1234567"));
		
		assertEquals("Test retrieving from a date with wrong format: symbols",
				null, fEventH.retrieveEventByDate(")(*&^%$#@!"));
		
		assertEquals("Test retrieving from a non-existing date",
				null, fEventH.retrieveEventByDate("95 mar 2100"));
		
		assertEquals("Test retrieving from a date with no event",
				new ArrayList<Event>(), fEventH.retrieveEventByDate("12 mar 2100"));

		ArrayList<Event> expectedEventBook = new ArrayList<Event>();
		expectedEventBook.add(event2);
		ArrayList<Event> actualEventBook = fEventH.retrieveEventByDate("20 aug 2000");
	
		assertEquals("Test retrieving from a date of the past",
				expectedEventBook, actualEventBook);
	}
	
	private void testChangeDirectory(){
		PreparationCleanUp.cleanUp(baseDirectory);
		testSetNewDirectory();
		// new directory would be set at this point.
		PreparationCleanUp.makeNewDirectory(baseDirectory + "/Event");
		testSaveEventBook(true);
		
		//test if data has been transferred.
		testRetrieveEventByDate();
		testRetrieveAllEvents();
		
		// pseudo-reset to re-start up FileEventHandler to test for data persistence.
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
	
	private void testSaveEventBook(boolean bool){
		assertEquals("Test saveEventBook",
				bool, fEventH.saveEventBook());
	}
	
}




