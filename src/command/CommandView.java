package command;

import java.util.ArrayList;
import java.util.Stack;

import helper.Helper;
import object.Event;
import object.Todo;
import project.ProjectHandler;
import service.ServiceHandler;

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
        
        StringBuilder feedback = new StringBuilder();
        feedback.append("NowGotTime on " + dateString + "\n");
        feedback.append("----------------------------------------\n");
        feedback.append("--Event\n");
        feedback.append(Helper.getFormattedEventList(eventList, dateString));
        feedback.append("----------------------------------------\n");
        feedback.append("--Todo\n");
        feedback.append(Helper.getFormattedTodoList(todoList, dateString));
        feedback.append("----------------------------------------\n");
        feedback.append("--Floating Todo\n");
        feedback.append(Helper.getFormattedTodoList(floatingTodoList, dateString));
        feedback.append("----------------------------------------\n");
        
        return feedback.toString();
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        // view command cannot be reverted
        return null;
    }

}
