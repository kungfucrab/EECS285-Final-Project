package bouncyballapp;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import bouncyballapp.Crate;
import bouncyballapp.BouncyText;
import bouncyballapp.WaveText;

public class BouncyBallApp extends Application {
  
  ArrayList<PhysicalGameObject> pGameObjects = new ArrayList<PhysicalGameObject>();

  float text_size = 0;
  
  @Override
  public void start(Stage primaryStage) {
    
    Utility.root = new Group();
    Utility.scene = new Scene(Utility.root, Utility.WIDTH, Utility.HEIGHT);
    
    Utility.canvas = new Canvas(Utility.WIDTH, Utility.HEIGHT);
    Utility.gc = Utility.canvas.getGraphicsContext2D();
    
    Utility.root.getChildren().add(Utility.canvas);
    
    // Temporary menu texts...
    WaveText wt = new WaveText("Structural Showdown", 100, 100);
    BouncyText btnArcade = new BouncyText("Arcade Mode", 100, 200, 40);
    /*BouncyText btnGallery = new BouncyText("Gallery", 100, 250, 20);
    BouncyText btnCredits = new BouncyText("Credits", 100, 300, 20);
    BouncyText btnQuit = new BouncyText("Quit", 100, 350, 20);*/
    
    primaryStage.setTitle("Bouncy Ball");
    primaryStage.setFullScreen(false);
    primaryStage.setResizable(false);

    Utility.addGround(100, 20);

    //Add left and right walls so balls will not move outside the viewing area.
    Utility.addWall(0,100,1,100); //Left wall
    Utility.addWall(99,100,1,100); //Right wall

    final Timeline timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.

    
    Utility.scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if(Utility.toPosY((float)mouseEvent.getY()) > 10)
        {
          PhysicalGameObject newPGObject;
          newPGObject = new Crate(Utility.toPosX((float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), 50, 50);
          
          pGameObjects.add(newPGObject);
        }
      }
    });
    //Create an ActionEvent, on trigger it executes a world time step and moves the balls to new position 
    EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent t) {
        //Create time step. Set Iteration count 8 for velocity and 3 for positions
        Utility.world.step(1.0f/60.f, 8, 3); 
        
        Utility.gc.clearRect(0, 0, Utility.WIDTH, Utility.HEIGHT);
        SpecialText.updateSystem();
        
        for(PhysicalGameObject pgObject : pGameObjects)
        {
          pgObject.update();
        }
      }
    };

    /**
     * Set ActionEvent and duration to the KeyFrame. 
     * The ActionEvent is trigged when KeyFrame execution is over. 
     */
    KeyFrame frame = new KeyFrame(duration, ae, null,null);

    timeline.getKeyFrames().add(frame);

    //Create button to start simulation.
    final Button btn = new Button();
    btn.setLayoutX((Utility.WIDTH/2) -15);
    btn.setLayoutY((Utility.HEIGHT-30));
    btn.setText("Start");
    btn.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        timeline.playFromStart(); 
        btn.setVisible(false);
      }
    });

    Utility.root.getChildren().add(btn);
    primaryStage.setScene(Utility.scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
