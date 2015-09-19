import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	private static final String EVENT_OVERVIEWER = "overviewer.txt";
	private File inputFile;
	private String eventPath;
	private String todoPath;
	private String projectPath;
	
	private FileProjectHandler fProjH;
	private FileEventHandler fEventH;
	
	
/************************************* Public Methods ****************************************************/
	public FileHandler(){
		new StartUpHandler();
		readOverviewerFile();
		
		fEventH = new FileEventHandler(eventPath);
		fProjH = new FileProjectHandler(projectPath);
		
	}
	
	@Override
	public ArrayList<Event> retrieveEventByDate(String date){
		return fEventH.retrieveEventByDate(date);
	}
	
	@Override
	public ArrayList<Todo> retrieveTodoByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Todo> retrieveUniversalTodo(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Event> retrieveProjectTimeLine(String projectName) {
		return fProjH.retrieveProject(projectName);
	}
	
	@Override
	public boolean saveNewEventHandler(Event event){
		return fEventH.saveNewEventHandler(event);
	}
	
	@Override
	public boolean saveEditedEventHandler(ArrayList<Event> eventBook) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveNewTodoHandler(Todo task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveEditedTodoHandler(ArrayList<Todo> taskBook) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}
	

/************************************* Overview File ****************************************************/	
	
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
	
//	private boolean updateOverviewFile(String fileName){
//		availableMonths.add(fileName);
//		try{
//			File outfile = new File(EVENT_OVERVIEWER);
//			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
//			
//			for(String month : availableMonths){
//				writer.write(month.trim());
//				writer.newLine();
//			}
//			writer.close();
//			return true;
//	
//		}catch(IOException e){
//			System.out.println("File cannot be written.\n");
//			return false;
//		}
//	}
	
/*********************************** File Manipulation *************************************************/	
	
//	private String setFileName(String date){
//		String[] brokenUpDate = date.split(" ");
//		return brokenUpDate[1].concat(brokenUpDate[2] + ".txt");	
//	}
//
//	private void selectFileAsInputFile(String FileName){
//		inputFile = new File(FileName);
//	}
//	
//	private void createNewMonthFile(String textFileName){
//
//		try{
//			File newFile = new File(textFileName);
//			newFile.createNewFile();
//			
//		}catch(IOException e){
//			
//		}catch(SecurityException e){
//			
//		}
//	}
//	
///************************************* Reading File ***************************************************/
//	
//	private ArrayList<Event> filterEventsToSpecificDate(String date, ArrayList<Event> eventBookByMonth) {
//		ArrayList<Event> eventBookByDate = new ArrayList<Event>();
//		
//		for(Event anEvent : eventBookByMonth){
//			if( anEvent.getStartDateString().toLowerCase().equals(date.toLowerCase())){
//				eventBookByDate.add(anEvent);
//			}
//		}
//		return eventBookByDate;
//	}
//	
///********************************** Writing on File ***************************************************/
//	
//	private boolean saveEventBook(String textFileName, ArrayList<Event> eventBook){
//		
//		try{
//			File outfile = new File(textFileName);
//			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
//			
//			
//			
//			for(Event anEvent : eventBook){
//				writer.write(anEvent.toString()); 
//				writer.newLine();
//			}
//			writer.close();
//			return true;
//	
//		}catch(IOException e){
//			System.out.println("File cannot be written.\n");
//			return false;
//		}
//	}
//	
//	private void sortEventsByDate(){
//		
//	}


}