package command;

import java.util.Stack;

import helper.CommonHelper;
import helper.Parser;
import project.ProjectHandler;
import service.ServiceHandler;

public class CommandExit implements Command {
    
    public static final String KEYWORD = "exit";
    
    /**
     * Parses the arguments for exit command
     */
    public CommandExit(String args) throws Exception {
        
        if (Parser.matches(args,Parser.PATTERN_EMPTY));
        else {
            // undo command accepts no arguments
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandUndo.KEYWORD));
        }
        
    }

    /**
     * Executes edit command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Revertible> historyList) throws Exception {
        // return empty string
        // TODO exit gui
        return "";
    }

    @Override
    public Displayable getDisplayable() {
        // TODO Auto-generated method stub
        return null;
    }
}
