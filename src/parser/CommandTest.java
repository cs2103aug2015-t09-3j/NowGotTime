package parser;


import java.util.Stack;

import org.junit.Before;

import projectlogic.ProjectHandler;
import crudLogic.ServiceHandler;
import storage.FileHandler;

public class CommandTest {

    protected ServiceHandler service;
    protected ProjectHandler project;
    protected Stack<Command> history;
    
    @Before
    public void setUp() throws Exception {
        FileHandler clear = new FileHandler();
        clear.clearAll();
        service = new ServiceHandler();
        project = new ProjectHandler();
        history = new Stack<Command>();
    }
    
    public void addTodo(String name) throws Exception {
        String args = "\"" + name + "\"";
        CommandAdd cmd = new CommandAdd(args);
        history.add(cmd);
        cmd.execute(service, project, history);
        
    }
    
    public void addTodo(String name, String deadlineDateTime) throws Exception {
        String args = "\"" + name + "\" on " + deadlineDateTime;
        CommandAdd cmd = new CommandAdd(args);
        history.add(cmd);
        cmd.execute(service, project, history);
    }
    
    public void addTodo(String name, String deadlineDate, String deadlineTime) throws Exception {
        String args = "\"" + name + "\" on " + deadlineDate + " " + deadlineTime;
        CommandAdd cmd = new CommandAdd(args);
        history.add(cmd);
        cmd.execute(service, project, history);
    }
    
    public void addEvent(String name, String startDateTime, String endDateTime) throws Exception {
        String args = "\"" + name + "\" on " + startDateTime + " to " + endDateTime;
        CommandAdd cmd = new CommandAdd(args);
        history.add(cmd);
        cmd.execute(service, project, history);
    }
    

}
