import java.util.Calendar;

/**
 * Assumption is that the time and date format goes as follow:
 * 1) date: dd mm yyyy
 * 2) time: hhmm
 * 
 */

public class Event extends Item {
	
	private Calendar start;
	private Calendar end;
	
	/*********************************** Constructor ******************************************/
	
	public Event(String name, String date, String startTime, String endTime, String additionalInfo){
		this(name, date, date, startTime, endTime, additionalInfo);
	}
	
	public Event(String name, String startDate, String endDate, String startTime, String endTime, String additionalInfo ){
		super(name, additionalInfo);
		
		start = Calendar.getInstance();
		end = Calendar.getInstance();
		
		updateDate(start, startDate);
		updateTime(start, startTime);
		updateDate(end, endDate);
		updateTime(end, endTime);
		
	}
	
	/*********************************** Accessors ********************************************/
	
	public Calendar getStartCalendar(){
	    return start;
	}
	
	public Calendar getEndCalendar(){
        return end;
    }
	
	public String getStartDateString() {
	    return getDateString(start);
	}
	
	public String getEndDateString() {
        return getDateString(end);
    }
	
	public String getStartTimeString() {
        return getTimeString(start);
    }
    
    public String getEndTimeString() {
        return getTimeString(end);
    }
	
/**************************************  Mutators ********************************************/
	
	public boolean updateStartDate(String dateString) {
        return updateDate(start, dateString);
    }
    
    public boolean updateStartTime(String timeString) {
        return updateTime(start, timeString);
    }
    
    public boolean updateEndDate(String dateString) {
        return updateDate(end, dateString);
    }
    
    public boolean updateEndTime(String timeString) {
        return updateTime(end, timeString);
    }

/*********************************** Overriding Methods ***********************************/
	
	public String toString(){
		return  getName() + "\n" 
				+ getAdditionalInfo() + "\n" 
				+ getStartDateString() + "\n" 
				+ getEndDateString() + "\n" 
				+ getStartTimeString() + "\n" 
				+ getEndTimeString();
	}


}