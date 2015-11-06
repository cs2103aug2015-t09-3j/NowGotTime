//@@author A0126509E

package command;

import helper.CommonHelper;
import helper.Parser;
import object.State;

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
    public String execute(State state) throws Exception {
        mostRecent = state.getUndoCommand();
        if (mostRecent != null) {
            throw new Exception(CommonHelper.ERROR_EMPTY_HISTORY);
        }
        state.addRedoCommand(mostRecent);
        return mostRecent.revert(null);
    }

    @Override
    public Displayable getDisplayable() {
        return ((Command)mostRecent).getDisplayable();
    }

}
