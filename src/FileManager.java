import java.util.ArrayList;


public interface FileManager {
	
	public ArrayList<Event> retrieveEventByDate(String date); 
	public ArrayList<Todo> retrieveTodoByDate(String date);
	public ArrayList<Todo> retrieveUniversalTodo(String date);
	public ArrayList<Event> retrieveProjectTimeLine(String projectName);
	
	public boolean saveEditedEventHandler(ArrayList<Event> eventBook);
	public boolean saveNewTodoHandler(Todo task);
	public boolean saveEditedTodoHandler(ArrayList<Todo> taskBook);
	
	public boolean createNewProject(String projectName);
	public boolean saveEditedProjectDetails(ArrayList<Event> projectBook);
	
	public ArrayList<String> getListOfExistingProject( );
	
}
