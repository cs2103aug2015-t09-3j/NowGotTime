//@@author A0126509E

package command;

import java.util.ArrayList;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.Item;
import object.State;
import project.Projects;
import service.ServiceHandler;

public class CommandUncheck implements Command, Revertible {

    public static final String KEYWORD = "uncheck";
    
    String itemKey = null;
    int itemIndex;
    Item item;
    
    public CommandUncheck(String args) throws Exception {
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
    
    public CommandUncheck(Item item) {
        this.item = item;
    }

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
        
        if (serviceHandler.unmark(item)) {
            return String.format(CommonHelper.SUCCESS_ITEM_UNCHECKED, item.getName());
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_ALREADY_UNCHECKED, item.getName()));
        }
    }
    

    @Override
    public String revert(State state)
            throws Exception {
        CommandCheck commandCheck = new CommandCheck(item);
        return commandCheck.execute(state);
    }


    @Override
    public Displayable getDisplayable() {
        if (item == null) {
            return new CommandSearch(itemKey);
        } else if (itemKey == null) {
            // refresh current display if edit by index
            return null;
        } else {
            // display item's date
            return new CommandViewDate(item.getDisplayDateString());
        }
    }

}
