
public class CommandUndo extends Command {

    public static final String KEYWORD = "undo";
    
    public CommandUndo(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(false);
        
        if (args.trim().isEmpty());
        else {
            // undo command accept no arguments
            throw new Exception(String.format(Helper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
        
    }
    
    @Override
    public String execute() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String revert() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
