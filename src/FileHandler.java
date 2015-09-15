import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

public class FileHandler {
	private static final String EVENT_OVERVIEWER = "overviewer.txt";
	private static final String EVENT_NAME = "eventName";
	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String START_TIME = "startTime";
	private static final String END_TIME = "endTime";
	private static final String	ADDITIONAL_INFORMATION = "addInfo";	
	
	private File inputFile;
	private ArrayList<String> availableMonths;
	
/************************************* Public Methods ****************************************************/
	public FileHandler(){
		readOverviewerFile();
	}
	
	public ArrayList<HashMap<String, String>> retrieveEventByDate(String date){
		
		ArrayList<HashMap<String, String>> eventBookByMonth = retrieveEventByMonth(date);
		
		ArrayList<HashMap<String, String>> eventBookByDate = filterEventsToSpecificDate(
				date, eventBookByMonth);
		
		return eventBookByDate;
	}

	public ArrayList<HashMap<String, String>> retrieveEventByMonth(String date){
		String fileName = setFileName(date);
		selectFileAsInputFile(fileName);
		ArrayList<HashMap<String, String>> eventBook  = new ArrayList<HashMap<String, String>>();
		
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String lineOfText;
			
			while( (lineOfText = reader.readLine()) != null ){
				HashMap<String, String> anEvent = new HashMap<String, String>();
				anEvent.put(EVENT_NAME, lineOfText);
				anEvent.put(START_DATE, reader.readLine());
				anEvent.put(END_DATE, reader.readLine());
				anEvent.put(START_TIME, reader.readLine());
				anEvent.put(END_TIME, reader.readLine());
				anEvent.put(ADDITIONAL_INFORMATION, reader.readLine());
				
				eventBook.add(anEvent);
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
	
	public void saveNewEventHandler(HashMap<String, String> event){

		boolean dateWillSpillOverMonth = false;
		
		String startDate = event.get(START_DATE);
		String fileName = setFileName(startDate);
		if(!availableMonths.contains(fileName)){
			createNewMonthFile(fileName);
			updateOverviewFile(fileName);
		}
		
		ArrayList<HashMap<String, String>> eventBook = retrieveEventByMonth(startDate);
		eventBook.add(event);
		sortEventsByDate();
		saveEventBook(setFileName(startDate), eventBook);
		
		String endDate = event.get(END_DATE);
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
	
	private ArrayList<HashMap<String, String>> filterEventsToSpecificDate(
			String date, ArrayList<HashMap<String, String>> eventBookByMonth) {
		ArrayList<HashMap<String, String>> eventBookByDate = new ArrayList<HashMap<String, String>>();
		
		for(HashMap<String, String> anEvent : eventBookByMonth){
			if( anEvent.get("startDate").equals(date)){
				eventBookByDate.add(anEvent);
			}
		}
		return eventBookByDate;
	}
	
/********************************** Writing on File ***************************************************/
	
	private boolean saveEventBook(String textFileName, ArrayList<HashMap<String, String>> eventBook){
		
		try{
			File outfile = new File(textFileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			
			for(HashMap<String, String> anEvent : eventBook){
				writer.write(anEvent.get(EVENT_NAME)); writer.newLine();
				writer.write(anEvent.get(START_DATE)); writer.newLine();
				writer.write(anEvent.get(END_DATE)); writer.newLine();
				writer.write(anEvent.get(START_TIME)); writer.newLine();
				writer.write(anEvent.get(END_TIME)); writer.newLine();
				writer.write(anEvent.get(ADDITIONAL_INFORMATION)); writer.newLine();
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
