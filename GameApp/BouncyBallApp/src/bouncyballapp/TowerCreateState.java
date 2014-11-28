package bouncyballapp;

import java.util.ArrayList;

import org.jbox2d.dynamics.BodyType;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import GameServer.*;

public class TowerCreateState extends GameState implements ClickResponder
{
  ArrayList<PhysicalGameObject> pGameObjects = new ArrayList<PhysicalGameObject>();
  
  float mouseX = Utility.WIDTH/2;
  float mouseY = Utility.HEIGHT/2;
  
  
  
  Text text = new Text(20, 40, "JavaFX Scene");
  Font font = new Font(20);
  
  public static final int RECT_BIG = 100;
  public static final int RECT_LITTLE = 20;
  
  //public static final int MIN_BLOCKS = 15;
  public static final int MAX_BLOCKS = 20;
  public static final int MAX_EGGS = 3;
  
  
  private int blocksUsed = 0;
  
  Circle toPlace1 = new Circle();
  Rectangle toPlace2 = new Rectangle();
  Rectangle toPlace3 = new Rectangle();
  
  Rectangle measure1 = new Rectangle();
  Rectangle measure2 = new Rectangle();
  Rectangle measure3 = new Rectangle();
  Rectangle measure4 = new Rectangle();
  Rectangle measure5 = new Rectangle();
  
  Rectangle lava = new Rectangle();
  
  Camera camera = new ParallelCamera();
  int camX = 0;
  
  float timeLeft = 30.0f;

  float text_size = 0;
  
  boolean acceptClicks = true;
  boolean acceptEggClicks = false;
  
  // Enter Tower name
  boolean enteringTowerName = false;
  BorderPane borderPane;
  VBox verticalBox;
  Label towerNameLabel;
  TextField towerNameField;
  Button towerNameButton;
  
  int playerNum = 1;
  
  //boolean eggPlaced = false;
  int eggsPlaced = 0;

  boolean towerGenerateFlag = false;
  
