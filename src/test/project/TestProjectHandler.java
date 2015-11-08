//@@author A0130445R
package test.project;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import object.Event;
import object.Item;
import project.ProjectHandler;
import project.Projects;
import storage.FileHandler;

public class TestProjectHandler {

	private static final String PROJECT_NAME_PROJECT = "Project";
	private static final String PROJECT_HELLOALL = "helloAll";
	private static final String MESSAGE_TEAR_DOWN = "Test Files used in ProjectHandler Testing Cleared";
	private static final String MESSAGE_SET_UP = "Files Cleared for ProjectHandler Class Testing";
	protected static FileHandler clear;
	private ProjectHandler pH;
	private Projects project;
	
	// Make sure dates are in DD MMM YYY format 
	private Event event1;
	private Event event2;
	private Event event3;
	
	@Before
	public void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println(MESSAGE_SET_UP);
		
		Item.setCounter(0);
	    event1 = new Event("EventName1", "25 Oct 2015", "26 Oct 2015", "10:00", "21:00", "");
	    event2 = new Event("EventName2", "21 Oct 2015", "31 Oct 2015", "19:00", "23:00", "");
	    event3 = new Event("EventName3", "21 Oct 2015", "30 Oct 2015", "17:00", "02:00", "");
	    pH = new ProjectHandler();
	    project = new Projects();
	    
	    clear.saveNewEventHandler(event1);
	    clear.saveNewEventHandler(event2);
	    clear.saveNewEventHandler(event3);
	}
	
	@After
	public void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println(MESSAGE_TEAR_DOWN);
	}
	
	@Test
	public void testAddViewEditDeleteProjectHandlerTimeline() throws AssertionError {
		try {
			
			project.createProject(PROJECT_HELLOALL);
			
			//Add
			assertTrue("Failed to add event to projecthandler by name", pH.addProjectEvent(event1, PROJECT_HELLOALL));
			
			//View
			Integer[] intArray = new Integer[]{0}; 
			assertArrayEquals("Failed to view projecthandler timeline by name", intArray, pH.viewProjectTimeline(PROJECT_HELLOALL).toArray());
			
			Event[] eventArray = new Event[]{event1}; 
			assertArrayEquals("Failed to view projecthandler event timeline by name", eventArray, pH.viewProjectTimelineInEvents(PROJECT_HELLOALL).toArray());
			
			//Pass to-be-edited Event to Parser to call editing in Event Class.
			assertEquals("Failed to edit Event projecthandler", event1, pH.editEvent(0, PROJECT_HELLOALL));
			
			//Delete
			assertTrue("Failed to delete Event by event, projectname in project handler", pH.deleteProjectEvent(event1, PROJECT_HELLOALL));
			assertFalse("Successfully deleted non-existent Event by event, projectname in project handler", pH.deleteProjectEvent(event3, PROJECT_HELLOALL));
			
			project.addProjectEvent(event3, PROJECT_HELLOALL);
			assertTrue("Failed to delete Event by eventindex, projectname in project handler", pH.deleteProjectEvent(0, PROJECT_HELLOALL));
			assertFalse("Successfully deleted non-existent Event by eventindex, projectname in project handler", pH.deleteProjectEvent(1, PROJECT_HELLOALL));		
		
			project.createProject("new name");
			assertTrue("Failed to rename project", pH.editProjectName("new name", "helloall"));

		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testProgressMessageAndProgressBar() throws AssertionError {
		try {
			
			project.createProject(PROJECT_NAME_PROJECT);
			
			// Sort method is tested in add. Sort is private.
			pH.addProjectEvent(event1, PROJECT_NAME_PROJECT);
			pH.addProjectEvent(event2, PROJECT_NAME_PROJECT);
			pH.addProjectEvent(event3, PROJECT_NAME_PROJECT);
			
			//Add prog msg
			//Events sorted: event3,2,1
			assertTrue("Failed to add progress message", pH.addProgressMessage(0, "hello", PROJECT_NAME_PROJECT));
			assertFalse("Successfully added progress message in nonexistent event", pH.addProgressMessage(3, "Harro", PROJECT_NAME_PROJECT));
			
			//Edit prog msg
			assertTrue("Failed to edit progress message", pH.editProgressMessage(0, "This is new", PROJECT_NAME_PROJECT));
			assertFalse("Successfully edited progress message in nonexistent event", pH.editProgressMessage(3, "new message", PROJECT_NAME_PROJECT));
			
			//View prog msg
			assertEquals("Failed to view Event progress timeline", "This is new", pH.viewEventProgressTimeline(PROJECT_NAME_PROJECT).get(0).getAdditionalInfo());
			
			//Delete prog msg
			assertTrue("Failed to delete progress message", pH.deleteProgressMessage(0, PROJECT_NAME_PROJECT));
			assertFalse("Successfully deleted progress message in nonexistent event", pH.deleteProgressMessage(3,PROJECT_NAME_PROJECT));
			assertFalse("Successfully deleted progress message in event that has no message", pH.deleteProgressMessage(0, PROJECT_NAME_PROJECT));
			
			event1.setDone(true);
			
			FileHandler fHandler = new FileHandler();
			fHandler.saveNewEventHandler(event1);
			
			assertEquals("Failed to calculate percentage in project handler", 33.33, pH.progressBar(PROJECT_NAME_PROJECT), 0.01);
			assertEquals("Successfully calculated percentage of a non-existent project in project handler", 0.0, pH.progressBar("nonexistent"), 0.01);
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testFindItem() throws AssertionError {
		try {
			
			project.createProject(PROJECT_NAME_PROJECT);
			
			pH.addProjectEvent(event1, PROJECT_NAME_PROJECT);
			pH.addProjectEvent(event2, PROJECT_NAME_PROJECT);
			pH.addProjectEvent(event3, PROJECT_NAME_PROJECT);
			
			assertTrue("Failed to search for existing event in project", pH.findItem(0, PROJECT_NAME_PROJECT));
			assertFalse("Successfully search for non-existing event in project", pH.findItem(3, PROJECT_NAME_PROJECT));
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
}

