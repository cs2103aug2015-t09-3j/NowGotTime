package command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import javafx.scene.layout.GridPane;
import object.Event;
import object.Item;
import object.Todo;
import project.ProjectHandler;
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
        
        if (args.matches(Parser.PATTERN_EDIT_NAME_BY_INDEX)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_NAME_BY_INDEX);
            itemIndex = Integer.parseInt(matcher.group(Parser.TAG_INDEX));
            fieldName = matcher.group(Parser.TAG_FIELD);
            newValue = matcher.group(Parser.TAG_NAME);
            
        } else if (args.matches(Parser.PATTERN_EDIT_NAME_BY_KEY)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_NAME_BY_KEY);
            itemKey = matcher.group(Parser.TAG_KEYWORD);
            fieldName = matcher.group(Parser.TAG_FIELD);
            newValue = matcher.group(Parser.TAG_NAME);
            
        } else if (args.matches(Parser.PATTERN_EDIT_DATE_TIME_BY_INDEX)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_DATE_TIME_BY_INDEX);
            itemIndex = Integer.parseInt(matcher.group(Parser.TAG_INDEX));
            fieldName = matcher.group(Parser.TAG_FIELD);
            newValue = matcher.group(Parser.TAG_DATE);
            
        } else if (args.matches(Parser.PATTERN_EDIT_DATE_TIME_BY_KEY)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_DATE_TIME_BY_KEY);
            itemKey = matcher.group(Parser.TAG_KEYWORD);
            fieldName = matcher.group(Parser.TAG_FIELD);
            newValue = matcher.group(Parser.TAG_DATE);
            
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
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        
        if (item == null) {
            if (itemKey == null) {
                // TODO Edit by index
            } else {
                ArrayList<Item> filteredItem = serviceHandler.search(itemKey);
                
                if (filteredItem.size() == 0) {
                    throw new Exception(String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, itemKey));
                } else if (filteredItem.size() > 1) {
                    // TODO Show search display on
                    return null;
                } else {
                    item = filteredItem.get(0);
                }
            }
        }
        
        assert(item != null);
        
        if (item instanceof Event) {
            oldValue = serviceHandler.editEvent(item.getName(), fieldName, newValue);
        }
        else if (item instanceof Todo) {
            oldValue = serviceHandler.editTask(item.getName(), fieldName, newValue);
        }
        else {
            assert(false);
        }
        
        return String.format(CommonHelper.SUCCESS_ITEM_EDITED, item.getName(), fieldName, newValue);
    }

    /**
     * Reverts to initial value
     */
    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        Command revertEditCommand = new CommandEditItem(item, fieldName, oldValue);
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
