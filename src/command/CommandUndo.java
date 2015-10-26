package command;

import helper.CommonHelper;
import helper.Parser;
import project.Projects;
import service.ServiceHandler;

public class CommandUndo implements Command {

    public static final String KEYWORD = "undo";
    
    /**
     * Parses the arguments for undo command
     */
    public CommandUndo(String args) throws Exception {
        
        if (Parser.matches(args,Parser.PATTERN_EMPTY));
        else {
            // undo command accepts no arguments
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandUndo.KEYWORD));
        }
        
    }
    
    Revertible mostRecent;
    
    /**
     * Executes undo command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Revertible mostRecent, Displayable currentDisplay) throws Exception {
        this.mostRecent = mostRecent;
        return mostRecent.revert(serviceHandler, projectHandler, currentDisplay);
    }

    @Override
    public Displayable getDisplayable() {
        return ((Command)mostRecent).getDisplayable();
    }

}
