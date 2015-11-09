package test.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import command.CommandDeleteFromProject;
import command.CommandViewProjectName;
import project.Projects;

public class CommandDeleteFromProjectTest extends CommandTest {

    String projectName;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        projectName = "yo la yo";
        addProject(projectName);
        addEvent(EVENT1);
        addToProject(EVENT1, projectName);
        state.updateDisplay(new CommandViewProjectName(projectName));
    }

    @Test
    public void testCanDeleteEventFromProject() {
        try {
            Projects projectHandler = state.getProjectHandler();
            
            assertEquals(1, projectHandler.viewEventProgressTimeline(projectName).size());
            
            String commandString = "1 from project";
            
            CommandDeleteFromProject command = new CommandDeleteFromProject(commandString);
            command.execute(state);
            
            assertEquals(0, projectHandler.viewEventProgressTimeline(projectName).size());
            
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(EXCEPTION_SHOULD_NOT_BE_THROWN);
        }
    }
    
    @Test
    public void testCannotDeleteNonExistingEventFromProject() {
        Projects projectHandler = state.getProjectHandler();
        try {
            
            assertEquals(1, projectHandler.viewEventProgressTimeline(projectName).size());
            
            String commandString = "2 from project";
            
            CommandDeleteFromProject command = new CommandDeleteFromProject(commandString);
            command.execute(state);
            fail(EXCEPTION_SHOULD_BE_THROWN);
            
        } catch (Exception e) {
            assertEquals(1, projectHandler.viewEventProgressTimeline(projectName).size());
        }
    }

}
