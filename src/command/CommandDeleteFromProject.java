//@@author A0126509E

package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.Item;
import object.State;
import project.Projects;

public class CommandDeleteFromProject implements CommandDelete {

    int index;
    String projectName;
    Item item;
    
    public CommandDeleteFromProject(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_DELETE_INDEX_FROM_PROJECT);
        index = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
    }
    
    public CommandDeleteFromProject(Item item, String projectName) {
        this.item = item;
        this.projectName = projectName;
    }

    @Override
    public String execute(State state) throws Exception {
        Displayable currentDisplay = state.getCurrentDisplay();
        Projects projectHandler = state.getProjectHandler();
        
        if (projectName == null) {
            if (currentDisplay instanceof CommandViewProjectName) {
                projectName = ((CommandViewProjectName) currentDisplay).getProjectName();
            } else {
                throw new Exception(CommonHelper.ERROR_INDEX_OUT_OF_BOUND);
            }
        }
        
        if (item == null) {
            if (currentDisplay instanceof CommandViewProjectName) {
                item = projectHandler.editEvent(index, projectName);
            }
        }
        
        if (item == null) {
            throw new Exception(String.format(CommonHelper.ERROR_PROJECT_NOT_FOUND, projectName));
        } else if (projectHandler.deleteProjectEvent(index, projectName)) {
            return String.format(CommonHelper.SUCCESS_DELETED_FROM_PROJECT, item.getName(), projectName);
        } else {
            throw new Exception(CommonHelper.ERROR_INDEX_OUT_OF_BOUND);
        }
    }

    @Override
    public String revert(State state)
            throws Exception {
        Command revertDeleteFromProjectCommand = new CommandAddToProject(item, projectName);
        return revertDeleteFromProjectCommand.execute(state);
    }

    @Override
    public Displayable getDisplayable() {
        return new CommandViewProjectName("\"" + projectName + "\"");
    }

}
