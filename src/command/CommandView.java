package command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Stack;

import helper.CalendarHelper;
import helper.CommonHelper;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import object.Event;
import object.Item;
import object.Todo;
import project.ProjectHandler;
import service.ServiceHandler;
import ui.GUI;

public class CommandView extends Command {

    public static final String KEYWORD = "view";
    
    String dateString;
    
    /**
     * Parses the arguments for view command
     */
    public CommandView(String args) throws Exception {
        this.setRequireConfirmation(false);
        this.setRevertible(false);
        
        dateString = args.trim();
        mergedList = new ArrayList<Item>();
        
        if (CalendarHelper.getCalendarStringType(dateString) != CalendarHelper.TYPE_DATE) {
            throw new Exception(CommonHelper.ERROR_INVALID_DATE_TIME);
        }
    }
    
    public CommandView(Calendar date) {
        dateString = CalendarHelper.getDateString(date);
        mergedList = new ArrayList<Item>();
    }
    
    public void display(ServiceHandler serviceHandler, ProjectHandler projectHandler, GridPane displayBox) {
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
                    dateString = GUI.getText("Task", Color.BLACK, 16);
                }
                else {
                    dateString = GUI.getText(date, Color.BLACK, 16);
                }
                rowIndex++;
                rowIndex++;
                displayBox.add(dateString, 1, rowIndex++, 5, 1);
                displayBox.add(new Separator(), 0, rowIndex++, 5, 1);
            }
            
            Text markText = GUI.getText("\u2714", Color.GREEN, 16);
            Text nameText = GUI.getText(item.getName(), Color.GREY, 14);
            
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

            Text timeText = GUI.getText(timeString, Color.GREY, 14);

            displayBox.add(markText, 1, rowIndex);
            displayBox.add(nameText, 2, rowIndex);
            displayBox.add(timeText, 3, rowIndex);
            
            rowIndex++;
            previousDate = date;
        }
    }
    
    ArrayList<Item> mergedList;
    
    /**
     * Executes view command, returns feedback string
     */
    @Override
    public String execute(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        ArrayList<Event> eventList = serviceHandler.viewEventByDate(dateString);
        ArrayList<Todo> todoList = serviceHandler.viewTaskByDate(dateString);
        ArrayList<Todo> floatingTodoList = serviceHandler.viewTaskNoDate();
        
        mergedList.addAll(eventList);
        mergedList.addAll(todoList);
        mergedList.addAll(floatingTodoList);
        
        return "Got it!";
                
        /*
        
        StringBuilder feedback = new StringBuilder();
        feedback.append("NowGotTime on " + dateString + "\n");
        feedback.append("----------------------------------------\n");
        feedback.append("--Event\n");
        feedback.append(CommonHelper.getFormattedEventList(eventList, dateString));
        feedback.append("----------------------------------------\n");
        feedback.append("--Todo\n");
        feedback.append(CommonHelper.getFormattedTodoList(todoList, dateString));
        feedback.append("----------------------------------------\n");
        feedback.append("--Floating Todo\n");
        feedback.append(CommonHelper.getFormattedTodoList(floatingTodoList, dateString));
        feedback.append("----------------------------------------\n");
        
        return feedback.toString();
        */
    }

    @Override
    public String revert(ServiceHandler serviceHandler, ProjectHandler projectHandler, Stack<Command> historyList)
            throws Exception {
        // view command cannot be reverted
        return null;
    }


}
