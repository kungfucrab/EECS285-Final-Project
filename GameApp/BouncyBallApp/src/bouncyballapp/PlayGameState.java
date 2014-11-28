package bouncyballapp;

import java.util.ArrayList;
import java.util.Iterator;

import org.jbox2d.dynamics.BodyType;

import GameServer.ServerHelper;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PlayGameState extends GameState implements ClickResponder
{
  ArrayList<PhysicalGameObject> pGameObjects = new ArrayList<PhysicalGameObject>();
  
  float mouseX = Utility.WIDTH/2;
  float mouseY = Utility.HEIGHT/2;
  
  float startDragX = -1;
  float startDragY = -1;
  
  float MAX_VELOCITY = 500;
  
  Camera camera = new ParallelCamera();
  int camX = 0;
  
  float timeLeft = 10.0f;

  float text_size = 0;
  
  int currentPlayer = 1;
  boolean canSwitch = false;
  
  boolean canShoot = true;
  
  PhysicalGameObject trackedBall = null;
  
  Line shootLine = new Line();
  
  Text text = new Text(20, 40, "JavaFX Scene");
  Font font = new Font(20);
  
  int p1EggsLeft = 3;
  int p2EggsLeft = 3;
  
  private int lastDestroyed = 0;
  
  Rectangle lava = new Rectangle();
  
  boolean gameOver = false;
  
  boolean active = true;
  
  boolean updatedScore = false;
  
  public PlayGameState()
  {
    ArrayList<PhysicalGameObject> pgoList1 = Utility.getPGameObjects(1);
    ArrayList<PhysicalGameObject> pgoList2 = Utility.getPGameObjects(2);
    
    for(PhysicalGameObject pgo1 : pgoList1)
    {
      pgo1.getBody().setType(BodyType.DYNAMIC);
      
      pGameObjects.add(pgo1);
    }
    for(PhysicalGameObject pgo2 : pgoList2)
    {
      pgo2.getBody().setType(BodyType.DYNAMIC);
      
      pGameObjects.add(pgo2);
    }
    
    shootLine.setFill(Color.BLACK);
    shootLine.setVisible(false);
    Utility.root.getChildren().add(shootLine);
    
    Utility.scene.setCamera(camera);
    
    Utility.RegisterForClicks(this);
    Utility.addGround(100, 20);
    
    lava.setWidth(10000);
    lava.setHeight(Utility.LAVA_HEIGHT_BASE * 2);
    lava.setFill(Color.CRIMSON);
    lava.setFill(new Color(1, 0, 0, .8));
    lava.setLayoutX(-5000);
    lava.setLayoutY(Utility.HEIGHT - Utility.LAVA_HEIGHT_BASE);
    
    Utility.root.getChildren().add(lava);
    Utility.root.getChildren().add(text);
    
    text.setFill(Color.DARKRED);
    text.setFont(font);

    
    Utility.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent t) {
        //System.out.println("hi");
        if(gameOver)
        {
          if(t.getCode() == KeyCode.SPACE && active);
          {
            System.out.println("ending game");
            Utility.transitionGameState(new MenuState());
            
            Utility.scene.setOnKeyPressed(null);
            
            active = false;
          }
        }
        else if(t.getCode() == KeyCode.SPACE && canSwitch == true)
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
        else if(t.getCode() == KeyCode.ESCAPE && active)
        {
          Utility.transitionGameState(new MenuState());
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
      float velocityX = 4*(float) (mouseEvent.getX() - startDragX);
      float velocityY = -4*(float) (mouseEvent.getY() - startDragY);
      
      float vMag = (float) Math.sqrt(velocityX*velocityX + velocityY*velocityY);
      
      if(vMag > MAX_VELOCITY)
      {
        velocityX = velocityX * MAX_VELOCITY / vMag;
        velocityY = velocityY * MAX_VELOCITY / vMag;
      }
      
      if(currentPlayer == 1)
      {
        PhysicalGameObject newPGObject;
        newPGObject = new Ball(Utility.toPosX(0 + startDragX), Utility.toPosY(startDragY), velocityX, velocityY, 10);
        pGameObjects.add(newPGObject);
        
        trackedBall = newPGObject;
        
      }
      if(currentPlayer == 2)
      {
        PhysicalGameObject newPGObject;
        newPGObject = new Ball(Utility.toPosX(0 + startDragX), Utility.toPosY(startDragY), velocityX, velocityY, 10);
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
    // Lave height.
    double lava_offset = Math.sin(Utility.lava_animation_frame) * Utility.LAVA_AMPLITUDE;
    Utility.lava_animation_frame += Utility.LAVA_ANIMATION_SPEED;
    if(Utility.lava_animation_frame > 1000000)
      Utility.lava_animation_frame = 0;
    
    lava.setLayoutY(Utility.HEIGHT - Utility.LAVA_HEIGHT_BASE + lava_offset);
    
    if(gameOver)
    {
      if(p1EggsLeft == p2EggsLeft)
      {
        text.setText("Tie game\nPress Space to go to main menu");
        
        if(!updatedScore)
        {
          // Ties result in no change of record for either player.
        }
      }
      else if(p2EggsLeft == 0)
      {
        text.setText("Player 1 wins\nPress Space to go to main menu");
        
        if(!updatedScore)
        {
        	//give player 2 a loss.
        	ServerHelper.updateUserScore(Utility.player2Username, -1);
        	//player 1 win
         	ServerHelper.updateUserScore(Utility.player1Username, 1);
         	
            //TODO: update tower score
        }
      }
      else if(p1EggsLeft == 0)
      {
        text.setText("Player 2 wins\nPress Space to go to main menu");
        
        if(!updatedScore)
        {
          //player 2 win
          ServerHelper.updateUserScore(Utility.player2Username, 1);
          //player 1 a loss.
          ServerHelper.updateUserScore(Utility.player1Username, -1);
          
          //TODO: update tower score
        }
      }
      
      //TODO: UPDATE SCORES HERE
      updatedScore = true;
      
      return;
    }
    
    //Create time step. Set Iteration count 8 for velocity and 3 for positions
    Utility.world.step(1.0f/60.f, 8, 3); 
    
    
    
    mouseX = (float) Utility.mousePosition.getX();
    mouseY = (float) Utility.mousePosition.getY();
    
    if(canShoot && startDragX > 0 && startDragY > 0)
    {
      float magX = 4*(float) (mouseX - startDragX);
      float magY = -4*(float) (mouseY - startDragY);
      
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
      shootLine.setEndX(startDragX + magX/4);
      shootLine.setEndY(startDragY - magY/4);
    }
    else
    {
      shootLine.setVisible(false);
    }
    
    int eggCount1 = 0;
    Iterator<PhysicalGameObject> itr1 = Utility.getPGameObjects(1).iterator();
    while(itr1.hasNext())
    {
      PhysicalGameObject pgObject = itr1.next();
      
      pgObject.update();
      
      if(pgObject instanceof Egg)
      {
        if(!((Egg) pgObject).getIsDestroyed())
        {
          eggCount1++;
        }
        else
        {
          Utility.root.getChildren().remove(pgObject);
          Utility.world.destroyBody(pgObject.getBody());
          
          pgObject.deleteComponents();
          
          itr1.remove();
          
          System.out.println("Player 1 egg destroyed!");
          
        }
      }
    }
    if(eggCount1 == 0)
    {
      gameOver = true;
    }
    
    int eggCount2 = 0;
    Iterator<PhysicalGameObject> itr2 = Utility.getPGameObjects(2).iterator();
    while(itr2.hasNext())
    {
      PhysicalGameObject pgObject = itr2.next();
      
      pgObject.update();
      
      if(pgObject instanceof Egg)
      {
        if(!((Egg) pgObject).getIsDestroyed())
        {
          eggCount2++;
        }
        else
        {
          
          Utility.root.getChildren().remove(pgObject);
          Utility.world.destroyBody(pgObject.getBody());
          
          pgObject.deleteComponents();
          
          itr2.remove();
          
          System.out.println("Player 2 egg destroyed!");
          
          
        }
      }
    }
    
    if(eggCount2 == 0)
    {
      gameOver = true;
    }
    
    if(p1EggsLeft != eggCount1 || p2EggsLeft != eggCount2)
    {
      lastDestroyed = 0;
    }
    p1EggsLeft = eggCount1;
    p2EggsLeft = eggCount2;
    
    for(PhysicalGameObject pgObject : pGameObjects)
    {
      pgObject.update();
    }
    
    
    text.setText("Player 1: " + p1EggsLeft + " eggs remaining\n"
                +"Player 2: " + p2EggsLeft + " eggs remaining");
    
    if(canSwitch && !canShoot)
    {
      camX = (int) (.99*camX + .01*(trackedBall.getfxShape().getLayoutX() + Utility.toPixelWidth(trackedBall.getBody().getLinearVelocity().x) - Utility.WIDTH/2));
    }
    camera.setLayoutX(camX);
    text.setLayoutX(camX + 20);
  }

  @Override
  void cleanUp()
  {
    Utility.UnregisterForClicks(this);
    
    Utility.destroyAllObjects(pGameObjects);
    //Utility.destroyAllObjects(Utility.getPGameObjects(1));
    //Utility.destroyAllObjects(Utility.getPGameObjects(2));
    
    pGameObjects.clear();
    Utility.getPGameObjects(1).clear();
    Utility.getPGameObjects(2).clear();
    
    Utility.root.getChildren().remove(lava);
    Utility.root.getChildren().remove(text);
    
    
    
    //TODO actually delete all of the objects
  }
  
}
