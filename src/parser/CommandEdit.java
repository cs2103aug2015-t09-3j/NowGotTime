package parser;

import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import crudLogic.ServiceHandler;
import objects.Event;
import objects.Item;
import objects.Todo;
import projectlogic.ProjectHandler;


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
    
    public CommandEdit(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(true);
        
        HashMap<String, String> result = null;
        
        if ((result = parseEdit(args, REGEX_EDIT_NAME)) != null);
        else if ((result = parseEdit(args, REGEX_EDIT_DATE_TIME)) != null);
        else {
            // parsing unsuccessful
            throw new Exception(String.format(Helper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
        
        itemName = result.get(MATCH_TITLE);
        fieldName = result.get(MATCH_KEY);
        newValue = result.get(MATCH_VALUE);
        
        // Validate the date format
        if (fieldName == Helper.FIELD_START
         || fieldName == Helper.FIELD_END
         || fieldName == Helper.FIELD_DUE) {
            if (Helper.getCalendarStringType(newValue) == null) {
                throw new Exception(Helper.ERROR_INVALID_DATE_TIME);
            }
        }
    }
    
    public CommandEdit(String itemName, String fieldName, String newValue) {
        this.itemName = itemName;
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    private String itemName;
    private String fieldName;
    private String newValue;
    private String oldValue;
    
    public String getItemName() {
        return itemName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getNewValue() {
        return newValue;
    }

    public String getOldValue() {
        return oldValue;
    }
    
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        Item item = null;
        if ((item = serviceHandler.viewSpecificEvent(itemName)) != null);
        else if ((item = serviceHandler.viewSpecificTask(itemName)) != null);
        else {
            throw new Exception(String.format(Helper.ERROR_NOT_FOUND, itemName));
        }
        switch (fieldName) {
            case (Helper.FIELD_NAME):
                oldValue = item.getName();
                if (item instanceof Event) { 
                    if (!serviceHandler.editEventName(itemName, newValue)) {
                        throw new Exception(Helper.ERROR_EDIT_DUPLICATE);
                    }
                }
                else {
                    if (!serviceHandler.editTaskName(itemName, newValue)) {
                        throw new Exception(Helper.ERROR_EDIT_DUPLICATE);
                    }
                }
                return String.format(Helper.MESSAGE_EDIT, newValue);
            case (Helper.FIELD_START):
                if (item instanceof Event) { 
                    oldValue = ((Event) item).getStartDateTimeString();
                    switch (Helper.getCalendarStringType(newValue)) {
                        case (Helper.DATE_TYPE):
                            serviceHandler.editEventStartDate(itemName, newValue);
                            break;
                        case (Helper.TIME_TYPE):
                            serviceHandler.editEventStartTime(itemName, newValue);
                            break;
                        case (Helper.DATE_TIME_TYPE):
                            serviceHandler.editEventStartDateTime(itemName, newValue);
                            break;
                    }
                }
                else {
                    throw new Exception(String.format(Helper.ERROR_NOT_FOUND, itemName));
                }
                return String.format(Helper.MESSAGE_EDIT, item.getName());
            case (Helper.FIELD_END):
                if (item instanceof Event) { 
                    oldValue = ((Event) item).getEndDateTimeString();
                    
                    switch (Helper.getCalendarStringType(newValue)) {
                        case (Helper.DATE_TYPE):
                            serviceHandler.editEventEndDate(itemName, newValue);
                            break;
                        case (Helper.TIME_TYPE):
                            serviceHandler.editEventEndTime(itemName, newValue);
                            break;
                        case (Helper.DATE_TIME_TYPE):
                            serviceHandler.editEventEndDateTime(itemName, newValue);
                            break;
                    }
                }
                else {
                    throw new Exception(String.format(Helper.ERROR_NOT_FOUND, itemName));
                }
                return String.format(Helper.MESSAGE_EDIT, item.getName());
            case (Helper.FIELD_DUE):
                if (item instanceof Todo) { 
                    oldValue = ((Todo) item).getDeadlineDateTimeString();
                    switch (Helper.getCalendarStringType(newValue)) {
                        case (Helper.DATE_TYPE):
                            serviceHandler.editTaskDeadlineDate(itemName, newValue);
                            break;
                        case (Helper.TIME_TYPE):
                            serviceHandler.editTaskDeadlineTime(itemName, newValue);
                            break;
                        case (Helper.DATE_TIME_TYPE):
                            serviceHandler.editTaskDeadlineDateTime(itemName, newValue);
                            break;
                    }
                }
                else {
                    throw new Exception(String.format(Helper.ERROR_NOT_FOUND, itemName));
                }
                return String.format(Helper.MESSAGE_EDIT, item.getName());
        }
        return String.format(Helper.MESSAGE_EDIT, item.getName());
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        Command revertEditCommand = new CommandEdit(itemName, fieldName, oldValue);
        return revertEditCommand.execute(serviceHandler, projectHandler, historyList);
    }

}
