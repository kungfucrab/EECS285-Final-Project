package bouncyballapp;
import java.util.Vector;
import javafx.scene.text.*;

public class BouncyText extends SpecialText
{
  private double scale = 0.0f;
  private double target = 0.0f;
  private double stiffness = 0.001f;
  private double velocity = 0.0f;
  private double damping = 0.99f;
  
  public BouncyText(String message, int x, int y, double target)
  {
    this.target = target;
    init(message, x, y);
    bouncyTexts.add(this);
  }
  
  public void update()
  {
    for(int i = 0; i < characters.size(); i++)
    {
      double force = (target - scale) * stiffness;
      velocity = damping * (velocity + force);
      scale += velocity;
      
      Text t = characters.elementAt(i);
      Utility.gc.setFont(Font.font("Verdana", scale));
      Utility.gc.fillText(t.getText(), this.x + i * 25, this.y);
    }
  }
  
  public void setTarget(double t)
  {
    this.target = t;
  }
}
