import static org.junit.Assert.*;

import org.junit.Test;

public class CommandEditTest {

    /* parsing test for edit command */

    String args = null;
    CommandEdit cmd = null;
    
    @Test
    public void testCannotParseInvalidFormat() {
        try {
            // cannot parse invalid format
            args = " eat again ";
            cmd = new CommandEdit(args);
            fail("exception should be thrown");
        } catch (Exception e) {
            assertEquals(String.format(Helper.ERROR_INVALID_ARGUMENTS, CommandEdit.KEYWORD), e.getMessage());
        }
    }
    
    @Test
    public void testCanParseNameField() {
        
        try {
            // can parse edit command on name
            args = "\"eat again!\" name \"sleep again\"";
            cmd = new CommandEdit(args);
            
            assertEquals(cmd.getItemName(), "eat again!");
            assertEquals(cmd.getFieldName(), "name");
            assertEquals(cmd.getNewValue(), "sleep again");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
    }
    
    @Test
    public void testCanParseDateField() {
        
        try {
            // can parse edit command on start date
            args = "\"eat again!\" start 15 September 2015 23:12";
            cmd = new CommandEdit(args);
            
            assertEquals(cmd.getItemName(), "eat again!");
            assertEquals(cmd.getFieldName(), "start");
            assertEquals(cmd.getNewValue(), "15 September 2015 23:12");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
        try {
            // can parse edit command on end date
            args = "\"eat again!\" end 15 September 2015 23:12";
            cmd = new CommandEdit(args);
            
            assertEquals(cmd.getItemName(), "eat again!");
            assertEquals(cmd.getFieldName(), "end");
            assertEquals(cmd.getNewValue(), "15 September 2015 23:12");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
        try {
            // can parse edit command on due date
            args = "\"eat again!\" due 15 September 2015 23:12";
            cmd = new CommandEdit(args);
            
            assertEquals(cmd.getItemName(), "eat again!");
            assertEquals(cmd.getFieldName(), "due");
            assertEquals(cmd.getNewValue(), "15 September 2015 23:12");
            
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
        
    }

}
