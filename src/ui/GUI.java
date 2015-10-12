package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.ProjectHandler;
import service.ServiceHandler;

import java.util.Stack;

import command.Command;
import helper.CommonHelper;

public class GUI extends Application {
    
    private int GUI_HEIGHT = 300;
    private int GUI_WIDTH = 400; 
    private Insets BOX_PADDING = new Insets(10, 10, 10, 10);
    Text feedback;
    TextField prompt;
    
    private ServiceHandler serviceHandler = null;
    private ProjectHandler projectHandler = null;
    private Stack<Command> historyList;
    
    
    private String executeResponse(String userResponse) {
        Command command = null;
        String feedback;
        try {
            command = Command.parseCommand(userResponse);
            feedback = command.execute(serviceHandler, projectHandler, historyList);
            if (command.isRevertible()) {
                // add to history list if project revertible
                historyList.add(command);
            }
            
        } catch (Exception e) {
            // catch error message
            feedback = e.getMessage();
        }
        
        return feedback;
    }
    
    private void displayText(String text) {
        feedback.setText(text);
    }
    
    private Scene getUserInterface() {
        
        StackPane ui = new StackPane();

        feedback = new Text("Welcome to NowGotTime");
        prompt = new TextField();
        
        HBox feedbackBox = new HBox(feedback);
        HBox.setHgrow(feedback, Priority.ALWAYS);
        feedbackBox.setPadding(BOX_PADDING);
        feedbackBox.setAlignment(Pos.TOP_LEFT);
        
        HBox promptBox = new HBox(prompt);
        HBox.setHgrow(prompt, Priority.ALWAYS);
        promptBox.setPadding(BOX_PADDING);
        promptBox.setAlignment(Pos.BOTTOM_CENTER);
        
        ui.getChildren().addAll(feedbackBox, promptBox);
        
        prompt.setOnKeyPressed(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        displayText(executeResponse(prompt.getText()));
                        prompt.clear();
                    }
                }
            });
        
        return new Scene(ui, GUI_WIDTH, GUI_HEIGHT);
    }
    
    private void configureHandler() {
        serviceHandler = new ServiceHandler();
        projectHandler = new ProjectHandler();
        historyList = new Stack<Command>();
    }
    
	@Override
	public void start(Stage primaryStage) {
	    configureHandler();
	    
	    primaryStage.setTitle(CommonHelper.APP_TITLE);
	    Scene ui = getUserInterface();
	    
	    primaryStage.setScene(ui);
	    primaryStage.show();
	}


    public static void main(String[] args) {
		launch(args);
	}
}
