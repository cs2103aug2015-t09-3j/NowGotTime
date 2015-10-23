package service;
import java.text.ParseException;
import java.util.ArrayList;

import object.Event;
import object.Item;
import object.Todo;

/**
 * The interface will be a blueprint of a service handler in charge of 
 * 1) Creating 2) Deleting 3) editing 4)Viewing 
 */
public interface ServiceManager {
	
	public boolean createItem (Item item) throws Exception;
	
	public ArrayList<Event> viewEventByDate(String date);
	public ArrayList<Todo> viewTaskByDate(String date);
	public ArrayList<Todo> viewTaskNoDate();
	
	public boolean deleteItem(Item item)throws Exception;
	
	public String editItem(Item item, String fieldName, String newInputs )throws Exception;
    
	public Event viewSpecificEvent (String eventName);
	
	public Todo viewSpecificTask(String taskName);
	
	public ArrayList<Item> search (String inputs);
	
	//after searching
	public boolean deleteItemByIndex(int index);
	public Item viewItemByIndex(int index);
	
	public boolean changeDirectory(String newDirectory);
	
	public boolean mark(Item item);
	public boolean unmark(Item item);
	
	public ArrayList<Item> viewMultipleDays(String date) throws ParseException;
}
