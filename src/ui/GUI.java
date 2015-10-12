package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import helper.CommonHelper;

public class GUI extends Application {
    
    private int GUI_HEIGHT = 500;
    private int GUI_WIDTH = 600; 
    private Insets BOX_PADDING = new Insets(10, 10, 10, 10);

    private Scene getUserInterface() {
        TextField prompt = new TextField();
        HBox promptBox = new HBox(prompt);
        HBox.setHgrow(prompt, Priority.ALWAYS);
        

        promptBox.setPadding(BOX_PADDING);
        promptBox.setAlignment(Pos.BOTTOM_CENTER);
        
        return new Scene(promptBox, GUI_WIDTH, GUI_HEIGHT);
    }
    
	@Override
	public void start(Stage primaryStage) {
	    primaryStage.setTitle(CommonHelper.APP_TITLE);
	    Scene ui = getUserInterface();
	    
	    primaryStage.setScene(ui);
	    primaryStage.show();
	}


    public static void main(String[] args) {
		launch(args);
	}
}
