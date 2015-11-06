//@@author A0126509E

package ui;

import java.util.Scanner;

import command.Command;
import command.CommandExit;
import command.Revertible;
import helper.CommonHelper;
import object.State;

public class CLI {
    
    private static Scanner stdin = null;
    private boolean shouldExit;
    
    State currentState;
    
    public CLI() {
        stdin = new Scanner(System.in);
        currentState = new State(true);
        shouldExit = false;
    }
    
    
    private String executeResponse(String userResponse) {
        Command command = null;
        String feedback;
        try {
            command = Command.parseCommand(userResponse);
            feedback = command.execute(currentState);
            if (command instanceof Revertible) {
                // add to history list if project revertible
                currentState.addUndoCommand((Revertible)command);
            }
            
        } catch (Exception e) {
            // catch error message
            feedback = e.getMessage();
        }
        
        if (command instanceof CommandExit) {
            shouldExit = true;
        }
        return feedback;
    }
    
    /**
     * Start command line interface
     */
    public void start() {
        
        System.out.println(CommonHelper.MESSAGE_WELCOME);
        
        while(!shouldExit) {
            System.out.print(CommonHelper.MESSAGE_PROMPT);
            String userResponse = stdin.nextLine();
            String feedback = executeResponse(userResponse);
            System.out.println(feedback);
        }
    }
}
