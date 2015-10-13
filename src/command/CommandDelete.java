package command;

import java.util.Calendar;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.CommonHelper;
import javafx.scene.layout.GridPane;
import object.Event;
import object.Item;
import object.Todo;
import project.ProjectHandler;
import service.ServiceHandler;

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
    
    /**
     * Parses the arguments for delete command
     */
    public CommandDelete(String args) throws Exception {
        this.setRequireConfirmation(true);
        this.setRevertible(true);
        
        itemName = parseDelete(args, REGEX_DELETE);
        
        if (itemName == null) {
            // parsing unsuccessful
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, KEYWORD));
        }
    }
    
    /**
     * Constructs command from specified Item object
     */
    public CommandDelete(Item item) {
        this.item = item;
        this.itemName = item.getName();
    }
    
    private String itemName;
    private Item item;
    
    public String getItemName() {
        return itemName;
    }
    
    /**
     * Executes delete command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        if ((item = serviceHandler.viewSpecificEvent(itemName)) != null);
        else if ((item = serviceHandler.viewSpecificTask(itemName)) != null);
        else {
            throw new Exception(String.format(CommonHelper.ERROR_NOT_FOUND, itemName));
        }
        
        if (item instanceof Event) {
            serviceHandler.deleteEvent(itemName);
        }
        else {
            serviceHandler.deleteTask(itemName);
        }
        return String.format(CommonHelper.MESSAGE_DELETE, item.getName());
    }

    /**
     * Re-add the deleted command
     */
    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList) throws Exception {
        Command revertDeleteCommand = new CommandAdd(item);
        return revertDeleteCommand.revert(serviceHandler, projectHandler, historyList);
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
