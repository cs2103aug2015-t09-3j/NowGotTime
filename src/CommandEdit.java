import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommandEdit extends Command {

    public static final String KEYWORD = "edit";

    private static final String PATTERN_EDIT_NAME      = "\\s*\"(?<name>.+)\"\\s+(?<field>name) \"(?<value>.+)\"";
    private static final String PATTERN_EDIT_DATE_TIME = "\\s*\"(?<name>.+)\"\\s+(?<field>start|end|due) (?<value>.+)";
    
    private static final Pattern REGEX_EDIT_NAME       = Pattern.compile(PATTERN_EDIT_NAME);
    private static final Pattern REGEX_EDIT_DATE_TIME  = Pattern.compile(PATTERN_EDIT_DATE_TIME);
    
    private static final String FIELD_NAME     = "name";
    private static final String FIELD_START    = "start";
    private static final String FIELD_END      = "end";
    private static final String FIELD_DUE      = "due";
    private static final String FIELD_KEY      = "field";
    private static final String FIELD_VALUE    = "value";
    
    private HashMap<String, String> parseEdit(String args, Pattern REGEX_EDIT) {
        Matcher nameEditMatcher = REGEX_EDIT.matcher(args);
        if (nameEditMatcher.matches()) {
            String name = nameEditMatcher.group(FIELD_NAME);
            String field = nameEditMatcher.group(FIELD_KEY);
            String value = nameEditMatcher.group(FIELD_VALUE);
            
            HashMap<String, String> result = new HashMap<String, String>();
            
            result.put(FIELD_NAME, name);
            result.put(FIELD_KEY, field);
            result.put(FIELD_VALUE, value);
            
            return result;
        }
        else {
            return null;
        }
    }
    
    private String itemName;
    private String fieldName;
    private String newValue;
    private String oldValue;
    
    public CommandEdit(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(true);
        
        HashMap<String, String> result = null;
        
        if ((result = parseEdit(args, REGEX_EDIT_NAME)) != null);
        else if ((result = parseEdit(args, REGEX_EDIT_DATE_TIME)) != null);
        else {
            // parsing unsuccessful
            throw new Exception(String.format(Helper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
        
        itemName = result.get(FIELD_NAME);
        fieldName = result.get(FIELD_KEY);
        newValue = result.get(FIELD_VALUE);
        
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
