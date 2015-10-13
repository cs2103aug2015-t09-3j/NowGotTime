package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
import command.CommandSearch;

public class GUI extends Application {
    
    private String GUI_TITLE = "NowGotTime";
    private int GUI_HEIGHT = 500;
    private int GUI_WIDTH = 500; 
    private Insets BOX_PADDING = new Insets(10, 10, 10, 10);
    HBox statusBox;
    GridPane displayBox;
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
            prompt.clear();
            if (command instanceof CommandSearch) {
                ((CommandSearch)command).display(displayBox);
            }
            
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
    
    private static Font getFont(int size) {
        return Font.font("Lucida Grande", FontWeight.NORMAL, size);
    }
    
    public static Text getText(String string, Color color, int size) {
        Text text = new Text(string);
        text.setFill(color);
        text.setFont(getFont(size));
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
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

    private ColumnConstraints getColumn(double percentage) {

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(percentage);
        
        return column;
    }
    
    private GridPane getDisplayBox() {
        
        GridPane displayBox = new GridPane();
        displayBox.setVgap(3);
        displayBox.setStyle("-fx-background-color: white; -fx-background-radius: 3;");
        displayBox.setPadding(BOX_PADDING);
        displayBox.setMinHeight(GUI_HEIGHT-130);

        displayBox.getColumnConstraints().add(getColumn(7.0));
        displayBox.getColumnConstraints().add(getColumn(5.0));
        displayBox.getColumnConstraints().add(getColumn(40.0));
        displayBox.getColumnConstraints().add(getColumn(48.0));
        displayBox.getColumnConstraints().add(getColumn(1.0));
        
        return displayBox;
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
        HBox promptBox = getPromptBox();
        
        displayBox = getDisplayBox();
        statusBox = getStatusBox();

        // add enter key handler;
        addEnterHandler(prompt);
        
        // setup input output box
        VBox ioBox = new VBox();
        ioBox.setAlignment(Pos.BOTTOM_CENTER);
        
        // setup display box
        VBox upperBox = new VBox();
        upperBox.setPadding(new Insets(0, 0, 5, 0));
        
        // make a scroll pane for display box
        ScrollPane scrollBox = new ScrollPane();
        scrollBox.setContent(displayBox);
        scrollBox.fitToHeightProperty();
        scrollBox.setPrefSize(200, GUI_HEIGHT-128);
        scrollBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollBox.setFitToWidth(true);
        scrollBox.setStyle("-fx-background-radius: 4;");
        
        // attach everything
        ioBox.getChildren().addAll(statusBox, promptBox);
        upperBox.getChildren().addAll(titleBox, scrollBox);
        ui.getChildren().addAll(upperBox, ioBox);
        ioBox.setMouseTransparent(true);
        
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
