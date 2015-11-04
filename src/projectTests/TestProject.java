package projectTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import object.Event;
import object.Item;
import project.Projects;
import storage.FileHandler;

public class TestProject {

	protected static FileHandler clear;
	private Projects project;
	
	// Make sure dates are in DD MMM YYY format 
	private Event event1;
	private Event event2;
	private Event event3;
	
	@Before
	public void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Files Cleared for Event Class Testing");
		
		Item.setCounter(0);
	    event1 = new Event("EventName1", "25 Oct 2015", "26 Oct 2015", "10:00", "21:00", "");
	    event2 = new Event("EventName2", "21 Oct 2015", "31 Oct 2015", "19:00", "23:00", "");
	    event3 = new Event("EventName3", "21 Oct 2015", "30 Oct 2015", "17:00", "02:00", "");
	    project = new Projects();
	    
	    clear.saveNewEventHandler(event1);
	    clear.saveNewEventHandler(event2);
	    clear.saveNewEventHandler(event3);
	    
	    
	}
	
	@After
	public void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Test Files used in Event Testing Cleared");
	}
	
	@Test
	public void testCreateDeleteEditProject() throws AssertionError {
		try {
			assertTrue("Failed to create project", project.createProject("cS2103t"));
			assertTrue("Failed to create project beginning with space", project.createProject("       cs2101"));
			
			assertFalse("Successfully created project with symbols", project.createProject("A'\\/;*^<>|?"));
			//Parser handles create null project and FileProjHandler handles "" as exception
			assertFalse("Successfully created project with no project name", project.createProject(""));
			assertFalse("Successfully created project with too long a name", project.createProject("welovedamith"
					+ "welovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamith"
					+ "welovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamith"
					+ "welovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamithwelovedamith"));
			
			//delete
			assertTrue("Failed to delete project", project.deleteProject("cS2103t"));
			
			//Cannot delete damithsucks because damithsucks does not exist = we love damith
			assertFalse("Successfully deleted a non-existent project", project.deleteProject("damithsucks"));
			
			//System.out.println(project.listExistingProjects());
			//edit
			assertTrue("Failed to edit project name", project.editProjectName("cs2103T", "cs2101"));
			
			assertFalse("Successfully edited a non-existent project name", project.editProjectName("ee2020", "ma1506"));
			assertFalse("Successfully edited an invalid new project name", project.editProjectName("|'\\/?*:><^", "cs2103T"));
			assertFalse("Successfully renamed a project with same project name", project.editProjectName("cs2103T", "cs2103T"));
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testlistExistingProjects() throws AssertionError {
		try {
			
			String[] strArray = new String[]{};
			assertArrayEquals("Failed to list empty existing projects", strArray, project.listExistingProjects().toArray());
			
			project.createProject("helloAll");
			project.createProject("helloWorld");
			String[] strArray2 = new String[]{"helloall", "helloworld"};
			
			assertArrayEquals("Failed to list existing projects", strArray2, project.listExistingProjects().toArray());	
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}

	@Test
	public void testAddViewEditDeleteProjectTimeline() throws AssertionError {
		try {
			
			
			project.createProject("helloAll");
			project.createProject("helloWorld");
			
			//Add
			assertTrue("Failed to add event to project by index", project.addProjectEvent(event1, 0));
			assertTrue("Failed to add event to project by name", project.addProjectEvent(event2, "helloall"));
			
			//View
			Integer[] intArray = new Integer[]{1, 0}; //Sorted by timing
			assertArrayEquals("Failed to view project timeline by name", intArray, project.viewProjectTimeline("helloall").toArray());
			assertArrayEquals("Failed to view project timeline by index", intArray, project.viewProjectTimeline(0).toArray());
		
			Event[] eventArray = new Event[]{event2, event1}; //Sorted too!
			assertArrayEquals("Failed to view project event timeline by name", eventArray, project.viewProjectTimelineInEvents("helloAll").toArray());
			assertArrayEquals("Failed to view project event timeline by index", eventArray, project.viewProjectTimelineInEvents(0).toArray());
			
			//Pass to-be-edited Event to Parser to call editing in Event Class.
			assertEquals("Failed to edit Event", event2, project.editEvent(0, "helloAll"));
			
			//Delete
			assertTrue("Failed to delete Event by event, projectname", project.deleteProjectEvent(event1, "helloAll"));
			assertFalse("Successfully deleted non-existent Event by event, projectname", project.deleteProjectEvent(event3, "helloAll"));
			
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	
}
