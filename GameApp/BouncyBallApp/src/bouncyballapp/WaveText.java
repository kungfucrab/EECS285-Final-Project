package bouncyballapp;
import java.util.Vector;
import javafx.scene.text.*;

public class WaveText extends SpecialText
{
  float waveCounter = 0;
  
  public WaveText(String message, int x, int y)
  {
    init(message, x, y);
    waveTexts.add(this);
  }
  
  public void update()
  {
    waveCounter += 0.05;
    for(int i = 0; i < characters.size(); i++)
    {
      Text t = characters.elementAt(i);
      Utility.gc.setFont(Font.font("Verdana", (Math.sin(waveCounter + i * 0.5) + Math.PI) * 10));
      Utility.gc.fillText(t.getText(), this.x + i * 25, this.y);
    }
  }
}
