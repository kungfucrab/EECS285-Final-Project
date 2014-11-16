package bouncyballapp;

import java.util.ArrayList;

import org.jbox2d.dynamics.BodyType;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PlayGameState extends GameState implements ClickResponder
{
  ArrayList<PhysicalGameObject> pGameObjects = new ArrayList<PhysicalGameObject>();
  
  float mouseX = Utility.WIDTH/2;
  float mouseY = Utility.HEIGHT/2;
  
  Camera camera = new ParallelCamera();
  int camX = 0;
  
  float timeLeft = 10.0f;

  float text_size = 0;
  
  int currentPlayer = 1;
  boolean canSwitch = false;
  
  boolean canShoot = true;
  
  public PlayGameState()
  {
    
    Utility.scene.setCamera(camera);
    
    Utility.RegisterForClicks(this);
    Utility.addGround(100, 20);

    //Add left and right walls so balls will not move outside the viewing area.
    //Utility.addWall(0,100,1,100); //Left wall
    //Utility.addWall(99,100,1,100); //Right wall
    
    final Timeline timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.
    
    Utility.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent t) {
        if(t.getCode() == KeyCode.SPACE && canSwitch == true)
        {
          if(currentPlayer == 1)
          {
            currentPlayer = 2;
            canSwitch = false;
            canShoot = true;
            
            camX = 1000;
          }
          if(currentPlayer == 2)
          {
            currentPlayer = 1;
            canSwitch = false;
            canShoot = true;
            
            camX = 0;
          }
        }
      }
    });
    
  }
  
  @Override
  public void onClick(MouseEvent mouseEvent)
  {
    /*if(!acceptClicks)
    {
      if(acceptEggClicks)
      {
        PhysicalGameObject newPGObject;
        newPGObject = new Egg(Utility.toPosX((float)mouseEvent.getX())
                              , Utility.toPosY((float)mouseEvent.getY())
                              , 25, "Player "+ playerNum);
        
        pGameObjects.add(newPGObject);
        
        acceptEggClicks = false;
        
        eggPlaced = true;
        
      }
      return;
    }
    
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
     //PhysicalGameObject newPGObject;
      //newPGObject = new Ball(Utility.toPosX((float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), 25);
      
      //pGameObjects.add(newPGObject);
    }*/
  }

  @Override
  void update()
  {
    //Create time step. Set Iteration count 8 for velocity and 3 for positions
    Utility.world.step(1.0f/60.f, 8, 3); 
    
    mouseX = (float) Utility.mousePosition.getX();
    mouseY = (float) Utility.mousePosition.getY();
    
    
    for(PhysicalGameObject pgObject : Utility.getPGameObjects(1))
    {
      pgObject.update();
    }
    for(PhysicalGameObject pgObject : Utility.getPGameObjects(2))
    {
      pgObject.update();
    }
    
    
    
    //Utility.gc.fillText(Float.toString(timeLeft), 20, 20);
    
    
    
    
    
    camera.setLayoutX(camX);
    //camX++;
  }

  @Override
  void cleanUp()
  {
    //TODO actually delete all of the objects
  }
  
}
