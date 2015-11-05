//@@author A0126509E

package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import project.Projects;
import service.ServiceHandler;

public class CommandDeleteProject implements CommandDelete {

    String projectName;
    
    public CommandDeleteProject(String args) {
        
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_PROJECT);
        projectName = matcher.group(Parser.TAG_NAME);
    }

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Revertible mostRecent, Displayable currentDisplay)
            throws Exception {
        if (projectHandler.deleteProject(projectName)) {
            return String.format(CommonHelper.SUCCESS_PROJECT_DELETED, projectName);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_PROJECT_NOT_FOUND, projectName));
        }
    }

    @Override
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Displayable currentDisplay)
            throws Exception {
        // TODO: re-add all events inside project
        Command commandAddProject = new CommandAddProject("project \"" + projectName + "\"");
        return commandAddProject.execute(serviceHandler, projectHandler, null, currentDisplay);
    }

    @Override
    public Displayable getDisplayable() {
        return new CommandViewProject();
    }

}
