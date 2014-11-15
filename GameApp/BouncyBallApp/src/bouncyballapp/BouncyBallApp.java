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
import javafx.stage.Stage;
import javafx.util.Duration;

public class BouncyBallApp extends Application {

  Group root = new Group(); //Create a group for holding all objects on the screen
  Scene scene = new Scene(root, Utility.WIDTH, Utility.HEIGHT);
  
  ArrayList<BuildingRect> balls = new ArrayList<BuildingRect>();

  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("Bouncy Ball");
    primaryStage.setFullScreen(false);
    primaryStage.setResizable(false);
    

    //create ball   
    //balls.add(new BouncyBall(45, 90, Utility.BALL_RADIUS, Color.RED));

    //Add ground to the application, this is where balls will land
    Utility.addGround(100, 20);
    Rectangle r = new Rectangle();
    r.setX(0);
    r.setY(Utility.toPixelPosY(10));
    r.setWidth(Utility.WIDTH);
    r.setHeight(100);
    r.setArcWidth(20);
    r.setArcHeight(20);

    //Add left and right walls so balls will not move outside the viewing area.
    Utility.addWall(0,100,1,100); //Left wall
    Utility.addWall(99,100,1,100); //Right wall

    final Timeline timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.

    
    scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if(Utility.toPosY((float)mouseEvent.getY()) > 10)
        {
          for(int i=0; i<1; i++)
          {
            BuildingRect newRect = new BuildingRect(Utility.toPosX((float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), 10, 20, Color.BLUE);
            //BouncyBall newBall = new BouncyBall(Utility.toPosX((float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), Utility.BALL_RADIUS, Color.RED);
            System.out.println(Utility.toPosX((float)mouseEvent.getX()) + " " + Utility.toPosY((float)mouseEvent.getY()));
            root.getChildren().add(newRect.node);
            System.out.println("mouse click: "+mouseEvent.getSource());

            Body body = (Body)newRect.node.getUserData();
            float xpos = Utility.toPixelPosX(body.getPosition().x);
            float ypos = Utility.toPixelPosY(body.getPosition().y);

            newRect.node.setLayoutX(xpos);
            newRect.node.setLayoutY(ypos);

            balls.add(newRect);
          }
        }
      }
    });
    //Create an ActionEvent, on trigger it executes a world time step and moves the balls to new position 
    EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent t) {
        //Create time step. Set Iteration count 8 for velocity and 3 for positions
        Utility.world.step(1.0f/60.f, 8, 3); 

        for(BuildingRect bally : balls)
        {
          Body b = (Body)bally.node.getUserData();;

          //Move balls to the new position computed by JBox2D
          //Body body = (Body)ball.node.getUserData();
          float xpos = Utility.toPixelPosX(b.getPosition().x);
          float ypos = Utility.toPixelPosY(b.getPosition().y);
          bally.node.setLayoutX(xpos);
          bally.node.setLayoutY(ypos);
          bally.node.setRotate(-180*b.getAngle()/((float) Math.PI));
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

    root.getChildren().add(r);
    root.getChildren().add(btn);
    for(BuildingRect b : balls)
    {
      root.getChildren().add(b.node);
    }
    primaryStage.setScene(scene);
    primaryStage.show();

    
  }


  public static void main(String[] args) {
    launch(args);
  }
}
