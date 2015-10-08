package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarHelper {
    
    /* Date and time format */
    public static final String PATTERN_DATE = "dd MMM yyyy";
    public static final String PATTERN_TIME = "HH:mm";
    public static final String PATTERN_DATE_TIME = "dd MMM yyyy HH:mm";
    
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat(PATTERN_TIME);
    public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(PATTERN_DATE_TIME);

    public static final String TYPE_DATE = "date";
    public static final String TYPE_TIME = "time";
    public static final String TYPE_DATE_TIME = "datetime";
    
    /* Calendar Helper functions */
    
    /**
     * Returns a Calendar object with parsed timeString as the time
     */
    public static Calendar parseTime(String timeString) throws ParseException {
        Calendar time = Calendar.getInstance();
        time.setTime(FORMAT_TIME.parse(timeString));
        return time;
    }
    
    /**
     * Returns a Calendar object with parsed dateString as the date
     */
    public static Calendar parseDate(String dateString) throws ParseException {
        Calendar date = Calendar.getInstance();
        date.setTime(FORMAT_DATE.parse(dateString));
        return date;
    }
    
    /**
     * Returns a Calendar object with parsed dateTimeString as the date and time
     */
    public static Calendar parseDateTime(String dateTimeString) throws ParseException {
        Calendar datetime = Calendar.getInstance();
        datetime.setTime(FORMAT_DATE_TIME.parse(dateTimeString));
        return datetime;
    }
    
    /**
     * Update the time of the specified calendar object with parsed timeString. Returns true when timeString is valid, otherwise returns false.
     */
    public static boolean updateTime(Calendar calendar, String timeString) {
        try {
            Calendar time = parseTime(timeString);
            
            calendar.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Update the date of the specified calendar object with parsed dateString. Returns true when dateString is valid, otherwise returns false.
     */
    public static boolean updateDate(Calendar calendar, String dateString) {
        try {
            Calendar date = parseDate(dateString);
            
            calendar.set(Calendar.DATE, date.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Update the date and time of the specified calendar object with parsed dateTimeString. Returns true when dateTimeString is valid, otherwise returns false.
     */
    public static boolean updateDateTime(Calendar calendar, String dateTimeString) {
        try {
            Calendar date = parseDateTime(dateTimeString);
            
            calendar.set(Calendar.DATE, date.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
            calendar.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, date.get(Calendar.MINUTE));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Update the date and/or time of the specified calendar object with parsed calendarString. Returns true when calendarString is valid, otherwise returns false.
     */
    public static boolean updateCalendar(Calendar calendar, String calendarString) {
        String stringType = getCalendarStringType(calendarString);
        switch (stringType) {
            case (TYPE_DATE_TIME):
                return updateDateTime(calendar, calendarString);
            case (TYPE_DATE):
                return updateDate(calendar, calendarString);
            case (TYPE_TIME):
                return updateTime(calendar, calendarString);
            default:
                return false;
        }
    }
    
    /**
     * Returns date string of the specified calendar object
     */
    public static String getDateString(Calendar calendar) {
        return FORMAT_DATE.format(calendar.getTime());
    }
    
    /**
     * Returns time string of the specified calendar object
     */
    public static String getTimeString(Calendar calendar) {
        return FORMAT_TIME.format(calendar.getTime());
    }
    
    /**
     * Returns date and time string of the specified calendar object
     */
    public static String getDateTimeString(Calendar calendar) {
        return FORMAT_DATE_TIME.format(calendar.getTime());
    }
    
    /**
     * Returns calendar type (TYPE_DATE_TIME, TYPE_DATE, TYPE_TIME) of the specified string
     */
    public static String getCalendarStringType(String calendarString) {
        try {
            parseDateTime(calendarString);
            return TYPE_DATE_TIME;
        } catch (ParseException e) {
            
        }
        try {
            parseDate(calendarString);
            return TYPE_DATE;
        } catch (ParseException e) {
            
        }
        try {
            parseTime(calendarString);
            return TYPE_TIME;
        } catch (ParseException e) {
            
        }
        return null;
    }
    
}
