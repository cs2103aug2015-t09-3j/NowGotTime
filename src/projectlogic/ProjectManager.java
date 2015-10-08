package projectlogic;
import java.util.ArrayList;

import objects.Event;


public interface ProjectManager {
	
	public boolean createProject (String projectName); //works
	
	public boolean addProjectEvent (Event eventName, String projectName); //works
	
	public boolean deleteProject(String projectName); //works
	public boolean deleteProjectEvent(String eventName, String projectName);//tested
	public boolean deleteProjectEvent(int index, String projectName);//tested
	
	public boolean editProjectEvent(int eventIndex, int infoIndex, String newValue, String projectName); //works
	public boolean editProjectEvent(Event eventName, int infoIndex, String newValue, String projectName); //works
	
	public ArrayList<Event> viewProjectTimeline(String name); //works
	public ArrayList<Event> viewProjectTimeline(int index); //works
	public ArrayList<String> listExistingProjects(); //works
	}
