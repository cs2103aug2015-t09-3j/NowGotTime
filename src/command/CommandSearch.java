package command;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.ProjectHandler;
import service.ServiceHandler;
import helper.CommonHelper;
import object.Item;

public class CommandSearch extends Command {
    
    private static final String PATTERN_SEARCH_BY_KEYWORD = "\\s*\"(?<name>.+)\"\\s*";
    
    private static final Pattern REGEX_SEARCH_BY_KEYWORD = Pattern.compile(PATTERN_SEARCH_BY_KEYWORD);

    private static String parseKeyword(String args) {
        Matcher deleteMatcher = REGEX_SEARCH_BY_KEYWORD.matcher(args);
        if (deleteMatcher.matches()) {
            String name = deleteMatcher.group(CommonHelper.FIELD_NAME);
            
            return name;
        }
        else {
            return null;
        }
    }
    
    public CommandSearch(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(false);
        keyword = parseKeyword(args);
        
        if (keyword == null) {
            throw new Exception(CommonHelper.ERROR_INVALID_ARGUMENTS);
        }
    }
    
    String keyword;
    
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        
        ArrayList<Item> filteredItem = serviceHandler.search(keyword);
        
        return CommonHelper.getFormattedItemList(filteredItem);
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
