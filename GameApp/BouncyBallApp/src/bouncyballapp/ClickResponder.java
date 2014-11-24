package bouncyballapp;

import javafx.scene.input.MouseEvent;

public interface ClickResponder
{
  void onClick(MouseEvent m);
  
  void onRelease(MouseEvent m);
}
