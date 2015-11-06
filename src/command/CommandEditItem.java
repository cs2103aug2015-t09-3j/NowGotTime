//@@author A0126509E

package command;

import java.util.ArrayList;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.Event;
import object.Item;
import object.State;
import object.Todo;
import project.Projects;
import service.ServiceHandler;


public class CommandEditItem implements CommandEdit {
    
    private String itemKey = null;
    private int itemIndex;
    Item item = null;
    
    private String fieldName;
    private String newValue;
    private String oldValue;
    
    /**
     * Parses the arguments for edit command
     */
    public CommandEditItem(String args) throws Exception {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_DATE_TIME_BY_INDEX);
        
        if (Parser.matches(args,Parser.PATTERN_EDIT_NAME_BY_INDEX)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_NAME_BY_INDEX);
            itemIndex = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
            fieldName = matcher.group(Parser.TAG_FIELD);
            newValue = matcher.group(Parser.TAG_NAME);
            
        } else if (Parser.matches(args,Parser.PATTERN_EDIT_NAME_BY_KEY)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_NAME_BY_KEY);
            itemKey = matcher.group(Parser.TAG_KEYWORD);
            fieldName = matcher.group(Parser.TAG_FIELD);
            newValue = matcher.group(Parser.TAG_NAME);
            
        } else if (Parser.matches(args,Parser.PATTERN_EDIT_DATE_TIME_BY_INDEX)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_DATE_TIME_BY_INDEX);
            itemIndex = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
            fieldName = matcher.group(Parser.TAG_FIELD);
            newValue = matcher.group(Parser.TAG_DATETIME);
            
        } else if (Parser.matches(args,Parser.PATTERN_EDIT_DATE_TIME_BY_KEY)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_DATE_TIME_BY_KEY);
            itemKey = matcher.group(Parser.TAG_KEYWORD);
            fieldName = matcher.group(Parser.TAG_FIELD);
            newValue = matcher.group(Parser.TAG_DATETIME);
            
        } else {
            assert(false);
        }
        
    }
    
    /**
     * Constructs this command from specified itemName, fieldName, and newValue
     */
    public CommandEditItem(String itemKey, String fieldName, String newValue) {
        this.itemKey = itemKey;
        this.fieldName = fieldName;
        this.newValue = newValue;
    }
    
    public CommandEditItem(Item item, String fieldName, String newValue) {
        this.item = item;
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    /**
     * Returns name of item that wants to be edited
     */
    public String getItemKey() {
        return itemKey;
    }

    /**
     * Returns index of item that wants to be edited
     */
    public int getItemIndex() {
        return itemIndex;
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
    
    
    /**
     * Executes edit command, returns feedback string
     */
    @Override
    public String execute(State state) throws Exception {
        Displayable currentDisplay = state.getCurrentDisplay();
        Projects projectHandler = state.getProjectHandler();
        ServiceHandler serviceHandler = state.getServiceHandler();
        
        if (item == null) {
            if (itemKey == null) {
                if (currentDisplay instanceof CommandViewProjectName) {
                    String projectName = ((CommandViewProjectName)currentDisplay).getProjectName();
                    item = projectHandler.editEvent(itemIndex, projectName);
                } else if (currentDisplay instanceof CommandViewDate
                        || currentDisplay instanceof CommandSearch) {
                    item = serviceHandler.viewItemByIndex(itemIndex);
                }
                
                if (item == null) {
                    throw new Exception(CommonHelper.ERROR_INDEX_OUT_OF_BOUND);
                }
            } else {
                ArrayList<Item> filteredItem = serviceHandler.search(itemKey);
                
                if (filteredItem.size() == 0) {
                    throw new Exception(String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, itemKey));
                } else if (filteredItem.size() > 1) {
                    return String.format(CommonHelper.ERROR_MULTIPLE_OCCURRENCE, itemKey);
                } else {
                    item = filteredItem.get(0);
                }
            }
        }
        
        assert(item != null);
        
        String oldName = item.getName();
        
        oldValue = serviceHandler.editItem(item, fieldName, newValue);
        
        return String.format(CommonHelper.SUCCESS_ITEM_EDITED, oldName, fieldName, newValue);
    }

    /**
     * Reverts to initial value
     */
    @Override
    public String revert(State state) throws Exception {
        Command revertEditCommand = new CommandEditItem(item, fieldName, oldValue);
        return revertEditCommand.execute(state);
    }

    @Override
    public Displayable getDisplayable() {
        if (item == null) {
            // TODO Refactor this
            return new CommandSearch("\"" + itemKey + "\"");
        } else if (itemKey == null) {
            // refresh current display if edit by index
            return null;
        } else {
            // TODO Refactor : implement this on item class
            String date;
            if (item instanceof Event) {
                date = ((Event)item).getStartDateString();
            }
            else {
                date = ((Todo)item).getDeadlineDateString();
            }
            return new CommandViewDate(date);
        }
    }

}
