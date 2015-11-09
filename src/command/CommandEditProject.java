//@@author A0126509E

package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.State;
import project.Projects;

public class CommandEditProject implements CommandEdit {

    private String projectName;
    private String fieldName;
    private String newValue;
    
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
    public String execute(State state)
            throws Exception {
        Projects projectHandler = state.getProjectHandler();
        if (projectHandler.editProjectName(newValue, projectName)) {
            return String.format(CommonHelper.SUCCESS_PROJECT_EDITED, projectName, newValue);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_DUPLICATE_PROJECT, newValue));
        }
    }

    @Override
    public String revert(State state)
            throws Exception {
        CommandEditProject commandEditProject = new CommandEditProject(newValue, fieldName, projectName);
        return commandEditProject.execute(state);
    }

    @Override
    public Displayable getDisplayable() {
        return new CommandViewProjectName(newValue);
    }

}
