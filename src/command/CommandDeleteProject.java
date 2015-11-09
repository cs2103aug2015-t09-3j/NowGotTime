//@@author A0126509E

package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.State;
import project.Projects;

public class CommandDeleteProject implements CommandDelete {

    private String projectName;
    
    public CommandDeleteProject(String args) {
        
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_PROJECT);
        projectName = matcher.group(Parser.TAG_NAME);
    }

    @Override
    public String execute(State state) throws Exception {
        Projects projectHandler = state.getProjectHandler();
        
        if (projectHandler.deleteProject(projectName)) {
            return String.format(CommonHelper.SUCCESS_PROJECT_DELETED, projectName);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_PROJECT_NOT_FOUND, projectName));
        }
    }

    @Override
    public String revert(State state)
            throws Exception {
        Command commandAddProject = new CommandAddProject("project \"" + projectName + "\"");
        return commandAddProject.execute(state);
    }

    @Override
    public Displayable getDisplayable() {
        return new CommandViewProject();
    }

}
