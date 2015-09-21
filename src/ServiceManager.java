import java.util.ArrayList;

/**
 * The interface will be a blueprint of a service handler in charge of 
 * 1) Creating 2) Deleting 3) editing 4)Viewing 
 */
public interface ServiceManager {
	
	public boolean createEvent (Event newEvent); 
	public boolean createTask(Todo newTask);
	
	public ArrayList<Event> viewEventByDate(String date);
	public ArrayList<Todo> viewTaskByDate(String date);
	public ArrayList<Todo> viewTaskNoDate(String date);
	
	public boolean deleteEvent(String eventName);
	public boolean deleteEvent(int eventIndex);
	public boolean deleteTaskWithDeadline(String task);
	public boolean deleteTaskWithoutDeadline(String task);
	public boolean deleteTask(int taskIndex);
	
	public boolean editEventName(String eventName, String newEventName);
	public boolean editEventStartDate(String eventName, String newStartDate);
	public boolean editEventEndDate(String eventName, String newStartDate);
	public boolean editEventStartTime(String eventName, String newStartTime);
	public boolean editEventEndTime(String eventName, String newEndTime);
	
	public boolean editTaskNameWithDeadline(String taskName, String newTaskName);
	public boolean editTaskDeadlineWithDeadline(String taskName, String newDeadline);
	public boolean editTaskTimeWithDeadline(String taskName, String newTime);
	
	public boolean editTaskNameWithoutDeadline(String taskName, String newTaskName);
	
	public Event viewSpecificEvent (String eventName);
	
	public Todo viewSpecificTaskWithDeadline (String taskName);
	public Todo viewSpecificTaskWithoutDeadline (String taskName);
}
