//@@author A0126509E

package command;

import java.util.ArrayList;
import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import object.Event;
import object.State;
import project.Projects;
import ui.GUI;

public class CommandViewProjectName implements CommandView {

    public CommandViewProjectName(String args) {
        if (Parser.matches(args, Parser.PATTERN_NAME)) {
            Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_NAME);
            projectName = matcher.group(Parser.TAG_NAME);
        } else {
            projectName = args;
        }
    }
    
    private String projectName = null;
    private double projectProgress;
    private ArrayList<Event> projectEvents;

    public String getProjectName() {
        return projectName;
    }
    
    @Override
    public String execute(State state) throws Exception {
        Projects projectHandler = state.getProjectHandler();
        projectEvents = projectHandler.viewEventProgressTimeline(projectName);
        if (projectEvents == null) {
            throw new Exception(String.format(CommonHelper.ERROR_PROJECT_NOT_FOUND, projectName));
        }
        projectProgress = projectHandler.progressBar(projectName);
        return CommonHelper.SUCCESS_EXECUTED;
    }

    @Override
    public void display(GridPane displayBox) {
        displayBox.getChildren().clear();
        
        String previousDate = null;
        
        int rowIndex = 0;
        int listNumber = 0;
        HBox title = new HBox(
                GUI.getText(projectName + " (" + String.valueOf(projectProgress)+"%)",
                        Color.ORANGE, GUI.FONT_SIZE_HEADER));
        
        title.setAlignment(Pos.CENTER);
        
        displayBox.add(title, 0, rowIndex++, 5, 1);
        
        for (Event item : projectEvents) {
            listNumber++;
            String date = item.getStartDateString();
            
            if (!date.equals(previousDate)) {
                Separator separator = new Separator();
                rowIndex++;
                rowIndex++;
                
                Text dateString = GUI.getText(date, Color.BLACK, GUI.FONT_SIZE_TITLE);
                
                displayBox.add(dateString, 1, rowIndex++, 5, 1);
                displayBox.add(separator, 0, rowIndex++, 5, 1);
            }
            
            Text markText;
            
            if (item.getDone()) {
                markText = GUI.getText(GUI.TEXT_CHECK, 
                        Color.GREEN, GUI.FONT_SIZE_TITLE);
            } else {
                markText = GUI.getText(GUI.TEXT_BOX, 
                        Color.GREY, GUI.FONT_SIZE_TITLE);
            }
            
            Text nameText;
            Text additionalText = null;
            
            if (!item.getAdditionalInfo().isEmpty()) {
                nameText = GUI.getText(item.getName(),
                        Color.BLACK, GUI.FONT_SIZE_TEXT);
                additionalText = GUI.getText(item.getAdditionalInfo(), 
                        Color.GREY, GUI.FONT_SIZE_TEXT);
                
            } else {
                nameText = GUI.getText(item.getName(), 
                        Color.GREY, GUI.FONT_SIZE_TEXT);
            }
            
            Text numberingText = GUI.getText(String.valueOf(listNumber),
                    Color.BLACK, GUI.FONT_SIZE_TEXT);
            Text timeText = GUI.getText(item.getTimeStringOn(date),
                    Color.GREY, GUI.FONT_SIZE_TEXT);

            displayBox.add(numberingText, 0, rowIndex);
            displayBox.add(markText, 1, rowIndex);
            displayBox.add(nameText, 2, rowIndex);
            displayBox.add(timeText, 3, rowIndex);
            
            rowIndex++;
            
            if (additionalText != null) {
                displayBox.add(additionalText, 2, rowIndex);
                rowIndex++;
            }
            
            previousDate = date;
        }
        
    }
    
    @Override
    public Displayable getDisplayable() {
        return this;
    }

}
