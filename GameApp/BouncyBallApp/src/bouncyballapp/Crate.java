package bouncyballapp;

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
    return r;
  }
  
  public PolygonShape createShape()
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
  
  public Color createColor()
  {
    return Color.RED;
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
