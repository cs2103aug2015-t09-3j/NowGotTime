package object;

import java.util.Stack;

import command.Command;
import command.CommandViewDate;
import command.Displayable;
import command.Revertible;
import javafx.scene.layout.GridPane;
import project.Projects;
import service.ServiceHandler;

public class State {

    private ServiceHandler serviceHandler = null;
    private Projects projectHandler = null;

    private Stack<Revertible> undoStack;
    private Stack<Revertible> redoStack;
    private Displayable currentDisplay;
    
    private boolean textMode;
    
    public boolean isTextMode() {
        return textMode;
    }

    public State(boolean textMode) {
        serviceHandler = new ServiceHandler();
        projectHandler = new Projects();
        undoStack = new Stack<Revertible>();
        redoStack = new Stack<Revertible>();
        currentDisplay = new CommandViewDate();
        this.textMode = textMode;
    }
    
    public State() {
        this(true);
    }
    
    
    
    public ServiceHandler getServiceHandler() {
        return serviceHandler;
    }

    public Projects getProjectHandler() {
        return projectHandler;
    }
    
    public Revertible getUndoCommand() {
        if (undoStack.isEmpty()) {
            return null;
        } else {
            return undoStack.pop();
        }
    }
    
    public Revertible getRedoCommand() {
        if (redoStack.isEmpty()) {
            return null;
        } else {
            return undoStack.pop();
        }
    }
    
    public void addUndoCommand(Revertible command) {
        undoStack.push(command);
    }
    
    public void addRedoCommand(Revertible command) {
        redoStack.push(command);
    }
    
    public void clearRedoStack() {
        redoStack.clear();
    }
    
    public void updateDisplay(Displayable newDisplay) {
        if (newDisplay != null) {
            currentDisplay = newDisplay;
        }
    }
    
    public Displayable getCurrentDisplay() {
        return currentDisplay;
    }
    
    public boolean showCurrentDisplay(GridPane displayBox) {
        if (currentDisplay != null) {
            try {
                ((Command)currentDisplay).execute(this);
            } catch (Exception e) {
                return false;
            }
            currentDisplay.display(displayBox);
            return true;
        } else {
            return false;
        }
    }

}
