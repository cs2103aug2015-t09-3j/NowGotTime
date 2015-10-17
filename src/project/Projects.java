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

	public boolean editProjectEvent(int id, int infoIndex, String newValue, String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return false;
		} else {
		return pHandler.editProjectEvent(id, infoIndex, newValue, projectName);
		}
	}
	
	public boolean editProjectEvent(int id, int infoIndex, String newValue, int index) {
		projectList = listExistingProjects();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return editProjectEvent(id, infoIndex, newValue, projectName);
		} else {
			return false;
		}
	}
	
	public boolean deleteProjectEvent(int id, String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return false;
		} else {
		return pHandler.deleteProjectEvent(id, projectName);
		}
	}
	
	public boolean deleteProjectEvent(int id, int index) {
		projectList = listExistingProjects();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return deleteProjectEvent(id, projectName);
		} else {
			return false;
		}
	}
	
	public boolean addProjectEvent(int id, String projectName) {
		if (!listExistingProjects().contains(projectName.toLowerCase())) {
			return false;
		} else {
		return pHandler.addProjectEvent(id, projectName);
		}
	}
	
	public boolean addProjectEvent(int id, int index) {
		projectList = listExistingProjects();
		if (index < projectList.size()) {
			String projectName = projectList.get(index);
			return addProjectEvent(id, projectName);
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
}
