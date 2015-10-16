package command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;

import project.ProjectHandler;
import service.ServiceHandler;
import ui.GUI;
import ui.Main;
import helper.CommonHelper;
import helper.Parser;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import object.Event;
import object.Item;
import object.Todo;

public class CommandSearch implements Command {

    public static final String KEYWORD = "search";
    
    String itemKey;
    ArrayList<Item> filteredItem;

    public CommandSearch(String args) throws Exception {
        
        if (args.matches(Parser.PATTERN_NAME)) {
            Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_NAME);
            itemKey = matcher.group(Parser.TAG_NAME);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandSearch.KEYWORD));
        }
    }
    
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        
        filteredItem = serviceHandler.search(itemKey);
        if (Main.mode.equals("GUI")) {
            return "Got it!";
        } else {
            return CommonHelper.getFormattedItemList(filteredItem);
        }
    }

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
    

}
