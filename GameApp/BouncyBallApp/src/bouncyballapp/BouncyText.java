package bouncyballapp;
import java.util.Vector;
import javafx.scene.text.*;

public class BouncyText extends SpecialText
{
  private float scale = 0.0f;
  private float target = 0.0f;
  private float stiffness = 0.1f;
  private float velocity = 0.0f;
  private float damping = 0.999f;
  
  public BouncyText(String message, int x, int y, float target)
  {
    this.target = target;
    init(message, x, y);
    bouncyTexts.add(this);
  }
  
  public void update()
  {
    for(int i = 0; i < characters.size(); i++)
    {
      float force = (target - scale) * stiffness;
      velocity = damping * (velocity + force);
      scale += velocity;
      
      Text t = characters.elementAt(i);
      Utility.gc.setFont(Font.font("Verdana", scale));
      Utility.gc.fillText(t.getText(), this.x + i * 25, this.y);
    }
  }
  
  public void setTarget(float t)
  {
    this.target = t;
  }
}
