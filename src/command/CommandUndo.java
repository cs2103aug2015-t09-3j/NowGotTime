package command;

import java.util.Stack;

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
    
    Revertible lastCommand;
    
    /**
     * Executes undo command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList) throws Exception {
        if (historyList.empty()) {
            return CommonHelper.ERROR_EMPTY_HISTORY;
        }
        lastCommand = ((Revertible)historyList.pop());
        return lastCommand.revert(serviceHandler, projectHandler, historyList);
    }

    @Override
    public Displayable getDisplayable() {
        return ((Command)lastCommand).getDisplayable();
    }

}
