import java.util.ArrayList;


public interface FileManager {
	
	public ArrayList<Event> retrieveEventByDate(String date); 
	public boolean saveNewEventHandler(Event event);
	public boolean saveEditedEventHandler(ArrayList<Event> eventBook);
	
	public ArrayList<Todo> retrieveTodoByDate(String date);
	public ArrayList<Todo> retrieveUniversalTodo(String date);
	public boolean saveNewTodoHandler(Todo task);
	public boolean saveEditedTodoHandler(ArrayList<Todo> taskBook);
	
	public ArrayList<Event> retrieveProjectTimeLine(String projectName);
	public boolean createNewProject(String projectName);
	public boolean saveEditedProjectDetails(ArrayList<Event> projectBook, String projectName);
	public ArrayList<String> getListOfExistingProject( );
	public boolean deleteProject(String projectName);
	
}