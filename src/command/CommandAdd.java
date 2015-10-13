package command;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.CalendarHelper;
import helper.CommonHelper;
import javafx.scene.layout.GridPane;
import object.Event;
import object.Item;
import object.Todo;
import project.ProjectHandler;
import service.ServiceHandler;

public class CommandAdd extends Command {
    
    public static final String KEYWORD = "add";
    
    private static final String PATTERN_ADD_EVENT         = "\\s*\"(?<name>.+)\"\\s+on (?<start>.+) to (?<end>.+)";
    private static final String PATTERN_ADD_TODO          = "\\s*\"(?<name>.+)\"\\s+on (?<due>.+)";
    private static final String PATTERN_ADD_FLOATING_TODO = "\\s*\"(?<name>.+)\"\\s*"; 
    
    private static final Pattern REGEX_ADD_EVENT         = Pattern.compile(PATTERN_ADD_EVENT);
    private static final Pattern REGEX_ADD_TODO          = Pattern.compile(PATTERN_ADD_TODO);
    private static final Pattern REGEX_ADD_FLOATING_TODO = Pattern.compile(PATTERN_ADD_FLOATING_TODO);
    
    private static final String FIELD_NAME     = "name";
    private static final String FIELD_START    = "start";
    private static final String FIELD_END      = "end";
    private static final String FIELD_DEADLINE = "due";
    
    private static Event parseAsEvent(String args) {
        Matcher eventMatcher = REGEX_ADD_EVENT.matcher(args);
        if (eventMatcher.matches()) {
            String name = eventMatcher.group(FIELD_NAME);
            String start = eventMatcher.group(FIELD_START).trim();
            String end = eventMatcher.group(FIELD_END).trim();
            
            Calendar startCalendar = null;
            Calendar endCalendar = null; 
            try {
                startCalendar = CalendarHelper.parseDateTime(start);
                endCalendar = CalendarHelper.parseDateTime(end);
            } catch (ParseException e) {
                // Invalid DateTime format
                return null;
            }
            
            String startDate = CalendarHelper.getDateString(startCalendar);
            String startTime = CalendarHelper.getTimeString(startCalendar);
            String endDate = CalendarHelper.getDateString(endCalendar);
            String endTime = CalendarHelper.getTimeString(endCalendar);
            
            
            return new Event(name, startDate, endDate, startTime, endTime, ""); // Remove empty string parameter once we delete additionalInformation
        }
        else {
            return null;
        }
    }
    
    private static Todo parseAsTodo(String args) {
        Matcher todoMatcher = REGEX_ADD_TODO.matcher(args);
        
        if (todoMatcher.matches()) {
            String name = todoMatcher.group(FIELD_NAME);
            String deadline = todoMatcher.group(FIELD_DEADLINE);
            Calendar deadlineCalendar = null; 
            try {
                deadlineCalendar = CalendarHelper.parseDateTime(deadline);
            } catch (ParseException e) {
                // Invalid DateTime format
                return null;
            }
            
            String deadlineDate = CalendarHelper.getDateString(deadlineCalendar);
            String deadlineTime = CalendarHelper.getTimeString(deadlineCalendar);
            
            return new Todo(name, "", deadlineDate, deadlineTime);  // Remove empty string parameter once we delete additionalInformation
        }
        else {
            return null;
        }
    }
    
    private static Todo parseAsFloatingTodo(String args) {
        Matcher todoMatcher = REGEX_ADD_FLOATING_TODO.matcher(args);
        
        if (todoMatcher.matches()) {
            String name = todoMatcher.group(FIELD_NAME);
            
            return new Todo(name);
        }
        else {
            return null;
        }
    }
    
    /**
     * Parses the arguments for add command
     */
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
        if ((this.item = parseAsEvent(args)) != null);
        else if ((this.item = parseAsTodo(args)) != null);
        else if ((this.item = parseAsFloatingTodo(args)) != null);
        else {
            // parsing unsuccessful
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
    }
    
    /**
     * Constructs this command from specified Item object
     */
    public CommandAdd(Item item) {
        this.item = item;
    }
    
    private Item item;
    
    /**
     * Returns item object of this command
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Executes add command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        
        if (item instanceof Event) {
            // add new event
            if (serviceHandler.createEvent((Event)item)) {
                return String.format(CommonHelper.MESSAGE_ADD, item.getName());
            }
            else {
                // name already exists
                throw new Exception(String.format(CommonHelper.ERROR_ADD_EVENT, item.getName()));
            }
        }
        else {
            // add new todo
            if (serviceHandler.createTask((Todo)item)) {
                return String.format(CommonHelper.MESSAGE_ADD, item.getName());
            }
            else {
                // name already exists
                throw new Exception(String.format(CommonHelper.ERROR_ADD_TODO, item.getName()));
            }
        }
    }

    /**
     * Delete the added command
     */
    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        Command revertAddCommand = new CommandDelete(item);
        return revertAddCommand.execute(serviceHandler, projectHandler, historyList);
    }

    @Override
    public void display(ServiceHandler serviceHandler, ProjectHandler projectHandler, GridPane displayBox) throws Exception {
        Calendar date;
        if (item instanceof Event) {
            date = ((Event)item).getStartCalendar();
        }
        else {
            date = ((Todo)item).getDeadline();
        }
        Command viewCommand = new CommandView(date);
        viewCommand.execute(serviceHandler, projectHandler, new Stack<Command>());
        viewCommand.display(serviceHandler, projectHandler, displayBox);
    }
}
