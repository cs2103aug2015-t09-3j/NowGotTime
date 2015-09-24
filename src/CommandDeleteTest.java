import static org.junit.Assert.*;

import org.junit.Test;

public class CommandDeleteTest extends CommandTest {

    /* parsing test for delete command */

    String args = null;
    CommandDelete cmd = null;
    
    @Test
    public void testCannotParseInvalidFormat() {
        try {
            // cannot parse invalid format
            args = " \" item ";
            cmd = new CommandDelete(args);
            fail("exception should be thrown");
        } catch (Exception e) {
            assertEquals(String.format(Helper.ERROR_INVALID_ARGUMENTS, CommandDelete.KEYWORD), e.getMessage());
        }
    }

    @Test
    public void testCanParseValidFormat() {
        try {
            // can parse edit command on start date
            args = "\"eat again!\"";
            cmd = new CommandDelete(args);
            
            assertEquals(cmd.getItemName(), "eat again!");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
        try {
            // can parse edit command on end date
            args = "  \"eat again!\"  ";
            cmd = new CommandDelete(args);
            
            assertEquals(cmd.getItemName(), "eat again!");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
    }
    
    /* execution test for delete command */
    
    @Test
    public void testCanDeleteEvent() throws Exception {
        String name = "eat again";
        String startDateTime = "21 Sep 2015 10:00";
        String endDateTime = "22 Sep 2015 23:00";
        addEvent(name, startDateTime, endDateTime);
        
        CommandDelete cmd = new CommandDelete("\"" + name + "\"");
        
        // new event exists on service
        assertNotNull(service.viewSpecificEvent(name));
        
        String feedback = cmd.execute(service, project, history);

        // successfully deleted
        assertEquals(feedback, String.format(Helper.MESSAGE_DELETE, name));
        

        // new event removed from the service
        assertNull(service.viewSpecificEvent(name));
        
    }
    
    @Test
    public void testCanDeleteTodo() throws Exception {
        String name = "eat again";
        String deadlineDateTime = "21 Sep 2015 10:00";
        addTodo(name, deadlineDateTime);
        
        CommandDelete cmd = new CommandDelete("\"" + name + "\"");
        
        // new todo exists on service
        assertNotNull(service.viewSpecificTask(name));
        
        String feedback = cmd.execute(service, project, history);

        // successfully deleted
        assertEquals(feedback, String.format(Helper.MESSAGE_DELETE, name));
        

        // new todo removed from the service
        assertNull(service.viewSpecificTask(name));
    }
    
    @Test
    public void testCanDeleteFloatingTodo() throws Exception {
        String name = "eat again";
        addTodo(name);
        
        CommandDelete cmd = new CommandDelete("\"" + name + "\"");
        
        // new todo exists on service
        assertNotNull(service.viewSpecificTask(name));
        
        String feedback = cmd.execute(service, project, history);

        // successfully deleted
        assertEquals(feedback, String.format(Helper.MESSAGE_DELETE, name));
        

        // new todo removed from the service
        assertNull(service.viewSpecificTask(name));
    }
}
