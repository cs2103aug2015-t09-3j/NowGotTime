import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class will be in charge of data storage of events by month.
 * It works with an overview page which will keep track of the months that have
 * already created.
 *  
 *  Assumption: 
 *  1) There is a parser to ensure that all inputs are all valid.
 *  
 *  Cases:
 *  1) Spill over dates.
 *  
 * @author RX.huang
 *
 */

public class FileHandler implements FileManager{
	
	private static final String EVENT_OVERVIEWER = "overview.txt";
	private File inputFile;
	private String eventPath;
	private String todoPath;
	private String projectPath;
	
	private FileProjectHandler fProjH;
	private FileEventHandler fEventH;
	private FileTodoHandler fTodoH;
	
/******************************* Constructor *************************************/	

	public FileHandler(){
		new StartUpHandler();
		readOverviewerFile();
		
		fEventH = new FileEventHandler(eventPath);
		fProjH = new FileProjectHandler(projectPath);
		fTodoH = new FileTodoHandler(todoPath);
	}
	
/********************************* Events ****************************************/
	
	@Override
	public ArrayList<Event> retrieveEventByDate(String date){
		return fEventH.retrieveEventByDate(date);
	}
	
	@Override
	public boolean saveNewEventHandler(Event event){
		return fEventH.saveNewEventHandler(event);
	}
	
	@Override
	public boolean saveEditedEventHandler(String date, ArrayList<Event> eventBook) {
		return fEventH.saveEventBook(date, eventBook);
	}

/********************************* Todo *****************************************/
	
	@Override
	public ArrayList<Todo> retrieveTodoByDate(String date) {
		return fTodoH.retrieveTodoEventByDate(date);
	}
	
	@Override
	public ArrayList<Todo> retrieveUniversalTodo(String date) {
		return fTodoH.retrieveUniversalTodo();
	}

	@Override
	public boolean saveNewTodoHandler(Todo task) {
		return fTodoH.saveNewTodoHandler(task);
	}
	
	@Override
	public boolean saveEditedTodoHandler(String date, ArrayList<Todo> taskBook) {
		return fTodoH.saveToDoList(date, taskBook);
	}

/******************************** Project **************************************/
	
	@Override
	public ArrayList<Event> retrieveProjectTimeLine(String projectName) {
		return fProjH.retrieveProject(projectName);
	}
	
	@Override
	public boolean createNewProject(String projectName) {
		return fProjH.createNewProject(projectName);
	}

	@Override
	public boolean saveEditedProjectDetails(ArrayList<Event> projectBook, String projectName) {
		return fProjH.saveEditedProjectDetails(projectBook, projectName);
	}

	@Override
	public ArrayList<String> getListOfExistingProject() {
		return fProjH.getListOfExistingProjects();
	}
	
	@Override
	public boolean deleteProject(String projectName){
		return fProjH.deleteProject(projectName);
	}
	
	private void readOverviewerFile() {
		inputFile = new File(EVENT_OVERVIEWER);	
			
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			todoPath = reader.readLine();
			eventPath = reader.readLine();
			projectPath = reader.readLine();
			
			reader.close();		 
		}
		catch (FileNotFoundException e) {
			// Do nothing
		}catch (IOException e) {
			// Do nothing
		}
	}
	
}