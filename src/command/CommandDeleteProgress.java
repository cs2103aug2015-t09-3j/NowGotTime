package command;

import java.util.Stack;
import java.util.regex.Matcher;

import helper.Parser;
import javafx.scene.layout.GridPane;
import project.ProjectHandler;
import service.ServiceHandler;

public class CommandDeleteProgress implements CommandDelete {

    String projectName;
    int index;
    
    public CommandDeleteProgress(String args) {
        
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_DELETE_PROGRESS);
        index = Integer.parseInt(matcher.group(Parser.TAG_INDEX));
        projectName = matcher.group(Parser.TAG_NAME);
    }

    @Override
    public void display(ServiceHandler serviceHandler, ProjectHandler projectHandler, GridPane displayBox)
            throws Exception {
        // TODO Auto-generated method stub

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

}
