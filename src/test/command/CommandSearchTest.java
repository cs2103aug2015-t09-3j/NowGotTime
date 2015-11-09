//@@author A0126509E

package test.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import command.CommandSearch;

public class CommandSearchTest extends CommandTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        addEvent(EVENT1);
        addEvent(EVENT2);
        addEvent(EVENT3);
        addEvent(EVENT4);
        addTodo(TASK1);
        addTodo(TASK2);
        addTodo(FLOATING);
    }

    @Test
    public void testCanSearchItem() {
        String keyword = "la";
        String commandString = "\"" + keyword + "\"";
        CommandSearch command = new CommandSearch(commandString);
        try {
            command.execute(state);
            assertEquals(4, command.getFilteredItem().size());
            assertEquals(EVENT1, command.getFilteredItem().get(0));
            assertEquals(EVENT3, command.getFilteredItem().get(1));
            assertEquals(EVENT4, command.getFilteredItem().get(2));
            assertEquals(FLOATING, command.getFilteredItem().get(3));
        } catch (Exception e) {
            e.printStackTrace();
            fail(EXCEPTION_SHOULD_NOT_BE_THROWN);
        }
    }
    
    @Test
    public void testCanSearchWithoutQuote() {
        String keyword = "laadjklas";
        String commandString = "" + keyword + "";
        CommandSearch command = new CommandSearch(commandString);
        try {
            command.execute(state);
            assertEquals(0, command.getFilteredItem().size());
        } catch (Exception e) {
            e.printStackTrace();
            fail(EXCEPTION_SHOULD_NOT_BE_THROWN);
        }
    }

}
