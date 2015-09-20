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


public class FileProjectHandler {
	private static final String PROJECT_OVERVIEWER = "projectOverviewer.txt";
	private String baseDirectory;
	private ArrayList<String> existingProjects;
	private File inputFile;

/*******************************************************************************/
	
	public FileProjectHandler(String baseDirectory){
		this.baseDirectory = baseDirectory.concat("\\");
		readOverviewerFile();
	}
	
	public ArrayList<Event> retrieveProject(String name){
		
		String fileName = setFileName(name);
		selectFileAsInputFile(baseDirectory + fileName);
		
		ArrayList<Event> projectBook  = new ArrayList<Event>();
		String eventName, startDate, endDate, startTime, endTime, addInfo;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String lineOfText;
			
			while( (lineOfText = reader.readLine()) != null ){
				eventName = lineOfText;
				addInfo = reader.readLine();
				startDate = reader.readLine();
				endDate = reader.readLine();
				startTime = reader.readLine();
				endTime = reader.readLine();			
				
				Event event = new Event(eventName, startDate, endDate, startTime, endTime, addInfo);
				projectBook.add(event);
			}
			
			reader.close();		 
			return projectBook;
		}
		catch (FileNotFoundException e) {
			return projectBook;
		}catch (IOException e) {
			return projectBook;
		}	
	}
	
	public boolean createNewProject(String projectName){
		if(!existingProjects.contains(projectName)){
			return createNewProjectFile(projectName);
		}
		return false;
	}
	
	public boolean saveEditedProjectDetails(ArrayList<Event> projectBook, String projectName){
		sortEventsByDate(projectBook);
		return overwriteSave(projectName, projectBook);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getListOfExistingProjects(){
		return (ArrayList<String>) existingProjects.clone();
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
	
/*******************************************************************************/

	private boolean createNewProjectFile(String textFileName){

		try{
			File newFile = new File(baseDirectory + textFileName + ".txt");
			existingProjects.add(textFileName);
			updateOverviewFile();
			return newFile.createNewFile();
			
		}catch(IOException e){
			return false;
		}catch(SecurityException e){
			return false;
		}
	}
	
	private boolean overwriteSave(String projectName, ArrayList<Event> projectBook) {
		
		try{
			File outfile = new File(baseDirectory + projectName + ".txt");
			
			if(!outfile.exists()){
				return false; 
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Event anEvent : projectBook){
				writer.write(anEvent.toString()); 
				writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
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
	
	private void sortEventsByDate(ArrayList<Event> projectBook){
		//Collections.sort(currentWorkingMonthFile, new customComparator);
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
