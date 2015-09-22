import static org.junit.Assert.*;

import org.junit.Test;

public class CommandAddTest {

    /* parsing test for command add */
    
    
    String args = null;
    CommandAdd cmd = null;
    Event event = null;
    Todo todo = null;
    
    @Test
    public void testCannotParseInvalidFormat() {
        try {
            // cannot parse invalid format
            args = " eat again ";
            cmd = new CommandAdd(args);
            fail("exception should be thrown");
        } catch (Exception e) {
            assertEquals(String.format(Helper.ERROR_INVALID_ARGUMENTS, CommandAdd.KEYWORD), e.getMessage());
        }
    }
    
    @Test
    public void testCanParseEvent() {
        
        
        try {
            // can parse normal add event
            args = "\"eat again!\" on 21 September 2015 10:00 to 22 September 2015 23:00";
            cmd = new CommandAdd(args);
            assertTrue(cmd.getItem() instanceof Event);
            event = (Event) cmd.getItem();
            
            assertEquals(event.getName(), "eat again!");
            assertEquals(event.getStartDateString(), "21 Sep 2015");
            assertEquals(event.getStartTimeString(), "10:00");
            assertEquals(event.getEndDateString(), "22 Sep 2015");
            assertEquals(event.getEndTimeString(), "23:00");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
        try {
            // can parse add event with some whitespace
            args = "  \"eat again\"  on  21 September 2015 10:00 to  22 September 2015 23:00  ";
            cmd = new CommandAdd(args);
            assertTrue(cmd.getItem() instanceof Event);
            event = (Event) cmd.getItem();
            
            assertEquals(event.getName(), "eat again");
            assertEquals(event.getStartDateString(), "21 Sep 2015");
            assertEquals(event.getStartTimeString(), "10:00");
            assertEquals(event.getEndDateString(), "22 Sep 2015");
            assertEquals(event.getEndTimeString(), "23:00");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
        try {
            // cannot parse invalid format
            args = " eat again ";
            cmd = new CommandAdd(args);
            fail("exception should be thrown");
        } catch (Exception e) {
            assertEquals(String.format(Helper.ERROR_INVALID_ARGUMENTS, CommandAdd.KEYWORD), e.getMessage());
        }
        
    }
    
    @Test
    public void testCanParseTodo() {
        
        try {
            // can parse normal add event
            args = "\"eat again!\" on 21 September 2015 10:00";
            cmd = new CommandAdd(args);
            assertTrue(cmd.getItem() instanceof Todo);
            todo = (Todo) cmd.getItem();

            assertEquals(todo.getName(), "eat again!");
            assertEquals(todo.getDeadlineDateString(), "21 Sep 2015");
            assertEquals(todo.getDeadlineTimeString(), "10:00");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
        try {
            // can parse add event with some whitespace
            args = "  \"eat again\"   on   21 September 2015 10:00  ";
            cmd = new CommandAdd(args);
            assertTrue(cmd.getItem() instanceof Todo);
            todo = (Todo) cmd.getItem();
            
            assertEquals(todo.getName(), "eat again");
            assertEquals(todo.getDeadlineDateString(), "21 Sep 2015");
            assertEquals(todo.getDeadlineTimeString(), "10:00");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
    }
    
    @Test
    public void testCanParseFloatingTodo() {
        
        try {
            // can parse normal add event
            args = "\"eat again!\"";
            cmd = new CommandAdd(args);
            assertTrue(cmd.getItem() instanceof Todo);
            todo = (Todo) cmd.getItem();

            assertEquals(todo.getName(), "eat again!");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
        try {
            // can parse add event with some whitespace
            args = "  \"eat again\"      ";
            cmd = new CommandAdd(args);
            assertTrue(cmd.getItem() instanceof Todo);
            todo = (Todo) cmd.getItem();
            
            assertEquals(todo.getName(), "eat again");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
    }

}
