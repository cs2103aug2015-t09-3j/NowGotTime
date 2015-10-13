package command;

import java.util.Stack;

import helper.CommonHelper;
import javafx.scene.layout.GridPane;
import project.ProjectHandler;
import service.ServiceHandler;

public class CommandUndo extends Command {

    public static final String KEYWORD = "undo";
    
    /**
     * Parses the arguments for undo command
     */
    public CommandUndo(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(false);
        
        if (args.trim().isEmpty());
        else {
            // undo command accept no arguments
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
        
    }
    
    /**
     * Executes undo command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        if (historyList.empty()) {
            return CommonHelper.ERROR_EMPTY_HISTORY;
        }
        Command lastCommand = historyList.pop();
        return lastCommand.revert(serviceHandler, projectHandler, historyList);
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        // not revertible
        return null;
    }

    @Override
    public void display(ServiceHandler serviceHandler, ProjectHandler projectHandler, GridPane displayBox)
            throws Exception {
        // TODO Auto-generated method stub
        
    }

}
