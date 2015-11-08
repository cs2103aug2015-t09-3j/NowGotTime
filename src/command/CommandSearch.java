//@@author A0126509E

package command;

import java.util.ArrayList;
import java.util.regex.Matcher;

import ui.GUI;
import helper.CommonHelper;
import helper.Parser;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import object.Event;
import object.Item;
import object.State;
import object.Todo;
import service.ServiceHandler;

public class CommandSearch implements Command, Displayable {

    public static final String KEYWORD = "search";
    
    public static CommandSearch parseCommandSearch(String text) throws Exception {
        
        CommandSearch commandSearch = null;
        
        if (Parser.matches(text, Parser.PATTERN_NAME)) {
            commandSearch = new CommandSearch(text);
            
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandSearch.KEYWORD));
            
        }
        
        return commandSearch;
    }
    
    private String itemKey;
    private ArrayList<Item> filteredItem;

    public CommandSearch(String args) {
        
        if (Parser.matches(args,Parser.PATTERN_NAME)) {
            Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_NAME);
            itemKey = matcher.group(Parser.TAG_NAME);
        } else {
            itemKey = args;
        }
    }
    
    @Override
    public String execute(State state) throws Exception {
        ServiceHandler serviceHandler = state.getServiceHandler();
        
        filteredItem = serviceHandler.search(itemKey);
        if (!state.isTextMode()) {
            return CommonHelper.SUCCESS_EXECUTED;
        } else {
            return CommonHelper.getFormattedItemList(filteredItem);
        }
    }

    @Override
    public void display(GridPane displayBox) {
        
        displayBox.getChildren().clear();
        
        String previousDate = null;
        
        int rowIndex = 0;
        int listNumber = 0;
        int matchesNumber = filteredItem.size();
        
        displayBox.add(
                GUI.getText(String.format(CommonHelper.FORMAT_SEARCH, matchesNumber),
                Color.GREEN, GUI.FONT_SIZE_TEXT), 
                    0, rowIndex++, 4, 1);
        
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
                    dateString = GUI.getText(GUI.TEXT_FLOATING_TASK,
                            Color.BLACK, GUI.FONT_SIZE_TITLE);
                }
                else {
                    dateString = GUI.getText(date,
                            Color.BLACK, GUI.FONT_SIZE_TITLE);
                }
                displayBox.add(dateString, 1, rowIndex++, 4, 1);
                displayBox.add(separator, 0, rowIndex++, 4, 1);
            }
            listNumber++;
            
            Text numberingText = GUI.getText(String.valueOf(listNumber),
                    Color.BLACK, GUI.FONT_SIZE_TEXT);
            Text markText;
            
            if (item.getDone()) {
                markText = GUI.getText(GUI.TEXT_CHECK, 
                        Color.GREEN, GUI.FONT_SIZE_TITLE);
            } else {
                markText = GUI.getText(GUI.TEXT_BOX, 
                        Color.GREY, GUI.FONT_SIZE_TITLE);
            }
            
            Text nameText = GUI.getText(item.getName(),
                    Color.GREY, GUI.FONT_SIZE_TEXT);
            
            // get time string for this item
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
            

            Text timeText = GUI.getText(timeString,
                    Color.GREY, GUI.FONT_SIZE_TEXT);
            
            displayBox.add(numberingText, 0, rowIndex);
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
