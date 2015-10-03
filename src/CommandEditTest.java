import static org.junit.Assert.*;

import org.junit.Test;

public class CommandEditTest {

    /* parsing test for edit command */

    public void testParseEdit(String args, String expectedName, String expectedField, String expectedValue) {
        try {
            CommandEdit cmd = new CommandEdit(args);
            assertEquals(cmd.getItemName(), expectedName);
            assertEquals(cmd.getFieldName(), expectedField);
            assertEquals(cmd.getNewValue(), expectedValue);
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
    }
    
    @Test
    public void testCannotParseInvalidFormat() {
        try {
            // cannot parse invalid format
            String args = " eat again ";
            new CommandEdit(args);
            fail("exception should be thrown");
        } catch (Exception e) {
            assertEquals(String.format(Helper.ERROR_INVALID_ARGUMENTS, CommandEdit.KEYWORD), e.getMessage());
        }
    }
    
    @Test
    public void testCanParseEditField() {
        
        // can parse name field
        testParseEdit("\"eat again!\" name \"sleep again\"",
                      "eat again!", "name", "sleep again");
        
        // can parse start field
        testParseEdit("\"eat again!\" start 15 September 2015 23:12",
                      "eat again!", "start", "15 September 2015 23:12");
        
        // can parse end field
        testParseEdit("\"eat again!\" end 15 September 2015 23:12",
                      "eat again!", "end", "15 September 2015 23:12");
        
        // can parse due field
        testParseEdit("\"eat again!\" due 15 September 2015 23:12",
                      "eat again!", "due", "15 September 2015 23:12");
        
    }
   

}
