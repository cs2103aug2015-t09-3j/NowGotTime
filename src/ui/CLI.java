//@@author A0126509E

package ui;

import java.util.Scanner;
import java.util.Stack;

import command.Command;
import command.CommandExit;
import command.Revertible;
import helper.CommonHelper;
import project.Projects;
import service.ServiceHandler;

public class CLI {
    
    private static Scanner stdin = null;
    private boolean shouldExit;
    
    private ServiceHandler serviceHandler = null;
    private Projects projectHandler = null;
    private Stack<Revertible> historyList;
    
    public CLI() {
        stdin = new Scanner(System.in);
        serviceHandler = new ServiceHandler();
        projectHandler = new Projects();
        historyList = new Stack<Revertible>();
        shouldExit = false;
    }
    
    private Revertible getMostRecentRevertible() {
        if (historyList.empty()) {
            return null;
        } else {
            return historyList.pop();
        }
    }
    
    private String executeResponse(String userResponse) {
        Command command = null;
        String feedback;
        try {
            command = Command.parseCommand(userResponse);
            feedback = command.execute(serviceHandler, projectHandler, getMostRecentRevertible(), null);
            if (command instanceof Revertible) {
                // add to history list if project revertible
                historyList.add((Revertible)command);
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
