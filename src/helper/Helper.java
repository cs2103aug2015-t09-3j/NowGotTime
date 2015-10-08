package helper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import object.Event;
import object.Item;
import object.Todo;

/*
 * This class contains useful functions and constants
 */

public class Helper {

    /* Prompt messages */
    public static final String MESSAGE_WELCOME = "Welcome to NowGotTime";
    public static final String MESSAGE_PROMPT = "command: ";
    public static final String MESSAGE_ADD = "'%1$s' successfully added";
    public static final String MESSAGE_DELETE = "'%1$s' successfully deleted";
    public static final String MESSAGE_EDIT = "'%1$s' successfully edited";
    

    /* Error messages */
    public static final String ERROR_INVALID_COMMAND = "unknown command '%1$s'";
    public static final String ERROR_INVALID_ARGUMENTS = "invalid arguments for command %1$s";
    public static final String ERROR_INVALID_DATE_TIME = "invalid date/time format";
    public static final String ERROR_ADD_EVENT = "failed to add new event, '%1$s' already exists";
    public static final String ERROR_ADD_TODO = "failed to add new todo, '%1$s' already exists";
    public static final String ERROR_EDIT_DUPLICATE = "failed to edit, name already exists";
    public static final String ERROR_NOT_FOUND = "cannot find event or todo with name '%1$s'";
    public static final String ERROR_EMPTY_HISTORY = "cannot undo any previous command";
    
    public static final String FORMATTED_EVENT         = "[%1$s-%2$s] %3$s";
    public static final String FORMATTED_TODO          = "[   by %1$s] %2$s";
    public static final String FORMATTED_FLOATING_TODO = "[           ] %1$s";
    
    
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
    
    public static final String FIELD_NAME     = "name";
    public static final String FIELD_START    = "start";
    public static final String FIELD_END      = "end";
    public static final String FIELD_DUE      = "due";
    
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
    
    public static Calendar parseDate(String dateString) throws ParseException {
        Calendar date = Calendar.getInstance();
        date.setTime(FORMAT_DATE.parse(dateString));
        return date;
    }
    
    public static Calendar parseDateTime(String dateTimeString) throws ParseException {
        Calendar datetime = Calendar.getInstance();
        datetime.setTime(FORMAT_DATE_TIME.parse(dateTimeString));
        return datetime;
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
    
    public static boolean doesOccurOn(Item item, String dateString) {
        try {
            Calendar date = parseDate(dateString);
            
            if (item instanceof Event) {
                Event event = (Event) item;
                
                updateTime(date, "00:00");
                
                // start before the date
                if (event.getStartCalendar().after(date)) return false;
                
                updateTime(date, "23:59");

                // end after the date
                if (event.getEndCalendar().before(date)) return false;
                
                return true;
            }
            else {
                Todo todo = (Todo) item;
                
                updateTime(date, "00:00");
                
                // deadline before the date
                if (todo.getDeadline().after(date)) return false;
                
                updateTime(date, "23:59");
                
                // deadline after the date
                if (todo.getDeadline().before(date)) return false;
                
                return true;
            }
            
        } catch(ParseException e) {
            return false;
        }
    }
    
    public static String getFormattedEventList(ArrayList<Event> eventList, String dateString) throws ParseException {
        if (eventList.isEmpty()) return "   [ No events on this day ]\n";
        StringBuilder formattedString = new StringBuilder();
        
        int index = 0;
        for (Event event : eventList) {
            index++;
            formattedString.append(index);
            formattedString.append(". ");
            formattedString.append(event.toFormattedString(dateString));
            formattedString.append("\n");
        }
        
        return formattedString.toString();
    }
    
    public static String getFormattedTodoList(ArrayList<Todo> todoList, String dateString) throws ParseException {
        if (todoList.isEmpty()) return "   [ No todos on this day ]\n";
        StringBuilder formattedString = new StringBuilder();
        
        int index = 0;
        for (Todo todo : todoList) {
            index++;
            formattedString.append(index);
            formattedString.append(". ");
            formattedString.append(todo.toFormattedString(dateString));
            formattedString.append("\n");
        }
        
        return formattedString.toString();
    }
    
    
    
}
