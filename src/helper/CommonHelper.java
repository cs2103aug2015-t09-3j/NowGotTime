package helper;
import java.text.ParseException;
import java.util.ArrayList;

import object.Event;
import object.Item;
import object.Todo;

/*
 * This class contains useful functions and constants
 */

public class CommonHelper {

    /* Prompt messages */
    public static final String MESSAGE_WELCOME = "Welcome to NowGotTime";
    public static final String MESSAGE_PROMPT = "command: ";
    
    /* Success messages */
    public static final String SUCCESS_ITEM_CREATED          = "'%1$s' added";
    public static final String SUCCESS_PROJECT_CREATED       = "project '%1$s' created";
    public static final String SUCCESS_ITEM_DELETED          = "'%1$s' deleted";
    public static final String SUCCESS_PROJECT_DELETED       = "project '%1$s' deleted";
    public static final String SUCCESS_ITEM_EDITED           = "'%1$s' %2$s changed to '%3$s'";
    public static final String SUCCESS_PROJECT_EDITED        = "project '%1$s' changed to '%2$s'";
    public static final String SUCCESS_ADDED_TO_PROJECT      = "'%1$s' added to project '%2$s'";
    public static final String SUCCESS_DELETED_FROM_PROJECT  = "'%1$s' deleted from project '%2$s'";
    
    public static final String SUCCESS_SEARCHED              = "Got it!";
    public static final String SUCCESS_ITEM_CHECKED          = "marked done '%1$s'";
    public static final String SUCCESS_ITEM_UNCHECKED        = "marked not done '%1$s'";
    public static final String SUCCESS_SET_DIRECTORY         = "save directory moved to '%1$s'";
    public static final String SUCCESS_PROGRESS_ADDED        = "progress added";
    public static final String SUCCESS_PROGRESS_DELETED      = "progress deleted";
    
    
    /* Error messages */
    public static final String ERROR_INVALID_COMMAND     = "unknown command '%1$s'";
    public static final String ERROR_INVALID_ARGUMENTS   = "invalid arguments for command '%1$s'";
    public static final String ERROR_DUPLICATE_PROJECT   = "project '%1$s' already exists";
    public static final String ERROR_MULTIPLE_OCCURRENCE = "multiple occurrence of '%1$s' found";
    public static final String ERROR_START_AFTER_END     = "start of event cannot be after end of event";
    public static final String ERROR_INDEX_OUT_OF_BOUND  = "item with this index does not exists";
    public static final String ERROR_ITEM_NOT_FOUND      = "'%1$s' not found";
    public static final String ERROR_PROJECT_NOT_FOUND   = "project '%1$s' not found";
    public static final String ERROR_EMPTY_HISTORY       = "cannot undo any previous command";
    public static final String ERROR_INVALID_PATH        = "invalid new save directory";
    public static final String ERROR_ALREADY_CHECKED     = "'%1$s' already checked";
    public static final String ERROR_ALREADY_UNCHECKED   = "'%1$s' already unchecked";
    public static final String ERROR_TODO_ON_PROJECT     = "only event can be added to project";
    public static final String ERROR_FAIL_ADD_PROGRESS   = "progress already exist";
    public static final String ERROR_FAIL_DEL_PROGRESS   = "progress doesn't exist";
    public static final String ERROR_ITEM_INSIDE_PROJECT = "cannot delete, item is inside project '%1$s'";
    
    
    
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
    
    public static String getFormattedItemList(ArrayList<Item> list) throws ParseException {
        if (list.isEmpty()) return "   [ No results found ]\n";
        StringBuilder formattedString = new StringBuilder();
        
        int index = 0;
        for (Item item : list) {
            index++;
            formattedString.append(index);
            formattedString.append(". ");
            formattedString.append(item.toFormattedString());
            formattedString.append("\n");
        }
        
        return formattedString.toString();
    }
    
    
    
}
