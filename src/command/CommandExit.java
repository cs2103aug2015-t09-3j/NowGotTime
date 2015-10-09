package command;

import java.util.Stack;

import helper.CommonHelper;
import project.ProjectHandler;
import service.ServiceHandler;

public class CommandExit extends Command {
    
    public static final String KEYWORD = "exit";
    
    /**
     * Parses the arguments for exit command
     */
    public CommandExit(String args) throws Exception {
        this.setRequireConfirmation(true);
        this.setRevertible(false);
        
        if (args.trim().isEmpty());
        else {
            // delete command accept no arguments
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
        
    }

    /**
     * Executes edit command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        // return empty string
        return "";
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
