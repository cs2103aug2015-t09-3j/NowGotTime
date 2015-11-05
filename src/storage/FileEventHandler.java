//@@author A0124402Y

package storage;
import helper.CalendarHelper;
import helper.MyLogger;

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
import java.util.logging.Level;

import object.Event;

public class FileEventHandler {
	
	private MyLogger myLogger = new MyLogger();
	
	private static final String EVENTS = "All_Events.txt";
	private static final String EVENT = "Event";
	
	private String baseDirectory;
	
	private ArrayList<Event> allEvents = new ArrayList<Event>();
	private ArrayList<Event> allEventsClone= new ArrayList<Event>();
	
/*****************************************************************************************/		

 	@SuppressWarnings("unchecked")
	public FileEventHandler(String theBaseDirectory){
		this.baseDirectory = theBaseDirectory.concat("/");
		allEvents = retrieveEvent();
		allEventsClone = (ArrayList<Event>) allEvents.clone();
	}

	public boolean saveNewEventHandler(Event event){
		if(event != null){
			allEventsClone.add(event);
			saveEventBook();
			return true;
		}
		return false;
	}
			
	@SuppressWarnings("unchecked")
	public boolean saveEventBook(){
		//TODO: change to flag instead? can improve efficiency
		if(allEvents != allEventsClone){
			allEvents = allEventsClone;
		}
		
		sortEventsByDate(allEvents);
		allEventsClone = (ArrayList<Event>) allEvents.clone();
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
			if( (date = createDate(dateString, date)) == null){
				return null;
			}
			
			extractEventByDate(eventBookByDate, date);
			return eventBookByDate;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Event> retrieveAllEvents(){
		allEvents = retrieveEvent();
		allEventsClone = (ArrayList<Event>) allEvents.clone();
		return allEventsClone;
	}
	
/*************************************************************************************/
 	
	private Calendar createDate(String dateString, Calendar date) {
		try {
			date = CalendarHelper.parseDate(dateString);
			setZeroTime(date);
		} catch (ParseException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"retrieveEventByDate", e.getMessage());
			return null;
		}
		return date;
	}
	
	private Event createEvent(String eventName, String startDate, String endDate,
			String startTime, String endTime, String addInfo, String ID,
			boolean isDone) {
		Event event = new Event(eventName, startDate, endDate, startTime, endTime, addInfo);
		event.setId(Integer.parseInt(ID));
		event.setDone(isDone);
		return event;
	}
	
	private void extractEventByDate(ArrayList<Event> eventBookByDate, 
			Calendar date) {
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
	
	private ArrayList<Event> retrieveEvent(){
		ArrayList<Event> eventBook = new ArrayList<Event>();
		String eventName, startDate, endDate, startTime, endTime, addInfo, ID;
		boolean isDone;
		
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
				isDone = Boolean.parseBoolean(reader.readLine());
				addInfo = "";
				
				Event event = createEvent(eventName, startDate, endDate,
						startTime, endTime, addInfo, ID, isDone);
				eventBook.add(event);
			}
			reader.close();	
			return eventBook;
			
		}catch (FileNotFoundException e) {
			myLogger.logp(Level.WARNING, getClass().getName(),
					"retrieveEventHandler", e.getMessage());
			saveEventBook();
			return eventBook;
			
		}catch (IOException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"retrieveEventHandler", e.getMessage());
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
			Collections.sort(eventBook);
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
				writer.write(anEvent.getDone() + "");
				writer.newLine();
			}
			
			writer.close();
			return true;
	
		}catch(Exception e){
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"writeToFile", e.getMessage());
			return false;
		}
	}
}