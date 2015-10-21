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
	private File inputFile;
	
	private ArrayList<String> existingProjects;
	private HashMap<Integer, String> progressBook;
	private ArrayList<ArrayList<Integer>> projectBookShelf;
	private ArrayList<HashMap<Integer, String>> progressBookShelf;
	
/*******************************************************************************/
	
	/**
	 * Construct an instance of project handler with all the project details.
	 * @param baseDirectory
	 */
	public FileProjectHandler(String baseDirectory){
		//TODO: what if base directory is null?
		this.baseDirectory = baseDirectory.concat("/");
		
		projectBookShelf = new ArrayList<ArrayList<Integer>>();
		progressBookShelf = new ArrayList<HashMap<Integer, String>>();
		progressBook = new HashMap<Integer, String>();
		
		readOverviewerFile();
	}
	
	/**
	 * Delete the whole project with the projectName.
	 * @param projectName
	 * @return true if project has been successfully deleted, else return false
	 */
	public boolean deleteProject(String projectName){		
		if(!existingProjects.contains(projectName) || projectName == null){
			return false;
		}
		
		projectName = projectName.toLowerCase();
		
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
	
	/**
	 * Retrieve the names of the existing projects
	 * @return ArrayList<String> containing the names of existing projects, 
	 * return empty ArrayList<String> if there are no existing projects.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getListOfExistingProjects(){
		return (ArrayList<String>) existingProjects.clone();
	}
	
	/**
	 * Retrieve the index of events added into a project
	 * @param name of the project to be retrieved
	 * @return ArrayList<Integer> of index, return empty ArrayList<Integer> if 
	 * there are no events in the project
	 */
	public ArrayList<Integer> retrieveProject(String name){
		name = name.toLowerCase();
		
		ArrayList<Integer> projectBook = new ArrayList<Integer>();
		progressBook = new HashMap<Integer, String>();
		String fileName = name + ".txt";
		String addInfo;
		int id;
		
		try{
			File inputFile = new File(baseDirectory + fileName);
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
		 
	}
	
	/**
	 * Retrieve the Progress statements of the events in the project.
	 * @return a HashMap, where the key is the ID for the event, 
	 * and the value is the String of the progress details
	 */
	public HashMap<Integer, String> retrieveProjectProgress(){
		//TODO: what if user call this first before retrieveProject.
		return progressBook;
	}
	
	/**
	 * Create a new project.
	 * @param projectName
	 * @return true if the project has been created, else return false. 
	 */
	public boolean createNewProject(String projectName){
		if(!existingProjects.contains(projectName) && projectName != null){
			projectName = projectName.toLowerCase();
			return createNewProjectFile(projectName);
		}
		return false;
	}
	
	/**
	 * Save any changes done to the project
	 * @param projectBook contains the index of events in the project
	 * @param progressBook contains the progress statements of events in the project
	 * @param projectName 
	 * @return true if the project has been successfully saved, else return false.
	 */
	public boolean saveEditedProjectDetails(ArrayList<Integer> projectBook, 
				HashMap<Integer, String> progressBook, String projectName){
		
		projectName = projectName.toLowerCase();
		
		try{
			File outfile = new File(baseDirectory + projectName + ".txt");
			
			if(!outfile.exists()){
				return false; 
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Integer id : projectBook){
				writer.write(id.toString()); writer.newLine();
				if(progressBook.isEmpty()){
					writer.write("");
				}else{
					writer.write(progressBook.get(id));
				}
				writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}catch(NullPointerException e){
			System.out.println("One of the parameter is null");
			return false;
		}
		
	}
	
	/**
	 * Set another new base directory
	 * @param newBaseDirectory
	 * @return true if directory has been updated, else return false
	 */
	public boolean setNewDirectory(String newBaseDirectory){	
		if((newBaseDirectory != null) && new File(newBaseDirectory).exists()){
			baseDirectory = newBaseDirectory.concat("/" + PROJECT + "/");
			return true;
		}
		return false;	
	}
	
	/**
	 * read in all the existing projects
	 * @return true if successful in reading the projects, else return false.
	 */
	public boolean readAll(){
		projectBookShelf = new ArrayList<ArrayList<Integer>>();
		progressBookShelf = new ArrayList<HashMap<Integer, String>>();
		
		for(String project: existingProjects){
			ArrayList<Integer> projectBook = retrieveProject(project);
			HashMap<Integer, String> progressBook = retrieveProjectProgress();
			projectBookShelf.add(projectBook);
			progressBookShelf.add(progressBook);
		}
		
		return true;
	}
	
	/**
	 * Save all the project data onto text files.
	 * @return true when successfully written all the existing projects, else return false
	 */
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
	/**
	 * create a new project
	 * @param textFileName
	 * @return true when successful in creating the project file, else return false.
	 */
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
	
	/**
	 * read the overview file which contains the list of names of all the
	 * existing projects
	 */
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
			updateOverviewFile();
		}catch (IOException e) {
			// Do nothing
		}
	}
	
	/**
	 * Save any changes in project files.
	 * @return
	 */
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
		}catch(NullPointerException e){
			// base directory is null
			return false;
		}
	}
}