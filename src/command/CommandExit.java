package command;

import helper.CommonHelper;
import helper.Parser;
import javafx.application.Platform;
import project.Projects;
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
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Revertible mostRecent, Displayable currentDisplay) throws Exception {
        Platform.exit();
        return "";
    }

    @Override
    public Displayable getDisplayable() {
        return null;
    }
}
