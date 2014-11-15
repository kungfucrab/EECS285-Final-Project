package bouncyballapp;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public interface Serializable
{
  String getStringRepresentation();
  void initViaStringRepresentation(String rep);
}
