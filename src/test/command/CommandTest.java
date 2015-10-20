package test.command;


import java.util.Stack;

import org.junit.Before;

import command.Revertible;
import command.CommandAddItem;
import project.Projects;
import service.ServiceHandler;
import storage.FileHandler;

public class CommandTest {

    protected ServiceHandler service;
    protected Projects project;
    protected Stack<Revertible> history;
    
    @Before
    public void setUp() throws Exception {
        FileHandler clear = new FileHandler();
        clear.clearAll();
        service = new ServiceHandler();
        project = new Projects();
        history = new Stack<Revertible>();
    }
    
    public void addTodo(String name) throws Exception {
        String args = "\"" + name + "\"";
        CommandAddItem cmd = new CommandAddItem(args);
        history.add(cmd);
        cmd.execute(service, project, history);
        
    }
    
    public void addTodo(String name, String deadlineDateTime) throws Exception {
        String args = "\"" + name + "\" on " + deadlineDateTime;
        CommandAddItem cmd = new CommandAddItem(args);
        history.add(cmd);
        cmd.execute(service, project, history);
    }
    
    public void addTodo(String name, String deadlineDate, String deadlineTime) throws Exception {
        String args = "\"" + name + "\" on " + deadlineDate + " " + deadlineTime;
        CommandAddItem cmd = new CommandAddItem(args);
        history.add(cmd);
        cmd.execute(service, project, history);
    }
    
    public void addEvent(String name, String startDateTime, String endDateTime) throws Exception {
        String args = "\"" + name + "\" on " + startDateTime + " to " + endDateTime;
        CommandAddItem cmd = new CommandAddItem(args);
        history.add(cmd);
        cmd.execute(service, project, history);
       
    }
    

}
