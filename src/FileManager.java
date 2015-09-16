import java.util.ArrayList;
import java.util.HashMap;

public interface FileManager {
	
	// Retrieve events of a specific date
	public ArrayList<HashMap<String, String>> retrieveEventByDate(String date); 
	// Retrieve to-do tasks of a specific day
	public ArrayList<HashMap<String, String>> retrieveTodoByDay(String date);
	// Retrieve to-do tasks with no date
	public ArrayList<HashMap<String, String>> retrieveUniversalTodo(String date);
	// Retrieve a project time-line
	public ArrayList<HashMap<String, String>> retrieveProjectTimeLine(String projectName);
	
	// save new event
	public boolean saveNewEventHandler(HashMap<String, String> event);
	// save edited event
	public boolean saveEditedEventHandler(HashMap<String, String> event);
	// save new To-Do task
	public boolean saveNewTodoHandler(HashMap<String, String> event);
	// save edited To-Do task
	public boolean saveEditedTodoHandler(HashMap<String, String> event);
	// save new project group
	public boolean saveNewProject(String projectName);
	// save edited project details
	public boolean saveEditedProjectDetails();	
}
