import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;


public class FileEventHandler {
	private static final String EVENTS = "Upcoming Events.txt";
	private static final String HISTORY = "History.txt";
	private static final String EVENT = "Event";
	
	
	private static final String PATTERN_DATE = "dd MMM yyyy";
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);
	
	private String baseDirectory;
	private File inputFile;
	
	private ArrayList<Event> allEvents;
	private ArrayList<Event> historyEvents;
	
	private Calendar todaysDate;
	
/*****************************************************************************************/		

 	public FileEventHandler(String theBaseDirectory){
		this.baseDirectory = theBaseDirectory.concat("/");
		allEvents = retrieveEvent(EVENTS);
		retrievePassedEvents();
		pushPassedEventsToHistoryFile();
	}

	public boolean saveNewEventHandler(Event event){
		allEvents.add(event);
		saveEventBook();
		return true;
	}
			
	public boolean saveEventBook(){
		sortEventsByDate(allEvents);
		try{
			File outfile = new File(baseDirectory + EVENTS);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Event anEvent : allEvents){
				writer.write(anEvent.toString()); 
				writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			
			System.out.println("File cannot be written.\n" + e.getMessage());
			return false;
		}
	}
	
	public boolean setNewDirectory(String newBaseDirectory){
		baseDirectory = newBaseDirectory.concat("/" + EVENT + "/");
		return true;
	}
	
	public ArrayList<Event> retrieveEventByDate(String date){
		
		ArrayList<Event> eventBookByDate = filterEventsToSpecificDate(date);
		
		return eventBookByDate;
	}
	
	public ArrayList<Event> retrieveEventsToDelete(){
		return allEvents;
	}
	
	public boolean updateHistory(){
		sortEventsByDate(historyEvents);
		try{
			File outfile = new File(baseDirectory + HISTORY);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Event anEvent : historyEvents){
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
	
/*****************************************************************************************/	

	private ArrayList<Event> filterEventsToSpecificDate(String dateString) {
		ArrayList<Event> eventBookByDate = new ArrayList<Event>();
		Calendar date = Calendar.getInstance();
		updateDate(date, dateString);
		Calendar startDate, endDate;
		
		ArrayList<Event> eventBook;
		if(date.before(todaysDate)){
			eventBook = historyEvents;
		}else{
			eventBook = allEvents;
		}
		
		for(Event event: eventBook){
			startDate = (Calendar) event.getStartCalendar().clone();
			endDate = (Calendar) event.getEndCalendar().clone();
			setZeroTime(date);
			setZeroTime(startDate);
			setZeroTime(endDate);
			
			if( date.equals(startDate) || date.equals(endDate) || (date.after(startDate) && date.before(endDate) ) ){
				eventBookByDate.add(event);
			}
		}
		return eventBookByDate;
	}
	
	private boolean pushPassedEventsToHistoryFile(){
		todaysDate = Calendar.getInstance();
		setZeroTime(todaysDate);
		boolean allEventsValid = true;
		int counter = 0;
		
		for(Event event: allEvents){

			if(event.getEndCalendar().before(todaysDate)){
				historyEvents.add(event);
				counter++;
				allEventsValid = false;
			}else{
				break;
			}
		}
		
		if(allEventsValid){
			return false;
		}else{
			for(int i=0; i<counter; i++){
				allEvents.remove(0);
			}
			saveEventBook();
			updateHistory();
			return true;
		}
		
	}
 	
	private ArrayList<Event> retrieveEvent(String textFileName){
		
		selectFileAsInputFile(baseDirectory.concat(textFileName));
		
		ArrayList<Event> eventBook = new ArrayList<Event>();
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
 	
 	private boolean retrievePassedEvents(){
		historyEvents = retrieveEvent(HISTORY);
		return true;
	}
 	
	private void selectFileAsInputFile(String fileName){
		inputFile = new File(fileName);
	}
	
	private void setZeroTime(Calendar date){
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}
	
	private void sortEventsByDate(ArrayList<Event> eventBook){
		Collections.sort(eventBook, new customComparator());
	}

	private boolean updateDate(Calendar calendar, String dateString) {
        try {
            Calendar date = Calendar.getInstance();
            date.setTime(FORMAT_DATE.parse(dateString));
            
            calendar.set(Calendar.DATE, date.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
	
}

class customComparator implements Comparator<Event>{
	
	Calendar startDate1, startDate2, endDate1, endDate2;
	
	@Override
	public int compare(Event event1, Event event2) {
		
		setupCalendar(event1, event2);
		
		return compareStartDate();
	}

	private int compareStartDate() {
		if(startDate1.before(startDate2)){
			return -1;
		}else if(startDate1.after(startDate2)){
			return 1;
		}else{
			return compareEndDate();
		}
	}

	private int compareEndDate() {
		if(endDate1.before(endDate2)){
			return -1;
		}else if(endDate1.after(endDate2)){
			return 1;
		}else{
			return 0;
		}
	}

	private void setupCalendar(Event event1, Event event2) {
		startDate1 = (Calendar) event1.getStartCalendar().clone();
		startDate2 = (Calendar) event2.getStartCalendar().clone();
		endDate1 = (Calendar) event1.getEndCalendar().clone();
		endDate2 = (Calendar) event2.getEndCalendar().clone();
	}
	
}
