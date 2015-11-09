package test.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import command.CommandEditProject;
import project.Projects;

public class CommandEditProjectTest extends CommandTest {

    String projectName;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        projectName = "yo la yo";
        addProject(projectName);
    }

    @Test
    public void testCanEditProject() {
        String projectName2 = "yo la";
        String commandString = "project \"" + projectName + "\" name \"" + projectName2 + "\"";
        CommandEditProject command = new CommandEditProject(commandString);
        try {
            command.execute(state);
            Projects projectHandler = state.getProjectHandler();
            assertEquals(1, projectHandler.listExistingProjects().size());
            assertEquals(projectName2, projectHandler.listExistingProjects().get(0));
        } catch (Exception e) {
            e.printStackTrace();
            fail(EXCEPTION_SHOULD_NOT_BE_THROWN);
        }
    }
    
    @Test
    public void testCannotEditToAnExistingProjectName() {
        String projectName2 = "yo la";
        addProject(projectName2);
        String commandString = "project \"" + projectName + "\" name \"" + projectName2 + "\"";
        CommandEditProject command = new CommandEditProject(commandString);
        try {
            command.execute(state);
            fail(EXCEPTION_SHOULD_BE_THROWN);
        } catch (Exception e) {
            Projects projectHandler = state.getProjectHandler();
            assertEquals(2, projectHandler.listExistingProjects().size());
        }
    }

}
