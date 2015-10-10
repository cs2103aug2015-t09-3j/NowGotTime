package service;
import java.util.ArrayList;

import object.Event;
import object.Item;
import object.Todo;

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
	
	public String editEvent(String eventName, String fieldName, String newInputs)throws Exception; 
    public String editTask(String taskName, String fieldName, String newInputs)throws Exception; 
    
	public Event viewSpecificEvent (String eventName);
	
	public Todo viewSpecificTask(String taskName);
	
	public ArrayList<Item> search (String inputs);

}
