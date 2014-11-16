package bouncyballapp;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import bouncyballapp.Crate;
import bouncyballapp.BouncyText;
import bouncyballapp.WaveText;

public class BouncyBallApp extends Application {
  
  ArrayList<PhysicalGameObject> pGameObjects = new ArrayList<PhysicalGameObject>();
  
  float mouseX = Utility.WIDTH/2;
  float mouseY = Utility.HEIGHT/2;
  
  Circle toPlace1 = new Circle();
  Rectangle toPlace2 = new Rectangle();
  Rectangle toPlace3 = new Rectangle();
  
  Rectangle measure1 = new Rectangle();
  Rectangle measure2 = new Rectangle();
  Rectangle measure3 = new Rectangle();
  Rectangle measure4 = new Rectangle();
  Rectangle measure5 = new Rectangle();
  
  Camera camera = new ParallelCamera();
  int camX = 0;
  

  float text_size = 0;
  
  @Override
  public void start(Stage primaryStage) {
    
    
    
    Utility.root = new Group();
    Utility.scene = new Scene(Utility.root, Utility.WIDTH, Utility.HEIGHT);
    
    Utility.scene.setCamera(camera);
    Group cameraGroup = new Group();
    cameraGroup.getChildren().add(camera);
    Utility.root.getChildren().add(cameraGroup);
    
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
    
    toPlace1.setRadius(25);
    toPlace1.setFill(Color.RED);
    
    toPlace2.setWidth(10);
    toPlace2.setHeight(50);
    toPlace2.setFill(Color.GREEN);
    
    toPlace3.setWidth(50);
    toPlace3.setHeight(10);
    toPlace3.setFill(Color.YELLOW);
    
    measure1.setWidth(5);
    measure1.setHeight(50);
    measure1.setFill(Color.BLACK);
    
    measure2.setWidth(5);
    measure2.setHeight(50);
    measure2.setFill(Color.BLACK);
    
    measure3.setWidth(90);
    measure3.setHeight(2);
    measure3.setFill(Color.BLACK);
    
    measure4.setWidth(5);
    measure4.setHeight(50);
    measure4.setFill(Color.GRAY);
    
    measure5.setWidth(5);
    measure5.setHeight(50);
    measure5.setFill(Color.GRAY);
    
    
    Utility.root.getChildren().add(measure1);
    Utility.root.getChildren().add(measure2);
    Utility.root.getChildren().add(measure3);
    Utility.root.getChildren().add(measure4);
    Utility.root.getChildren().add(measure5);
    
    Utility.root.getChildren().add(toPlace1);
    Utility.root.getChildren().add(toPlace2);
    Utility.root.getChildren().add(toPlace3);

    Utility.scene.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event)
      {
        mouseX = (float)event.getX();
        mouseY = (float)event.getY();
        System.out.println(event.getX() + " " + event.getY());
      }
      
    });
    
    Utility.scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.isPrimaryButtonDown())
        {
          if(Utility.toPosY((float)mouseEvent.getY()) > 10)
          {
            PhysicalGameObject newPGObject;
            newPGObject = new Crate(Utility.toPosX((float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), 10, 50);
            
            pGameObjects.add(newPGObject);
          }
        }
        else if(mouseEvent.isSecondaryButtonDown())
        {
          PhysicalGameObject newPGObject;
          newPGObject = new Crate(Utility.toPosX((float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), 50, 10);
          
          pGameObjects.add(newPGObject);
        }
        else if(mouseEvent.isMiddleButtonDown())
        {
          PhysicalGameObject newPGObject;
          newPGObject = new Ball(Utility.toPosX((float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), 25);
          
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
        toPlace1.setLayoutX(mouseX);
        toPlace1.setLayoutY(mouseY);
        
        toPlace2.setLayoutX(mouseX - 10/2);
        toPlace2.setLayoutY(mouseY - 50/2);
        
        toPlace3.setLayoutX(mouseX - 50/2);
        toPlace3.setLayoutY(mouseY - 10/2);
        
        measure1.setLayoutX(mouseX - 45 - 5f/2);
        measure2.setLayoutX(mouseX + 45 - 5f/2);
        measure3.setLayoutX(mouseX + 0 - 90f/2);
        measure4.setLayoutX(mouseX - 25 - 5f/2);
        measure5.setLayoutX(mouseX + 25 - 5f/2);
        
        measure1.setLayoutY(mouseY + 0 - 50f/2);
        measure2.setLayoutY(mouseY + 0 - 50f/2);
        measure3.setLayoutY(mouseY - 0 - 2f/2);
        measure4.setLayoutY(mouseY + 0 - 50f/2);
        measure5.setLayoutY(mouseY + 0 - 50f/2);
        
        camera.setLayoutX(camX);
        camX++;
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
