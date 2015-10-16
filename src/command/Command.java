package command;

import java.util.Stack;

import helper.CommonHelper;
import project.ProjectHandler;
import service.ServiceHandler;

public interface Command {

    /**
     * Returns Command object from parsed text
     */
    public static Command parseCommand(String text) throws Exception {
        String commandType = CommonHelper.getFirstWord(text);
        String arguments = CommonHelper.removeFirstWord(text);
        
        Command command;
        
        switch (commandType.toLowerCase()) {
            case "add":
                command = CommandAdd.parseCommandAdd(arguments);
                break;
            case "edit":
                command = CommandEdit.parseCommandEdit(arguments);
                break;
            case "delete":
                command = CommandDelete.parseCommandDelete(arguments);
                break;
            case "undo":
                command = new CommandUndo(arguments);
                break;
            case "view":
                command = new CommandViewDate(arguments);
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

    public Displayable getDisplayable();
    
    /**
     * Executes this command
     */
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception;
    
    
}
