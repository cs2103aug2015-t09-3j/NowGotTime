package command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;

import helper.Parser;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import object.Event;
import object.Item;
import object.Todo;
import project.Projects;
import service.ServiceHandler;
import ui.GUI;

public class CommandViewDate implements CommandView {

    
    String dateString = null;
    ArrayList<Item> mergedList = new ArrayList<Item>();
    
    /**
     * Parses the arguments for view command
     */
    public CommandViewDate(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_DATE);
        dateString = matcher.group(Parser.TAG_DATE).trim();
    }

    /**
     * Executes view command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, Projects projectHandler, Stack<Revertible> historyList)
            throws Exception {
        
        mergedList = serviceHandler.viewMultipleDays(dateString);
        return "Got it!";
    }
    
    @Override
    public void display(GridPane displayBox) {
        // TODO Refactor this messy code
        displayBox.getChildren().clear();
        
        Collections.sort(mergedList);
        
        String previousDate = dateString;
        
        int rowIndex = 0;
        
        rowIndex++;
        rowIndex++;
        Text dateText = GUI.getText((dateString.equals("01 Jan 0001") ? "Task" : dateString), Color.BLACK, 16);
        displayBox.add(dateText, 1, rowIndex++, 5, 1);
        displayBox.add(new Separator(), 0, rowIndex++, 5, 1);
        
        for (Item item : mergedList) {
            String date, time;
            if (item instanceof Event) {
                date = ((Event)item).getStartDateString();
                time = ((Event)item).getStartTimeString();
            
                if (!date.equals(dateString)) {
                    date = dateString;
                    time = "00:00";
                }
                
            }
            else {
                date = ((Todo)item).getDeadlineDateString();
                time = ((Todo)item).getDeadlineTimeString();
            }
            
            if (!date.equals(previousDate)) {
                
                // create row for date
                Text dateString = null;
                
                if (item instanceof Todo && !((Todo)item).hasDate()) {
                    dateString = GUI.getText("Task", Color.BLACK, 18);
                }
                else {
                    dateString = GUI.getText(date, Color.BLACK, 18);
                }
                rowIndex++;
                rowIndex++;
                displayBox.add(dateString, 1, rowIndex++, 5, 1);
                displayBox.add(new Separator(), 0, rowIndex++, 5, 1);
            }
            
            Text markText = GUI.getText("\u2714", Color.GREEN, 18);
            Text nameText = GUI.getText(item.getName(), Color.GREY, 16);
            
            if (!item.getDone()) {
                markText = GUI.getText("\u2610", Color.GREY, 18);
            }
            
            String timeString = "";
            if (item instanceof Event) {
                timeString += time;
                timeString += " to ";
                
                if (((Event)item).getEndDateString().equals(date)) {
                    timeString += ((Event)item).getEndTimeString();
                }
                else {
                    timeString += "23:59";
                }
            }
            else {
                if (((Todo)item).hasDate())
                    timeString += ((Todo)item).getDeadlineTimeString();
            }

            Text timeText = GUI.getText(timeString, Color.GREY, 16);

            displayBox.add(markText, 1, rowIndex);
            displayBox.add(nameText, 2, rowIndex);
            displayBox.add(timeText, 3, rowIndex);
            
            rowIndex++;
            previousDate = date;
        }
    }

    @Override
    public Displayable getDisplayable() {
        return this;
    }

}
