import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDelete extends Command {

    public static final String KEYWORD = "delete";

    private static final String PATTERN_DELETE = "\\s*\"(?<name>.+)\"\\s*";

    private static final Pattern REGEX_DELETE       = Pattern.compile(PATTERN_DELETE);

    private static final String FIELD_NAME = "name";
    
    private static String parseDelete(String args, Pattern REGEX_DELETE) {
        Matcher deleteMatcher = REGEX_DELETE.matcher(args);
        if (deleteMatcher.matches()) {
            String name = deleteMatcher.group(FIELD_NAME);
            
            return name;
        }
        else {
            return null;
        }
    }
    
    public CommandDelete(String args) throws Exception {
        this.setRequireConfirmation(true);
        this.setRevertible(true);
        
        itemName = parseDelete(args, REGEX_DELETE);
        
        if (itemName == null) {
            // parsing unsuccessful
            throw new Exception(String.format(Helper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
    }
    
    private String itemName;
    private Item item;
    
    public String getItemName() {
        return itemName;
    }
    
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        if ((item = serviceHandler.viewSpecificEvent(itemName)) != null);
        else if ((item = serviceHandler.viewSpecificTask(itemName)) != null);
        else {
            throw new Exception(String.format(Helper.ERROR_NOT_FOUND, itemName));
        }
        
        if (item instanceof Event) {
            serviceHandler.deleteEvent(itemName);
        }
        else {
            serviceHandler.deleteTask(itemName);
        }
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
