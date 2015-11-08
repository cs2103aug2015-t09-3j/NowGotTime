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
	 * @param projectName is the name of the Project to be created
	 * @return returns true once Project is created, else return false
	 */
	public boolean createProject(String projectName) {
		return project.createNewProject(projectName.toLowerCase().trim());
	}
	
	/**
	 * Deletes an existing Project from the ArrayList
	 * @param projectName is the name of the Project to be deleted
	 * @return returns true once Project is deleted, else return false
	 */
	public boolean deleteProject(String projectName) {
		return project.deleteProject(projectName.toLowerCase());
	}
	
	/**
	 * Edits an existing Project from the ArrayList
	 * @param newProjectName is the name of the Project that is edited to
	 * @param oldProjectName is the name of the Project that is being edited
	 * @return returns true once Project is edited, else return false.
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
	 * @return returns an ArrayList of Strings where each String is the name of existing Projects
	 */
	public ArrayList<String> listExistingProjects() {
		return project.getListOfExistingProject();
	}
	
	/**
	 * Adds an existing Event into a Project by the Project Name
	 * @param event is the Event that is being added into the Project
	 * @param projectName is the Project that the Event is added to
	 * @return returns true once Event is added into Project, else return false
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
	 * @param event is the Event that is being added into the Project
	 * @param index is the index of the Project that the Event is added to
	 * @return returns true once Event is added into Project, else return false
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
	 * @param event is the Event that is to be deleted from a Project
	 * @param projectName is the name of the Project that is to be deleted from
	 * @return returns true once the Event is deleted from the Project, else return false
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
	 * @param event is the Event that is to be deletd from a Project
	 * @param index is the index of the Project that the Event is to be deleted from
	 * @return returns true once the Event is deleted from the Project, else return false
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
	 * @param eventArrayListIndex is the index of the ArrayList that the Event is in
	 * @param projectName is the name of the Project that the Event is being deleted from
	 * @return returns true once Event is deleted from the Project, else return false
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
	 * @param arrayIndex is the index of the ArrayList that holds the Event to be edited
	 * @param projectName is the name of the Project that the Event is in
	 * @return returns an Event that is to be edited, else return null 
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
	 * @param projectName is the name of the Project that is being viewed
	 * @return returns an ArrayList of Integers if Project exists, else return null
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
	 * @param index is the Index of the Project that is being viewed
	 * @return returns an ArrayList of Integers if Project exists, else return null
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
	 * @param projectName is the name of the Project that is being viewed
	 * @return returns an ArrayList of Events if Project exists, else return null
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
	 * @param index is the Index of the Project that is being viewed
	 * @return returns an ArrayList of Events if Project exists, else return null
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
	
	/**
	 * Searches for a Project in an ArrayList of Projects
	 * @param name is part of the name of the Project that is searched
	 * @return returns an ArrayLst of Strings that contains Projects that contain the String name. 
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
	 * @param projectName is the Project name of the Project
	 * @return returns a double value of the percentage of checked Events, else return negative value
	 */
	public double progressBar(String projectName) {
		if (checkIfProjectExists(projectName)) {
			return pHandler.progressBar(projectName.toLowerCase());
		}
		return INVALID_PROGRESS_PERCENTAGE;
	}
	
	/**
	 * Adds a message into a HashMap of a Project
	 * @param index is the index of the Event in a Project
	 * @param projectName is the name of the Project that the message is being added to
	 * @param progressMessage is the message that is being added to an Event
	 * @return returns true once message is added to the Event, else return false
	 */
	public boolean addProgressMessage(int index, String projectName, String progressMessage) {
		if (checkIfProjectExists(projectName)) {
			return pHandler.addProgressMessage(index, progressMessage, projectName.toLowerCase());
		} 
		return false;
	}
	
	/**
	 * Deletes a message from a HashMap of a Project
	 * @param index is the index of the Event in a Project
	 * @param projectName is the name of the Project that the message is being deleted from
	 * @return returns true once message is deleted from the Event, else return false
	 */
	public boolean deleteProgressMessage(int index, String projectName) {
		if (checkIfProjectExists(projectName)) {
			return pHandler.deleteProgressMessage(index, projectName.toLowerCase());
		} 
		return false;
	}
	
	/**
	 * Edits a message in a HashMap of a Project
	 * @param index is the index of the Event in a Project
	 * @param newProressMessage is the message that is being updated to 
	 * @param projectNmae is the name of the Project that the new message is being added to
	 * @return returns true once the message is edited in the Event, else return false
	 */
	public boolean editProgressMessage(int index, String newProgressMessage, String projectName) {
		if (checkIfProjectExists(projectName)) {
			return pHandler.editProgressMessage(index, newProgressMessage, projectName.toLowerCase());
		} 
		return false;
	}

	/**
	 * Checks if Project exists in the ArrayList
	 * @param projectName is the name of the Project that is being checked
	 * @return returns true if the searched Project exists, else return false
	 */
	public boolean checkIfProjectExists(String projectName) {
		return listExistingProjects().contains(projectName.toLowerCase());
	}
	
	/**
	 * Views by Project Name, a Project Timeline with the Progress Message each Event holds
	 * @param projectName is the name of the Project that is being viewed
	 * @return returns an ArrayList of Events that holds the Events and its Progress Message
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
	 * @param index is the index of the Project that is being viewed
	 * @return returns an ArrayList of Events that holds the Events and its Progress Message
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
	 * @param item is the item that is to be found 
	 * @return returns a String that is the Project the item is found in, else return null
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
