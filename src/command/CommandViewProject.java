package command;

import java.util.Stack;
import java.util.regex.Matcher;

import helper.Parser;
import javafx.scene.layout.GridPane;
import project.Projects;
import service.ServiceHandler;

public class CommandViewProject implements CommandView {

    String projectName = null;
    
    public CommandViewProject(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_NAME);
        projectName = matcher.group(Parser.TAG_NAME);
    }

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void display(GridPane displayBox) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public Displayable getDisplayable() {
        return this;
    }

}
