//@@author A0126509E

package command;

import helper.CommonHelper;
import project.Projects;
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
            case CommandAdd.KEYWORD:
                command = CommandAdd.parseCommandAdd(arguments);
                break;
            case CommandEdit.KEYWORD:
                command = CommandEdit.parseCommandEdit(arguments);
                break;
            case CommandDelete.KEYWORD:
                command = CommandDelete.parseCommandDelete(arguments);
                break;
            case CommandUndo.KEYWORD:
                command = new CommandUndo(arguments);
                break;
            case CommandView.KEYWORD:
                command = CommandView.parseCommandView(arguments);
                break;
            case CommandSearch.KEYWORD:
                command = CommandSearch.parseCommandSearch(arguments);
                break;
            case CommandCheck.KEYWORD:
                command = new CommandCheck(arguments);
                break;
            case CommandUncheck.KEYWORD:
                command = new CommandUncheck(arguments);
                break;
            case CommandSet.KEYWORD:
                command = new CommandSet(arguments);
                break;
            case CommandExit.KEYWORD:
                command = new CommandExit(arguments);
                break;
            default:
                // throw exception if it is not a valid command
                throw new Exception(String.format(CommonHelper.ERROR_INVALID_COMMAND, commandType));
        }
        return command;
    }
    
    /**
     * Executes this command
     * @param currentDisplay TODO
     */
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Revertible mostRecent, Displayable currentDisplay) throws Exception;
    
    /**
     * Returns a displayable object
     */
    public Displayable getDisplayable();
}
