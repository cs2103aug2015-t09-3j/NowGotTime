//@@author A0126509E

package command;

import object.State;

public interface Revertible {

    /**
     * Reverts effect of this command
     */
    public String revert(State state) throws Exception;
}
