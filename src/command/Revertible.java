package command;

import java.util.Stack;

import project.Projects;
import service.ServiceHandler;

public interface Revertible {

    /**
     * Reverts effect of this command
     */
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList) throws Exception;
}
