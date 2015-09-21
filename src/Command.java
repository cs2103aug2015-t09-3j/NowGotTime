
public abstract class Command {

    private boolean revertible;
    private boolean requireConfirmation;

    public static Command parseCommand(String text) throws Exception {
        String commandType = Helper.getFirstWord(text);
        String arguments = Helper.removeFirstWord(text);
        
        Command command;
        
        switch (commandType.toLowerCase()) {
            case "add":
                command = new CommandAdd(arguments);
                break;
            case "exit":
                command = new CommandExit(arguments);
                break;
            default:
                // throw exception if it is not a valid command
                throw new Exception(String.format(Helper.ERROR_INVALID_COMMAND, commandType));
        }
        return command;
    }
    
    public boolean isRevertible() {
        return revertible;
    }


    public void setRevertible(boolean revertible) {
        this.revertible = revertible;
    }


    public boolean isRequireConfirmation() {
        return requireConfirmation;
    }


    public void setRequireConfirmation(boolean requireConfirmation) {
        this.requireConfirmation = requireConfirmation;
    }

    
    
    /*
     * Execute this command
     * @return Feedback from execution
     */
    public abstract String execute() throws Exception;
    
    /*
     * Revert this command
     * @return Feedback from execution
     */
    public abstract String revert() throws Exception;
    
}
