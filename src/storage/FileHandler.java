//@@author A0124402Y
package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import object.Event;
import object.Item;
import object.Todo;

public class FileHandler implements FileManager{
	
	private static final String FLOATING_TODO = "Floating_Todo.txt";
	private static final String NORMAL_TODO = "Normal_Todo.txt";
	
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
	
	/**
	 * Creates a new instance of the FileHandler.
	 */
	public FileHandler(){
		directHand = new DirectoryHandler();
		readOverviewerFile();
		fEventH = new FileEventHandler(eventPath);
		fProjH = new FileProjectHandler(projectPath);
		fTodoH = new FileTodoHandler(todoPath);
		
		readCounter();
				
	}
	
/********************************* Events ****************************************/
	
	/**
	 * Retrieves a specific events by date
	 */
	@Override
	public ArrayList<Event> retrieveEventByDate(String date){
		if(date != null){
			return fEventH.retrieveEventByDate(date);
		}else{
			return null;
		}
	}
	
	/**
	 * Retrieves an ArrayList of events to make changes/delete
	 */
	public ArrayList<Event> retrieveAllEvents(){
		return fEventH.retrieveAllEvents();
	}
	
	/**
	 * Saves a new Event
	 */
	@Override
	public boolean saveNewEventHandler(Event event){
		if(event != null){
			writeCounter();
			return fEventH.saveNewEventHandler(event);
			
		}else{
			return false;
		}
	}
	
	/**
	 * Saves any changes made to the ArrayList<Event> retrieved 
	 * using the retrieveAllEvent() method
	 */
	@Override
	public boolean saveEditedEventHandler() {
		writeCounter();
		return fEventH.saveEventBook();
	}

/********************************* Todo *****************************************/
	
	/**
	 * Retrieves a specific task by date
	 */
	@Override
	public ArrayList<Todo> retrieveTodoByDate(String date) {
		if(date != null)
			return fTodoH.retrieveTodoByDate(date);
		else
			return null;
	}
	
	/**
	 * Retrieves an ArrayList of tasks to make changes/delete
	 */
	public ArrayList<Todo> retrieveAllTodo(){
		return fTodoH.retrieveAllTodo();
	}
	
	/**
	 * Retrieves all the floating Tasks available
	 */
	@Override
	public ArrayList<Todo> retrieveUniversalTodo() {
		return fTodoH.retrieveFloatingTodo();
	}
	
	/**
	 * Saves a new task
	 */
	@Override
	public boolean saveNewTodoHandler(Todo task) {
		if(task != null){
			writeCounter();
		return fTodoH.saveNewTodoHandler(task);
		}else{
			return false;
		}
	}
	
	/**
	 * Saves any changes made to the ArrayList<Todo> retrieved using the 
	 * retrieveAllTodo() method
	 */
	@Override
	public boolean saveEditedTodoHandler() {
		writeCounter();
		fTodoH.separateTodoTypes();
		return fTodoH.saveChange(NORMAL_TODO);
	}
		
	/**
	 * Saves all the Todo and Floating Todo into text files
	 */
	@Override
	public boolean saveAllEditedTodo(){
		writeCounter();
		fTodoH.separateTodoTypes();
		return fTodoH.saveChange(NORMAL_TODO) && fTodoH.saveChange(FLOATING_TODO);
	}
	
/******************************** Project **************************************/
	
	/**
	 * Retrieves all the events added to a project
	 */
	@Override
	public ArrayList<Integer> retrieveProjectTimeLine(String projectName) {
		if(projectName != null){
			return fProjH.retrieveProject(projectName);
		} else {
			return null;
		}
	}
	
	@Override
	public HashMap<Integer, String> retrieveProjectProgress() {
		return fProjH.retrieveProjectProgress();
	}
	
	/**
	 * Creates a new text file to store information of a new project
	 */
	@Override
	public boolean createNewProject(String projectName) {
		return fProjH.createNewProject(projectName);
	}
	
	/**
	 * Saves any changes made to the ArrayList<Integer> retrieved using the retrieveProjectTimeLine( ) method
	 */
	@Override
	public boolean saveEditedProjectDetails(ArrayList<Integer> projectBook, HashMap<Integer, String> progressBook, String projectName) {
		if(projectBook == null || progressBook == null || projectName == null){
			return false;
		} else{
			projectName = projectName.toLowerCase();
			return fProjH.saveEditedProjectDetails(projectBook, progressBook, projectName);
		}
	}

	/**
	 * Retrieves the names of all the existing project 
	 */
	@Override
	public ArrayList<String> getListOfExistingProject() {
		return fProjH.getListOfExistingProjects();
	}
	
