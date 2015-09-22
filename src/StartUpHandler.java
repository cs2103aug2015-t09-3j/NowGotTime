import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

public class StartUpHandler {
	
	private static final String PROJECT = "Project";
	private static final String EVENT = "Event";
	private static final String TODO = "Todo";
	private static final String END_OF_PATH_DIRECTORY = "<endOfPathDirectory>";
	private static final String OVERVIEW = "overview.txt";
	
	private String todoPath;
	private String eventPath;
	private String projectPath;
	
//	public static void main(String[] args){
//		StartUpHandler s = new StartUpHandler();
//	}
	
	public StartUpHandler(){
		if(!checkIfOverviewExist()){
			atFirstStartUp();
		}else{
			checkDirectories();
		}
	}
	
	private boolean atFirstStartUp(){
		todoPath = createDirectory(TODO);
		eventPath = createDirectory(EVENT);
		projectPath = createDirectory(PROJECT);
		
		if(todoPath != null && eventPath != null && projectPath!= null){
			return createOverviewTextFile();
		}
		return false;
	}
	
	//create directory if they are missing.
	private boolean checkDirectories() {
		
		readOverviewFile();
		
		if( !(new File(todoPath)).exists() ){
			createDirectory(TODO);
		}
		
		if( !(new File(eventPath)).exists() ){
			createDirectory(EVENT);
		}
		
		if( !(new File(todoPath)).exists() ){
			createDirectory(PROJECT);
		}
		
		return true;
	}

	private boolean checkIfOverviewExist(){
		File file = new File(OVERVIEW);
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}
	
	//return String of directory path if created, else return null
	private String createDirectory(String directoryName){
		String baseDirectory = getJavaProjectDirectory();
		String newDirectoryPath = baseDirectory.concat("\\" + directoryName);
		if(makeNewDirectory(newDirectoryPath)){
			return newDirectoryPath;
		}else{
			return null;
		}
	}
	
	private boolean createOverviewTextFile(){
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
			System.out.println("File cannot be written.\n");
			return false;
		}
	}

	private String getJavaProjectDirectory(){
		return System.getProperty("user.dir").toString();
	}
	
	private boolean makeNewDirectory(String directoryName){
		File file = new File(directoryName);
		if (!file.exists()) {
			if (file.mkdir()) {
				return true;
			}
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
			e.printStackTrace();
			return false;
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}		
	}
}