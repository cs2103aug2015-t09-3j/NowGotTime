package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import project.ProjectHandler;
import service.ServiceHandler;

import java.util.Stack;

import command.Command;

public class GUI extends Application {
    
    private String GUI_TITLE = "NowGotTime";
    private int GUI_HEIGHT = 400;
    private int GUI_WIDTH = 500; 
    private Insets BOX_PADDING = new Insets(10, 10, 10, 10);
    HBox statusBox;
    Text feedback;
    Text status;
    Text title;
    TextField prompt;
    
    private ServiceHandler serviceHandler = null;
    private ProjectHandler projectHandler = null;
    private Stack<Command> historyList;
    
    private static String CSS_SUCCESS = "-fx-background-color: #5cb85c; -fx-background-radius: 3;";
    private static String CSS_ERROR = "-fx-background-color: #d9534f; -fx-background-radius: 3;";
    private static String CSS_WARNING = "-fx-background-color: #f0ad4e; -fx-background-radius: 3;";
    
    
    private String executeResponse(String userResponse) {
        Command command = null;
        String feedback;
        try {
            command = Command.parseCommand(userResponse);
            feedback = command.execute(serviceHandler, projectHandler, historyList);
            statusBox.setStyle(CSS_SUCCESS);
            if (command.isRevertible()) {
                // add to history list if project revertible
                historyList.add(command);
            }
            
        } catch (Exception e) {
            // catch error message
            statusBox.setStyle(CSS_ERROR);
            feedback = e.getMessage();
        }
        
        return feedback;
    }
    
    private void displayText(Text field, String text) {
        field.setText(text);
    }
    
    private Font getFont(int size) {
        return Font.font("Lucida Grande", FontWeight.NORMAL, size);
    }
    
    private HBox getTitleBox() {
        title = new Text("Welcome to NowGotTime");
        title.setFill(Color.WHITE);
        title.setFont(getFont(20));
        title.setTextAlignment(TextAlignment.CENTER);
        
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.setPadding(new Insets(0, 0, 5, 0));
        
        return titleBox;
    }
    
    private HBox getFeedbackBox() {
        feedback = new Text("");
        feedback.setFill(Color.GRAY);
        feedback.setFont(getFont(20));
        feedback.setTextAlignment(TextAlignment.CENTER);
        
        HBox feedbackBox = new HBox(feedback);
        feedbackBox.setAlignment(Pos.TOP_CENTER);
        feedbackBox.setStyle("-fx-background-color: white; -fx-background-radius: 3;");
        feedbackBox.setPadding(new Insets(5, 0, 5, 0));
        feedbackBox.setMinHeight(GUI_HEIGHT-130);
        
        return feedbackBox;
    }
    
    private HBox getPromptBox() {
        prompt = new TextField();

        HBox promptBox = new HBox(prompt);
        HBox.setHgrow(prompt, Priority.ALWAYS);
        promptBox.setAlignment(Pos.BOTTOM_CENTER);
        promptBox.setPadding(new Insets(5, 0, 5, 0));
        
        return promptBox;
    }
    
    private HBox getStatusBox() {
        status = new Text("");
        status.setFont(getFont(14));
        status.setFill(Color.WHITE);
        status.setTextAlignment(TextAlignment.CENTER);
        
        HBox statusBox = new HBox(status);
        statusBox.setAlignment(Pos.BOTTOM_CENTER);
        statusBox.setPadding(new Insets(5, 0, 5, 0));
        statusBox.setStyle(CSS_WARNING);
        
        return statusBox;
    }
    
    private void addEnterHandler(Node node) {
        node.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    displayText(status, executeResponse(prompt.getText()));
                    prompt.clear();
                }
            }
        });
    }
    
    private Scene getUserInterface() {
        
        // setup ui
        StackPane ui = new StackPane();
        ui.setPadding(BOX_PADDING);
        ui.setStyle("-fx-background-color: #333333;");

        // create ui component
        HBox titleBox = getTitleBox();
        HBox feedbackBox = getFeedbackBox();
        HBox promptBox = getPromptBox();
        statusBox = getStatusBox();

        // add enter key handler;
        addEnterHandler(prompt);
        
        // setup input output box
        VBox ioBox = new VBox();
        ioBox.setAlignment(Pos.BOTTOM_CENTER);
        
        // setup display box
        VBox displayBox = new VBox();
        displayBox.setPadding(new Insets(0, 0, 5, 0));
        
        // attach everything
        ioBox.getChildren().addAll(statusBox, promptBox);
        displayBox.getChildren().addAll(titleBox, feedbackBox);
        ui.getChildren().addAll(displayBox, ioBox);
        
        
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
	    
	    primaryStage.setTitle(GUI_TITLE);
	    Scene ui = getUserInterface();
	    
	    primaryStage.setScene(ui);
	    primaryStage.show();
	}


    public static void main(String[] args) {
		launch(args);
	}
}
