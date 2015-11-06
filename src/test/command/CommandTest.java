//@@author A0126509E

package test.command;


import org.junit.Before;

import object.State;
import command.CommandAddItem;
import storage.FileHandler;

public class CommandTest {
    
    State state;

    @Before
    public void setUp() throws Exception {
        FileHandler clear = new FileHandler();
        clear.clearAll();
        state = new State();
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
    
    public void addTodo(String name, String deadlineDate, String deadlineTime) throws Exception {
        String args = "\"" + name + "\" by " + deadlineDate + " " + deadlineTime;
        CommandAddItem cmd = new CommandAddItem(args);
        cmd.execute(state);
    }
    
    public void addEvent(String name, String startDateTime, String endDateTime) throws Exception {
        String args = "\"" + name + "\" from " + startDateTime + " to " + endDateTime;
        CommandAddItem cmd = new CommandAddItem(args);
        cmd.execute(state);
       
    }
    

}
