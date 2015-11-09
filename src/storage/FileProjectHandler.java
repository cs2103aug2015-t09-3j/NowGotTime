//@@author A0124402Y

package storage;

import helper.MyLogger;

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
import java.util.logging.Level;

/**
 * 
 * @author RX.huang
 *
 * This class is responsible for data storage and retrieval of project related 
 * information.
 *
 */
public class FileProjectHandler {
	
	private MyLogger myLogger = new MyLogger();
	
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
		    
		} catch (NoSuchFileException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"deleteProject", e.getMessage());
		    return false;
		    
		} catch (DirectoryNotEmptyException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"deleteProject",  e.getMessage());
		    return false;
		    
		} catch (IOException e) {
		    // File permission problems are caught here.
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"deleteProject", e.getMessage());
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
			
		}catch(Exception e){
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"retrieveProject", e.getMessage());
			return projectBook;
		}		 
	}
	
	/**
	 * Retrieve the Progress statements of the events in the project.
	 * @return a HashMap, where the key is the ID for the event, 
	 * and the value is the String of the progress details
	 */
	public HashMap<Integer, String> retrieveProjectProgress(){
		assert(progressBook != null);
		return progressBook;
	}
	
	/**
	 * Create a new project.
	 * @param projectName
	 * @return true if the project has been created, else return false. 
	 */
	public boolean createNewProject(String projectName){
		if(!existingProjects.contains(projectName) && projectName != null 
				&& !projectName.isEmpty()){
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
					if(progressBook.get(id) == null){
						writer.write("");
					}else{
						writer.write(progressBook.get(id));
					}
				}
				writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(Exception e){
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"saveEditedProjectDetails", e.getMessage());
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
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"writeAll", e.getMessage());
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
			
		}catch(Exception e){
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"createNewProjectFile", e.getMessage());
			existingProjects.remove(textFileName);
			updateOverviewFile();
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
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"readOverviewerFile", e.getMessage());
			updateOverviewFile();
		}catch (Exception e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"readOverviewerFile", e.getMessage());
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
	
		}catch(Exception e){
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"updateOverviewFile", e.getMessage());
			return false;
		}
	}
}