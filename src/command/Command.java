package command;

import java.util.Stack;

import helper.CommonHelper;
import javafx.scene.layout.GridPane;
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
                command = new CommandEditItem(arguments);
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

    public void display(ServiceHandler serviceHandler, ProjectHandler projectHandler, GridPane displayBox) throws Exception;
    
    /**
     * Executes this command
     */
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception;
    
    
}
