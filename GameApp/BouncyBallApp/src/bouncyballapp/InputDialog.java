package bouncyballapp;

import javax.swing.GroupLayout.Alignment;

import javafx.stage.*; 
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class InputDialog {
    Button btn;
    Label lbl;
    TextField field;
    public InputDialog(final Stage stg, String message) {
        btn = new Button();
        lbl = new Label(message); 
        field = new TextField();
        
        Stage newStage = new Stage();
        
        Scene newScene = new Scene(Utility.root, 300, 200);
        
        final Stage stage = new Stage();
        //Initialize the Stage with type of modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Set the owner of the Stage 
        stage.initOwner(stg);
        stage.setTitle("Top Stage With Modality");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 200, Color.ANTIQUEWHITE);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
             stage.hide();
            }
        });
        
        lbl.setTextAlignment(TextAlignment.CENTER);
        lbl.setLayoutX(scene.getWidth() * 0.1);
        lbl.setLayoutY(scene.getHeight() * 0.5);
        btn.setLayoutX(scene.getWidth() * 0.5);
        btn.setLayoutY(scene.getHeight() * 0.75);
        btn.setText("OK");
       
        root.getChildren().add(lbl);
        root.getChildren().add(field);
        root.getChildren().add(btn);   
        stage.setScene(scene);        
        stage.show();
    }
    
    public String getInput()
    {
      return lbl.getText();
    }
}