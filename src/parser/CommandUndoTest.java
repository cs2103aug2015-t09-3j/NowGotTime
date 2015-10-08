package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandUndoTest {

    String args = null;
    CommandUndo cmd = null;
    
    @Test
    public void testCannotAcceptArguments() {
        try {
            // cannot accept arguments
            args = " item ";
            cmd = new CommandUndo(args);
            fail("exception should be thrown");
        } catch (Exception e) {
            assertEquals(String.format(Helper.ERROR_INVALID_ARGUMENTS, CommandUndo.KEYWORD), e.getMessage());
        }
    }
    
    @Test
    public void testCanAcceptNoArguments() {
        try {
            // delete command accept no arguments
            args = "     ";
            cmd = new CommandUndo(args);
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
    }

}
