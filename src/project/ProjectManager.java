package project;
import java.util.ArrayList;

import object.Event;


public interface ProjectManager {
	
	public boolean createProject (String projectName); //works
	
	public boolean addProjectEvent (int id, String projectName); //works
	
	public boolean deleteProject(String projectName); //works
	//public boolean deleteProjectEvent(String eventName, String projectName);//tested
	//public boolean deleteProjectEvent(int index, String projectName);//tested
	public boolean deleteProjectEvent(int id, String projectName);
	
	public boolean editProjectEvent(int id, int infoIndex, String newValue, String projectName); //works
	//public boolean editProjectEvent(int eventIndex, int infoIndex, String newValue, String projectName);
	//public boolean editProjectEvent(Event eventName, int infoIndex, String newValue, String projectName); //works
	
	public ArrayList<Integer> viewProjectTimeline(String name); //works
	public ArrayList<Integer> viewProjectTimeline(int index); //works
	public ArrayList<String> listExistingProjects(); //works
	public ArrayList<Event> viewEventProgressTimeline(String projectName);
	}
