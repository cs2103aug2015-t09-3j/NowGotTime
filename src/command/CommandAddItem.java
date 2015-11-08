//@@author A0126509E

package command;

import java.util.Calendar;
import java.util.regex.Matcher;

import helper.CalendarHelper;
import helper.CommonHelper;
import helper.Parser;
import object.Event;
import object.Item;
import object.State;
import object.Todo;
import service.ServiceHandler;

public class CommandAddItem implements CommandAdd {
    
    private Item item;
    
    /**
     * Parses the arguments for add command
     */
    public CommandAddItem(String args) throws Exception {
        // try to parse command arguments in one of these
        if (Parser.matches(args, Parser.PATTERN_ADD_EVENT)) {
            item = parseAsEvent(args);
        } else if (Parser.matches(args, Parser.PATTERN_ADD_TASK)) {
            item = parseAsTodo(args);
        } else if (Parser.matches(args, Parser.PATTERN_NAME)) {
            item = parseAsFloatingTodo(args);
        } else {
            assert(false);
        }
        
        
    }
    
    /**
     * Constructs this command from specified Item object
     */
    public CommandAddItem(Item item) {
        this.item = item;
    }
    
    /**
     * Returns item object of this command
     */
    public Item getItem() {
        return this.item;
    }
    
    private static Event parseAsEvent(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_ADD_EVENT);
        
        String name = matcher.group(Parser.TAG_NAME);
        String start = matcher.group(Parser.TAG_START);
        String end = matcher.group(Parser.TAG_END);
        
        
        Calendar startCalendar = Calendar.getInstance();
        CalendarHelper.updateCalendar(startCalendar, start);
        
        Calendar endCalendar =  (Calendar) startCalendar.clone();
        CalendarHelper.updateCalendar(endCalendar, end);
        
        String startDate = CalendarHelper.getDateString(startCalendar);
        String startTime = CalendarHelper.getTimeString(startCalendar);
        String endDate = CalendarHelper.getDateString(endCalendar);
        String endTime = CalendarHelper.getTimeString(endCalendar);
        
        return new Event(name, startDate, endDate, startTime, endTime, "");
    }
    
    private static Todo parseAsTodo(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_ADD_TASK);
        
        String name = matcher.group(Parser.TAG_NAME);
        String due = matcher.group(Parser.TAG_DATETIME);
        
        Calendar dueCalendar = Calendar.getInstance();
        CalendarHelper.updateCalendar(dueCalendar, due);
        
        String dueDate = CalendarHelper.getDateString(dueCalendar);
        String dueTime = CalendarHelper.getTimeString(dueCalendar);
        
        return new Todo(name, "", dueDate, dueTime);
    }
    
    private static Todo parseAsFloatingTodo(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_NAME);
        
        String name = matcher.group(Parser.TAG_NAME);
        
        return new Todo(name);
    }


    /**
     * Executes add command, returns feedback string
     */
    @Override
    public String execute(State state) throws Exception {
        ServiceHandler serviceHandler = state.getServiceHandler();
        serviceHandler.createItem(item);
        return String.format(CommonHelper.SUCCESS_ITEM_CREATED, item.getName());
    }

    /**
     * Delete the added command
     */
    @Override
    public String revert(State state) throws Exception {
        Command revertAddCommand = new CommandDeleteItem(item);
        return revertAddCommand.execute(state);
    }

    @Override
    public Displayable getDisplayable() {
        String date;
        if (item instanceof Event) {
            date = ((Event)item).getStartDateString();
        }
        else {
            if (((Todo)item).hasDate()) {
                date = ((Todo)item).getDeadlineDateString();
            } else {
                return new CommandViewDate();
            }
        }
        
        return new CommandViewDate(date);
    }
}
