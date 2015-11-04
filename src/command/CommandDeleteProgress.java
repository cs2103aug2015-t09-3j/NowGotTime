package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import project.Projects;
import service.ServiceHandler;

public class CommandDeleteProgress implements CommandDelete {

    int index;
    String projectName;
    
    public CommandDeleteProgress(String args) {
        
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_DELETE_PROGRESS);
        index = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
    }
    
    public CommandDeleteProgress(int index, String projectName) {
        this.index = index;
        this.projectName = projectName;
    }

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Revertible mostRecent, Displayable currentDisplay)
            throws Exception {
        
        if (projectName == null) {
            if (currentDisplay instanceof CommandViewProjectName) {
                projectName = ((CommandViewProjectName) currentDisplay).getProjectName();
            } else {
                throw new Exception(CommonHelper.ERROR_INDEX_OUT_OF_BOUND);
            }
        }
        
        if (projectHandler.deleteProgressMessage(index, projectName)) {
            return CommonHelper.SUCCESS_PROGRESS_DELETED;
        } else {
            // TODO: different error when project not found
            throw new Exception(CommonHelper.ERROR_FAIL_DEL_PROGRESS);
        }
    }

    @Override
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Displayable currentDisplay)
            throws Exception {
        // TODO: save old value
        Command revertDeleteProgressCommand = new CommandAddProgress(index, projectName, "");
        return revertDeleteProgressCommand.execute(serviceHandler, projectHandler, null, currentDisplay);
 
    }

    @Override
    public Displayable getDisplayable() {
        // TODO what to show
        return null;
    }

}
