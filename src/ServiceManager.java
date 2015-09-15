import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The interface will be a blueprint of a service handler in charge of 
 * 1) Creating 2) Deleting 3) editing 4)Viewing 
 */
public interface ServiceManager {
	
	// Create both floating and normal events
	// Return true if successful, else return false
	public boolean createEvent(Scanner sc);
	public boolean delete(int index, ArrayList<HashMap<String, String>> eventBook);
	public boolean edit(String date);
	public ArrayList<HashMap<String, String>> viewDay(String date);
	public HashMap<String, String> viewEvent(String eventName);
	public ArrayList<HashMap<String, String>> viewToDoList(String taskName);
}
