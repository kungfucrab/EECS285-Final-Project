package bouncyballapp;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.sun.javafx.Utils;

import java.util.Vector;


public class Utility
{

//Create a JBox2D world.
  public static final World world = new World(new Vec2(0.0f, -10.0f));
   
  //Screen width and height
  public static final int WIDTH = 1000;//600;
  public static final int HEIGHT = 1000;
   
  //Ball radius in pixel
  public static final int BALL_RADIUS = 8;
  
  static Group root;
  static Scene scene;
  
  public static Canvas canvas;
  public static GraphicsContext gc;
  
  public static GameState gameState;
  
  public static Point2D mousePosition = new Point2D(0, 0);
  
  private static Vector<ClickResponder> clickResponders = new Vector<ClickResponder>();
  public static Vector<TickResponder> tickResponders = new Vector<TickResponder>();
  
  private static String player1Username;
  private static String player2Username;
  
  //This method adds a ground to the screen. 
  public static void addGround(float width, float height){
    PolygonShape ps = new PolygonShape();
    ps.setAsBox(toWidth(100000),toHeight(10));
         
    FixtureDef fd = new FixtureDef();
    fd.shape = ps;
 
    BodyDef bd = new BodyDef();
    bd.position= new Vec2(0.0f,toPosY(1000) - toHeight(10)/2);
 
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
    synchronized(clickResponders)
    {
      clickResponders.add(clickResponder);
    }
  }
  public static synchronized void UnregisterForClicks(ClickResponder clickResponder)
  {
    synchronized(clickResponders)
    {
      clickResponders.remove(clickResponder);
    }
  }
  public static synchronized void fireClickResponders(MouseEvent mouseEvent)
  {
    synchronized(clickResponders)
    {
      Vector<ClickResponder> copyVec;
      copyVec = (Vector<ClickResponder>)clickResponders.clone();

      for(int i = 0; i < copyVec.size(); i++)
      {
        copyVec.get(i).onClick(mouseEvent);
      }
    }
  }
}