	/**
	 * Deletes the entire project 
	 */
	@Override
	public boolean deleteProject(String projectName){
		if(projectName != null){
			return fProjH.deleteProject(projectName);
		} else{
			return false;
		}	
	}
	
/****************************** Internal *************************************/	
	
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
//		System.out.println("Counter is " + Item.getCounter()); //for checking
		Item.setCounter(0);
		inputFile = new File(COUNTER);	
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			int counter = Integer.parseInt(reader.readLine());
//			System.out.println("Counter is now set to " + counter); //for checking
			Item.setCounter(counter);
			
			reader.close();		 
		}
		catch (FileNotFoundException e) {
			writeCounter();
		}catch (IOException e) {
			// Do nothing
		}
	}
	
	private boolean writeCounter(){
		
//		System.out.println("Counter is now " + Item.getCounter()); //for checking
		
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
	/**
	 * Takes in a new directory for storage data to be stored and 
	 * transfers all the existing files to the new directory.
	 * @param newBaseDirectory
	 * @return
	 */
	@Override
	public boolean changeBaseDirectory(String newBaseDirectory){
		if(newBaseDirectory != null && (new File(newBaseDirectory).exists())){
			fProjH.readAll();
			clearAll();
			if(directHand.setNewBaseDirectory(newBaseDirectory)){
				newBaseDirectory = newBaseDirectory + "/database";
				fEventH.setNewDirectory(newBaseDirectory);
				fTodoH.setNewDirectory(newBaseDirectory);
				fProjH.setNewDirectory(newBaseDirectory);
			}
			saveAll();
			return true;
		}
		return false;		
	}
	
	/**
	 * Saves all the data available to the text files.
	 * @return
	 */
	public boolean saveAll(){
		return fEventH.saveEventBook() &&
				fTodoH.saveChange(NORMAL_TODO) &&
				fTodoH.saveChange(FLOATING_TODO) &&
				fProjH.writeAll() && writeCounter();
	}
	
	/**
	 * Deletes all text files available
	 */
	public void clearAll(){
		String baseDirectory = directHand.getBaseDirectory();
		cleanUp(baseDirectory);
		
		
//		try {		
//			File dir = new File(todoPath);
//			if(dir.isDirectory() && dir.list().length > 0){
//				for(File file: dir.listFiles()) file.delete(); 
//			}
//			Path path = Paths.get(todoPath);
//			Files.delete(path);
//			
//			dir = new File(eventPath);
//			if(dir.isDirectory() && dir.list().length > 0){
//				for(File file: dir.listFiles()) file.delete(); 
//			}
//			path = Paths.get(eventPath);
//			Files.delete(path);
//			
//			dir = new File(projectPath);
//			if(dir.isDirectory() && dir.list().length > 0){
//				for(File file: dir.listFiles()) file.delete(); 
//			}
//			path = Paths.get(projectPath);
//			Files.delete(path);
//			
//			path = Paths.get("overview.txt");
//			Files.delete(path);
//			
//			path = Paths.get("counter.txt");
//			Files.delete(path);
//			
//		} catch (NoSuchFileException x) {
//			System.out.println("err no such file.");
//		} catch (DirectoryNotEmptyException x) {
//			System.out.println("err directory not empty.");
//		} catch (IOException x) {
//			System.out.println("err");
//		}
	}
	
	public boolean cleanUp(String baseDirectory){
		
		File dir = new File(baseDirectory);
		if(dir.isDirectory() && dir.list().length > 0){
			for(File file: dir.listFiles()) {
				if(!file.isDirectory()){
					file.delete(); 
				}else{
					cleanUp(file.getPath());
				}
			}
		}
		
		Path path = Paths.get(baseDirectory);
		
		try {
			Files.delete(path);
			return true;
		} catch(NoSuchFileException e) {
			System.out.println("No such file exist to delete");
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

/****************************** Item *****************************************/
	/**
	 * Retrieves event by the ID assigned. 
	 * @param wantedId
	 * @return
	 */
	@Override
	public Event retrieveEventById(int wantedId){
		ArrayList<Event> allEvent = fEventH.retrieveAllEvents();
		for(Event event: allEvent){
			if(event.getId() == wantedId){
				return event;
			}
		}
		return null;
	}
	
	/**
	 * Retrieves task by the ID assigned
	 * @param wantedId
	 * @return
	 */
	@Override
	public Todo retrieveTaskById(int wantedId){
		ArrayList<Todo> allTodo = fTodoH.retrieveAllTodo();
		for(Todo todo: allTodo){
			if(todo.getId() == wantedId){
				return todo;
			}
		}
		return null;
	}
	
}