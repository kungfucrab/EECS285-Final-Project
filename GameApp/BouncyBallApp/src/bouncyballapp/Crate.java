package bouncyballapp;

import java.util.Random;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Crate extends PhysicalGameObject implements Serializable
{
  
  private int width, height;
  /*
   * x and y are specified in box2d coordinates.
   */
  public Crate(float x, float y, int width, int height)
  {
    this.width = width;
    this.height = height;
    
    init(x, y);
  }
  
  public Shape createFXShape()
  {
    Rectangle r = new Rectangle();
    r.setWidth(this.width);
    r.setHeight(this.height);
    r.setFill(createColor());
    return r;
  }
  
  public org.jbox2d.collision.shapes.Shape createShape()
  {
    PolygonShape ps = new PolygonShape();
    ps.setAsBox(Utility.toWidth(width/2), Utility.toHeight(height/2));
    return ps;
  }
  
  public FixtureDef createFixtureDef()
  {
    FixtureDef fd = new FixtureDef();
    fd.shape = createShape();
    fd.density = 0.6f;
    fd.friction = 0.3f;
    return fd;
  }
  
  public void update()
  {
    Body body = getBody();
    float xpos = Utility.toPixelPosX(body.getPosition().x);
    float ypos = Utility.toPixelPosY(body.getPosition().y);
    
    Shape fxShape = getfxShape();
    float pWidth = Utility.toPixelWidth(width);
    float pHeight = Utility.toPixelWidth(height);
    fxShape.setLayoutX(xpos - width/2);
    fxShape.setLayoutY(ypos - height/2);
    fxShape.setRotate(-180*body.getAngle()/((float) Math.PI));
    
    //System.out.println("XPos" + xpos);
  }
  
  public Color createColor()
  {
    Random rand = new Random();
    
    int r = rand.nextInt(128)+64;
    int g = rand.nextInt(128)+128;
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
}
