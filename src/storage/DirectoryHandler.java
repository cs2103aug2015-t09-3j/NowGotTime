package storage;
import helper.MyLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author RX.huang
 * 
 * This class is responsible for:
 * 1) Creation of directories for
 * 		i) event, 
 * 		ii) todo,
 * 		ii) project
 * 2) Creation of the overview file
 * 
 * if these files do not exist.
 *  
 **/

public class DirectoryHandler {
	
	private static final String DATABASE = "database";

	private MyLogger myLogger = new MyLogger();
	
	private static final String PROJECT = "Project";
	private static final String EVENT = "Event";
	private static final String TODO = "Todo";
	private static final String END_OF_PATH_DIRECTORY = "<endOfPathDirectory>";
	private static final String OVERVIEW = "overview.txt";
	
	private String todoPath;
	private String eventPath;
	private String projectPath;
	private String baseDirectory;

	
/********************* Public class methods **********************************/
	
	public DirectoryHandler(){
		
		if(!doesFileExist(OVERVIEW)){
			setUpDirectory(null);
		}else{
			checkDirectories();
		}
	}
	
	public boolean setNewBaseDirectory(String theBaseDirectory){
		if(theBaseDirectory != null && (new File(theBaseDirectory).exists())){
			String oldDirectory = baseDirectory;			
			if(setUpDirectory(theBaseDirectory)){
				return true;
			}else{
				setUpDirectory(oldDirectory);	//revert back
			}
		}
		return false;
	}
	
/************************* Private class methods *****************************/
	
	//return true if directories have all been created, return false if some/all directories failed to be created.
	private boolean setUpDirectory(String theBaseDirectory){
		if(theBaseDirectory != null){
			baseDirectory = theBaseDirectory;
			baseDirectory = createDirectory(DATABASE);
		}

		todoPath = createDirectory(TODO);
		eventPath = createDirectory(EVENT);
		projectPath = createDirectory(PROJECT);
		
		if(todoPath != null && eventPath != null && projectPath != null){
			return writeOverviewTextFile();
		}
		return false;
	}
	
	//create directory if they are missing.
	private boolean checkDirectories() {	
		readOverviewFile();
		
		if( !doesFileExist(todoPath) ){
			createDirectory(TODO);
		}
		
		if( !doesFileExist(eventPath) ){
			createDirectory(EVENT);
		}
		
		if( !doesFileExist(projectPath) ){
			createDirectory(PROJECT);
		}
		
		return true;
	}

	//TODO: modularized it to check if a path exist instead.
	private boolean doesFileExist(String path){
		return new File(path).exists();
	}
	
	//return String of directory path if created, else return null
	private String createDirectory(String directoryName){
		if(baseDirectory == null){
			baseDirectory = getJavaProjectDirectory();
		}
		
		String newDirectoryPath = baseDirectory.concat("/" + directoryName);
		if(makeNewDirectory(newDirectoryPath)){
			return newDirectoryPath;
		}else{
			return null;
		}
	}
	
	private boolean writeOverviewTextFile(){
		try{
			File outfile = new File(OVERVIEW);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
					
			writer.write(todoPath); writer.newLine();
			writer.write(eventPath); writer.newLine();
			writer.write(projectPath); writer.newLine();
			writer.write(END_OF_PATH_DIRECTORY);
			writer.close();
			return true;
	
		}catch(IOException e){
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"writeOverviewTextFile", e.getMessage());
			return false;
		}
	}
	
	//TODO: rename this. it is not longer a project directory.
	private String getJavaProjectDirectory(){
		String baseDirectory = System.getProperty("user.dir").toString() + "/" + DATABASE + "/";
		File file = new File(baseDirectory);
		if(!file.exists()){
			file.mkdir();
			//TODO: What if directory cannot be written???
		}
		
		return baseDirectory;
	}
	
	private boolean makeNewDirectory(String directoryName){
		File file = new File(directoryName);
		if (!file.exists()) {
			return file.mkdir();
		}
		return false;
	}
	 
	private boolean readOverviewFile(){
		File outfile = new File(OVERVIEW);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(outfile));
			
			todoPath = reader.readLine();
			eventPath = reader.readLine();
			projectPath = reader.readLine();
			
			reader.close();
			return true;
			
		} catch (FileNotFoundException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					getClass().getEnclosingMethod().getName(), e.getMessage());
			return false;
		}catch (IOException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					getClass().getEnclosingMethod().getName(), e.getMessage());
			return false;
		}		
	}
}