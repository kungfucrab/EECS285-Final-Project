package bouncyballapp;
import java.util.Vector;
import javafx.scene.text.*;
import javafx.geometry.Point2D;

public abstract class SpecialText
{
  protected static Vector<BouncyText> bouncyTexts = new Vector<BouncyText>();
  protected static Vector<WaveText> waveTexts = new Vector<WaveText>();
  
  protected Vector<Text> characters = new Vector<Text>();
  protected int x, y;
  
  public void init(String message, int x, int y)
  {
    this.x = x;
    this.y = y;
    for(int i = 0; i < message.length(); i++)
    {
      char c = message.charAt(i);
      Text t = new Text(String.valueOf(c));
      t.setFont(Font.font("Verdana", 0));
      t.setTextAlignment(TextAlignment.CENTER);
      characters.add(t);
    }
  }
  
  public static void updateSystem()
  {
    for(BouncyText b : bouncyTexts)
    {
      b.update();
    }
    
    for(WaveText w : waveTexts)
    {
      w.update();
    }
  }
  
  public Point2D getPosition()
  {
    return new Point2D(this.x, this.y);
  }
  
  abstract void update();
}
