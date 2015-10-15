package command;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.CalendarHelper;
import helper.CommonHelper;
import javafx.scene.layout.GridPane;
import object.Event;
import object.Item;
import object.Todo;
import project.ProjectHandler;
import service.ServiceHandler;


public class CommandEdit extends Command {

    public static final String KEYWORD = "edit";

    private static final String PATTERN_EDIT_NAME      = "\\s*\"(?<name>.+)\"\\s+(?<field>name) \"(?<value>.+)\"";
    private static final String PATTERN_EDIT_DATE_TIME = "\\s*\"(?<name>.+)\"\\s+(?<field>start|end|due) (?<value>.+)";
    
    private static final Pattern REGEX_EDIT_NAME       = Pattern.compile(PATTERN_EDIT_NAME);
    private static final Pattern REGEX_EDIT_DATE_TIME  = Pattern.compile(PATTERN_EDIT_DATE_TIME);
    
    private static final String MATCH_TITLE     = "name";
    private static final String MATCH_KEY      = "field";
    private static final String MATCH_VALUE    = "value";
    
    
    private static HashMap<String, String> parseEdit(String args, Pattern REGEX_EDIT) {
        Matcher nameEditMatcher = REGEX_EDIT.matcher(args);
        if (nameEditMatcher.matches()) {
            String name = nameEditMatcher.group(MATCH_TITLE);
            String field = nameEditMatcher.group(MATCH_KEY);
            String value = nameEditMatcher.group(MATCH_VALUE);
            
            HashMap<String, String> result = new HashMap<String, String>();
            
            result.put(MATCH_TITLE, name);
            result.put(MATCH_KEY, field);
            result.put(MATCH_VALUE, value);
            
            return result;
        }
        else {
            return null;
        }
    }
    
    /**
     * Parses the arguments for edit command
     */
    public CommandEdit(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(true);
        
        HashMap<String, String> result = null;
        
        if ((result = parseEdit(args, REGEX_EDIT_NAME)) != null);
        else if ((result = parseEdit(args, REGEX_EDIT_DATE_TIME)) != null);
        else {
            // parsing unsuccessful
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
        
        itemName = result.get(MATCH_TITLE);
        fieldName = result.get(MATCH_KEY);
        newValue = result.get(MATCH_VALUE);
        
        // Validate the date format
        if (fieldName == CommonHelper.FIELD_START
         || fieldName == CommonHelper.FIELD_END
         || fieldName == CommonHelper.FIELD_DUE) {
            if (CalendarHelper.getCalendarStringType(newValue) == null) {
                throw new Exception(CommonHelper.ERROR_INVALID_ARGUMENTS);
            }
        }
    }
    
    /**
     * Constructs this command from specified itemName, fieldName, and newValue
     */
    public CommandEdit(String itemName, String fieldName, String newValue) {
        this.itemName = itemName;
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    private String itemName;
    private String fieldName;
    private String newValue;
    private String oldValue;
    
    /**
     * Returns name of item that wants to be edited
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Returns name of field that wants to be edited
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the new value
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Returns the old value
     */
    public String getOldValue() {
        return oldValue;
    }
    
    Item item;
    
    /**
     * Executes edit command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        item = null;
        if ((item = serviceHandler.viewSpecificEvent(itemName)) != null);
        else if ((item = serviceHandler.viewSpecificTask(itemName)) != null);
        else {
            throw new Exception(String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, itemName));
        }
        
        if (item instanceof Event) {
            oldValue = serviceHandler.editEvent(item.getName(), fieldName, newValue);
        }
        else if (item instanceof Todo) {
            oldValue = serviceHandler.editTask(item.getName(), fieldName, newValue);
        }
        return String.format(CommonHelper.SUCCESS_ITEM_EDITED, item.getName(), fieldName, newValue);    
    }

    /**
     * Reverts to initial value
     */
    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        Command revertEditCommand = new CommandEdit(itemName, fieldName, oldValue);
        return revertEditCommand.execute(serviceHandler, projectHandler, historyList);
    }
    
    @Override
    public void display(ServiceHandler serviceHandler, ProjectHandler projectHandler, GridPane displayBox) throws Exception {
        Calendar date;
        if (item instanceof Event) {
            date = ((Event)item).getStartCalendar();
        }
        else {
            date = ((Todo)item).getDeadline();
        }
        
        Command viewCommand = new CommandView(date);
        viewCommand.execute(serviceHandler, projectHandler, new Stack<Command>());
        viewCommand.display(serviceHandler, projectHandler, displayBox);
    }

}
