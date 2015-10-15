package project;
import java.util.*;

import object.Event;
import storage.FileHandler;


public class ProjectHandler implements ProjectManager{

	private FileHandler project;
	// String in HashMap => Progress
	private HashMap<Integer, String> map;
	private ArrayList<Integer> projectBook = new ArrayList<Integer>();
		
	public ProjectHandler (){
		project = new FileHandler();
	}		
	
	/**
	 * Adds an existing Event by ID into Project ArrayList
	 */
	@Override
	public boolean addProjectEvent(int id, String projectName) {
		if (id < 0) {
			return false;
		} else {
			projectBook = viewProjectTimeline(projectName.toLowerCase());
			projectBook.add(id);
			project.saveEditedProjectDetails(projectBook, map, projectName.toLowerCase());
			return true;
		}
	}

	/**
	 * Deletes an existing Event in the Project ArrayList by the ID
	 */
	
	@Override
	public boolean deleteProjectEvent(int id, String projectName) {
		for (int savedId : projectBook) {
			if (savedId == id) {
				projectBook.remove(id);
				map.remove(id);
				project.saveEditedProjectDetails(projectBook, map, projectName.toLowerCase());
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
					map.put(id, newValue);
					break;
				}
			}
			project.saveEditedProjectDetails(projectBook, map, projectName.toLowerCase());
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
	
	/*
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
	*/

	
	/**
	 * View Project timeline (ArrayList of Events) by the Project name
	 */
	
	
	@Override
	public ArrayList<Integer> viewProjectTimeline(String projectName) {
		
		projectBook = project.retrieveProjectTimeLine(projectName.toLowerCase());
		map = project.retrieveProjectProgress();
		return projectBook;
	}

	/**
	 * View Project timeline (ArrayList of Events) by the Project index
	 */
	/*
	@Override
	public ArrayList<Integer> viewProjectTimeline(int index) {
		
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			projectBook = project.retrieveProjectTimeLine(projectName);
			map = project.retrieveProjectProgress();
			return projectBook;
		} else {
			return null;
		}
	}
	*/
	
	@Override
	public ArrayList<Event> viewEventProgressTimeline(String projectName) {
		
		ArrayList<Integer> arrayListOfId = viewProjectTimeline(projectName);
		ArrayList<Event> eventProgress = new ArrayList<Event>();
		
		for (int id : arrayListOfId) {
			Event event = project.retrieveEventById(id);
			String progressMessage = map.get(id);
			event.setAdditionalInfo(progressMessage);
			eventProgress.add(event);
		}
		return eventProgress;
	}

	/*
	 -Have a search function to search through project book (arraylist of events). Return event ID.
	 -Progress bar function, show % completed.
	*/
}
