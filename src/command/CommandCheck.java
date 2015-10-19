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

public class CommandCheck implements Command, Revertible {

    public static final String KEYWORD = "check";

    String itemKey = null;
    int itemIndex;
    Item item;
    
    public CommandCheck(String args) throws Exception {
        Matcher matcher;
        
        if (Parser.matches(args,Parser.PATTERN_NAME)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_NAME);
            itemKey = matcher.group(Parser.TAG_NAME);
        } else if (Parser.matches(args,Parser.PATTERN_INTEGER)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_INTEGER);
            itemIndex = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
        } else {
            // undo command accepts no arguments
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandCheck.KEYWORD));
        }
    }

    public CommandCheck(Item item) {
        this.item = item;
    }

    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Revertible> historyList)
            throws Exception {
        
        if (item == null) {
            if (itemKey == null) {
                item = serviceHandler.viewItemByIndex(itemIndex);
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
        
        // TODO: serviceHandler.checkItem(item);
        return String.format(CommonHelper.SUCCESS_ITEM_CHECKED, item.getName());
    }
    
    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Revertible> historyList)
            throws Exception {
        CommandUncheck commandUncheck = new CommandUncheck(item);
        return commandUncheck.execute(serviceHandler, projectHandler, historyList);
    }

    @Override
    public Displayable getDisplayable() {
        if (item == null) {
            // TODO Refactor this
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
