
public interface Command {

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
    
    /*
     * Execute this command
     * @return Feedback from execution
     */
    public String execute() throws Exception;
    
    /*
     * Revert this command
     * @return Feedback from execution
     */
    public String revert() throws Exception;
    
}
