//@@author A0126509E

package command;

import helper.CommonHelper;
import helper.Parser;
import object.State;

public class CommandRedo implements Command {
    
    public static final String KEYWORD = "redo";

    public CommandRedo(String args) throws Exception {
        
        if (Parser.matches(args,Parser.PATTERN_EMPTY));
        else {
            // redo command accepts no arguments
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandUndo.KEYWORD));
        }
        
    }
    
    Revertible redoCommand;

    @Override
    public String execute(State state) throws Exception {
        redoCommand = state.getRedoCommand();
        if (redoCommand == null) {
            throw new Exception(CommonHelper.ERROR_EMPTY_REDO);
        }
        return ((Command) redoCommand).execute(state);
    }

    @Override
    public Displayable getDisplayable() {
        return ((Command) redoCommand).getDisplayable();
    }

}
