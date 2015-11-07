//@@author A0130445R
package project;

import java.util.ArrayList;

import storage.FileHandler;
import object.Event;
import object.Item;

public class Projects {

	private static final int INVALID_PROGRESS_PERCENTAGE = -1;
	private FileHandler project;
	private ProjectHandler pHandler;
	private ArrayList<String> projectList; 
	
	public Projects (){
		project = new FileHandler();
		projectList = project.getListOfExistingProject();
		pHandler = new ProjectHandler();
	}
	
	/**
	 * Adds a new Project into the ArrayList
	 */
	public boolean createProject(String projectName) {
		return project.createNewProject(projectName.toLowerCase().trim());
	}
	
	/**
	 * Deletes an existing Project from the ArrayList
	 */
	public boolean deleteProject(String projectName) {
		return project.deleteProject(projectName.toLowerCase());
	}
	
	/**
	 * Edits an existing Project from the ArrayList
	 */
	public boolean editProjectName(String newProjectName, String oldProjectName) {
		/*if (createProject(newProjectName) && pHandler.editProjectName(newProjectName.toLowerCase(), oldProjectName.toLowerCase())
				&& deleteProject(oldProjectName)) {
				return true;
		}
		return false;
		*/
		ArrayList<String> checkExistingProject = new ArrayList<String>();
		checkExistingProject = listExistingProjects();
		if (newProjectName.equals(oldProjectName) || !(checkExistingProject.contains(oldProjectName))) {
			return false;
		}
		
		if (createProject(newProjectName)) {
			if (pHandler.editProjectName(newProjectName.toLowerCase(), oldProjectName.toLowerCase())) {
				if (deleteProject(oldProjectName)) {
					return true;
				} else {
					deleteProject(newProjectName);
				}
			} else {
				deleteProject(newProjectName);
			}
		} 
		return false;
			
	}
	
	/**
	 * Views an ArrayList of existing Projects
	 */
	public ArrayList<String> listExistingProjects() {
		return project.getListOfExistingProject();
	}
	
	/**
	 * Adds an existing Event into a Project by the Project Name
	 */
	public boolean addProjectEvent(Event event, String projectName) {
		if (!checkIfProjectExists(projectName)) {
			return false;
		} else {
		return pHandler.addProjectEvent(event, projectName);
		}
	}
	
