package command;

import java.util.Stack;
import java.util.regex.Matcher;

import helper.Parser;
import project.Projects;
import service.ServiceHandler;

public class CommandDeleteFromProject implements CommandDelete {

    int index;
    String projectName;
    
    public CommandDeleteFromProject(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_DELETE_INDEX_FROM_PROJECT);
        index = Integer.parseInt(matcher.group(Parser.TAG_INDEX));
        projectName = matcher.group(Parser.TAG_NAME);
    }

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
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
