package command;

import java.util.Stack;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import project.Projects;
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
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        if (projectHandler.deleteProject(projectName)) {
            return String.format(CommonHelper.SUCCESS_PROJECT_DELETED, projectName);
        } else {
            throw new Exception(CommonHelper.ERROR_PROJECT_NOT_FOUND);
        }
    }

    @Override
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        // TODO Add all previous events to project
        CommandAddProject commandAddProject = new CommandAddProject("\"" + projectName + "\"");
        return commandAddProject.execute(serviceHandler, projectHandler, historyList);
    }

    @Override
    public Displayable getDisplayable() {
        // TODO what to show
        return null;
    }

}
