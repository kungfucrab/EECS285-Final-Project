package bouncyballapp;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Point2D;
import bouncyballapp.Crate;
import bouncyballapp.BouncyText;
import bouncyballapp.WaveText;
import bouncyballapp.GameState;
import bouncyballapp.MenuState;


public class BouncyBallApp extends Application {
  
  @Override
  public void start(Stage primaryStage) {
    
    Utility.root = new Group();
    Utility.scene = new Scene(Utility.root, Utility.WIDTH, Utility.HEIGHT);
    
    //Utility.scene.setCamera(camera);
    //Group cameraGroup = new Group();
    //cameraGroup.getChildren().add(camera);
    //Utility.root.getChildren().add(cameraGroup);
    
    Utility.canvas = new Canvas(Utility.WIDTH, Utility.HEIGHT);
    Utility.gc = Utility.canvas.getGraphicsContext2D();
    
    Utility.root.getChildren().add(Utility.canvas);
    
    primaryStage.setTitle("Bouncy Ball");
    primaryStage.setFullScreen(false);
    primaryStage.setResizable(false);
    
    Utility.gameState = new MenuState();
    
    Duration oneFrameAmount = Duration.millis(1000/60);
    KeyFrame oneFrame = new KeyFrame(oneFrameAmount, 
        
        /*
         * MAIN GAME LOOP
         */
        new EventHandler() 
        {
          @Override
          public void handle(Event arg0)
          {
            Utility.gc.clearRect(0, 0, Utility.WIDTH, Utility.HEIGHT);
            
            Utility.gameState.update();
            SpecialText.updateSystem();
          }
        });
    
    TimelineBuilder.create()
      .cycleCount(Animation.INDEFINITE)
      .keyFrames(oneFrame)
      .build()
      .play();

    
    Utility.scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
    {
      @Override
      public void handle(MouseEvent mouseEvent) 
      {
        Utility.fireClickResponders(mouseEvent);
      }
    });
    
    Utility.scene.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Utility.mousePosition = new Point2D(mouseEvent.getX(), mouseEvent.getY());
      }
    });

    primaryStage.setScene(Utility.scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
