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
	private static final String EVENT_OVERVIEWER = "eventOverviewer.txt";
	
	private static final String PATTERN_DATE = "dd MMM yyyy";
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);
	
	private String baseDirectory;
	private File inputFile;
	private ArrayList<String> availableMonths;
	
	private Calendar currentDate = null;
	private ArrayList<Event> currentWorkingMonthFile = new ArrayList<Event>();
	
/*****************************************************************************************/		

	public FileEventHandler(String theBaseDirectory){
		this.baseDirectory = theBaseDirectory.concat("\\");
		readOverviewerFile();
	}

	public boolean saveNewEventHandler(Event event){
		
		Calendar startDate = (Calendar) event.getStartCalendar().clone();			//unpack date
		Calendar endDate = (Calendar) event.getEndCalendar().clone();
		Calendar tempDate = (Calendar) startDate.clone();
		
		setZeroTime(startDate);
		setZeroTime(endDate);
		setZeroTime(tempDate);		
		
		while(endDate.after(tempDate) || endDate.equals(tempDate)){
			
			if(needToChangeMonthFile(tempDate)){
				save(event);				
			}
			
			tempDate.add(Calendar.DAY_OF_MONTH, 1);			
		}
		currentDate = null;	//reset
		return true;
	}
			
	public boolean saveEventBook(String date, ArrayList<Event> eventBook){
		//TODO: consider the case when an edited event has date that over spill month
		sortEventsByDate(eventBook);
		try{
			File outfile = new File(baseDirectory + setFileName(date));
			
			if(!outfile.exists()){
				updateOverviewFile(setFileName(date));
			}
			
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
	
	public ArrayList<Event> retrieveEventByDate(String date){
		
		ArrayList<Event> eventBookByMonth = retrieveEventByMonth(date);
	
		ArrayList<Event> eventBookByDate = filterEventsToSpecificDate(date, eventBookByMonth);
		
		return eventBookByDate;
	}
	
/*****************************************************************************************/	
		
	private boolean currentDateIsInitialized(){
		return currentDate != null;
	}

	private ArrayList<Event> filterEventsToSpecificDate(String dateString, ArrayList<Event> eventBookByMonth) {
		ArrayList<Event> eventBookByDate = new ArrayList<Event>();
		Calendar date = Calendar.getInstance();
		updateDate(date, dateString);
		Calendar startDate, endDate;
		
		for(Event event: eventBookByMonth){
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

	private boolean monthIsTheSame(Calendar date){

		return (date.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH));
	}
	
	//return true if the month input date and the currentDate is different, else return true; 
	private boolean needToChangeMonthFile(Calendar date){
		
		if(!currentDateIsInitialized() || !monthIsTheSame(date)){
			currentDate = (Calendar) date.clone();
			currentWorkingMonthFile = retrieveEventByMonth( date.get(Calendar.DAY_OF_MONTH) + " " 
							 				   + parseMonth(date.get(Calendar.MONTH)) + " " 
							 				   			  + date.get(Calendar.YEAR) );
			return true;			
		}else{
			return false;
		}
	}
		
	private String parseMonth(int month){
		switch(month){
			case 0: return "Jan";
			case 1: return "Feb";
			case 2: return "Mar";
			case 3: return "Apr";
			case 4: return "May";
			case 5: return "Jun";
			case 6: return "Jul";
			case 7: return "Aug";
			case 8: return "Sep";
			case 9: return "Oct";
			case 10: return "Nov";
			case 11: return "Dec";
			default: return null;
		}
	}
	
 	private ArrayList<Event> retrieveEventByMonth(String date){
		
		String fileName = setFileName(date);
		selectFileAsInputFile(baseDirectory + fileName);
		
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
	
	private void readOverviewerFile() {
		inputFile = new File( baseDirectory + EVENT_OVERVIEWER);	
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
	
	private boolean save(Event event){
		currentWorkingMonthFile.add(event);
		saveEventBook(setFileName(currentDate), currentWorkingMonthFile);
		return true;
	}
	
	private void selectFileAsInputFile(String fileName){
		inputFile = new File(fileName);
	}

	private String setFileName(String date){
		String[] brokenUpDate = date.split(" ");
		return brokenUpDate[1].concat(brokenUpDate[2] + ".txt");	
	}
	
	private String setFileName(Calendar date){
		return date.get(Calendar.DAY_OF_MONTH) +  " " + 
				parseMonth(date.get(Calendar.MONTH)) + " " + date.get(Calendar.YEAR);
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
	
	private boolean updateOverviewFile(String fileName){
		
		availableMonths.add(fileName);
		try{
			File outfile = new File(baseDirectory + EVENT_OVERVIEWER);
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
	
	public boolean updateDate(Calendar calendar, String dateString) {
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
