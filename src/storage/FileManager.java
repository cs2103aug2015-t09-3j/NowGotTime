//@@author A0124402Y
package storage;

import java.util.ArrayList;
import java.util.HashMap;

import object.Event;
import object.Todo;

/**
 * 
 * @author RX.huang
 * 
 * This is an interface implemented by FileHandler. The interface ensures that
 * FileHandler contains fixed methods that can be used by the Logic component.
 * 
 */
public interface FileManager {

	public ArrayList<Event> retrieveEventByDate(String date); 
	public ArrayList<Event> retrieveAllEvents();
	public boolean saveNewEventHandler(Event event);
	public boolean saveEditedEventHandler();
	
	public ArrayList<Todo> retrieveTodoByDate(String date);
	public ArrayList<Todo> retrieveAllTodo();
	public ArrayList<Todo> retrieveUniversalTodo();
	public boolean saveNewTodoHandler(Todo task);
	public boolean saveEditedTodoHandler();
	public boolean saveAllEditedTodo();
	
	public ArrayList<Integer> retrieveProjectTimeLine(String projectName);
	public HashMap<Integer, String> retrieveProjectProgress();
	public boolean createNewProject(String projectName);
	public boolean saveEditedProjectDetails(ArrayList<Integer> projectBook, HashMap<Integer, String> progressBook, String projectName);
	public ArrayList<String> getListOfExistingProject( );
	public boolean deleteProject(String projectName);
	
	public boolean changeBaseDirectory(String newBaseDirectory);

	public Event retrieveEventById(int wantedId);
	public Todo retrieveTaskById(int wantedId);
	
}