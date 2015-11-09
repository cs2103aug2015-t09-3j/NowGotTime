//@@author A0126509E

package test.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import command.CommandDeleteProject;
import project.Projects;

public class CommandDeleteProjectTest extends CommandTest {

    String projectName;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        projectName = "yo la yo";
        addProject(projectName);
    }

    @Test
    public void testCanDeleteProject() {
        String projectName = "yo la yo";
        String commandString = "project \"" + projectName + "\"";
        CommandDeleteProject command = new CommandDeleteProject(commandString);
        try {
            command.execute(state);
            Projects projectHandler = state.getProjectHandler();
            assertEquals(0, projectHandler.listExistingProjects().size());
        } catch (Exception e) {
            e.printStackTrace();
            fail(EXCEPTION_SHOULD_NOT_BE_THROWN);
        }
    }
    
    @Test
    public void testCannotDeleteNonExistingProject() {
        String projectName2 = "yo la yo2";
        String commandString = "project \"" + projectName2 + "\"";
        CommandDeleteProject command = new CommandDeleteProject(commandString);
        try {
            command.execute(state);
            fail(EXCEPTION_SHOULD_BE_THROWN);
        } catch (Exception e) {
            Projects projectHandler = state.getProjectHandler();
            assertEquals(1, projectHandler.listExistingProjects().size());
        }
    }
    

}
