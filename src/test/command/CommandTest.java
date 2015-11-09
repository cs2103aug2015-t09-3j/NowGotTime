//@@author A0126509E

package test.command;


import org.junit.Before;

import object.Event;
import object.State;
import object.Todo;
import project.Projects;
import command.Command;
import command.CommandAddItem;
import storage.FileHandler;

public class CommandTest {
    
    State state;
    
    protected Event EVENT1;
    protected Event EVENT2;
    protected Event EVENT3;
    protected Event EVENT4;
    protected Todo TASK1;
    protected Todo TASK2;
    protected Todo FLOATING;
    protected static final String EXCEPTION_SHOULD_NOT_BE_THROWN = "exception should not be thrown";
    protected static final String EXCEPTION_SHOULD_BE_THROWN = "exception should be thrown";

    
    @Before
    public void setUp() throws Exception {
        FileHandler clear = new FileHandler();
        clear.clearAll();
        state = new State(false);
        
        EVENT1 = new Event("lala", "05 Dec 2015 10:00", "07 Dec 2015 13:00");
        EVENT2 = new Event("lolo", "08 Dec 2015 18:00", "08 Dec 2015 19:00");
        EVENT3 = new Event("yo la", "08 Dec 2015 10:00", "13 Dec 2015 23:50");
        EVENT4 = new Event("yo la", "08 Dec 2015 10:10", "13 Dec 2015 23:50");
        TASK1 = new Todo("eat lo2", "13 Nov 2015 13:00");
        TASK2 = new Todo("eat lo", "18 Nov 2015 14:00");
        FLOATING = new Todo("lala");
    }
    
    public void addTodo(String name) throws Exception {
        String args = "\"" + name + "\"";
        CommandAddItem cmd = new CommandAddItem(args);
        cmd.execute(state);
        
    }
    
    public void addTodo(String name, String deadlineDateTime) throws Exception {
        String args = "\"" + name + "\" by " + deadlineDateTime;
        CommandAddItem cmd = new CommandAddItem(args);
        cmd.execute(state);
    }
    
    public void addEvent(String name, String startDateTime, String endDateTime) throws Exception {
        String args = "\"" + name + "\" from " + startDateTime + " to " + endDateTime;
        CommandAddItem cmd = new CommandAddItem(args);
        cmd.execute(state);
    }
    
    public void addTodo(Todo todo) throws Exception {
        Command cmd = new CommandAddItem(todo);
        cmd.execute(state);
    }
    
    public void addEvent(Event event) throws Exception {
        Command cmd = new CommandAddItem(event);
        cmd.execute(state);
    }
    
    public void addProject(String projectName) {
        Projects projectHandler = state.getProjectHandler();
        projectHandler.createProject(projectName);
    }
    
    public void addToProject(Event event, String projectName) {
        Projects projectHandler = state.getProjectHandler();
        projectHandler.addProjectEvent(event, projectName);
    }
    

}
