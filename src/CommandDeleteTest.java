import static org.junit.Assert.*;

import org.junit.Test;

public class CommandDeleteTest {

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
            assertEquals(String.format(Helper.ERROR_INVALID_ARGUMENTS, CommandEdit.KEYWORD), e.getMessage());
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
}
