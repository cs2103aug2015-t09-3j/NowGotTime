//@@author A0126509E

package command;

import java.util.ArrayList;

import helper.CommonHelper;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import object.State;
import project.Projects;
import ui.GUI;

public class CommandViewProject implements CommandView {

    public CommandViewProject() {
        
    }
    
    ArrayList<String> projectList;
    ArrayList<Double> progressList;

    @Override
    public String execute(State state) throws Exception {
        Projects projectHandler = state.getProjectHandler();
        projectList = projectHandler.listExistingProjects();
        progressList = new ArrayList<Double>();
        for (String projectName : projectList) {
            progressList.add(projectHandler.progressBar(projectName));
        }
        return CommonHelper.SUCCESS_EXECUTED;
    }

    @Override
    public Displayable getDisplayable() {
        return this;
    }

    @Override
    public void display(GridPane displayBox) {
        displayBox.getChildren().clear();
        
        int rowIndex = 0;
        
        HBox title = new HBox(GUI.getText(GUI.TEXT_PROJECT, Color.PURPLE, GUI.FONT_SIZE_HEADER));
        
        displayBox.add(title, 1, rowIndex++, 5, 1);
        Separator separator = new Separator();
        displayBox.add(separator, 0, rowIndex++, 5, 1);
        
        for (int i = 0; i < projectList.size(); i++) {
            String projectName = projectList.get(i);
            double progress = progressList.get(i);
            
            Text bulletText = GUI.getText(GUI.TEXT_BULLET,
                    Color.GREY, GUI.FONT_SIZE_TITLE);
            Text nameText = GUI.getText(projectName,
                    Color.GREY, GUI.FONT_SIZE_TEXT);
            Text progressText = GUI.getText(String.valueOf(progress)+"%",
                    Color.GREEN, GUI.FONT_SIZE_TEXT);

            displayBox.add(bulletText, 1, rowIndex);
            displayBox.add(nameText, 2, rowIndex);
            displayBox.add(progressText, 3, rowIndex);
            
            rowIndex++;
            
        }

    }

}
