package bouncyballapp;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.sun.javafx.Utils;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Utility
{

//Create a JBox2D world.
  public static final World world = new World(new Vec2(0.0f, -10.0f));
   
  //Screen width and height
  public static final int WIDTH = 650;//600;
  public static final int HEIGHT = 650;
   
  //Ball radius in pixel
  public static final int BALL_RADIUS = 8;
  
  // LAVA
  public static final int LAVA_HEIGHT_BASE = 50;
  public static double LAVA_ANIMATION_SPEED = 0.05;
  public static double LAVA_AMPLITUDE = 2;
  public static double lava_animation_frame = 0; // Lava Animation
  
  static Image graphPaperImage;
  
  static Group root;
  static Scene scene;
  static Stage stage;
  
  public static Canvas canvas;
  public static GraphicsContext gc;
  
  public static GameState gameState;
  
  public static Point2D mousePosition = new Point2D(0, 0);
  
  private static Vector<ClickResponder> clickDownResponders = new Vector<ClickResponder>();
  private static Vector<ClickResponder> clickUpResponders = new Vector<ClickResponder>();
  public static Vector<TickResponder> tickResponders = new Vector<TickResponder>();
  
  public static String player1Username = "";
  public static String player2Username = "";
  public static String player1TowerName = "";
  public static String player2TowerName = "";
  
  private static ArrayList<PhysicalGameObject> pGameObjects1 = new ArrayList<PhysicalGameObject>();
  private static ArrayList<PhysicalGameObject> pGameObjects2 = new ArrayList<PhysicalGameObject>();
  
  //This method adds a ground to the screen. 
  public static void addGround(float width, float height){
    PolygonShape ps = new PolygonShape();
    ps.setAsBox(100000,10);
         
    FixtureDef fd = new FixtureDef();
    fd.shape = ps;
 
    BodyDef bd = new BodyDef();
    bd.position= new Vec2(0, -10);
 
    world.createBody(bd).createFixture(fd);
  }
  
  //This method creates a walls. 
  public static void addWall(float posX, float posY, float width, float height){
    PolygonShape ps = new PolygonShape();
    ps.setAsBox(width,height);
         
    FixtureDef fd = new FixtureDef();
    fd.shape = ps;
    fd.density = 1.0f;
    fd.friction = 0.3f;    
 
    BodyDef bd = new BodyDef();
    bd.position.set(posX, posY);
         
    Utility.world.createBody(bd).createFixture(fd);
  }
  
  //Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
  public static float toPixelPosX(float posX) {
    float x = WIDTH*posX / 100.0f;
    return x;
  }

  //Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
  public static float toPosX(float posX) {
    float x =   (posX*100.0f*1.0f)/WIDTH;
    return x;
  }

  //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
  public static float toPixelPosY(float posY) {
    float y = HEIGHT - (1.0f*HEIGHT) * posY / 100.0f;
    return y;
  }

  //Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
  public static float toPosY(float posY) {
    float y = 100.0f - ((posY * 100*1.0f) /HEIGHT) ;
    return y;
  }

//Convert a JBox2D width to pixel width
  public static float toPixelWidth(float width) {
    return WIDTH*width / 100.0f;
  }

  //Convert a JBox2D height to pixel height
  public static float toPixelHeight(float height) {
    return HEIGHT*height/100.0f;
  }
  
  //Convert a pixel width to JBox2D width
  public static float toWidth(float width) {
    return 100.0f*width / WIDTH;
  }
  
//Convert a pixel height to JBox2D height
  public static float toHeight(float height) {
    return 100.0f*height/HEIGHT;
  }
  
  public static void transitionGameState(GameState newState)
  {
    System.out.println("TRANSITIONING GAME STATE");
    Utility.gameState.cleanUp();
    Utility.gameState = newState;
  }
  
  public static void RegisterForClicks(ClickResponder clickResponder)
  {
    synchronized(clickDownResponders)
    {
      clickDownResponders.add(clickResponder);
    }
    synchronized(clickUpResponders)
    {
      clickUpResponders.add(clickResponder);
    }
  }
  public static synchronized void UnregisterForClicks(ClickResponder clickResponder)
  {
    synchronized(clickDownResponders)
    {
      clickDownResponders.remove(clickResponder);
    }
    synchronized(clickUpResponders)
    {
      clickUpResponders.remove(clickResponder);
    }
  }
  public static synchronized void fireClickResponders(MouseEvent mouseEvent)
  {
    synchronized(clickDownResponders)
    {
      Vector<ClickResponder> copyVec;
      copyVec = (Vector<ClickResponder>)clickDownResponders.clone();

      for(int i = 0; i < copyVec.size(); i++)
      {
        copyVec.get(i).onClick(mouseEvent);
      }
    }
  }
  
  public static synchronized void fireReleaseResponders(MouseEvent mouseEvent)  
  {
    synchronized(clickUpResponders)
    {
      Vector<ClickResponder> copyVec;
      copyVec = (Vector<ClickResponder>)clickUpResponders.clone();

      for(int i = 0; i < copyVec.size(); i++)
      {
        copyVec.get(i).onRelease(mouseEvent);
      }
    }
  }
  
  public static ArrayList<PhysicalGameObject> getPGameObjects(int playerNum)
  {
    if(playerNum == 1)
    {
      //System.out.println("player 1 list");
      return pGameObjects1;
    }
    else if(playerNum == 2)
    {
      //System.out.println("player 2 list");
      return pGameObjects2;
    }
    else
    {
      System.exit(1);
      return new ArrayList<PhysicalGameObject>();
    }
  }
  
  public static String getTowerString(ArrayList<PhysicalGameObject> pgos, int offset)
  {
    String out = "";
    for(PhysicalGameObject pgo : pgos)
    {
      if(pgo instanceof Crate)
      {
        out += "C " + (pgo.getBody().getPosition().x - offset) + " "
                + pgo.getBody().getPosition().y + " "
                + ((Crate)pgo).getWidth() + " "
                + ((Crate)pgo).getHeight() + " "
                + pgo.getBody().getAngle() + ";";
      }
      else if(pgo instanceof Egg)
      {
        out += "E " + (pgo.getBody().getPosition().x - offset) + " "
                + pgo.getBody().getPosition().y + " "
                + ((Egg)pgo).getRadius() + ";";
      }
    }
    
    return out;
  }
  
  public static void parseTowerString(String towerString, ArrayList<PhysicalGameObject> pgos, int offset, String playerName)
  {
    String[] gameObjectStrings = (towerString.split(";"));
    
    //ArrayList<PhysicalGameObject> pgos = new ArrayList<PhysicalGameObject>();
    
    
    for(String s : gameObjectStrings)
    {
      String[] vals = s.split(" ");
      if(vals[0].equals("E"))
      {
        float xCoord = Float.parseFloat(vals[1]);
        float yCoord = Float.parseFloat(vals[2]);
        int radius = Integer.parseInt(vals[3]);
        
        pgos.add(new Egg(xCoord + toPosX(offset), yCoord, radius, playerName));
      }
      else if(vals[0].equals("C"))
      {
        float xCoord = Float.parseFloat(vals[1]);
        float yCoord = Float.parseFloat(vals[2]);
        int width = Integer.parseInt(vals[3]);
        int height = Integer.parseInt(vals[4]);
        float angle = Float.parseFloat(vals[5]);
        
        pgos.add(new Crate(xCoord + toPosX(offset), yCoord, width, height, angle));
      }
    }
    
    //return pgos;
  }
  
  public static void destroyAllObjects(ArrayList<PhysicalGameObject> pGameObjects)
  {
    for(PhysicalGameObject pgo : pGameObjects)
    {
      //Utility.root.getChildren().remove(pgo);
      //Utility.world.destroyBody(pgo.getBody());
      pgo.deleteComponents();
    }
    pGameObjects.clear();
  }
  
  public static String getPlayerName(int playerNum)
  {
    if(playerNum == 1)
    {
      return player1Username;
    }
    if(playerNum == 2)
    {
      return player2Username;
    }
    else
    {
      return "";
    }
  }
}
