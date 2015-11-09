//@@author A0126509E
package test.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import command.CommandAddProject;
import project.Projects;

public class CommandAddProjectTest extends CommandTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testCanAddProject() {
        String projectName = "yo la yo";
        String commandString = "project \"" + projectName + "\"";
        CommandAddProject command = new CommandAddProject(commandString);
        try {
            command.execute(state);
            Projects projectHandler = state.getProjectHandler();
            assertEquals(1, projectHandler.listExistingProjects().size());
            assertEquals(projectName, projectHandler.listExistingProjects().get(0));
        } catch (Exception e) {
            e.printStackTrace();
            fail(EXCEPTION_SHOULD_NOT_BE_THROWN);
        }
    }
    
    @Test
    public void testCannotAddMultipleProjectWithSameName() {
        String projectName = "yo la yo";
        addProject(projectName);
        
        String commandString = "project \"" + projectName + "\"";
        CommandAddProject command = new CommandAddProject(commandString);
        try {
            command.execute(state);
            fail(EXCEPTION_SHOULD_BE_THROWN);
        } catch (Exception e) {
            Projects projectHandler = state.getProjectHandler();
            assertEquals(1, projectHandler.listExistingProjects().size());
            assertEquals(projectName, projectHandler.listExistingProjects().get(0));
        }
    }

}
