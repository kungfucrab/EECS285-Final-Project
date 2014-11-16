package bouncyballapp;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public abstract class PhysicalGameObject implements Serializable
{
  private Shape fxShape;
  private Body body;
  
  public void init(float x, float y)
  {
    fxShape = createFXShape();
    fxShape.setLayoutX(Utility.toPixelPosX(x)); 
    fxShape.setLayoutY(Utility.toPixelPosY(y));
    
    BodyDef bd = createBodyDef(x, y);
    FixtureDef fd = createFixtureDef();
    
    body = Utility.world.createBody(bd);
    body.createFixture(fd);
    
    Utility.root.getChildren().add(fxShape);
    
    update();
  }
  
  abstract Shape createFXShape();
  abstract org.jbox2d.collision.shapes.Shape createShape();
  abstract FixtureDef createFixtureDef();
  abstract Color createColor();
  
  abstract void update();
  
  public Body getBody()
  {
    return body;
  }
  
  public Shape getfxShape()
  {
    return fxShape;
  }
  
  public BodyDef createBodyDef(float x, float y)
  {
    BodyDef bd = new BodyDef();
    bd.type = BodyType.DYNAMIC;
    bd.position.set(x, y);
    return bd;
  }
}
