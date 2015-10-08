package ui;

import java.util.Scanner;
import java.util.Stack;

import parser.Command;
import parser.CommandExit;
import parser.Helper;
import projectlogic.ProjectHandler;
import crudLogic.ServiceHandler;

public class CLI {
    
    private static Scanner stdin = null;
    private boolean shouldExit;
    
    private ServiceHandler serviceHandler = null;
    private ProjectHandler projectHandler = null;
    private Stack<Command> historyList;
    
    
    public CLI() {
        stdin = new Scanner(System.in);
        serviceHandler = new ServiceHandler();
        projectHandler = new ProjectHandler();
        historyList = new Stack<Command>();
        shouldExit = false;
    }
    
    /**
     * Process user input
     * @param userResponse Text entered by user
     * @return Feedback from program
     */
    private String executeResponse(String userResponse) {
        Command command = null;
        String feedback;
        try {
            command = Command.parseCommand(userResponse);
            feedback = command.execute(serviceHandler, projectHandler, historyList);
            if (command.isRevertible()) {
                // add to history list if project revertible
                historyList.add(command);
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
    
    public void start() {
        
        System.out.println(Helper.MESSAGE_WELCOME);
        
        while(!shouldExit) {
            System.out.print(Helper.MESSAGE_PROMPT);
            String userResponse = stdin.nextLine();
            String feedback = executeResponse(userResponse);
            System.out.println(feedback);
        }
    }
}
