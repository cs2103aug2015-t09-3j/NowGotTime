package project;
import java.util.*;

import object.Event;
import storage.FileHandler;


public class ProjectHandler implements ProjectManager{

	private FileHandler project;
	// String in HashMap => Progress
	private HashMap<Integer, String> map = new HashMap<Integer, String>();
	private ArrayList<Integer> projectBook = new ArrayList<Integer>();
	private ArrayList<String> projectList = new ArrayList<String>();
		
	public ProjectHandler (){
		project = new FileHandler();
	}
	
	/**
	 * Creates a Project ArrayList
	 */
	@Override
	public boolean createProject(String projectName) {
		return project.createNewProject(projectName.toLowerCase());
	}
	
	/**
	 * Adds an existing Event by ID into Project ArrayList
	 */
	@Override
	public boolean addProjectEvent(int eventId, String projectName) {

		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return false;
		} else {
			projectBook = viewProjectTimeline(projectName.toLowerCase());
			projectBook.add(eventId);
			project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
			return true;
		}
	}

	/**
	 * Deletes an existing Project ArrayList
	 */
	@Override
	public boolean deleteProject(String projectName) {
		return project.deleteProject(projectName.toLowerCase());
	}

	/**
	 * Deletes an existing Event in the Project ArrayList by the ID
	 */
	
	@Override
	public boolean deleteProjectEvent(int id, String projectName) {
		for (int savedId : projectBook) {
			if (savedId == id) {
				projectBook.remove(id);
				project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
				return true;
			}
		} return false;
	}
	
	/*
	@Override
	public boolean deleteProjectEvent(String eventName, String projectName) {
		for (Event anEvent : projectBook) {
			if (anEvent.getName().toLowerCase().equals(eventName.toLowerCase())) {
				projectBook.remove(anEvent);
				project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
				return true;
			}
		}
		return false;
	}
	*/

	/**
	 * Deletes an existing Event in the Project ArrayList by the index
	 */
	
	/*
	@Override
	public boolean deleteProjectEvent(int index, String projectName) {
		if (index < projectBook.size()) {
			//System.out.println("CHECK: " + projectBook.remove(index));
			project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
			return true;
		} else {
			return false;
		}
	}
	*/
	
	/**
	 * Edits an existing Event in the Project ArrayList by the ID
	 */
	
	@Override
	public boolean editProjectEvent(int id, int infoIndex, String newValue, String projectName) {
		if (id < 0 || !projectBook.contains(id)) {
			return false;
		} else {
			Event event = project.retrieveEventById(id);
			
			switch(infoIndex) {
				case (1): {	
					event.setName(newValue);
					break;
				}
				
				case (2): {
					event.updateStartDate(newValue); 
					break;
				}
				
				case (3): {
					event.updateEndDate(newValue);
					break;
				}
				
				case (4): {
					event.updateStartTime(newValue);
					break;
				}
				
				case (5): {
					event.updateEndTime(newValue);
					break;
				}
				
				case (6): {
					event.setAdditionalInfo(newValue);
					break;
				}
			}
			project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
			return true;
		}
	}

	
	/*
	@Override
	public boolean editProjectEvent(int eventIndex, int infoIndex, String newValue, String projectName) {
		if (eventIndex < 0 || eventIndex > projectBook.size()) {
			return false;
		} else {
			Event event = projectBook.get(eventIndex);
			
			switch(infoIndex) {
				case (1): {	
					event.setName(newValue);
					break;
				}
				
				case (2): {
					event.updateStartDate(newValue); 
					break;
				}
				
				case (3): {
					event.updateEndDate(newValue);
					break;
				}
				
				case (4): {
					event.updateStartTime(newValue);
					break;
				}
				
				case (5): {
					event.updateEndTime(newValue);
					break;
				}
				
				case (6): {
					event.setAdditionalInfo(newValue);
					break;
				}
			}
			project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
			return true;
		}
	}
	*/
	
	/**
	 * Edits an existing Event in the Project ArrayList by the event name
	 */
	@Override
	public boolean editProjectEvent(Event eventName, int infoIndex, String newValue, String projectName) {
		//for testing: System.out.println("1" + projectBook);
	
		for (int i=0; i<projectBook.size(); i++) {
			if (eventName.getName().toLowerCase().equals(projectBook.get(i).getName().toLowerCase())) {
				int eventIndex = i;
				
				return editProjectEvent(eventIndex, infoIndex, newValue, projectName);
				//project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
				//return true;
			}
		} return false;
	}

	/**
	 * View an ArrayList of existing Projects
	 */
	@Override
	public ArrayList<String> listExistingProjects() {
		
		projectList = project.getListOfExistingProject();
		return projectList;
	}
	
	/**
	 * View Project timeline (ArrayList of Events) by the Project name
	 */
	@Override
	public ArrayList<Integer> viewProjectTimeline(String projectName) {
		
		projectBook = project.retrieveProjectTimeLine(projectName.toLowerCase());
		return projectBook;
		
		project.
	}

	/**
	 * View Project timeline (ArrayList of Events) by the Project index
	 */
	@Override
	public ArrayList<Event> viewProjectTimeline(int index) {
		// TODO Auto-generated method stub
		
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			projectBook = project.retrieveProjectTimeLine(projectName);
			return projectBook;
		} else {
			return null;
		}
	}
	
	/*
	 -Change from arraylist of events to arraylist of int (event IDs). Edit, add, del by ID as well.
	 -Change all the saves to saveAll.
	 -Change arraylist of events to arraylist of items => includes both to-dos and events.
	 	--> Create event = 1 ID. I call event in projects by ID.(in Item.java)
	
	 -Have a search function to search through project book (arraylist of events). Return event ID.
	 -Progress bar function, show % completed.
	 -Progress function. => Hashmap in project (containing int(ID) and string(Addinfo)). [HM key = ID]
			==> String Project Name, Int ID and String Addinfo passed to me.
			==> setProgress (String progressString, String projectNAme, Int ID)
	 -Progress (delete and edit) functions too.
	 		==> deleteProgress (); and editProgress ();
	 -Have a method to call RX for viewing. I pass ID to RX, RX returns name of event. 
			==> I return Stef arraylist of string (events).
	 
	 **see phone picture. Note that Jon's event does not have additional info. only proj has. thus nid to
	   add additional info into the event. BUT I DON'T SAVE IT, else Jon's events will also have additional
	   info. Additional info is exclusive only to projects.**
	*/
}
