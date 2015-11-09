//@@author A0126509E

package command;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;

import helper.CalendarHelper;
import helper.CommonHelper;
import helper.Parser;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import object.Item;
import object.State;
import service.ServiceHandler;
import ui.GUI;

public class CommandViewDate implements CommandView {

    
    private static final int TODAY_INDEX = 0;
    private static final int TASK_INDEX = 1;
    private static final String EMPTY_LIST_TEXT = "Nothing to do!";
    
    private String dateString = null;
    private ArrayList< ArrayList<Item> > mergedList;
    
    /**
     * Parses the arguments for view command
     */
    public CommandViewDate(String args) {
        Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_DATE);
        dateString = matcher.group(Parser.TAG_DATE).trim();
    }
    
    public CommandViewDate() {
        dateString = CalendarHelper.getDateString(Calendar.getInstance());
    }

    /**
     * Executes view command, returns feedback string
     */
    @Override
    public String execute(State state) throws Exception {
        ServiceHandler serviceHandler = state.getServiceHandler();
        
        mergedList = serviceHandler.viewMultipleDays(dateString);
        if (!state.isTextMode()) {
            return CommonHelper.SUCCESS_EXECUTED;
        } else {
            String formattedString = "";
            boolean isFirstList = true;
            for (ArrayList<Item> list : mergedList) {
                if (list.isEmpty()) {
                    continue;
                }
                if (isFirstList) {
                    isFirstList = false;
                } else {
                    formattedString += "\n";
                }
                formattedString += CommonHelper.getFormattedItemList(list);
            }
            return formattedString;
        }
    }
    
    @Override
    public void display(GridPane displayBox) {
        displayBox.getChildren().clear();
        
        Calendar date = null;
        try {
            date = CalendarHelper.parseDate(dateString);
        } catch (ParseException e) {
            assert(false);
        }
        
        // start from second row
        int rowIndex = 2;
        int listingNumber = 0;
        
        for (int i = 0; i < mergedList.size(); i++) {
            
            ArrayList<Item> itemList = mergedList.get(i);
            int size = itemList.size();
            String dateString = CalendarHelper.getDateString(date);
            
            // Only display if list not empty or this is today's date
            if (size > 0 || i == TODAY_INDEX) {
            
                Text dateText;
                if (i == TASK_INDEX) {
                    dateText = GUI.getText(GUI.TEXT_FLOATING_TASK, 
                            Color.BLACK, GUI.FONT_SIZE_TITLE);
                } else {
                    dateText = GUI.getText(dateString, 
                            Color.BLACK, GUI.FONT_SIZE_TITLE);
                    
                }
                
                // Add the date header to display box
                displayBox.add(dateText, 1, rowIndex++, 4, 1);
                displayBox.add(new Separator(), 0, rowIndex++, 4, 1);
                
                // Check if empty
                if (size == 0) {
                    
                    Text freeText = GUI.getText(EMPTY_LIST_TEXT, 
                            Color.GREY, GUI.FONT_SIZE_TEXT);
                    displayBox.add(freeText, 1, rowIndex++, 4, 1);
                    
                } else {
                    
                    for (Item item : itemList) {
                        listingNumber++;
                        
                        Text nameText = GUI.getText(item.getName(), 
                                Color.GREY, GUI.FONT_SIZE_TEXT);
                        Text timeText = GUI.getText(item.getTimeStringOn(dateString),
                                Color.GREY, GUI.FONT_SIZE_TEXT);
                        Text numberingText = GUI.getText(String.valueOf(listingNumber),
                                Color.BLACK, GUI.FONT_SIZE_TEXT);
                        
                        Text markText;
                        
                        if (item.getDone()) {
                            markText = GUI.getText(GUI.TEXT_CHECK, 
                                    Color.GREEN, GUI.FONT_SIZE_TITLE);
                        } else {
                            markText = GUI.getText(GUI.TEXT_BOX, 
                                    Color.GREY, GUI.FONT_SIZE_TITLE);
                        }
                        
                        displayBox.add(numberingText, 0, rowIndex);
                        displayBox.add(markText, 1, rowIndex);
                        displayBox.add(nameText, 2, rowIndex);
                        displayBox.add(timeText, 3, rowIndex);
                        rowIndex++;
                        
                    }
                }
                rowIndex++;
                rowIndex++;
            
            }
                
            if (i != TASK_INDEX) {
                date.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    @Override
    public Displayable getDisplayable() {
        return this;
    }

}
