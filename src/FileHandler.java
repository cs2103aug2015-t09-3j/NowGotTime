import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	
	private DirectoryHandler directHand;
	
/******************************* Constructor *************************************/	

	public FileHandler(){
		directHand = new DirectoryHandler();
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
	
	public ArrayList<Event> retrieveEventsToDelete(){
		return fEventH.retrieveEventsToDelete();
	}
	
	@Override
	public boolean saveNewEventHandler(Event event){
		return fEventH.saveNewEventHandler(event);
	}
	
	@Override
	public boolean saveEditedEventHandler() {
		return fEventH.saveEventBook();
	}

/********************************* Todo *****************************************/
	
	@Override
	public ArrayList<Todo> retrieveTodoByDate(String date) {
		return fTodoH.retrieveTodoByDate(date);
	}
	
	public ArrayList<Todo> retrieveTodoToDelete(){
		return fTodoH.retrieveTodoToDelete();
	}
	
	@Override
	public ArrayList<Todo> retrieveUniversalTodo() {
		return fTodoH.retrieveFloatingTodo();
	}

	@Override
	public boolean saveNewTodoHandler(Todo task) {
		return fTodoH.saveNewTodoHandler(task);
	}
	
	@Override
	public boolean saveEditedTodoHandler() {
		return fTodoH.saveToDoList();
	}
	
	@Override
	public boolean saveEditedUniversalTodoHandler(){
		return fTodoH.saveUniversalToDoList();
	}
	
	public boolean saveAllEditedTodo(){
		return fTodoH.saveToDoList() && fTodoH.saveUniversalToDoList();
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

/******************************** Testing **************************************/
	
	public boolean changeBaseDirectory(String newBaseDirectory){
		/*TODO: read all projects first before clearing, because we do not by
				default read all the existing projects */	
		clearAll();
		directHand.setNewBaseDirectory(newBaseDirectory);
		fEventH.setNewDirectory(newBaseDirectory);
		fTodoH.setNewDirectory(newBaseDirectory);
		fProjH.setNewDirectory(newBaseDirectory);
		saveAll();
		return true;
	}
	
	public boolean saveAll(){
		return fEventH.saveEventBook() &&
				fEventH.updateHistory() &&
				fTodoH.saveToDoList() &&
				fTodoH.updateHistory() &&
				fTodoH.saveUniversalToDoList();
		
		//TODO: save all projects methods missing
	}

	public void clearAll(){
		try {
			
			File dir = new File(todoPath);
			if(dir.isDirectory() && dir.list().length > 0){
				for(File file: dir.listFiles()) file.delete(); 
			}
			Path path = Paths.get(todoPath);
			Files.delete(path);
			
			dir = new File(eventPath);
			if(dir.isDirectory() && dir.list().length > 0){
				for(File file: dir.listFiles()) file.delete(); 
			}
			path = Paths.get(eventPath);
			Files.delete(path);
			
			dir = new File(projectPath);
			if(dir.isDirectory() && dir.list().length > 0){
				for(File file: dir.listFiles()) file.delete(); 
			}
			path = Paths.get(projectPath);
			Files.delete(path);
			
//			path = Paths.get("overview.txt");
//			Files.delete(path);
			
		} catch (NoSuchFileException x) {
			System.out.println("err no such file.");
		} catch (DirectoryNotEmptyException x) {
			System.out.println("err directory not empty.");
		} catch (IOException x) {
			System.out.println("err");
		}
	}
	
}