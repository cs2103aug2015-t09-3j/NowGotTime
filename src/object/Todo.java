//@@author A0130445R

package object;

import helper.CalendarHelper;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Assumption is that the time and date format goes as follow:
 * 1) date: dd mm yyyy
 * 2) time: hhmm
 * 
 */

public class Todo extends Item {

    private static final String DEFAULT_DATE = "01 Jan 0000";	// the earliest Date possible
    
	private Calendar deadline = null;
    private boolean hasDate = true;
    private boolean hasTime = true;


    /************************************ Constructor ***************************************/

    public Todo(String name) {
        this(name, "");
    }

    public Todo(String name, String additionalInfo) {
        this(name, additionalInfo, DEFAULT_DATE);
    }

    public Todo(String name, String additionalInfo, String deadlineDate) {
        this(name, additionalInfo, deadlineDate, "");
    }

    public Todo(String name, String additionalInfo, String deadlineDate, String deadlineTime) {
        super(name, additionalInfo);

        this.deadline = Calendar.getInstance();

        hasDate = CalendarHelper.updateDate(this.deadline, deadlineDate);
        hasTime = CalendarHelper.updateTime(this.deadline, deadlineTime);
        	
        if(deadlineDate.equals(DEFAULT_DATE)){
        	hasDate = false;
        }
    }

    /************************************ Accessors ***************************************/

    public Calendar getDeadline(){
        return deadline;
    }

    public String getDeadlineDateString(){
        return CalendarHelper.getDateString(deadline);
    }

    public String getDeadlineTimeString(){
        return CalendarHelper.getTimeString(deadline);
    }

    public String getDeadlineDateTimeString(){
        return CalendarHelper.getDateTimeString(deadline);
    }

    public boolean hasDate(){
        return hasDate;
    }

    public boolean hasTime(){
        return hasTime;
    }

    /*********************************  Mutators ********************************************/

    public boolean updateDeadlineDate(String dateString) {
        return CalendarHelper.updateDate(deadline, dateString);
    }

    public boolean updateDeadlineTime(String timeString) {
        return CalendarHelper.updateTime(deadline, timeString);
    }

    public boolean updateDeadlineDateTime(String dateTimeString) {
        return CalendarHelper.updateDateTime(deadline, dateTimeString);
    }
    
    public boolean updateDeadline(String calendarString) {
        return CalendarHelper.updateCalendar(deadline, calendarString);
    }

    /********************************** Process Dates ****************************************/

    /********************************* Process Time ******************************************/

    /******************************* Overriding Methods ***************************************/

    public String toString(){
        String allDetails = (getName() + "\n");

        if (hasDate) {
            allDetails = allDetails.concat(getDeadlineDateString());
        }
        else {
            allDetails = allDetails.concat("no date");
        }

        allDetails = allDetails.concat("\n");

        if (hasTime) {
            allDetails = allDetails.concat(getDeadlineTimeString());
        }
        else {
            allDetails = allDetails.concat("no time");
        }
                
        return allDetails;
    }

    public String toFormattedString(String dateString) throws ParseException {
        

        return getName();
    }
    
    public String toFormattedString() throws ParseException {
        
        return getName();
    }
    
    public String getTimeStringOn(String dateString) {
        if (hasDate()) {
            return getDeadlineTimeString();
        } else {
            return "";
        }
    }

}
