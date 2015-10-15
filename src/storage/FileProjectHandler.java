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
import java.util.HashMap;


public class FileProjectHandler {
	private static final String PROJECT_OVERVIEWER = "projectOverviewer.txt";
	private static final String PROJECT = "Project";
	
	private String baseDirectory;
	private ArrayList<String> existingProjects;
	private File inputFile;
	private ArrayList<ArrayList<Integer>> projectBookShelf;
	ArrayList<HashMap<Integer, String>> progressBookShelf;
	private HashMap<Integer, String> progressBook;
	
/*******************************************************************************/
	
	public FileProjectHandler(String baseDirectory){
		this.baseDirectory = baseDirectory.concat("/");
		readOverviewerFile();
	}
	
	public boolean deleteProject(String projectName){
		if(!existingProjects.contains(projectName)){
			return false;
		}
		existingProjects.remove(projectName);
		updateOverviewFile();
		
		Path path = Paths.get(baseDirectory + projectName + ".txt");
		try {
		    Files.delete(path);
		    return true;
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		    return false;
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		    return false;
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		    return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getListOfExistingProjects(){
		return (ArrayList<String>) existingProjects.clone();
	}
	
	public ArrayList<Integer> retrieveProject(String name){
		
		String fileName = setFileName(name);
		selectFileAsInputFile(baseDirectory + fileName);
		
		ArrayList<Integer> projectBook = new ArrayList<Integer>();
		progressBook = new HashMap<Integer, String>();
		String addInfo;
		int id;
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String lineOfText;
			
			while( (lineOfText = reader.readLine()) != null){
				id = Integer.parseInt(lineOfText);
				addInfo = reader.readLine();
				
				projectBook.add(id);
				progressBook.put(id, addInfo);
			}
			reader.close();
			return projectBook;
			
		}catch(FileNotFoundException e){
			return projectBook;
		}catch(IOException e){
			return projectBook;
		}
		 
//		ArrayList<Event> projectBook  = new ArrayList<Event>();
//		String eventName, startDate, endDate, startTime, endTime, addInfo;
//		
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
//			String lineOfText;
//			
//			while( (lineOfText = reader.readLine()) != null ){
//				eventName = lineOfText;
//				addInfo = reader.readLine();
//				startDate = reader.readLine();
//				endDate = reader.readLine();
//				startTime = reader.readLine();
//				endTime = reader.readLine();			
//				
//				Event event = new Event(eventName, startDate, endDate, startTime, endTime, addInfo);
//				projectBook.add(event);
//			}
//			
//			reader.close();		 
//			return projectBook;
//		}
//		catch (FileNotFoundException e) {
//			return projectBook;
//		}catch (IOException e) {
//			return projectBook;
//		}	
	}
	
	//new
	public HashMap<Integer, String> retrieveProjectProgress(){
		return progressBook;
	}
	
	public boolean createNewProject(String projectName){
		if(!existingProjects.contains(projectName)){
			return createNewProjectFile(projectName);
		}
		return false;
	}
	
	//will not sort the events by date.
	public boolean saveEditedProjectDetails(ArrayList<Integer> projectBook, HashMap<Integer, String> progressBook, String projectName){
		
		try{
			File outfile = new File(baseDirectory + projectName + ".txt");
			
			if(!outfile.exists()){
				return false; 
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Integer id : projectBook){
				writer.write(id.toString()); writer.newLine();
				writer.write(progressBook.get(id)); writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
		
	}
	
	public boolean setNewDirectory(String newBaseDirectory){
		baseDirectory = newBaseDirectory.concat("/" + PROJECT + "/");
		return true;
	}
	
	public boolean readAll(){
		projectBookShelf = new ArrayList<ArrayList<Integer>>();
		progressBookShelf = new ArrayList<HashMap<Integer, String>>();
		
		for(String project: existingProjects){
			ArrayList<Integer> projectBook = retrieveProject(project);
			HashMap<Integer, String> progressBook = retrieveProjectProgress();
			projectBookShelf.add(projectBook);
			progressBookShelf.add(progressBook);
		}
		
		if(projectBookShelf.isEmpty()){
			return false;
		}
		
		return true;
	}
	
	public boolean writeAll(){
		updateOverviewFile();
		
		try {
			for(int counter = 0; counter < existingProjects.size(); counter++){
				
				String projectName = existingProjects.get(counter);
				ArrayList<Integer> projectBook = projectBookShelf.get(counter);
				HashMap<Integer, String> progressBook = progressBookShelf.get(counter);
				createNewProjectFile(projectName);
				saveEditedProjectDetails(projectBook, progressBook, projectName);
			}
			
			return true;
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}
	}
	
/*******************************************************************************/

	private boolean createNewProjectFile(String textFileName){

		try{
			File newFile = new File(baseDirectory + textFileName + ".txt");
				
			if(!existingProjects.contains(textFileName)){
				existingProjects.add(textFileName);
				updateOverviewFile();
			}
			
			return newFile.createNewFile();
			
		}catch(IOException e){
			return false;
		}catch(SecurityException e){
			return false;
		}
	}
	
	
	private void readOverviewerFile() {
		inputFile = new File( baseDirectory + PROJECT_OVERVIEWER);	
		existingProjects = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String projectName;
			
			while((projectName = reader.readLine()) != (null)){
				existingProjects.add(projectName);
			}		
			reader.close();		 
		}
		catch (FileNotFoundException e) {
			// Do nothing
		}catch (IOException e) {
			// Do nothing
		}
	}

	private void selectFileAsInputFile(String fileName){
		inputFile = new File(fileName);
	}

	private String setFileName(String projectName){
		return (projectName + ".txt");	
	}
	
	private boolean updateOverviewFile(){
		try{
			File outfile = new File(baseDirectory + PROJECT_OVERVIEWER);

			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(String projectName : existingProjects){
				writer.write(projectName); 
				writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
		
	}
	
}