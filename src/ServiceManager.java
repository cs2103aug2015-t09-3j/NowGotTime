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
	public ArrayList<Todo> viewTaskNoDate();
	
	public boolean deleteEvent(String eventName);
	public boolean deleteTask(String taskName);
	
	//public boolean editEvent(String eventName, String fieldName, String newInputs); 
	public boolean editEventName(String eventName, String newEventName);
	public boolean editEventStartDate(String eventName, String newStartDate);
	public boolean editEventEndDate(String eventName, String newStartDate);
	public boolean editEventStartTime(String eventName, String newStartTime);
	public boolean editEventEndTime(String eventName, String newEndTime);
    public boolean editEventStartDateTime(String eventName, String newStartDateTime);
    public boolean editEventEndDateTime(String eventName, String newEndDateTime);
	
    //public boolean editTask(String taskName, String fieldName, String newInputs); 
    public boolean editTaskName(String taskName, String newTaskName);
    public boolean editTaskDeadlineDate(String taskName, String newDeadlineDate);
    public boolean editTaskDeadlineTime(String taskName, String newDeadlineTime);
    public boolean editTaskDeadlineDateTime(String taskName, String newDeadlineDateTime);
	
	public Event viewSpecificEvent (String eventName);
	
	public Todo viewSpecificTask(String taskName);

}
