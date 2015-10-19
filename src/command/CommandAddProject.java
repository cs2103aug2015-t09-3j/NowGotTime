package command;

import java.util.Stack;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import project.Projects;
import service.ServiceHandler;

public class CommandAddProject implements CommandAdd {

    String projectName;
    
    // TODO: allow args without "" 
    public CommandAddProject(String args) {
        
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_PROJECT);
        projectName = matcher.group(Parser.TAG_NAME);
    }

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        if (projectHandler.createProject(projectName)) {
            return String.format(CommonHelper.SUCCESS_PROJECT_CREATED, projectName);
        } else {
            throw new Exception(CommonHelper.ERROR_DUPLICATE_PROJECT);
        }
    }

    @Override
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        CommandDeleteProject commandDeleteProject = new CommandDeleteProject(projectName);
        return commandDeleteProject.execute(serviceHandler, projectHandler, historyList);
    }

    @Override
    public Displayable getDisplayable() {
        // TODO what to show ?
        return null;
    }

}
