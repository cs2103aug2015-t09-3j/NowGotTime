//@@author A0126509E

package command;

import java.util.ArrayList;

import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import project.Projects;
import service.ServiceHandler;
import ui.GUI;

public class CommandViewProject implements CommandView {

    public CommandViewProject() {
        
    }
    
    ArrayList<String> projectList;
    ArrayList<Double> progressList;

    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Revertible mostRecent, Displayable currentDisplay)
            throws Exception {
        projectList = projectHandler.listExistingProjects();
        progressList = new ArrayList<Double>();
        for (String projectName : projectList) {
            progressList.add(projectHandler.progressBar(projectName));
        }
        return "Got it!";
    }

    @Override
    public Displayable getDisplayable() {
        return this;
    }

    @Override
    public void display(GridPane displayBox) {
        displayBox.getChildren().clear();
        
        int rowIndex = 0;
        
        HBox title = new HBox(GUI.getText("Projects", Color.PURPLE, 20));
        
        displayBox.add(title, 1, rowIndex++, 5, 1);
        Separator separator = new Separator();
        displayBox.add(separator, 0, rowIndex++, 5, 1);
        
        for (int i = 0; i < projectList.size(); i++) {
            String projectName = projectList.get(i);
            double progress = progressList.get(i);
            
            Text bulletText = GUI.getText("\u2022", Color.GREY, 18);
            Text nameText = GUI.getText(projectName, Color.GREY, 16);
            Text progressText = GUI.getText(String.valueOf(progress)+"%", Color.GREEN, 16);

            displayBox.add(bulletText, 1, rowIndex);
            displayBox.add(nameText, 2, rowIndex);
            displayBox.add(progressText, 3, rowIndex);
            
            rowIndex++;
            
        }

    }

}
