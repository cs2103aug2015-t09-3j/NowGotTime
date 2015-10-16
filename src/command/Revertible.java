package command;

import java.util.Stack;

import project.ProjectHandler;
import service.ServiceHandler;

public interface Revertible {

    /**
     * Reverts effect of this command
     */
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception;
}
