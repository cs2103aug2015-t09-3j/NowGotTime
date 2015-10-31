package project;

import java.util.ArrayList;

import storage.FileHandler;
import object.Event;

public class Projects {

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
		return project.createNewProject(projectName.toLowerCase());
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
		if (newProjectName.equals(oldProjectName) || checkExistingProject.contains(oldProjectName)) {
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
	
	public ArrayList<Event> viewEventProgressTimeline(String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return null;
		} else {
		return pHandler.viewEventProgressTimeline(projectName);
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
	
	public ArrayList<String> searchProjects (String name) {
		ArrayList<String> searchedNames = new ArrayList<String>();
		for (String projectName : projectList) {
			if (projectName.contains(name)) {
				searchedNames.add(projectName);
			}
		}
		return searchedNames;
	}
	
	public double progressBar(String projectName) {
		if (listExistingProjects().contains(projectName)) {
			return pHandler.progressBar(projectName);
		}
		return -1;
	}
	
	public boolean addProgressMessage(Event event, String projectName, String progressMessage) {
		if (listExistingProjects().contains(projectName)) {
			return pHandler.addProgressMessage(event, progressMessage);
		} 
		return false;
	}
	
	public boolean addProgressMessage(int index, String projectName, String progressMessage) {
		if (listExistingProjects().contains(projectName)) {
			return pHandler.addProgressMessage(index, progressMessage, projectName);
		} 
		return false;
	}
	
	public boolean deleteProgressMessage(int index, String projectName) {
		if (listExistingProjects().contains(projectName)) {
			return pHandler.deleteProgressMessage(index, projectName);
		} 
		return false;
	}
	
	public boolean deleteProgressMessage(Event event, String projectName) {
		if (listExistingProjects().contains(projectName)) {
			return pHandler.deleteProgressMessage(event);
		} 
		return false;
	}
	
	public boolean editProgressMessage(Event event, String newProgressMessage, String projectName) {
		if (listExistingProjects().contains(projectName)) {
			return pHandler.editProgressMessage(event, newProgressMessage);
		} 
		return false;
	}
	
	public boolean editProgressMessage(int index, String newProgressMessage, String projectName) {
		if (listExistingProjects().contains(projectName)) {
			return pHandler.editProgressMessage(index, newProgressMessage, projectName);
		} 
		return false;
	}
}
