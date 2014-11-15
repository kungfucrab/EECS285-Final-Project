package bouncyballapp;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Ball extends PhysicalGameObject implements Serializable
{
  
  private int radius;
  /*
   * x and y are specified in box2d coordinates.
   */
  public Ball(float x, float y, int radius)
  {
    this.radius = radius;
    
    init(x, y);
  }
  
  public Shape createFXShape()
  {
    Circle c = new Circle();
    c.setRadius(this.radius);
    return c;
  }
  
  public PolygonShape createShape()
  {
    PolygonShape ps = new PolygonShape();
    ps.setRadius(this.radius);
    ps.setAsBox(this.radius, this.radius);
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
    return Color.BLUE;
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
