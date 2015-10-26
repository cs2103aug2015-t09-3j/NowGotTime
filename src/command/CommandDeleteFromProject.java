package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.Item;
import project.Projects;
import service.ServiceHandler;

public class CommandDeleteFromProject implements CommandDelete {

    int index;
    String projectName;
    Item item;
    
    public CommandDeleteFromProject(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_DELETE_INDEX_FROM_PROJECT);
        index = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
        projectName = matcher.group(Parser.TAG_NAME);
    }
    
    public CommandDeleteFromProject(Item item, String projectName) {
        this.item = item;
        this.projectName = projectName;
    }

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Revertible mostRecent, Displayable currentDisplay)
            throws Exception {
        
        // TODO: refactor this ????
        if (item == null) {
            item = projectHandler.editEvent(index, projectName);
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
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Displayable currentDisplay)
            throws Exception {
        Command revertDeleteFromProjectCommand = new CommandAddToProject(item, projectName);
        return revertDeleteFromProjectCommand.execute(serviceHandler, projectHandler, null, currentDisplay);
    }

    @Override
    public Displayable getDisplayable() {
        return new CommandViewProjectName("\"" + projectName + "\"");
    }

}
