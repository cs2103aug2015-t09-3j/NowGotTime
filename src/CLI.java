import java.util.Scanner;

public class CLI {
    
    private static Scanner scanner = null;
    private boolean shouldExit;
    
    CLI() {
        scanner = new Scanner(System.in);
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
            feedback = command.execute();
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
            String userResponse = scanner.nextLine();
            String feedback = executeResponse(userResponse);
            System.out.println(feedback);
        }
    }
}
