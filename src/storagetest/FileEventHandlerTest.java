package storagetest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

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
		
		PreparationCleanUp.cleanUp(baseDirectory);
		PreparationCleanUp.setUpDirectory(baseDirectory);
	}
	
	@Test
	public void testFileEventHandlerInIdealSequence(){
		testFileEventHandlerConstructor();
		testSaveNewEventHandler();
		testRetrieveEventByDate();
		testRetrieveAllEvents();
		testChangeDirectory();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Exiting, cleaning up folders");
		if(PreparationCleanUp.cleanUp(baseDirectory)){
			System.out.println("Clean up completed. bye");
		}
	}

	public void testFileEventHandlerConstructor() {
		fEventH = new FileEventHandler(baseDirectory);
		
		assertEquals("Test if there are no existing events", 
				new ArrayList<Event>(), fEventH.retrieveAllEvents());
		
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
	
		Event event = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
		event.setId(1);
		ArrayList<Event> expectedEventBook = new ArrayList<Event>();
		expectedEventBook.add(event);
		ArrayList<Event> actualEventBook = fEventH.retrieveEventByDate("20 aug 2000");
		
		assertEquals("Test retrieving from a date of the past",
				true, compareEventsArrayList(expectedEventBook,actualEventBook) );
	}
	
	public void testChangeDirectory(){
		PreparationCleanUp.cleanUp(baseDirectory);
		testSetNewDirectory();
		PreparationCleanUp.makeNewDirectory(baseDirectory + "/Event");
		fEventH.saveEventBook();
		
		//reset
		fEventH = new FileEventHandler(baseDirectory + "/Event");
		testRetrieveEventByDate();
		testRetrieveAllEvents();
	}
	
	public void testSetNewDirectory() {
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

	public void testRetrieveAllEvents() {
		ArrayList<Event> expectedList = new ArrayList<Event>();
		Event event = new Event("Event1", "31 aug 2100 23:00", "1 sep 2100 02:00");
		Event event2 = new Event("Event2", "20 aug 2000 23:00", "21 aug 2000 02:00");
		event.setId(0);
		event2.setId(1);
		expectedList.add(event2);
		expectedList.add(event);
		
//		assertEquals("Test retrieval of all events",
//				expectedList, fEventH.retrieveAllEvents());
		
		assertEquals("Test retrieval of all events", 
				true, compareEventsArrayList(expectedList, fEventH.retrieveAllEvents()));
		
	}
	
	//temp comparators
	public static boolean compareEvents(Event event1, Event event2){
		return ( event1.getId() == event2.getId() ) &&
				( event1.getName().equals( event2.getName() ) ) &&
				( event1.getStartCalendar().get(Calendar.DATE) == event2.getStartCalendar().get(Calendar.DATE) ) &&
				( event1.getStartCalendar().get(Calendar.HOUR_OF_DAY) == event2.getStartCalendar().get(Calendar.HOUR_OF_DAY) ) &&
				( event1.getStartCalendar().get(Calendar.MINUTE) == event2.getStartCalendar().get(Calendar.MINUTE) ) &&
				( event1.getEndCalendar().get(Calendar.DATE) ==  event2.getEndCalendar().get(Calendar.DATE) ) &&
				( event1.getEndCalendar().get(Calendar.HOUR_OF_DAY) ==  event2.getEndCalendar().get(Calendar.HOUR_OF_DAY) ) &&
				( event1.getEndCalendar().get(Calendar.MINUTE) ==  event2.getEndCalendar().get(Calendar.MINUTE) );
	}
	
	public static boolean compareEventsArrayList(ArrayList<Event> list1, ArrayList<Event> list2){
		if(list1.size() == list2.size()){
			for(int i=0; i<list1.size(); i++){
				if(!compareEvents(list1.get(i), list2.get(i))){
					return false;
				}
			}
		}else{
			return false;
		}
		return true;
	}
	
}




