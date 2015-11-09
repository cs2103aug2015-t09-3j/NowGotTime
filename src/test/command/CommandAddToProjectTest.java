package test.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import command.CommandAddToProject;
import helper.CommonHelper;
import project.Projects;

public class CommandAddToProjectTest extends CommandTest {

    String projectName;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        projectName = "yo la yo";
        addProject(projectName);
    }

    @Test
    public void testCanAddEventToProject() {
        try {
            
            addEvent(EVENT1);
            
            String commandString = "\"" + EVENT1.getName() + "\" to \"" + projectName + "\"";
            
            CommandAddToProject command = new CommandAddToProject(commandString);
            command.execute(state);
            
            Projects projectHandler = state.getProjectHandler();
            
            assertEquals(1, projectHandler.listExistingProjects().size());
            assertEquals(1, projectHandler.viewEventProgressTimeline(projectName).size());
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(EXCEPTION_SHOULD_NOT_BE_THROWN);
        }
    }
    
    @Test
    public void testCannotAddEventToNonExistingProject() {
        try {
            
            addEvent(EVENT1);
            
            String commandString = "\"" + EVENT1.getName() + "\" to \"" + projectName + "5\"";
            
            CommandAddToProject command = new CommandAddToProject(commandString);
            command.execute(state);
            
            Projects projectHandler = state.getProjectHandler();
            
            assertEquals(1, projectHandler.listExistingProjects().size());
            assertEquals(0, projectHandler.viewEventProgressTimeline(projectName).size());
            
            
            
        } catch (Exception e) {
            assertEquals(String.format(CommonHelper.ERROR_PROJECT_NOT_FOUND, projectName + "5"),
                    e.getMessage());
        }
    }
    
    @Test
    public void testCannotAddTodoToProject() {
        try {
            
            addTodo(TASK1);
            
            String commandString = "\"" + TASK1.getName() + "\" to \"" + projectName + "\"";
            
            CommandAddToProject command = new CommandAddToProject(commandString);
            command.execute(state);
            
            Projects projectHandler = state.getProjectHandler();
            
            assertEquals(1, projectHandler.listExistingProjects().size());
            assertEquals(0, projectHandler.viewEventProgressTimeline(projectName).size());
            
            
            
        } catch (Exception e) {
            assertEquals(CommonHelper.ERROR_TODO_ON_PROJECT, e.getMessage());
        }
    }

}
