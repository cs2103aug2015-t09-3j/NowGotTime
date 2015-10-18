package command;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.Event;
import object.Item;
import object.Todo;
import project.ProjectHandler;
import service.ServiceHandler;

public class CommandDeleteItem implements CommandDelete {

    String itemKey = null;
    int itemIndex;
    Item item;
    
    /**
     * Parses the arguments for delete command
     */
    public CommandDeleteItem(String args) throws Exception {
        Matcher matcher;
        
        if (Parser.matches(args,Parser.PATTERN_NAME)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_NAME);
            itemKey = matcher.group(Parser.TAG_NAME);
        } else if (Parser.matches(args,Parser.PATTERN_INTEGER)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_INTEGER);
            itemIndex = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
        } else {
            assert(false);
        }
    }
    
    /**
     * Constructs command from specified Item object
     */
    public CommandDeleteItem(Item item) {
        this.item = item;
    }
    
    
    public String getItemKey() {
        return itemKey;
    }
    
    /**
     * Executes delete command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Revertible> historyList) throws Exception {
        
        if (item == null) {
            if (itemKey == null) {
                item = serviceHandler.viewItemByIndex(itemIndex);
                if (item == null) {
                    throw new Exception(CommonHelper.ERROR_INDEX_OUT_OF_BOUND);
                }
            } else {
                ArrayList<Item> filteredItem = serviceHandler.search(itemKey);
                System.out.println(filteredItem);
                if (filteredItem.size() == 0) {
                    throw new Exception(String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, itemKey));
                } else if (filteredItem.size() > 1) {
                    return String.format(CommonHelper.ERROR_MULTIPLE_OCCURRENCE, itemKey);
                } else {
                    item = filteredItem.get(0);
                }
            }
        }
        
        serviceHandler.deleteItem(item);
        return String.format(CommonHelper.SUCCESS_ITEM_DELETED, item.getName());
    }

    /**
     * Re-add the deleted command
     */
    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Revertible> historyList) throws Exception {
        Revertible revertDeleteCommand = new CommandAddItem(item);
        return revertDeleteCommand.revert(serviceHandler, projectHandler, historyList);
    }

    @Override
    public Displayable getDisplayable() {
        if (item == null) {
            return new CommandSearch("\"" + itemKey + "\"");
        }
        else {
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
