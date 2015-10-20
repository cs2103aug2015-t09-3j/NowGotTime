package command;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.Item;
import project.Projects;
import service.ServiceHandler;

public class CommandAddToProject implements CommandAdd {

    int index;
    String keyword = null;
    String projectName;
    Item item;
    
    public CommandAddToProject(String args) {
        Matcher matcher;
        
        if (Parser.matches(args,Parser.PATTERN_ADD_INDEX_TO_PROJECT)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_ADD_INDEX_TO_PROJECT);
            index = Integer.parseInt(matcher.group(Parser.TAG_INDEX));
            projectName = matcher.group(Parser.TAG_NAME);
            
        } else if (Parser.matches(args,Parser.PATTERN_ADD_KEYWORD_TO_PROJECT)) {
            matcher = Parser.matchRegex(args, Parser.PATTERN_ADD_KEYWORD_TO_PROJECT);
            keyword = matcher.group(Parser.TAG_KEYWORD);
            projectName = matcher.group(Parser.TAG_NAME);
            
        }
    }

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        
        if (item == null) {
            if (keyword == null) {
                item = serviceHandler.viewItemByIndex(index);
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
        
        // TODO: handle item already on the list
        if (projectHandler.addProjectEvent(item.getId(), projectName)) {
            return String.format(CommonHelper.SUCCESS_ITEM_ADDED_TO_PROJECT, item.getName(), projectName);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_PROJECT_NOT_FOUND, projectName));
        }
        
    }

    @Override
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Displayable getDisplayable() {
        // TODO Auto-generated method stub
        return null;
    }

}
