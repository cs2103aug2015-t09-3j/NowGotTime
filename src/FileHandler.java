import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	private ArrayList<String> availableMonths;
	
/************************************* Public Methods ****************************************************/
	public FileHandler(){
		readOverviewerFile();
	}
	
	@Override
	public ArrayList<Event> retrieveEventByDate(String date){
		
		ArrayList<Event> eventBookByMonth = retrieveEventByMonth(date);
		
		ArrayList<Event> eventBookByDate = filterEventsToSpecificDate(date, eventBookByMonth);
		
		return eventBookByDate;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean saveNewEventHandler(Event event){

		boolean dateWillSpillOverMonth = false;
		
		String startDate = event.getStartDateString();
		String fileName = setFileName(startDate);
		if(!availableMonths.contains(fileName)){
			createNewMonthFile(fileName);
			updateOverviewFile(fileName);
			return false;
		}
		
		ArrayList<Event> eventBook = retrieveEventByMonth(startDate);
		eventBook.add(event);
		sortEventsByDate();
		saveEventBook(setFileName(startDate), eventBook);
		
		String endDate = event.getEndDateString();
		if(!startDate.equals(endDate)){
			//TODO: spill over days
			
			if(!availableMonths.contains(setFileName(endDate)) ){
				createNewMonthFile(setFileName(endDate));
				dateWillSpillOverMonth = true;
				updateOverviewFile(setFileName(endDate));
			}
		}
		
		if(dateWillSpillOverMonth){
			eventBook = retrieveEventByMonth(endDate);
			eventBook.add(event);
			sortEventsByDate();
			saveEventBook(setFileName(startDate), eventBook);
		}
		return true;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveEditedProjectDetails(ArrayList<Event> projectBook) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getListOfExistingProject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ArrayList<Event> retrieveEventByMonth(String date){
		
		String fileName = setFileName(date);
		selectFileAsInputFile(fileName);
		
		ArrayList<Event> eventBook  = new ArrayList<Event>();
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
				eventBook.add(event);
			}
			
			reader.close();		 
			return eventBook;
		}
		catch (FileNotFoundException e) {
			return eventBook;
		}catch (IOException e) {
			return eventBook;
		}	
	}
	

/************************************* Overview File ****************************************************/	
	
	private void readOverviewerFile() {
		inputFile = new File(EVENT_OVERVIEWER);	
		availableMonths = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String date;
			
			while((date = reader.readLine()) != (null)){
				availableMonths.add(date);
			}		
			reader.close();		 
		}
		catch (FileNotFoundException e) {
			// Do nothing
		}catch (IOException e) {
			// Do nothing
		}
	}
	
	private boolean updateOverviewFile(String fileName){
		availableMonths.add(fileName);
		try{
			File outfile = new File(EVENT_OVERVIEWER);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			
			for(String month : availableMonths){
				writer.write(month.trim());
				writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
	}
	
/*********************************** File Manipulation *************************************************/	
	
	private String setFileName(String date){
		String[] brokenUpDate = date.split(" ");
		return brokenUpDate[1].concat(brokenUpDate[2] + ".txt");	
	}

	private void selectFileAsInputFile(String FileName){
		inputFile = new File(FileName);
	}
	
	private void createNewMonthFile(String textFileName){

		try{
			File newFile = new File(textFileName);
			newFile.createNewFile();
			
		}catch(IOException e){
			
		}catch(SecurityException e){
			
		}
	}
	
/************************************* Reading File ***************************************************/
	
	private ArrayList<Event> filterEventsToSpecificDate(String date, ArrayList<Event> eventBookByMonth) {
		ArrayList<Event> eventBookByDate = new ArrayList<Event>();
		
		for(Event anEvent : eventBookByMonth){
			if( anEvent.getStartDateString().toLowerCase().equals(date.toLowerCase())){
				eventBookByDate.add(anEvent);
			}
		}
		return eventBookByDate;
	}
	
/********************************** Writing on File ***************************************************/
	
	private boolean saveEventBook(String textFileName, ArrayList<Event> eventBook){
		
		try{
			File outfile = new File(textFileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			
			
			
			for(Event anEvent : eventBook){
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
	
	private void sortEventsByDate(){
		
	}


}