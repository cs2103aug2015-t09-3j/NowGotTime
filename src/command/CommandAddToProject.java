//@@author A0126509E

package command;

import java.util.ArrayList;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.Event;
import object.Item;
import object.State;
import project.Projects;
import service.ServiceHandler;

public class CommandAddToProject implements CommandAdd {

    private int index;
    private String keyword = null;
    private String projectName;
    private Item item;
    
    public CommandAddToProject(String args) {
        Matcher matcher;
        
        if (Parser.matches(args,Parser.PATTERN_ADD_INDEX_TO_PROJECT)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_ADD_INDEX_TO_PROJECT);
            index = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
            projectName = matcher.group(Parser.TAG_NAME);
            
        } else if (Parser.matches(args,Parser.PATTERN_ADD_KEYWORD_TO_PROJECT)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_ADD_KEYWORD_TO_PROJECT);
            keyword = matcher.group(Parser.TAG_KEYWORD);
            projectName = matcher.group(Parser.TAG_NAME);
            
        }
    }
    
    public CommandAddToProject(Item item, String projectName) {
        this.item = item;
        this.projectName = projectName;
    }

    @Override
    public String execute(State state) throws Exception {
        Displayable currentDisplay = state.getCurrentDisplay();
        ServiceHandler serviceHandler = state.getServiceHandler();
        Projects projectHandler = state.getProjectHandler();
        
        if (item == null) {
            if (keyword == null) {
                if (currentDisplay instanceof CommandViewDate
                        || currentDisplay instanceof CommandSearch) {
                    item = serviceHandler.viewItemByIndex(index);
                }
                if (item == null) {
                    throw new Exception(CommonHelper.ERROR_INDEX_OUT_OF_BOUND);
                }
            } else {
                ArrayList<Item> filteredItem = serviceHandler.search(keyword);
                
                if (filteredItem.size() == 0) {
                    throw new Exception(String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, keyword));
                } else if (filteredItem.size() > 1) {
                    return String.format(CommonHelper.ERROR_MULTIPLE_OCCURRENCE, keyword);
                } else {
                    item = filteredItem.get(0);
                }
            }
        }
        
        if (!(item instanceof Event)) {
            throw new Exception(CommonHelper.ERROR_TODO_ON_PROJECT);
        } else if (projectHandler.addProjectEvent(((Event)item), projectName)) {
            return String.format(CommonHelper.SUCCESS_ADDED_TO_PROJECT, item.getName(), projectName);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_PROJECT_NOT_FOUND, projectName));
        }
        
    }

    @Override
    public String revert(State state)
            throws Exception {
        Command revertAddToProjectCommand = new CommandDeleteFromProject(item, projectName);
        return revertAddToProjectCommand.execute(state);
    }

    @Override
    public Displayable getDisplayable() {
        if (item == null) {
            return new CommandSearch(keyword);
        } else if (keyword == null) {
            // refresh current display if edit by index
            return null;
        } else {
            return new CommandViewProjectName(projectName);
        }
    }

}
