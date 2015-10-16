package command;

import java.util.Stack;
import java.util.regex.Matcher;

import helper.Parser;
import javafx.scene.layout.GridPane;
import project.ProjectHandler;
import service.ServiceHandler;

public class CommandEditProject implements CommandEdit {

    String projectName;
    String fieldName;
    String newValue;
    String oldValue;
    
    public CommandEditProject(String args) {

        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_PROJECT);
        
        projectName = matcher.group(Parser.TAG_NAME);
        fieldName = matcher.group(Parser.TAG_FIELD);
        newValue = matcher.group(Parser.TAG_VALUE);
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
