import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * This class contains useful functions and constants
 */

public class Helper {

    /* Prompt messages */
    public static final String MESSAGE_WELCOME = "Welcome to NowGotTime";
    public static final String MESSAGE_PROMPT = "command: ";
    public static final String MESSAGE_ADD = "'%1$s' successfully added";

    /* Error messages */
    public static final String ERROR_INVALID_COMMAND = "unknown command '%1$s'";
    public static final String ERROR_INVALID_ARGUMENTS = "invalid arguments for command %1$s";
    public static final String ERROR_INVALID_DATE_TIME = "invalid date/time format";
    public static final String ERROR_ADD_EVENT = "failed to add new event '%1$s'";
    public static final String ERROR_ADD_TODO = "failed to add new todo '%1$s'";
    public static final String ERROR_NOT_FOUND = "cannot find event or todo with name '%1$s'";
    
    /* Date and time format */
    public static final String PATTERN_DATE = "dd MMM yyyy";
    public static final String PATTERN_TIME = "HH:mm";
    public static final String PATTERN_DATE_TIME = "dd MMM yyyy HH:mm";
    
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat(PATTERN_TIME);
    public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(PATTERN_DATE_TIME);
    

    public static final String DATE_TYPE = "date";
    public static final String TIME_TYPE = "time";
    public static final String DATE_TIME_TYPE = "datetime";
    
    /* String Manipulation Helper functions */
    
    /**
     * Return first word from given text
     * @return The first word
     */
    public static String getFirstWord(String text) {
        // Find the first whitespace position
        int spacePosition = text.indexOf(' ');
        if (spacePosition > -1) {
            return text.substring(0, spacePosition);
        }
        else {
            return text;
        }
    }
    
    /**
     * Remove first word from text
     * @return Text without its first word
     */
    public static String removeFirstWord(String text) {
        String firstWord = getFirstWord(text);
        return text.substring(firstWord.length());
    }
    
    /* Calendar Helper functions */
    
    public static Calendar parseTime(String timeString) throws ParseException {
        Calendar time = Calendar.getInstance();
        time.setTime(FORMAT_TIME.parse(timeString));
        return time;
    }
    
    public static Calendar parseDate(String timeString) throws ParseException {
        Calendar time = Calendar.getInstance();
        time.setTime(FORMAT_DATE.parse(timeString));
        return time;
    }
    
    public static Calendar parseDateTime(String timeString) throws ParseException {
        Calendar time = Calendar.getInstance();
        time.setTime(FORMAT_DATE_TIME.parse(timeString));
        return time;
    }
    
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
    
    public static boolean updateDateTime(Calendar calendar, String dateString) {
        try {
            Calendar date = parseDateTime(dateString);
            
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
    
    public static String getDateString(Calendar calendar) {
        return Helper.FORMAT_DATE.format(calendar.getTime());
    }
    
    public static String getTimeString(Calendar calendar) {
        return Helper.FORMAT_TIME.format(calendar.getTime());
    }
    
    public static String getDateTimeString(Calendar calendar) {
        return Helper.FORMAT_DATE_TIME.format(calendar.getTime());
    }
    
    public static String getCalendarStringType(String calendarString) {
        try {
            parseDateTime(calendarString);
            return DATE_TIME_TYPE;
        } catch (ParseException e) {
            
        }
        try {
            parseDate(calendarString);
            return DATE_TYPE;
        } catch (ParseException e) {
            
        }
        try {
            parseTime(calendarString);
            return TIME_TYPE;
        } catch (ParseException e) {
            
        }
        return null;
    }
    
    public static String getCalendarString(Calendar calendar, String type) {
        switch (type) {
            case (DATE_TIME_TYPE):
                return getDateTimeString(calendar);
            case (DATE_TYPE):
                return getDateString(calendar);
            case (TIME_TYPE):
                return getTimeString(calendar);
            default:
                return null;
        }
    }
    
    
}
