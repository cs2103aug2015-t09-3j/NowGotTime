package test.project;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
		System.out.println("Files Cleared for Project Class Testing");
		
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
		System.out.println("Test Files used in Project Testing Cleared");
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
			assertFalse("Successfully added even into non-existent project by index", project.addProjectEvent(event3, 3));
			assertTrue("Failed to add event to project by name", project.addProjectEvent(event2, "helloall"));
			assertFalse("Successfully added even into non-existent project by projectname", project.addProjectEvent(event3, "projnotexist"));
			
			//View
			Integer[] intArray = new Integer[]{1, 0}; //Sorted by timing
			assertArrayEquals("Failed to view project timeline by name", intArray, project.viewProjectTimeline("helloall").toArray());
			assertNull("Successfully view non-existent project's timeline by name", project.viewProjectTimeline("nonexistent"));
			assertArrayEquals("Failed to view project timeline by index", intArray, project.viewProjectTimeline(0).toArray());
			assertNull("Successfully view non-existent project's timeline by index", project.viewProjectTimeline(3));
			
			Event[] eventArray = new Event[]{event2, event1}; //Sorted too!
			assertArrayEquals("Failed to view project event timeline by name", eventArray, project.viewProjectTimelineInEvents("helloAll").toArray());
			assertNull("Successfully viewed non-existent project event timeline by name", project.viewProjectTimelineInEvents("nonexistent"));
			assertArrayEquals("Failed to view project event timeline by index", eventArray, project.viewProjectTimelineInEvents(0).toArray());
			assertNull("Successfully viewed non-existent project event timeline by index", project.viewProjectTimelineInEvents(3));
			
			//Pass to-be-edited Event to Parser to call editing in Event Class.
			assertEquals("Failed to edit Event", event2, project.editEvent(0, "helloAll"));
			assertNull("Successfully edited event in non-existent project", project.editEvent(0, "nonexistent"));
			
			//Delete
			assertTrue("Failed to delete Event by event, projectname", project.deleteProjectEvent(event1, "helloAll"));
			assertFalse("Successfully deleted event in non-existent project (event, projectname)", project.deleteProjectEvent(event2, "nonexistent"));
			assertFalse("Successfully deleted non-existent Event by event, projectname", project.deleteProjectEvent(event3, "helloAll"));
			
			assertTrue("Failed to delete Event by event, index", project.deleteProjectEvent(event2, 0));
			assertFalse("Successfully deleted event in non-existent project (event, index)", project.deleteProjectEvent(event2, 3));
			assertFalse("Successfully deleted non-existent Event by event, index", project.deleteProjectEvent(event3, 0));
			
			project.addProjectEvent(event3, "helloworld");
			assertTrue("Failed to delete Event by eventArrayListindex, projectname", project.deleteProjectEvent(0, "helloworld"));
			assertFalse("Successfully deleted event in non-existent project (eventALindex, projectname)", project.deleteProjectEvent(0, "nonexistent"));
			assertFalse("Successfully deleted non-existent Event by eventArrayListindex, projectname", project.deleteProjectEvent(1, "helloworld"));		
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testSearch() throws AssertionError{
		try {
			ArrayList<String> searchedNames = new ArrayList<String>();
			ArrayList<String> emptyArray = new ArrayList<String>();
			searchedNames.add("project1");
			searchedNames.add("project2");
			searchedNames.add("project3");
			
			project.createProject("project1");
			project.createProject("project2");
			project.createProject("project3");
			
			assertEquals("Failed to search existing projects", searchedNames, project.searchProjects("project"));
			assertEquals("Successfully searched non existing project", emptyArray, project.searchProjects("lala"));
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testProgressBar() throws AssertionError{
		try {
			
			project.createProject("Project");
			project.addProjectEvent(event1, "Project");
			project.addProjectEvent(event2, "Project");
			project.addProjectEvent(event3, "Project");
			
			event1.setDone(true);
			
			FileHandler fHandler = new FileHandler();
			fHandler.saveNewEventHandler(event1);
			
			assertEquals("Failed to calculate percentage", 33.33, project.progressBar("Project"), 0.01);
			assertEquals("Successfully calculated percentage of a non-existent project", -1, project.progressBar("nonexistent"), 0.01);
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}		
	
	@Test
	public void testAddEditDeleteViewProgress() throws AssertionError{
		try {
			project.createProject("Project");
			project.addProjectEvent(event1, "Project");
			project.addProjectEvent(event2, "Project");
			project.addProjectEvent(event3, "Project");
			
			// sorted event 3,2,1
			
			assertTrue("Failed to add progress message", project.addProgressMessage(0, "Project", "Progress message here"));
			assertFalse("Successfully added progress message to non-existent project", project.addProgressMessage(0, "nonexistent", "progress Message here"));
			
			assertTrue("Failed to edit progress message", project.editProgressMessage(0, "this is new", "Project"));
			assertFalse("Successfully edited progress message in non-existent project", project.editProgressMessage(0, "this is new", "nonexistent"));
	
			assertEquals("Failed to view progress message by index", "this is new", project.viewEventProgressTimeline(0).get(0).getAdditionalInfo());
			assertEquals("Failed to view progress message by name", "this is new", project.viewEventProgressTimeline("Project").get(0).getAdditionalInfo());
			assertNull("Successfully viewed progress message in nonexistent project by index", project.viewEventProgressTimeline(2));
			assertNull("Successfully viewed progress message in nonexistent project by name", project.viewEventProgressTimeline("nonexistent"));
			
			assertTrue("Failed to delete progress message", project.deleteProgressMessage(0, "Project"));
			assertFalse("Successfully deleted progress message in non-existent project", project.deleteProgressMessage(0, "nonexistent"));
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testSearchItem() throws AssertionError{
		try {
			project.createProject("Project");
			project.addProjectEvent(event1, "Project");
			project.addProjectEvent(event2, "Project");
			
			//tolowercase
			assertEquals("Failed to search item", "project", project.searchItem(event1));
			assertNull("Successfully searched non exiting item", project.searchItem(event3));
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}		
}
