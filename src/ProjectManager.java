import java.util.ArrayList;


public interface ProjectManager {
	
	public boolean createProject (String projectName); 
	
	public boolean addProjectEvent (Event eventName, String projectName);
	
	public boolean deleteProject(String projectName); 
	public boolean deleteProjectEvent(String eventName, String projectName);//not tested
	public boolean deleteProjectEvent(int index, String projectName);//not tested
	
	public boolean editProjectEvent(int eventIndex, int infoIndex, String newValue, String projectName); 
	public boolean editProjectEvent(Event eventName, int infoIndex, String newValue, String projectName);
	//WHY DOES THIS NOT WORK. PROJECTBOOK.CONTAINS(EVENT) DOESNT WORK WHY //EVENT NEEDS TO EVENTNAME.GETNAME();?
	
	public ArrayList<Event> viewProjectTimeline(String name);
	public ArrayList<Event> viewProjectTimeline(int index);
	public ArrayList<String> listExistingProjects();
	}
