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
  }
  
  abstract Shape createFXShape();
  abstract PolygonShape createShape();
  abstract FixtureDef createFixtureDef();
  abstract Color createColor();
  
  public void update()
  {
    float xpos = Utility.toPixelPosX(body.getPosition().x);
    float ypos = Utility.toPixelPosY(body.getPosition().y);
    fxShape.setLayoutX(xpos);
    fxShape.setLayoutY(ypos);
    fxShape.setRotate(-180*body.getAngle()/((float) Math.PI));
  }
  
  public Body getBody()
  {
    return body;
  }
  
  public BodyDef createBodyDef(float x, float y)
  {
    BodyDef bd = new BodyDef();
    bd.type = BodyType.DYNAMIC;
    bd.position.set(x, y);
    return bd;
  }
}
