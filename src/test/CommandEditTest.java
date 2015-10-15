package test;

import static org.junit.Assert.*;

import org.junit.Test;

import command.CommandEdit;
import helper.CommonHelper;
import object.Event;
import object.Todo;

public class CommandEditTest extends CommandTest {

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
            assertEquals(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandEdit.KEYWORD), e.getMessage());
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
    
    /* execution test for edit command */
    
    @Test
    public void testCanEditEvent() throws Exception {
        String name = "eat again";
        String startDateTime = "21 Sep 2015 10:00";
        String endDateTime = "22 Sep 2015 23:00";
        addEvent(name, startDateTime, endDateTime);
        Event event = null;
        
        // test can edit start date
        new CommandEdit(name, CommonHelper.FIELD_START, "19 Jan 2015").execute(service, project, history);
        event = service.viewSpecificEvent(name);
        assertEquals(event.getStartDateTimeString(), "19 Jan 2015 10:00");
        
        // test can edit start time
        new CommandEdit(name, CommonHelper.FIELD_START, "13:00").execute(service, project, history);
        event = service.viewSpecificEvent(name);
        assertEquals(event.getStartDateTimeString(), "19 Jan 2015 13:00");
        
        // test can edit start date and time
        new CommandEdit(name, CommonHelper.FIELD_START, "20 Sep 2015 13:00").execute(service, project, history);
        event = service.viewSpecificEvent(name);
        assertEquals(event.getStartDateTimeString(), "20 Sep 2015 13:00");
        
        // test can edit end date
        new CommandEdit(name, CommonHelper.FIELD_END, "19 Dec 2015").execute(service, project, history);
        event = service.viewSpecificEvent(name);
        assertEquals(event.getEndDateTimeString(), "19 Dec 2015 23:00");
        
        // test can edit end time
        new CommandEdit(name, CommonHelper.FIELD_END, "13:00").execute(service, project, history);
        event = service.viewSpecificEvent(name);
        assertEquals(event.getEndDateTimeString(), "19 Dec 2015 13:00");
        
        // test can edit end date and time
        new CommandEdit(name, CommonHelper.FIELD_END, "20 Nov 2015 15:00").execute(service, project, history);
        event = service.viewSpecificEvent(name);
        assertEquals(event.getEndDateTimeString(), "20 Nov 2015 15:00");
        
        // test can edit name
        new CommandEdit(name, CommonHelper.FIELD_NAME, "eat meh").execute(service, project, history);
        assertNull(service.viewSpecificEvent(name));
        assertNotNull(service.viewSpecificEvent("eat meh"));
        
    }
    
    @Test
    public void testCanEditTodo() throws Exception {
        String name = "eat again lho";
        String deadlineDateTime = "21 Sep 2015 10:00";
        addTodo(name, deadlineDateTime);
        Todo todo = null;
        
        // test can edit due date
        new CommandEdit(name, CommonHelper.FIELD_DUE, "19 Dec 2015").execute(service, project, history);
        todo = service.viewSpecificTask(name);
        assertEquals(todo.getDeadlineDateTimeString(), "19 Dec 2015 10:00");
        
        // test can edit due time
        new CommandEdit(name, CommonHelper.FIELD_DUE, "13:00").execute(service, project, history);
        todo = service.viewSpecificTask(name);
        assertEquals(todo.getDeadlineDateTimeString(), "19 Dec 2015 13:00");
        
        // test can edit due date and time
        new CommandEdit(name, CommonHelper.FIELD_DUE, "20 Nov 2015 13:00").execute(service, project, history);
        todo = service.viewSpecificTask(name);
        assertEquals(todo.getDeadlineDateTimeString(), "20 Nov 2015 13:00");
        
        // test can edit name
        new CommandEdit(name, CommonHelper.FIELD_NAME, "eat meh meh").execute(service, project, history);
        assertNull(service.viewSpecificTask(name));
        assertNotNull(service.viewSpecificTask("eat meh meh"));
    }
    
    @Test
    public void testCanEditFloatingTodo() throws Exception {
        String name = "eat again lho";
        addTodo(name);
        
        new CommandEdit(name, CommonHelper.FIELD_NAME, "eat meh meh").execute(service, project, history);
        assertNull(service.viewSpecificTask(name));
        assertNotNull(service.viewSpecificTask("eat meh meh"));
    }
    
   

}
