package test.command;

import static org.junit.Assert.*;

import org.junit.Test;

import command.CommandAdd;
import command.CommandAddItem;
import helper.CommonHelper;
import object.Event;
import object.Item;
import object.Todo;

public class CommandAddTest extends CommandTest {

    /* parsing test for command add */
    
    public void testParseEvent(String args, String expectedName, String expectedStartDate, String expectedStartTime,
            String expectedEndDate, String expectedEndTime) {
        try {
            CommandAddItem cmd = new CommandAddItem(args);
            Item item = cmd.getItem();
            assertTrue(item instanceof Event); 
            assertEquals(((Event) item).getName(), expectedName);
            assertEquals(((Event) item).getStartDateString(), expectedStartDate);
            assertEquals(((Event) item).getStartTimeString(), expectedStartTime);
            assertEquals(((Event) item).getEndDateString(), expectedEndDate);
            assertEquals(((Event) item).getEndTimeString(), expectedEndTime);
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
    }
    
    public void testParseTodo(String args, String expectedName, String expectedDeadlineDate, String expectedDeadlineTime) {
        try {
            CommandAddItem cmd = new CommandAddItem(args);
            Item item = cmd.getItem();
            assertTrue(item instanceof Todo); 
            assertEquals(((Todo) item).getName(), expectedName);
            assertEquals(((Todo) item).getDeadlineDateString(), expectedDeadlineDate);
            assertEquals(((Todo) item).getDeadlineTimeString(), expectedDeadlineTime);
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
    }
    
    public void testParseFloatingTodo(String args, String expectedName) {
        try {
            CommandAddItem cmd = new CommandAddItem(args);
            Item item = cmd.getItem();
            assertTrue(item instanceof Todo); 
            assertEquals(((Todo) item).getName(), expectedName);
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
    }
    
    @Test
    public void testCannotParseInvalidFormat() {
        try {
            // cannot parse invalid format
            String args = " eat again ";
            CommandAdd.parseCommandAdd(args);
            fail("exception should be thrown");
        } catch (Exception e) {
            assertEquals(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandAddItem.KEYWORD), e.getMessage());
        }
    }
    
    @Test
    public void testCanParseEvent() {
        // Can parse valid format
        testParseEvent("\"eat again!\" on 21 September 2016 10:00 to 22 September 2016 23:00",
                "eat again!", "21 Sep 2016", "10:00", "22 Sep 2016", "23:00");
        // Can parse valid format with whitespace
        testParseEvent("  \"eat again!\"  on  21 September 2016 10:00 to  22 September 2016 23:00  ",
                "eat again!", "21 Sep 2016", "10:00", "22 Sep 2016", "23:00");   
    }
    
    @Test
    public void testCanParseTodo() {
        // Can parse valid format
        testParseTodo("\"eat again\" on 21 September 2016 10:00",
                "eat again", "21 Sep 2016", "10:00");
        // Can parse valid format with whitespace
        testParseTodo("  \"eat again\"   on   21 September 2016 10:00  ",
                "eat again", "21 Sep 2016", "10:00");
    }
    
    @Test
    public void testCanParseFloatingTodo() {
        // Can parse valid format
        testParseFloatingTodo("\"eat again!\"",
                "eat again!");
        // Can parse valid format with whitespace
        testParseFloatingTodo("  \"eat again!\"  ",
                "eat again!");
    }
    
    /* execution test */
    
    @Test
    public void testCanAddEvent() throws Exception {
        String name = "eat again";
        String startDateTime = "21 Sep 2016 10:00";
        String endDateTime = "22 Sep 2016 23:00";
        Event event = new Event(name, startDateTime, endDateTime);
        CommandAddItem command = new CommandAddItem(event);
        String feedback = command.execute(service, project, getMostRecentRevertible(), null);
        
        // The feedback message should be success
        assertEquals(feedback, String.format(CommonHelper.SUCCESS_ITEM_CREATED, name));
        // The added event should be the same event
        assertEquals(service.viewSpecificEvent(name), event);
    }
    
    @Test
    public void testCanAddTodo() throws Exception {
        String name = "eat again";
        String deadlineDateTime = "21 Sep 2016 10:00";
        Todo todo = new Todo(name, deadlineDateTime);
        CommandAddItem command = new CommandAddItem(todo);
        String feedback = command.execute(service, project, getMostRecentRevertible(), null);
        
        // The feedback message should be success
        assertEquals(feedback, String.format(CommonHelper.SUCCESS_ITEM_CREATED, name));
        // The added todo should be the same todo
        assertEquals(service.viewSpecificTask(name), todo);
    }
    
    @Test
    public void testCanAddFloatingTodo() throws Exception {
        String name = "eat again";
        Todo todo = new Todo(name);
        CommandAddItem command = new CommandAddItem(todo);
        String feedback = command.execute(service, project, getMostRecentRevertible(), null);
        
        // The feedback message should be success
        assertEquals(feedback, String.format(CommonHelper.SUCCESS_ITEM_CREATED, name));
        // The added todo should be the same todo
        assertEquals(service.viewSpecificTask(name), todo);
    }

}
