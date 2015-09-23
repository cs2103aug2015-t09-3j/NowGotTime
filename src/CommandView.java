import java.util.ArrayList;
import java.util.Stack;

public class CommandView extends Command {

    public static final String KEYWORD = "view";
    
    String dateString;
    
    public CommandView(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(false);
        
        dateString = args.trim();
        
        if (Helper.getCalendarStringType(dateString) != Helper.DATE_TYPE) {
            throw new Exception(Helper.ERROR_INVALID_DATE_TIME);
        }
    }
    
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        ArrayList<Event> eventList = serviceHandler.viewEventByDate(dateString);
        ArrayList<Todo> todoList = serviceHandler.viewTaskByDate(dateString);
        ArrayList<Todo> floatingTodoList = serviceHandler.viewTaskNoDate();
        
        // TODO return formatted string
        
        return null;
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        // view command cannot be reverted
        return null;
    }

}
