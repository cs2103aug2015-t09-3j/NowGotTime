package command;

import java.util.Stack;
import java.util.regex.Matcher;

import helper.Parser;
import project.ProjectHandler;
import service.ServiceHandler;

public class CommandAddToProject implements CommandAdd {

    int index;
    String keyword = null;
    String projectName;
    
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
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
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
