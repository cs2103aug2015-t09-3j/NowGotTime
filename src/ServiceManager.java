import java.util.ArrayList;

/**
 * The interface will be a blueprint of a service handler in charge of 
 * 1) Creating 2) Deleting 3) editing 4)Viewing 
 */
public interface ServiceManager {
	
	public boolean createEvent (ArrayList<String> eventDetails); 
	public boolean createTask(ArrayList<String> taskDetails);
	
	public ArrayList<Event> viewEventByDate(String date);
	public ArrayList<Todo> viewTaskByDate(String date);
	public ArrayList<Todo> viewTaskNoDate(String date);
	
	public boolean deleteEvent(String eventName);
	public boolean deleteEvent(int eventIndex);
	public boolean deleteTaskWithDeadline(String task);
	public boolean deleteTaskWithoutDeadline(String task);
	public boolean deleteTask(int taskIndex);
	
	public boolean editEvent(String eventName, int index, String update);
	public boolean editTask(String taskName, int index, String update);
	
	public boolean viewEvent (String eventName);
	public Event viewSpecificEvent (String eventName, String date);
	
	public boolean viewTask (String taskName);
	public Todo viewSpecificTask (String taskName);
}