  public TowerCreateState(int inPlayerNum)
  {
    this.playerNum = inPlayerNum;
    
    Utility.scene.setCamera(camera);
    
    acceptClicks = true;
    acceptEggClicks = false;
    eggsPlaced = 0;
    
    camX = getOffset();
    
    /*if(playerNum == 2)
    {  
      camX = 1000;
      
    }*/
    //System.out.println(camX);
    
    text.setFill(Color.DARKRED);
    
    text.setFont(font);
    
    Utility.root.getChildren().add(text);
    
    
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
    
    toPlace2.setWidth(RECT_LITTLE);
    toPlace2.setHeight(RECT_BIG);
    toPlace2.setFill(Color.GREEN);
    
    toPlace3.setWidth(RECT_BIG);
    toPlace3.setHeight(RECT_LITTLE);
    toPlace3.setFill(Color.YELLOW);
    
    measure1.setWidth(5);
    measure1.setHeight(50);
    measure1.setFill(Color.BLACK);
    
    measure2.setWidth(5);
    measure2.setHeight(50);
    measure2.setFill(Color.BLACK);
    
    measure3.setWidth(190);
    measure3.setHeight(2);
    measure3.setFill(Color.BLACK);
    
    measure4.setWidth(5);
    measure4.setHeight(50);
    measure4.setFill(Color.GRAY);
    
    measure5.setWidth(5);
    measure5.setHeight(50);
    measure5.setFill(Color.GRAY);
    
    lava.setWidth(10000);
    lava.setHeight(Utility.LAVA_HEIGHT_BASE * 2);
    lava.setFill(Color.CRIMSON);
    lava.setFill(new Color(1, 0, 0, .8));
    lava.setLayoutX(-5000);
    lava.setLayoutY(Utility.HEIGHT - Utility.LAVA_HEIGHT_BASE);
    
    Utility.root.getChildren().add(measure1);
    Utility.root.getChildren().add(measure2);
    Utility.root.getChildren().add(measure3);
    Utility.root.getChildren().add(measure4);
    Utility.root.getChildren().add(measure5);
    
    Utility.root.getChildren().add(toPlace1);
    Utility.root.getChildren().add(toPlace2);
    Utility.root.getChildren().add(toPlace3);
    
    Utility.root.getChildren().add(lava);
    
    //String testString = "C 10.400082 5.015525 20 100 -4.8023908E-4;C 20.900143 5.015328 20 100 2.947971E-4;C 31.464838 5.0150113 20 100 -3.9218652E-5;C 41.99568 5.0149198 20 100 -1.3925343E-4;C 52.496597 5.0146317 20 100 2.5470337E-4;C 62.97066 5.015214 20 100 2.2745799E-4;C 73.46909 5.0168724 20 100 2.4002364E-4;C 68.09985 5.015205 20 100 -2.383481E-4;C 68.00003 11.031966 100 20 2.270792E-5;C 57.52207 11.030038 100 20 5.5674336E-5;C 47.492157 11.028845 100 20 -1.6219268E-4;C 37.199158 11.03 100 20 -4.1013987E-5;C 26.000385 11.03049 100 20 -4.508348E-5;C 15.300134 11.030571 100 20 -3.9000286E-5;C 39.709846 17.045078 20 100 -6.2660525E-5;C 39.298996 27.06011 20 100 -8.154997E-5;E 36.5 2.5049994 25;C 80.87261 7.032245 20 100 -0.0024503812;C 87.42136 2.6403217 100 20 -0.34261692;C 81.15409 1.014935 100 20 -2.4452682E-5;E 50.90244 14.530932 25;";
    //ArrayList<PhysicalGameObject> towerPGOs = Utility.parseTowerString(testString, 0, "Player 1");
    
    //System.out.println(towerPGOs.size());
    //pGameObjects.addAll(towerPGOs);
    
    Utility.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent t) {
        if(t.getCode() == KeyCode.SPACE && blocksUsed >= MAX_BLOCKS && eggsPlaced == 0)
        {
          acceptEggClicks = true;
        }
        else if(t.getCode() == KeyCode.C)
        {
          Utility.destroyAllObjects(pGameObjects);
          pGameObjects.clear();
          blocksUsed = 0;
          eggsPlaced = 0;
          
          acceptEggClicks = false;
          
          acceptClicks = true;
        }
        else if(t.getCode() == KeyCode.G)
        {
          towerGenerateFlag = true;
        }
      }
    });
    
  }
  
  @Override
  public void onClick(MouseEvent mouseEvent)
  {
    if(!acceptClicks)
    {
      if(acceptEggClicks && eggsPlaced < MAX_EGGS)
      {
        PhysicalGameObject newPGObject;
        newPGObject = new Egg(Utility.toPosX(0 + (float)mouseEvent.getX())
                              , Utility.toPosY((float)mouseEvent.getY())
                              , 25, "Player "+ playerNum);
        
        pGameObjects.add(newPGObject);
        
        eggsPlaced++;;
        
        if(eggsPlaced >= MAX_EGGS)
        {
          acceptEggClicks = false;
        }
        
      }
      return;
    }
    
    if(mouseEvent.isPrimaryButtonDown())
    {
      
      PhysicalGameObject newPGObject;
      newPGObject = new Crate(Utility.toPosX(0 + (float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), RECT_LITTLE, RECT_BIG);
      pGameObjects.add(newPGObject);
      
      blocksUsed++;
    }
    else if(mouseEvent.isSecondaryButtonDown())
    {
      PhysicalGameObject newPGObject;
      newPGObject = new Crate(Utility.toPosX(0 + (float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), RECT_BIG, RECT_LITTLE);
      pGameObjects.add(newPGObject);
      blocksUsed++;
    }
    else if(mouseEvent.isMiddleButtonDown())
    {
     //PhysicalGameObject newPGObject;
      //newPGObject = new Ball(Utility.toPosX((float)mouseEvent.getX()), Utility.toPosY((float)mouseEvent.getY()), 25);
      
      //pGameObjects.add(newPGObject);
    }
  }
  
  @Override
  public void onRelease(MouseEvent mouseEvent)
  {
    
  }

  @Override
  void update()
  {
    // Lava Height
    double lava_offset = Math.sin(Utility.lava_animation_frame) * Utility.LAVA_AMPLITUDE;
    Utility.lava_animation_frame += Utility.LAVA_ANIMATION_SPEED;
    if(Utility.lava_animation_frame > 1000000)
      Utility.lava_animation_frame = 0;
    
    lava.setLayoutY(Utility.HEIGHT - Utility.LAVA_HEIGHT_BASE + lava_offset);
    
    if(towerGenerateFlag)
    {
    //TODO: API CALL TO GET TOWER HERE
//      String towerString = "C 22.769838 7.7074504 20 100 -9.028581E-5;C 38.923405 7.7073054 20 100 -3.8586637E-5;C 55.076233 7.707002 20 100 -3.519192E-5;C 71.23118 7.707309 20 100 5.320216E-5;C 63.231606 16.953136 100 20 1.4909657E-4;C 47.23332 16.952799 100 20 -1.3709241E-4;C 31.077705 16.953136 100 20 -4.1015745E-5;C 31.539476 26.19938 20 100 -3.220857E-4;C 63.54037 26.199657 20 100 -7.962991E-5;C 47.542023 26.198494 20 100 -1.4919715E-4;C 39.536453 35.444996 100 20 -4.126221E-5;C 56.00377 35.445057 100 20 1.6112691E-4;C 28.15738 26.203596 20 100 -2.0291282E-5;C 66.92998 26.201353 20 100 -4.7083333E-4;C 56.005135 44.69095 20 100 2.0222797E-4;C 40.00231 44.691532 20 100 -5.2561506E-4;C 47.6947 53.937187 100 20 -4.317683E-5;C 59.23504 44.69387 20 100 -9.8591474E-5;C 36.618286 44.692383 20 100 -7.2669337E-4;C 47.390823 57.029087 100 20 -6.051278E-5;E 48.614384 40.73768 25;E 38.770428 22.246534 25;E 55.22985 22.231611 25;";
      String towerString = (String) ServerHelper.getBestTower().get("towerdata");
      
      Utility.destroyAllObjects(pGameObjects);
      pGameObjects.clear();
      
      //ArrayList<PhysicalGameObject> newObjects = Utility.parseTowerString(towerString, getOffset(), Utility.getPlayerName(playerNum));
      Utility.parseTowerString(towerString, pGameObjects, getOffset(), Utility.getPlayerName(playerNum));
      
      
      //pGameObjects.addAll(Utility.parseTowerString(towerString, getOffset(), Utility.getPlayerName(playerNum)));
      //Utility.root.getChildren().addAll(pGameObjects);
      
      blocksUsed = 0;
      eggsPlaced = 0;
      
      acceptEggClicks = false;
      
      for(PhysicalGameObject pgo : pGameObjects)
      {
        if(pgo instanceof Crate)
        {
          blocksUsed++;
        }
        else if(pgo instanceof Egg)
        {
          eggsPlaced++;
        }
        
      }
      
      towerGenerateFlag = false;
    }
   
    //Create time step. Set Iteration count 8 for velocity and 3 for positions
    synchronized(Utility.root.getChildren())
    {
      Utility.world.step(1.0f/60.f, 8, 3);
    }
    
    
    mouseX = (float) Utility.mousePosition.getX();
    mouseY = (float) Utility.mousePosition.getY();
    
    
    //System.out.println("mouseX: " + mouseX);
    
    toPlace1.setLayoutX(mouseX);
    toPlace1.setLayoutY(mouseY);
    
    toPlace2.setLayoutX(mouseX - RECT_LITTLE/2);
    toPlace2.setLayoutY(mouseY - RECT_BIG/2);
    
    toPlace3.setLayoutX(mouseX - RECT_BIG/2);
    toPlace3.setLayoutY(mouseY - RECT_LITTLE/2);
    
    measure1.setLayoutX(mouseX - 95 - 5f/2);
    measure2.setLayoutX(mouseX + 95 - 5f/2);
    measure3.setLayoutX(mouseX + 0 - 190f/2);
    measure4.setLayoutX(mouseX - 50 - 5f/2);
    measure5.setLayoutX(mouseX + 50 - 5f/2);
    
    measure1.setLayoutY(mouseY + 0 - 50f/2);
    measure2.setLayoutY(mouseY + 0 - 50f/2);
    measure3.setLayoutY(mouseY - 0 - 2f/2);
    measure4.setLayoutY(mouseY + 0 - 50f/2);
    measure5.setLayoutY(mouseY + 0 - 50f/2);
    
    lava.toFront();
    
    toPlace1.toFront();
    toPlace2.toFront();
    toPlace3.toFront();
    
    measure1.toFront();
    measure2.toFront();
    measure3.toFront();
    measure4.toFront();
    measure5.toFront();
    
    for(PhysicalGameObject pgObject : pGameObjects)
    {
      pgObject.update();
    }
    
    //Utility.gc.getCanvas().setLayoutX(camX);
    text.setLayoutX(camX);
    if(blocksUsed >= MAX_BLOCKS)
    {
      if(eggsPlaced < MAX_EGGS)
      {
        
        if(!acceptEggClicks)
        {
          text.setText("PRESS SPACE TO ENTER EGG PLACEMENT MODE");
        
          //Utility.gc.fillText("PRESS SPACE TO PLACE EGG", 20, 20);
        }
        if(acceptEggClicks)
        {
          text.setText("CLICK TO PLACE EGG (" + (MAX_EGGS-eggsPlaced + " remaining)"));
        
          //Utility.gc.fillText("PRESS SPACE TO PLACE EGG", 20, 20);
        }
        
      }
      else if(eggsPlaced >= MAX_EGGS)
      {
        if(!enteringTowerName)
        {
          destroyMouse();
          Utility.UnregisterForClicks(this);
          Label towerNameLabel = new Label("Name your tower!");
          TextField towerNameField = new TextField();
          Button towerNameButton = new Button("Submit");
          
          towerNameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
              String towerData = Utility.getTowerString(pGameObjects, getOffset());
              System.out.println("CLICK!");
              if(playerNum == 1)
              {
                Utility.player1TowerName = towerNameField.getText();
                if(!ServerHelper.addNewUserTower(Utility.player1Username, Utility.player1TowerName, towerData))
                  return;
                System.out.println("CLEANUP1!");
                preCleanUp();
                Utility.transitionGameState(new TowerCreateState(2));
              }
              if(playerNum == 2)
              {
                Utility.player2TowerName = towerNameField.getText();
                if(!ServerHelper.addNewUserTower(Utility.player2Username, Utility.player2TowerName, towerData))
                  return;
                System.out.println("CLEANUP2!");
                preCleanUp();
                Utility.transitionGameState(new PlayGameState());
              }
            }
          });
          
          VBox verticalBox = new VBox();
          verticalBox.getChildren().addAll(towerNameLabel, towerNameField, towerNameButton);
          
          borderPane = new BorderPane();
          borderPane.setPrefSize(600, 600);
          borderPane.setCenter(verticalBox);
          
          Utility.root.getChildren().add(borderPane);
          enteringTowerName = true;
        }
      }
      timeLeft = 0;
      acceptClicks = false;
      return;
    }
    
    //Utility.gc.fillText(Float.toString(timeLeft), 20, 20);
    text.setText("Blocks left: " + Integer.toString(MAX_BLOCKS - blocksUsed));
    
    timeLeft -= 1.0f/60;
    if(timeLeft <= 0)
    {
      timeLeft = 0;
    }
    //Utility.gc.fillText(Float.toString(timeLeft), 20, 20);
    
    camera.setLayoutX(camX);
    //Utility.gc.getCanvas().setLayoutX(camX);
    //camX++;
  }

  void destroyMouse()
  {
    Utility.root.getChildren().remove(toPlace1);
    Utility.root.getChildren().remove(toPlace2);
    Utility.root.getChildren().remove(toPlace3);

    Utility.root.getChildren().remove(measure1);
    Utility.root.getChildren().remove(measure2);
    Utility.root.getChildren().remove(measure3);
    Utility.root.getChildren().remove(measure4);
    Utility.root.getChildren().remove(measure5);
    
    Utility.root.getChildren().remove(text);
  }
  
  void preCleanUp()
  {
    Utility.root.getChildren().remove(borderPane);
    
    System.out.println("CLEANUP");
    System.out.println("A player: " + playerNum);
    ArrayList<PhysicalGameObject> pgoList = Utility.getPGameObjects(playerNum);
    System.out.println("B");
    pgoList.clear();
    pgoList.addAll(pGameObjects);
    
    for(PhysicalGameObject pgo : pGameObjects)
    {
      pgo.getBody().setType(BodyType.STATIC);
      
      
      
      //pgo.getBody().getFixtureList().
    }
    pGameObjects.clear();
    
    Utility.root.getChildren().remove(lava);
  }
  
  @Override
  void cleanUp(){}
  
  public static int getOffset(int playerNum)
  {
    if(playerNum == 1)
    {
      return 0;
    }
    else if(playerNum == 2)
    {
      return 1000;
    }
    
    else
    {
      return -1;
    }
  }
  
  private int getOffset()
  {
    return getOffset(this.playerNum);
  }
}
