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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PlayGameState extends GameState implements ClickResponder
{
  ArrayList<PhysicalGameObject> pGameObjects = new ArrayList<PhysicalGameObject>();
  
  float mouseX = Utility.WIDTH/2;
  float mouseY = Utility.HEIGHT/2;
  
  float startDragX = -1;
  float startDragY = -1;
  
  float MAX_VELOCITY = 75;
  
  Camera camera = new ParallelCamera();
  int camX = 0;
  
  float timeLeft = 10.0f;

  float text_size = 0;
  
  int currentPlayer = 1;
  boolean canSwitch = false;
  
  boolean canShoot = true;
  
  PhysicalGameObject trackedBall = null;
  
  Line shootLine = new Line();

  
  public PlayGameState()
  {
    ArrayList<PhysicalGameObject> pgoList1 = Utility.getPGameObjects(1);
    ArrayList<PhysicalGameObject> pgoList2 = Utility.getPGameObjects(2);
    
    for(PhysicalGameObject pgo1 : pgoList1)
    {
      pgo1.getBody().setType(BodyType.DYNAMIC);
      //pgo.getBody().getFixtureList().
    }
    for(PhysicalGameObject pgo2 : pgoList2)
    {
      pgo2.getBody().setType(BodyType.DYNAMIC);
      //pgo.getBody().getFixtureList().
    }
    
    shootLine.setFill(Color.BLACK);
    shootLine.setVisible(false);
    Utility.root.getChildren().add(shootLine);
    
    Utility.scene.setCamera(camera);
    
    Utility.RegisterForClicks(this);
    Utility.addGround(100, 20);

    
    Utility.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent t) {
        //System.out.println("hi");
        if(t.getCode() == KeyCode.SPACE && canSwitch == true)
        {
          if(currentPlayer == 1)
          {
            currentPlayer = 2;
            canSwitch = false;
            canShoot = true;
            
            camX = 1000;
          }
          else if(currentPlayer == 2)
          {
            currentPlayer = 1;
            canSwitch = false;
            canShoot = true;
            
            camX = 0;
          }
          
          startDragX=-1;
          startDragY=-1;
        }
      }
    });
    
  }
  
  @Override
  public void onClick(MouseEvent mouseEvent)
  {
    if(canShoot)
    {
      if(currentPlayer == 1)
      {
        //PhysicalGameObject newPGObject;
        //newPGObject = new Ball(Utility.toPosX(0 + (float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), 30, 10, 5);
        //pGameObjects.add(newPGObject);
        
      }
      if(currentPlayer == 2)
      {
        //PhysicalGameObject newPGObject;
        //newPGObject = new Ball(Utility.toPosX(0 + (float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), -30, 10, 5);
        //pGameObjects.add(newPGObject);
      }
      
      shootLine.setStartX(mouseEvent.getX());
      shootLine.setStartY(mouseEvent.getY());
      shootLine.setEndX(mouseEvent.getX());
      shootLine.setEndY(mouseEvent.getY());
      shootLine.setVisible(true);
      
      
      startDragX = (float) mouseEvent.getX();
      startDragY = (float) mouseEvent.getY();
      //canSwitch = true;
      //canShoot = false;
    }
  }
  
  @Override
  public void onRelease(MouseEvent mouseEvent)
  {
    if(canShoot && startDragX > 0 && startDragY > 0)
    {
      float velocityX = .5f*(float) (mouseEvent.getX() - startDragX);
      float velocityY = -.5f*(float) (mouseEvent.getY() - startDragY);
      
      float vMag = (float) Math.sqrt(velocityX*velocityX + velocityY*velocityY);
      
      if(vMag > MAX_VELOCITY)
      {
        velocityX = velocityX * MAX_VELOCITY / vMag;
        velocityY = velocityY * MAX_VELOCITY / vMag;
      }
      
      if(currentPlayer == 1)
      {
        PhysicalGameObject newPGObject;
        newPGObject = new Ball(Utility.toPosX(0 + (float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), velocityX, velocityY, 5);
        pGameObjects.add(newPGObject);
        
        trackedBall = newPGObject;
        
      }
      if(currentPlayer == 2)
      {
        PhysicalGameObject newPGObject;
        newPGObject = new Ball(Utility.toPosX(0 + (float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), velocityX, velocityY, 5);
        pGameObjects.add(newPGObject);
        
        trackedBall = newPGObject;
      }
      
      canSwitch = true;
      canShoot = false;
      
      shootLine.setVisible(false);
    }
  }
  
  

  @Override
  void update()
  {
    //Create time step. Set Iteration count 8 for velocity and 3 for positions
    Utility.world.step(1.0f/60.f, 8, 3); 
    
    mouseX = (float) Utility.mousePosition.getX();
    mouseY = (float) Utility.mousePosition.getY();
    
    if(canShoot && startDragX > 0 && startDragY > 0)
    {
      float magX = (float) (mouseX - startDragX);
      float magY = (float) (mouseY - startDragY);
      
      if(canShoot && startDragX > 0 && startDragY > 0)
      {
        
        float vMag = (float) Math.sqrt(magX*magX + magY*magY);
        
        if(vMag > MAX_VELOCITY)
        {
          magX = magX * MAX_VELOCITY / vMag;
          magY = magY * MAX_VELOCITY / vMag;
        }
      }
      
      shootLine.setVisible(true);
      shootLine.setEndX(startDragX + magX);
      shootLine.setEndY(startDragY + magY);
    }
    else
    {
      shootLine.setVisible(false);
    }
    
    
    for(PhysicalGameObject pgObject : Utility.getPGameObjects(1))
    {
      pgObject.update();
    }
    for(PhysicalGameObject pgObject : Utility.getPGameObjects(2))
    {
      pgObject.update();
    }
    
    for(PhysicalGameObject pgObject : pGameObjects)
    {
      pgObject.update();
    }
    
    
    
    //Utility.gc.fillText(Float.toString(timeLeft), 20, 20);
    
    
    
    
    if(canSwitch && !canShoot)
    {
      camX = (int) (.99*camX + .01*(trackedBall.getfxShape().getLayoutX() + Utility.toPixelWidth(trackedBall.getBody().getLinearVelocity().x) - Utility.WIDTH/2));
      System.out.println("camX" + camX);
    }
    camera.setLayoutX(camX);
    
    //camX++;
  }

  @Override
  void cleanUp()
  {
    //TODO actually delete all of the objects
  }
  
}
