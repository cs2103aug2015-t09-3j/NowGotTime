//@@author A0126509E

package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.State;
import project.Projects;

public class CommandAddProject implements CommandAdd {

    private String projectName;
    
    public CommandAddProject(String args) {
        
        if (Parser.matches(args, Parser.PATTERN_PROJECT)) {
            Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_PROJECT);
            projectName = matcher.group(Parser.TAG_NAME);
        } else {
            projectName = args;
        }
        
    }

    @Override
    public String execute(State state) throws Exception {
        Projects projectHandler = state.getProjectHandler();
        
        if (projectHandler.createProject(projectName)) {
            return String.format(CommonHelper.SUCCESS_PROJECT_CREATED, projectName);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_DUPLICATE_PROJECT, projectName));
        }
    }

    @Override
    public String revert(State state)
            throws Exception {
        CommandDeleteProject commandDeleteProject = new CommandDeleteProject("project \"" + projectName + "\"");
        return commandDeleteProject.execute(state);
    }

    @Override
    public Displayable getDisplayable() {
        return new CommandViewProject();
    }

}
