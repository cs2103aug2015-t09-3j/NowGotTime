package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import project.Projects;
import service.ServiceHandler;

public class CommandEditProject implements CommandEdit {

    String projectName;
    String fieldName;
    String newValue;
    String oldValue;
    // TODO: whats more to do with fieldname ?
    // TODO: more flexible constructor
    public CommandEditProject(String args) {

        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_EDIT_PROJECT);
        
        projectName = matcher.group(Parser.TAG_NAME);
        fieldName = matcher.group(Parser.TAG_FIELD);
        newValue = matcher.group(Parser.TAG_VALUE);
    }
    
    public CommandEditProject(String projectName, String fieldName, String newValue) {
        this.projectName = projectName;
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Revertible mostRecent, Displayable currentDisplay)
            throws Exception {
        if (projectHandler.editProjectName(projectName, newValue)) {
            return String.format(CommonHelper.SUCCESS_PROJECT_EDITED, projectName, newValue);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_DUPLICATE_PROJECT, newValue));
        }
    }

    @Override
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Displayable currentDisplay)
            throws Exception {
        CommandEditProject commandEditProject = new CommandEditProject(newValue, fieldName, projectName);
        return commandEditProject.execute(serviceHandler, projectHandler, null, currentDisplay);
    }

    @Override
    public Displayable getDisplayable() {
        return new CommandViewProjectName(newValue);
    }

}
