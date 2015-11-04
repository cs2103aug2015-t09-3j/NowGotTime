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
	 * Creates a Project ArrayList
	 */
	public boolean createProject(String projectName) {
		return project.createNewProject(projectName.toLowerCase().trim());
	}
	
	/**
	 * Deletes an existing Project ArrayList
	 */
	public boolean deleteProject(String projectName) {
		return project.deleteProject(projectName.toLowerCase());
	}
	
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
	 * View an ArrayList of existing Projects
	 */
	public ArrayList<String> listExistingProjects() {
		return project.getListOfExistingProject();
	}
	
	
	public boolean addProjectEvent(Event event, String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return false;
		} else {
		return pHandler.addProjectEvent(event, projectName);
		}
	}
	
	public boolean addProjectEvent(Event event, int index) {
		projectList = listExistingProjects();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return addProjectEvent(event, projectName);
		} else {
			return false;
		}
	}
	
	public boolean deleteProjectEvent(Event event, String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return false;
		} else {
		return pHandler.deleteProjectEvent(event, projectName);
		}
	}
	
	public boolean deleteProjectEvent(Event event, int index) {
		projectList = listExistingProjects();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return deleteProjectEvent(event, projectName);
		} else {
			return false;
		}
	}
	
	public boolean deleteProjectEvent(int eventArrayListIndex, String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return false;
		} else {
		return pHandler.deleteProjectEvent(eventArrayListIndex, projectName);
		}
	}
	
	public Event editEvent(int arrayIndex, String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return null;
		} else {
		return pHandler.editEvent(arrayIndex, projectName);
		}
	}
	
	/**
	 * View Project timeline (ArrayList of Events) by the Project name
	 */
	public ArrayList<Integer> viewProjectTimeline(String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return null;
		} else {
		return pHandler.viewProjectTimeline(projectName);
		}
	}
	
	/**
	 * View Project timeline (ArrayList of Events) by the Project index
	 */
	public ArrayList<Integer> viewProjectTimeline(int index) {
		projectList = listExistingProjects();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return viewProjectTimeline(projectName);
		} else {
			return null;
		}
	}
	
	public ArrayList<Event> viewProjectTimelineInEvents(String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return null;
		} else {
		return pHandler.viewProjectTimelineInEvents(projectName);
		}
	}
	
	public ArrayList<Event> viewProjectTimelineInEvents(int index) {
		projectList = listExistingProjects();
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
	
	public double progressBar(String projectName) {
		if (listExistingProjects().contains(projectName.toLowerCase())) {
			return pHandler.progressBar(projectName.toLowerCase());
		}
		return INVALID_PROGRESS_PERCENTAGE;
	}
	
	public boolean addProgressMessage(int index, String projectName, String progressMessage) {
		if (listExistingProjects().contains(projectName.toLowerCase())) {
			return pHandler.addProgressMessage(index, progressMessage, projectName.toLowerCase());
		} 
		return false;
	}
	
	public boolean deleteProgressMessage(int index, String projectName) {
		if (listExistingProjects().contains(projectName.toLowerCase())) {
			return pHandler.deleteProgressMessage(index, projectName.toLowerCase());
		} 
		return false;
	}
	
	public boolean editProgressMessage(int index, String newProgressMessage, String projectName) {
		if (listExistingProjects().contains(projectName.toLowerCase())) {
			return pHandler.editProgressMessage(index, newProgressMessage, projectName.toLowerCase());
		} 
		return false;
	}
	
	public ArrayList<Event> viewEventProgressTimeline(String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return null;
		} else {
		return pHandler.viewEventProgressTimeline(projectName.toLowerCase());
		}
	}
	
	public ArrayList<Event> viewEventProgressTimeline(int index) {
		projectList = listExistingProjects();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return viewEventProgressTimeline(projectName);
		} else {
			return null;
		}
	}
	
	public String searchItem(Item item) {
	    int id = item.getId();
	    
	    projectList = listExistingProjects();
	    
	    for (String project : projectList) {
	        if (pHandler.findItem(id, project)) {
	            return project;
	        }
	    }
	    
	    return null;
	}
}
