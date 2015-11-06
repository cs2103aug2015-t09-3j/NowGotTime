//@@author A0126509E

package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.State;
import project.Projects;

public class CommandAddProgress implements CommandAdd {

    int index;
    String progress;
    String projectName;
    
    public CommandAddProgress(String args) {
        
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_ADD_PROGRESS);
        
        progress = matcher.group(Parser.TAG_PROGRESS);
        index = Integer.parseInt(matcher.group(Parser.TAG_INDEX)) - 1;
    }
    
    public CommandAddProgress(int index, String projectName, String progress) {
        this.index = index;
        this.progress = progress;
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
        
        if (projectHandler.addProgressMessage(index, projectName, progress)) {
            return CommonHelper.SUCCESS_PROGRESS_ADDED;
        } else {
            // TODO: different error when project not found
            throw new Exception(CommonHelper.ERROR_FAIL_ADD_PROGRESS);
        }
    }

    @Override
    public String revert(State state)
            throws Exception {
        Command revertAddProgressCommand = new CommandDeleteProgress(index, projectName);
        return revertAddProgressCommand.execute(state);
    
    }


    @Override
    public Displayable getDisplayable() {
        return null;
    }

}
