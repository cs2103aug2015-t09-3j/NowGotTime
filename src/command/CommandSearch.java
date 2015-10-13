package command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.ProjectHandler;
import service.ServiceHandler;
import ui.GUI;
import ui.Main;
import helper.CommonHelper;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import object.Event;
import object.Item;
import object.Todo;

public class CommandSearch extends Command {
    
    private static final String PATTERN_SEARCH_BY_KEYWORD = "\\s*\"(?<name>.+)\"\\s*";
    
    private static final Pattern REGEX_SEARCH_BY_KEYWORD = Pattern.compile(PATTERN_SEARCH_BY_KEYWORD);

    private static String parseKeyword(String args) {
        Matcher deleteMatcher = REGEX_SEARCH_BY_KEYWORD.matcher(args);
        if (deleteMatcher.matches()) {
            String name = deleteMatcher.group(CommonHelper.FIELD_NAME);
            
            return name;
        }
        else {
            return null;
        }
    }
    
    public CommandSearch(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(false);
        keyword = parseKeyword(args);
        
        if (keyword == null) {
            throw new Exception(CommonHelper.ERROR_INVALID_ARGUMENTS);
        }
    }
    
    private String keyword;
    
    private ArrayList<Item> filteredItem;
    
    @Override
    public void display(ServiceHandler serviceHandler, ProjectHandler projectHandler, GridPane displayBox) throws Exception {
        
        displayBox.getChildren().clear();
        
        Collections.sort(filteredItem);
        
        String previousDate = null;
        
        int rowIndex = 0;
        int listNumber = 0;
        int matchesNumber = filteredItem.size();
        
        displayBox.add(GUI.getText("Found " + matchesNumber + " item(s):", Color.GREEN, 14), 0, rowIndex++, 5, 1);
        
        for (Item item : filteredItem) {
            String date;
            if (item instanceof Event) {
                date = ((Event)item).getStartDateString();
            }
            else {
                date = ((Todo)item).getDeadlineDateString();
            }
            
            if (!date.equals(previousDate)) {
                Separator separator = new Separator();
                rowIndex++;
                rowIndex++;
                // create row for date
                Text dateString = null;
                
                if (item instanceof Todo && !((Todo)item).hasDate()) {
                    dateString = GUI.getText("Task", Color.BLACK, 16);
                }
                else {
                    dateString = GUI.getText(date, Color.BLACK, 16);
                }
                displayBox.add(dateString, 1, rowIndex++, 5, 1);
                displayBox.add(separator, 0, rowIndex++, 5, 1);
            }
            listNumber++;
            
            Text numberingText = GUI.getText(String.valueOf(listNumber), Color.BLACK, 14);
            Text markText = GUI.getText("\u2714", Color.GREEN, 16);
            Text nameText = GUI.getText(item.getName(), Color.GREY, 14);
            
            String timeString = "";
            if (item instanceof Event) {
                timeString += ((Event)item).getStartTimeString();
                timeString += " to ";
                
                if (((Event)item).getEndDateString().equals(date)) {
                    timeString += ((Event)item).getEndTimeString();
                }
                else {
                    timeString += ((Event)item).getEndDateTimeString();
                }
            }
            else {
                if (((Todo)item).hasDate())
                    timeString += ((Todo)item).getDeadlineTimeString();
            }
            

            Text timeText = GUI.getText(timeString, Color.GREY, 14);

            displayBox.add(numberingText, 0, rowIndex);
            displayBox.add(markText, 1, rowIndex);
            displayBox.add(nameText, 2, rowIndex);
            displayBox.add(timeText, 3, rowIndex);
            
            rowIndex++;
            previousDate = date;
        }
    }
    
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        
        filteredItem = serviceHandler.search(keyword);
        if (Main.mode == "GUI") {
            return "Got it!";
        }
        else {
            return CommonHelper.getFormattedItemList(filteredItem);
        }
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
