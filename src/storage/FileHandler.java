package storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import object.Event;
import object.Item;
import object.Todo;

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
	private static final String COUNTER = "Counter.txt";
		
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
		readCounter();
		
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
		return fEventH.saveEventBook(false);
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
		return fTodoH.saveToDoList(false);
	}
	
	@Override
	public boolean saveEditedUniversalTodoHandler(){
		return fTodoH.saveUniversalToDoList();
	}
	
	public boolean saveAllEditedTodo(){
		fTodoH.separateTodoTypes();
		return fTodoH.saveToDoList(false) && fTodoH.saveUniversalToDoList();
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
	
	private void readCounter(){
		inputFile = new File(COUNTER);	
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			int counter = Integer.parseInt(reader.readLine());
			Item.setCounter(counter);
			
			reader.close();		 
		}
		catch (FileNotFoundException e) {
			// Do nothing
		}catch (IOException e) {
			// Do nothing
		}
	}
	
	private boolean writeCounter(){
		try{
			File outfile = new File(COUNTER);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			int counter = Item.getCounter();
			writer.write(counter + ""); 
			
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
	}
	
/****************************** Directory *************************************/
	
	public boolean changeBaseDirectory(String newBaseDirectory){
		fProjH.readAll();
		clearAll();
		directHand.setNewBaseDirectory(newBaseDirectory);
		fEventH.setNewDirectory(newBaseDirectory);
		fTodoH.setNewDirectory(newBaseDirectory);
		fProjH.setNewDirectory(newBaseDirectory);
		saveAll();
		return true;
	}
	
	public boolean saveAll(){
		System.out.println("YOZZZZZ");
		fEventH.saveEventBook(false);
		System.out.println("YOZZZZZ");
		fEventH.updateHistory();
		System.out.println("YOZZZZZ");
		saveAllEditedTodo();
		System.out.println("here??");
		fTodoH.updateHistory();
		
		System.out.println("YOZZZZZ");
		fProjH.writeAll();
		System.out.println("hello");
		
		writeCounter();
		return true;
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

/****************************** Item *************************************/
	
	protected ArrayList<Item> synchronise(ArrayList<Item> itemSet1, ArrayList<Item> itemSet2){
		
		ArrayList<Item> syncList = new ArrayList<Item>();
		
		for(Item item1: itemSet1){
			for(Item item2: itemSet2){
				if(item1.getId() == item2.getId()){
					syncList.add(item2);
					itemSet2.remove(item2);
					break;
				}
			}
		}
		
		if(!itemSet2.isEmpty()){
			syncList.addAll(itemSet2);
		}
		
		return syncList;
	}
	
	
	
}