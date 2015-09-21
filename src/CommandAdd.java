import java.text.ParseException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandAdd extends Command {
    
    private static final String KEYWORD = "start";
    
    private static final String PATTERN_ADD_EVENT         = "\\s*\"(?<name>.+)\"\\s+on (?<start>.+) to (?<end>.+)";
    private static final String PATTERN_ADD_TODO          = "\\s*\"(?<name>.+)\"\\s+on (?<deadline>.+)";
    private static final String PATTERN_ADD_FLOATING_TODO = "\\s*\"(?<name>.+)\"\\s+"; 
    
    private static final Pattern REGEX_ADD_EVENT         = Pattern.compile(PATTERN_ADD_EVENT);
    private static final Pattern REGEX_ADD_TODO          = Pattern.compile(PATTERN_ADD_TODO);
    private static final Pattern REGEX_ADD_FLOATING_TODO = Pattern.compile(PATTERN_ADD_FLOATING_TODO);
    
    private static final String FIELD_NAME     = "name";
    private static final String FIELD_START    = "start";
    private static final String FIELD_END      = "end";
    private static final String FIELD_DEADLINE = "deadline";
    
    
    Item item;
    
    private boolean parseAsEvent(String args) {
        Matcher eventMatcher = REGEX_ADD_EVENT.matcher(args);
        
        if (eventMatcher.matches()) {
            String name = eventMatcher.group(FIELD_NAME);
            String start = eventMatcher.group(FIELD_START);
            String end = eventMatcher.group(FIELD_END);
            Calendar startCalendar = null;
            Calendar endCalendar = null; 
            try {
                startCalendar = Helper.parseDateTime(start);
                endCalendar = Helper.parseDateTime(end);
            } catch (ParseException e) {
                // Invalid DateTime format
                return false;
            }
            
            String startDate = Helper.getDateString(startCalendar);
            String startTime = Helper.getTimeString(startCalendar);
            String endDate = Helper.getDateString(endCalendar);
            String endTime = Helper.getTimeString(endCalendar);
            
            
            item = new Event(name, startDate, endDate, startTime, endTime);
            return true;
        }
        else {
            return false;
        }
    }
    
    private boolean parseAsTodo(String args) {
        Matcher todoMatcher = REGEX_ADD_TODO.matcher(args);
        
        if (todoMatcher.matches()) {
            String name = todoMatcher.group(FIELD_NAME);
            String deadline = todoMatcher.group(FIELD_DEADLINE);
            Calendar deadlineCalendar = null; 
            try {
                deadlineCalendar = Helper.parseDateTime(deadline);
            } catch (ParseException e) {
                // Invalid DateTime format
                return false;
            }
            
            String deadlineDate = Helper.getDateString(deadlineCalendar);
            String deadlineTime = Helper.getTimeString(deadlineCalendar);
            
            item = new Todo(name, deadlineDate, deadlineTime);
            return true;
        }
        else {
            return false;
        }
    }
    
    private boolean parseAsFloatingTodo(String args) {
        Matcher todoMatcher = REGEX_ADD_FLOATING_TODO.matcher(args);
        
        if (todoMatcher.matches()) {
            String name = todoMatcher.group(FIELD_NAME);
            
            item = new Todo(name);
            return true;
        }
        else {
            return false;
        }
    }
    
    public CommandAdd(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(true);
        
        // Event format (assumed to have a complete date time format for now)
        // add "eat again" on 21 September 2015 10.00 to 22 September 2015 11.00
        
        // Todo format
        // add "eat again" on 21 September 2015 10.00 -> have deadline
        // add "eat again" -> no deadline
        // add eat again -> no deadline
        
        // Try to parse as event, todo, or floating todo
        if (parseAsEvent(args)
         || parseAsTodo(args)
         || parseAsFloatingTodo(args));
        else {
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
