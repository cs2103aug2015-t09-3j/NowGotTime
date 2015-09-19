import java.io.BufferedWriter;
import java.io.File;
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
	
	private static final String END_OF_PATH_DIRECTORY = "<endOfPathDirectory>";
	private static final String OVERVIEW = "overview.txt";
	
	private String todoPath;
	private String eventPath;
	private String projectPath;
	
	public static void main(String[] args){
		StartUpHandler s = new StartUpHandler();
	}
	
	public StartUpHandler(){
		if(!checkIfOverviewExist()){
			atFirstStartUp();
		}
	}
	
	private boolean checkIfOverviewExist(){
		File file = new File(OVERVIEW);
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}

	private boolean atFirstStartUp(){
		todoPath = createDirectory("Todo");
		eventPath = createDirectory("Event");
		projectPath = createDirectory("Project");
		
		if(todoPath != null && eventPath != null && projectPath!= null){
			return createOverviewTextFile();
		}
		return false;
	}
	 
	private String getJavaProjectDirectory(){
		return System.getProperty("user.dir").toString();
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
	
	private boolean makeNewDirectory(String directoryName){
		File file = new File(directoryName);
		if (!file.exists()) {
			if (file.mkdir()) {
				return true;
			}
		}
		return false;
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
}