	/**
	 * Adds an existing Event into a Project by the Project Index
	 */
	public boolean addProjectEvent(Event event, int index) {
		updateProjectList();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return addProjectEvent(event, projectName);
		} else {
			return false;
		}
	}
	
	/**
	 * Removes an Event added into a Project by Project Name and Event
	 */
	public boolean deleteProjectEvent(Event event, String projectName) {
		if (!checkIfProjectExists(projectName)) {
			return false;
		} else {
		return pHandler.deleteProjectEvent(event, projectName);
		}
	}
	
	/**
	 * Removes an Event added into a Project by Project Index and Event
	 */
	public boolean deleteProjectEvent(Event event, int index) {
		updateProjectList();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return deleteProjectEvent(event, projectName);
		} else {
			return false;
		}
	}

	/**
	 * Updates Project List to its latest copy
	 */
	public void updateProjectList() {
		projectList = listExistingProjects();
	}
	
	/**
	 * Removes an Event added into a Project by Project Name and Event Index
	 */
	public boolean deleteProjectEvent(int eventArrayListIndex, String projectName) {
		if (!checkIfProjectExists(projectName)) {
			return false;
		} else {
		return pHandler.deleteProjectEvent(eventArrayListIndex, projectName);
		}
	}
	
	/**
	 * Extracts the Event to be edited by Project Name and Event Index
	 */
	public Event editEvent(int arrayIndex, String projectName) {
		if (!checkIfProjectExists(projectName)) {
			return null;
		} else {
		return pHandler.editEvent(arrayIndex, projectName);
		}
	}
	
	/**
	 * View an ArrayList of Event IDs by Project Name
	 */
	public ArrayList<Integer> viewProjectTimeline(String projectName) {
		if (!checkIfProjectExists(projectName)) {
			return null;
		} else {
		return pHandler.viewProjectTimeline(projectName);
		}
	}
	
	/**
	 * View an ArrayList of Event IDs by Project Index
	 */
	public ArrayList<Integer> viewProjectTimeline(int index) {
		updateProjectList();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return viewProjectTimeline(projectName);
		} else {
			return null;
		}
	}
	
	/**
	 * View an ArrayList of Events by Project Name
	 */
	public ArrayList<Event> viewProjectTimelineInEvents(String projectName) {
		if (!checkIfProjectExists(projectName)) {
			return null;
		} else {
		return pHandler.viewProjectTimelineInEvents(projectName);
		}
	}
	
	/**
	 * View an ArrayList of Events by Project Index
	 */
	public ArrayList<Event> viewProjectTimelineInEvents(int index) {
		updateProjectList();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return viewProjectTimelineInEvents(projectName);
		} else {
			return null;
		}
	}
	
	
	
	/*
	public boolean editProjectEvent(Event event, int infoIndex, String newValue, String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return false;
		} else {
		return pHandler.editProjectEvent(event, infoIndex, newValue, projectName);
		}
	}
	
	public boolean editProjectEvent(Event event, int infoIndex, String newValue, int index) {
		projectList = listExistingProjects();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return editProjectEvent(event, infoIndex, newValue, projectName);
		} else {
			return false;
		}
	}
	*/
	
	/**
	 * Searches for a Project in an ArrayList of Projects
	 */
	public ArrayList<String> searchProjects (String name) {
		projectList = project.getListOfExistingProject();
		ArrayList<String> searchedNames = new ArrayList<String>();
		for (String projectName : projectList) {
			if (projectName.contains(name)) {	
				searchedNames.add(projectName);
			}
		}
		return searchedNames;
	}
	
	/**
	 * Calculates the percentage of completed Events in a Project
	 */
	public double progressBar(String projectName) {
		if (checkIfProjectExists(projectName)) {
			return pHandler.progressBar(projectName.toLowerCase());
		}
		return INVALID_PROGRESS_PERCENTAGE;
	}
	
	/**
	 * Adds a message into a HashMap of a Project
	 */
	public boolean addProgressMessage(int index, String projectName, String progressMessage) {
		if (checkIfProjectExists(projectName)) {
			return pHandler.addProgressMessage(index, progressMessage, projectName.toLowerCase());
		} 
		return false;
	}
	
	/**
	 * Deletes a message from a HashMap of a Project
	 */
	public boolean deleteProgressMessage(int index, String projectName) {
		if (checkIfProjectExists(projectName)) {
			return pHandler.deleteProgressMessage(index, projectName.toLowerCase());
		} 
		return false;
	}
	
	/**
	 * Edits a message in a HashMap of a Project
	 */
	public boolean editProgressMessage(int index, String newProgressMessage, String projectName) {
		if (checkIfProjectExists(projectName)) {
			return pHandler.editProgressMessage(index, newProgressMessage, projectName.toLowerCase());
		} 
		return false;
	}

	/**
	 * Checks if Project exists in the ArrayList
	 */
	public boolean checkIfProjectExists(String projectName) {
		return listExistingProjects().contains(projectName.toLowerCase());
	}
	
	/**
	 * Views by Project Name, a Project Timeline with the Progress Message each Event holds
	 */
	public ArrayList<Event> viewEventProgressTimeline(String projectName) {
		if (!checkIfProjectExists(projectName)) {
			return null;
		} else {
		return pHandler.viewEventProgressTimeline(projectName.toLowerCase());
		}
	}
	
	/**
	 * Views by Project Index, a Project Timeline with the Progress Message each Event holds
	 */
	public ArrayList<Event> viewEventProgressTimeline(int index) {
		updateProjectList();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return viewEventProgressTimeline(projectName);
		} else {
			return null;
		}
	}
	
	/**
	 * Searches for an Event in a Project
	 */
	public String searchItem(Item item) {
	    int id = item.getId();
	    
	    updateProjectList();
	    
	    for (String project : projectList) {
	        if (pHandler.findItem(id, project)) {
	            return project;
	        }
	    }
	    
	    return null;
	}
}
