//@@author A0126509E

package ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import object.State;

import command.Command;
import command.CommandUndo;
import command.Revertible;

public class GUI extends Application {
    
    private static final String GUI_TITLE          = "NowGotTime";
    private static final String GUI_FONT           = "Lucida Grande";
    public static final String TEXT_FLOATING_TASK  = "Task";
    public static final String TEXT_CHECK          = "\u2714";
    public static final String TEXT_BOX            = "\u2610";
    public static final String TEXT_BULLET         = "\u2022";
    public static final String TEXT_PROJECT        = "Projects";
    public static final String TEXT_TITLE          = "Welcome to NowGotTime!";
    public static final String TEXT_DEFAULT_STATUS = "Hi There!";
    
    private static final int GUI_HEIGHT      = 650;
    private static final int GUI_WIDTH       = 800;
    public static final int FONT_SIZE_HEADER = 24;
    public static final int FONT_SIZE_TITLE  = 22;
    public static final int FONT_SIZE_TEXT   = 20;
    
    
    
    // CSS constant
    private static String CSS_SUCCESS     = "-fx-background-color: #5cb85c; -fx-background-radius: 3;";
    private static String CSS_ERROR       = "-fx-background-color: #d9534f; -fx-background-radius: 3;";
    private static String CSS_WARNING     = "-fx-background-color: #f0ad4e; -fx-background-radius: 3;";
    private static String CSS_DISPLAY_BOX = "-fx-background-color: white; -fx-background-radius: 3;";
    private static String CSS_PROMPT_BOX  = "-fx-font-size: 18px; -fx-font-family: Lucida Grande;";
    private Insets BOX_PADDING = new Insets(10, 10, 10, 10);
    
    GridPane displayBox;
    TextField prompt;
    HBox statusBox;
    Text status;
    Text title;
    
    private State currentState;
    
    private static Font getNormalFont(int size) {
        return Font.font(GUI_FONT, FontWeight.NORMAL, size);
    }
    
    private static Font getBoldFont(int size) {
        return Font.font(GUI_FONT, FontWeight.BOLD, size);
    }
    
    public static Text getText(String string, Color color, int size) {
        Text text = new Text(string);
        text.setFill(color);
        text.setFont(getNormalFont(size));
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }
    
    public String executeResponse(String userResponse) {
        Command command = null;
        String feedback;
        
        try {
            command = Command.parseCommand(userResponse);
            feedback = command.execute(currentState);
            
            // Show on display
            if (!currentState.isTextMode()) {
                prompt.clear();
                statusBox.setStyle(CSS_SUCCESS);
                currentState.updateDisplay(command.getDisplayable());
                currentState.showCurrentDisplay(displayBox);
            }
            
            if (!(command instanceof CommandUndo)) {
                // clear redo stack when non undo command executed
                currentState.clearRedoStack();
            }
            if (command instanceof Revertible) {
                // add to history list if project revertible
                currentState.addUndoCommand((Revertible)command);
            }
            
        } catch (Exception e) {
            // catch error message
            if (!currentState.isTextMode()) {
                statusBox.setStyle(CSS_ERROR);
            }
            feedback = e.getMessage();
            e.printStackTrace();
        }
        
        return feedback;
    }
    
    
    
    private HBox getTitleBox() {
        title = new Text(TEXT_TITLE);
        title.setFill(Color.WHITE);
        title.setFont(getNormalFont(FONT_SIZE_TEXT));
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

    private void displayText(Text field, String text) {
        field.setText(text);
    }
    
    private GridPane getDisplayBox() {
        
        GridPane displayBox = new GridPane();
        displayBox.setVgap(3);
        displayBox.setStyle(CSS_DISPLAY_BOX);
        displayBox.setPadding(BOX_PADDING);
        displayBox.setMinHeight(GUI_HEIGHT-142);

        displayBox.getColumnConstraints().add(getColumn(7.0));
        displayBox.getColumnConstraints().add(getColumn(5.0));
        displayBox.getColumnConstraints().add(getColumn(50.0));
        displayBox.getColumnConstraints().add(getColumn(38.0));
        displayBox.getColumnConstraints().add(getColumn(1.0));
        
        return displayBox;
    }
    
    private HBox getPromptBox() {
        prompt = new TextField();
        prompt.setStyle(CSS_PROMPT_BOX);

        HBox promptBox = new HBox(prompt);
        HBox.setHgrow(prompt, Priority.ALWAYS);
        promptBox.setAlignment(Pos.BOTTOM_CENTER);
        promptBox.setPadding(new Insets(5, 0, 5, 0));
        
        return promptBox;
    }
    
    private HBox getStatusBox() {
        status = new Text(TEXT_DEFAULT_STATUS);
        status.setFont(getBoldFont(20));
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
    
    private void addMaxLengthHandler(TextField tf, int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
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
        addMaxLengthHandler(prompt, 80);
        
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
        scrollBox.setPrefSize(200, GUI_HEIGHT-140);
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
    
    public void initiateState(boolean textMode) {
        currentState = new State(textMode);
    }
    
    public void initiateState() {
        initiateState(false);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
	    initiateState();
	    
	    primaryStage.setTitle(GUI_TITLE);
	    
	    Scene ui = getUserInterface();
	    
	    // Show current display to displaybox
	    currentState.showCurrentDisplay(displayBox);
	    
	    primaryStage.setScene(ui);
	    primaryStage.setResizable(false);
	    primaryStage.show();
	}


    public static void main(String[] args) {
		launch(args);
	}
}
