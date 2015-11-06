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

        CalendarHelper.updateDate(start, startDate);
        CalendarHelper.updateTime(start, startTime);
        CalendarHelper.updateDate(end, endDate);
        CalendarHelper.updateTime(end, endTime);

    }

    public Event(String name, String startDateTime, String endDateTime) {
        super(name , "");

        start = Calendar.getInstance();
        end = Calendar.getInstance();

        CalendarHelper.updateDateTime(start, startDateTime);
        CalendarHelper.updateDateTime(end, endDateTime);
    }

    /*********************************** Accessors ********************************************/

    public Calendar getStartCalendar(){
        return start;
    }

    public Calendar getEndCalendar(){
        return end;
    }

    public String getStartDateString() {
        return CalendarHelper.getDateString(start);
    }

    public String getEndDateString() {
        return CalendarHelper.getDateString(end);
    }

    public String getStartTimeString() {
        return CalendarHelper.getTimeString(start);
    }

    public String getEndTimeString() {
        return CalendarHelper.getTimeString(end);
    }

    public String getStartDateTimeString() {
        return CalendarHelper.getDateTimeString(start);
    }

    public String getEndDateTimeString() {
        return CalendarHelper.getDateTimeString(end);
    }

    /**************************************  Mutators ********************************************/

    public boolean updateStartDate(String dateString) {
        return CalendarHelper.updateDate(start, dateString);
    }

    public boolean updateStartTime(String timeString) {
        return CalendarHelper.updateTime(start, timeString);
    }

    public boolean updateStartDateTime(String dateTimeString) {
        return CalendarHelper.updateDateTime(start, dateTimeString);
    }

    public boolean updateEndDate(String dateString) {
        return CalendarHelper.updateDate(end, dateString);
    }

    public boolean updateEndTime(String timeString) {
        return CalendarHelper.updateTime(end, timeString);
    }

    public boolean updateEndDateTime(String dateTimeString) {
        return CalendarHelper.updateDateTime(end, dateTimeString);
    }

    public boolean updateStart(String calendarString) {
        return CalendarHelper.updateCalendar(start, calendarString);
    }
    
    public boolean updateEnd(String calendarString) {
        return CalendarHelper.updateCalendar(end, calendarString);
    }


    /*********************************** Overriding Methods ***********************************/

    public String toString(){
        return  getId() + "\n" 
                + getName() + "\n" 
                + getStartDateString() + "\n" 
                + getEndDateString() + "\n" 
                + getStartTimeString() + "\n" 
                + getEndTimeString();
    }

    public String toFormattedString(String dateString) throws ParseException {
        Calendar date = CalendarHelper.parseDate(dateString);

        String startTime = "00:00";
        String endTime = "23:59";

        CalendarHelper.updateTime(date, startTime);
        if (date.before(start)) {
            startTime = getStartTimeString();
        }

        CalendarHelper.updateTime(date, endTime);
        if (date.after(end)) {
            endTime = getEndTimeString();
        }

        return getName();
    }
    
    public String toFormattedString() throws ParseException {
        

        return getName();
    }

}