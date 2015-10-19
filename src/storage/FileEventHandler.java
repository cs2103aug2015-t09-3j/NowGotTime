package storage;
import helper.CalendarHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import object.Event;

public class FileEventHandler {
	private static final String EVENTS = "All_Events.txt";
	private static final String EVENT = "Event";
	
	private String baseDirectory;
	
	private ArrayList<Event> allEvents = new ArrayList<Event>();
	private ArrayList<Event> allEventsClone= new ArrayList<Event>();
	
/*****************************************************************************************/		

 	public FileEventHandler(String theBaseDirectory){
		this.baseDirectory = theBaseDirectory.concat("/");
		allEventsClone = allEvents;
		allEvents = retrieveEvent();
	}

	public boolean saveNewEventHandler(Event event){
		if(event != null){
			allEventsClone.add(event);
			saveEventBook();
			return true;
		}
		return false;
	}
			
	public boolean saveEventBook(){
		if(allEvents != allEventsClone){
			allEvents = allEventsClone;
		}
		sortEventsByDate(allEvents);
		return writeToFile();
	}
	
	public boolean setNewDirectory(String newBaseDirectory){
		if((newBaseDirectory != null) && new File(newBaseDirectory).exists()){
			baseDirectory = newBaseDirectory.concat("/" + EVENT + "/");
			return true;
		}
		return false;
	}
	
	public ArrayList<Event> retrieveEventByDate(String dateString){
		ArrayList<Event> eventBookByDate = new ArrayList<Event>();
		
		if(dateString != null){
			Calendar date = Calendar.getInstance();
			setZeroTime(date);
			try {
				date = CalendarHelper.parseDate(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			ArrayList<Event> eventBook = allEvents;
			Calendar startDate, endDate;
			for(Event event: eventBook){
				startDate = (Calendar) event.getStartCalendar().clone();
				endDate = (Calendar) event.getEndCalendar().clone();
				setZeroTime(startDate);
				setZeroTime(endDate);
				
				if( date.equals(startDate) || date.equals(endDate) || 
						(date.after(startDate) && date.before(endDate) ) ){
					eventBookByDate.add(event);
				}
			}
		}
		return eventBookByDate;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Event> retrieveAllEvents(){
		allEventsClone = (ArrayList<Event>) allEvents.clone();
		return allEventsClone;
	}
	
/*************************************************************************************/
 	
	private ArrayList<Event> retrieveEvent(){
		ArrayList<Event> eventBook = new ArrayList<Event>();
		String eventName, startDate, endDate, startTime, endTime, addInfo, ID;
		
		try {
			File inputFile = new File(baseDirectory + EVENTS);
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String lineOfText;
			
			while( (lineOfText = reader.readLine()) != null ){
				ID = lineOfText;
				eventName = reader.readLine();
				startDate = reader.readLine();
				endDate = reader.readLine();
				startTime = reader.readLine();
				endTime = reader.readLine();
				addInfo = "";
				
				Event event = new Event(eventName, startDate, endDate, startTime, endTime, addInfo);
				event.setId(Integer.parseInt(ID));
				eventBook.add(event);
			}
			
			reader.close();	
			return eventBook;
			
		}catch (FileNotFoundException e) {
			saveEventBook();
			return eventBook;
		}catch (IOException e) {
			return eventBook;
		}

	}
	
	private void setZeroTime(Calendar date){
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}
	
	private boolean sortEventsByDate(ArrayList<Event> eventBook){
		if(eventBook != null){
			Collections.sort(eventBook, new CustomComparator());
			return true;
		}
		return false;
	}
	
	private boolean writeToFile(){
		try{
			File outfile = new File(baseDirectory + EVENTS);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Event anEvent : allEvents){
				writer.write(anEvent.toString()); 
				writer.newLine();
			}
			
			writer.close();
			return true;
	
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("File cannot be written.\n");
			return false;
		}
	}
}

class CustomComparator implements Comparator<Event>{
	
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
