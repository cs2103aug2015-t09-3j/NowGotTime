import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDelete extends Command {

    public static final String KEYWORD = "edit";

    private static final String PATTERN_DELETE = "\\s*\"(?<name>.+)\"\\s*";

    private static final Pattern REGEX_DELETE       = Pattern.compile(PATTERN_DELETE);

    private static final String FIELD_NAME = "name";
    
    private String itemName;
    
    public String getItemName() {
        return itemName;
    }
    
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
