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

public class TowerCreateState extends GameState implements ClickResponder
{
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
  
  float timeLeft = 10.0f;

  float text_size = 0;
  
  boolean acceptClicks = true;
  boolean acceptEggClicks = false;
  
  int playerNum = 1;
  
  boolean eggPlaced = false;
  
  public TowerCreateState(int inPlayerNum)
  {
    this.playerNum = inPlayerNum;
    
    Utility.scene.setCamera(camera);
    
    if(playerNum == 2)
    {  
      camX = 1000;
      
    }
    //System.out.println(camX);
    
    Utility.RegisterForClicks(this);
    Utility.addGround(100, 20);

    //Add left and right walls so balls will not move outside the viewing area.
    //Utility.addWall(0,100,1,100); //Left wall
    //Utility.addWall(99,100,1,100); //Right wall
    
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
    
    Utility.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent t) {
        if(t.getCode() == KeyCode.SPACE && timeLeft <= 0 && !eggPlaced)
        {
          acceptEggClicks = true;
        }
        else if(t.getCode() == KeyCode.SPACE && timeLeft <= 0)
        {
          if(playerNum == 1)
          {
            
            Utility.transitionGameState(new TowerCreateState(2));
          }
          if(playerNum == 2)
          {
            //TODO Go to actual game
          }
        }
      }
    });
    
  }
  
  @Override
  public void onClick(MouseEvent mouseEvent)
  {
    if(!acceptClicks)
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
    }
  }

  @Override
  void update()
  {
    //Create time step. Set Iteration count 8 for velocity and 3 for positions
    Utility.world.step(1.0f/60.f, 8, 3); 
    
    mouseX = (float) Utility.mousePosition.getX();
    mouseY = (float) Utility.mousePosition.getY();
    
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
    
    for(PhysicalGameObject pgObject : pGameObjects)
    {
      pgObject.update();
    }
    
    Utility.gc.getCanvas().setLayoutX(camX);
    
    if(timeLeft <= 0)
    {
      if(!eggPlaced)
      {
        Utility.gc.fillText("PRESS SPACE TO PLACE EGG", 20, 20);
      }
      else if(eggPlaced)
      {
        if(playerNum == 1)
        {
          Utility.gc.fillText("PRESS SPACE FOR PLAYER 2 BUILD", 20, 20);
        }
        if(playerNum == 2)
        {
          Utility.gc.fillText("PRESS SPACE TO START GAME", 20, 20);
        }
      }
      timeLeft = 0;
      acceptClicks = false;
      return;
    }
    Utility.gc.fillText(Float.toString(timeLeft), 20, 20);
    
    timeLeft -= 1.0f/60;
    if(timeLeft <= 0)
    {
      timeLeft = 0;
    }
    //Utility.gc.fillText(Float.toString(timeLeft), 20, 20);
    
    
    
    
    
    //camera.setLayoutX(camX);
    //camX++;
  }

  @Override
  void cleanUp()
  {
    ArrayList<PhysicalGameObject> pgoList = Utility.getPGameObjects(playerNum);
    pgoList.clear();
    pgoList.addAll(pGameObjects);
    
    for(PhysicalGameObject pgo : pGameObjects)
    {
      pgo.getBody().setType(BodyType.STATIC);
      //pgo.getBody().getFixtureList().
    }
    pGameObjects.clear();
  }
  
}
