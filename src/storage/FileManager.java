package storage;
import java.util.ArrayList;
import java.util.HashMap;

import object.Event;
import object.Todo;


/** Expend this to read description **/
/**
 * 
 * @author RX.huang
 * 
 * ##################################
 * # DESCRIPTION OF ALL THE METHODS #
 * ##################################
 *** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***
 **	~~~~EVENTS~~~~EVENTS~~~~EVENTS~~~~EVENTS~~~~EVENTS~~~~EVENTS~~~~EVENTS~~~~EVENTS~~~~EVENTS~~~~  **
 *** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***
 **																									**
 **	public ArrayList<Event> retrieveEventByDate(String date);										**
 **		--> return the events for the specific date.												**
 **		--> can be used for editing																	**
 **																									**
 **	public boolean saveNewEventHandler(Event event);												**
 **		--> used for saving a NEW event																**
 **		--> "event" is the new event																**
 **																									**
 **	public boolean saveEditedEventHandler();														**
 **		--> used for saving any changed done, don't need any parameter								**
 **																									**
 **	public ArrayList<Event> retrieveEventsToDelete();												**
 **		--> this is the special method to retrieve the events to delete one event					**
 **																									**
 *** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***
 **   ~~~~ToDo~~~~ToDo~~~~ToDo~~~~ToDo~~~~ToDo~~~~ToDo~~~~ToDo~~~~ToDo~~~~ToDo~~~~ToDo~~~~ToDo~~~~  **
 *** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***
 **																									**
 **	public ArrayList<Todo> retrieveTodoByDate(String date);											**
 **		--> return the tasks for the specified "date"												**
 **		--> can be used for editing																	**
 **																									**	
 **	public ArrayList<Todo> retrieveUniversalTodo();													**
 **		--> return the floating events																**
 **		--> can be used for editing																	**
 **																									**
 **	public ArrayList<Todo> retrieveTodoToDelete();													**
 **		--> get task list to delete																	**
 **																									**
 **	public boolean saveNewTodoHandler(Todo task);													**
 **		--> save NEW task																			**
 **		--> "task" is the new todo, can be floating or normal tasks									**
 **																									**
 **	public boolean saveEditedTodoHandler();															**
 **		--> Save any changes done to the todo list													**
 **																									**
 **	public boolean saveEditedUniversalTodoHandler();												**
 **		--> Save any changes done to the floating todo list.										**
 **																									**								
 *** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***
 **   ~~~~Project~~~~Project~~~~Project~~~~Project~~~~Project~~~~Project~~~~Project~~~~Project~~~~  **
 *** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***
 **	public ArrayList<Event> retrieveProjectTimeLine(String projectName);							**
 **		--> get the events in a project																**
 **																									**
 ** public boolean createNewProject(String projectName);											**
 **		--> create a new project file																**
 **																									**
 ** public boolean saveEditedProjectDetails(ArrayList<Event> projectBook, String projectName);		**
 **		--> save any changes done to events in a project 											**
 **																									**
 ** public ArrayList<String> getListOfExistingProject( );											**
 **		--> get all the names of existing projects													**
 **																									**
 ** public boolean deleteProject(String projectName);												**
 **		--> delete a whole project																	**
 **																									**
 *** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ***
 *
 *	< end >
 *
 ***/

public interface FileManager {

	public ArrayList<Event> retrieveEventByDate(String date); 
	public boolean saveNewEventHandler(Event event);
	public boolean saveEditedEventHandler();
	public ArrayList<Event> retrieveAllEvents();
	
	public ArrayList<Todo> retrieveTodoByDate(String date);
	public ArrayList<Todo> retrieveUniversalTodo();
	public ArrayList<Todo> retrieveAllTodo();
	public boolean saveNewTodoHandler(Todo task);
	public boolean saveEditedTodoHandler();
	
	public ArrayList<Integer> retrieveProjectTimeLine(String projectName);
	public HashMap<Integer, String> retrieveProjectProgress();
	public boolean createNewProject(String projectName);
	public boolean saveEditedProjectDetails(ArrayList<Integer> projectBook, HashMap<Integer, String> progressBook, String projectName);
	public ArrayList<String> getListOfExistingProject( );
	public boolean deleteProject(String projectName);
	
}