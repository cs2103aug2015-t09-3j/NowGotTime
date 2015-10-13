package command;

import java.util.Stack;

import helper.CommonHelper;
import project.ProjectHandler;
import service.ServiceHandler;

public abstract class Command {

    private boolean revertible;
    private boolean requireConfirmation;

    /**
     * Returns Command object from parsed text
     */
    public static Command parseCommand(String text) throws Exception {
        String commandType = CommonHelper.getFirstWord(text);
        String arguments = CommonHelper.removeFirstWord(text);
        
        Command command;
        
        switch (commandType.toLowerCase()) {
            case "add":
                command = new CommandAdd(arguments);
                break;
            case "edit":
                command = new CommandEdit(arguments);
                break;
            case "delete":
                command = new CommandDelete(arguments);
                break;
            case "undo":
                command = new CommandUndo(arguments);
                break;
            case "view":
                command = new CommandView(arguments);
                break;
            case "search":
                command = new CommandSearch(arguments);
                break;
            case "exit":
                command = new CommandExit(arguments);
                break;
            default:
                // throw exception if it is not a valid command
                throw new Exception(String.format(CommonHelper.ERROR_INVALID_COMMAND, commandType));
        }
        return command;
    }
    
    /**
     * Returns whether this command is revertible
     */
    public boolean isRevertible() {
        return revertible;
    }

    /**
     * Set isRevertible
     */
    protected void setRevertible(boolean revertible) {
        this.revertible = revertible;
    }

    /**
     * Returns whether this command require confirmation before execution
     */
    public boolean isRequireConfirmation() {
        return requireConfirmation;
    }

    /**
     * Set requireConfirmation
     */
    protected void setRequireConfirmation(boolean requireConfirmation) {
        this.requireConfirmation = requireConfirmation;
    }

    
    
    /**
     * Executes this command
     */
    public abstract String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception;
    
    /**
     * Reverts effect of this command
     */
    public abstract String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception;
    
}
