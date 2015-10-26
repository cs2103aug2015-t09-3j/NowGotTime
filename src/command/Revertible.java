package command;

import project.Projects;
import service.ServiceHandler;

public interface Revertible {

    /**
     * Reverts effect of this command
     */
    public String revert(ServiceHandler serviceHandler, Projects projectHandler, Displayable currentDisplay) throws Exception;
}
