package bouncyballapp;

import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Egg extends PhysicalGameObject implements Serializable
{
  
  private int radius;
  
  boolean isDestroyed;
  
  private String playerName;
  /*
   * x and y are specified in box2d coordinates.
   */
  public Egg(float x, float y, int radius, String inPlayerName)
  {
    
    this.playerName = inPlayerName;
    
    this.radius = radius;
    
    init(x, y);
  }
  
  public javafx.scene.shape.Shape createFXShape()
  {
    Circle c = new Circle();
    c.setRadius(this.radius);
    c.setFill(createColor());
    return c;
  }
  
  public org.jbox2d.collision.shapes.Shape createShape()
  {
    org.jbox2d.collision.shapes.Shape cs = new CircleShape();
    cs.setRadius(.15f*this.radius);
    //ps.setAsBox(.1f*this.radius/2, .1f*this.radius/2);
    return cs;
  }
  
  public FixtureDef createFixtureDef()
  {
    FixtureDef fd = new FixtureDef();
    fd.shape = createShape();
    fd.density = .6f;
    fd.friction = 0.3f;
    return fd;
  }
  
  public void update()
  {
    Body body = getBody();
    float xpos = Utility.toPixelPosX(body.getPosition().x);
    float ypos = Utility.toPixelPosY(body.getPosition().y);
    javafx.scene.shape.Shape fxShape = getfxShape();
    fxShape.setLayoutX(xpos);
    fxShape.setLayoutY(ypos);
    fxShape.setRotate(-180*body.getAngle()/((float) Math.PI));
  }
  
  public Color createColor()
  {
    Random rand = new Random();
    
    int r = rand.nextInt(128)+128;
    int g = rand.nextInt(128)+64;
    int b = rand.nextInt(128)+64;
    
    return Color.rgb(r, g, b);
  }
  
  public String getStringRepresentation()
  {
    return "IMPLEMENT MEEE!";
  }
  
  public void initViaStringRepresentation(String rep)
  {
    // Implement me.
  }
  
  public int getRadius()
  {
    return radius;
  }
  
  public boolean getIsDestroyed()
  {
    if(isDestroyed)
    {
      return true;
    }
    else if(this.getfxShape().getLayoutY() + radius > Utility.HEIGHT - Utility.LAVA_HEIGHT_BASE)
    {
      isDestroyed = true;
      return true;
    }
    else
    {
      return false;
    }
  }
}
