import java.util.ArrayList;


public interface ProjectManager {
	
	public boolean createProject (String projectName); 
	public boolean deleteProject(String projectName);
	public boolean deleteProjectEvent(String eventName);
	public boolean deleteProjectEvent(int index);
	public boolean editProjectEvent(int eventIndex, int infoIndex, String newValue);
	public boolean editProjectEvent(String name, int infoIndex, String newValue);
	public ArrayList<Event> viewProjectTimeline(String name);
	public ArrayList<Event> viewProjectTimeline(int index);
	public ArrayList<String> listExistingProjects();
	}
