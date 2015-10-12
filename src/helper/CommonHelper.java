package helper;
import java.text.ParseException;
import java.util.ArrayList;

import object.Event;
import object.Todo;

/*
 * This class contains useful functions and constants
 */

public class CommonHelper {
    
    public static final String APP_TITLE = "NowGotTime";

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
    public static final String ERROR_UNEXPECTED = "unexpected error";
    
    public static final String FORMATTED_EVENT         = "[%1$s-%2$s] %3$s";
    public static final String FORMATTED_TODO          = "[   by %1$s] %2$s";
    public static final String FORMATTED_FLOATING_TODO = "[           ] %1$s";
    
    public static final String FIELD_NAME     = "name";
    public static final String FIELD_START    = "start";
    public static final String FIELD_END      = "end";
    public static final String FIELD_DUE      = "due";
    
    /* String Manipulation Helper functions */
    
    /**
     * Returns first word of the specified text
     * @param text a non-empty string
     * @return the first word of the specified text
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
     * Returns a text with its first word removed
     * @param text a non-empty string
     * @return text without the first word
     */
    public static String removeFirstWord(String text) {
        String firstWord = getFirstWord(text);
        return text.substring(firstWord.length());
    }
    
    /**
     * Returns a formatted list of events
     * @param eventList list of events
     * @param dateString a valid date string
     * @return formatted list of events
     */
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
    
    /**
     * Returns a formatted list of todos
     * @param todoList list of todos
     * @param dateString a valid date string
     * @return formatted list of todos
     */
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
