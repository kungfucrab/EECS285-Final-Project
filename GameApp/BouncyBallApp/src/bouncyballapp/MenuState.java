package bouncyballapp;

import java.util.HashMap;
import java.awt.event.*;

import javafx.event.*;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Point2D;

public class MenuState extends GameState implements ClickResponder
{
  public abstract class MenuChoice
  {
    protected boolean highlighted = false;
    protected BouncyText text;
    public void update()
    { 
      double verticalDistance = Math.abs(Utility.mousePosition.getY() - text.getPosition().getY());
            
      if(verticalDistance < 25)
      {
        text.setTarget(40);
        highlighted = true;
      }
      else
      {
        text.setTarget(20);
        highlighted = false;
      }
    }
    public void onClick() { }
  }
  
  public class ArcadeMenuChoice extends MenuChoice
  {
    public ArcadeMenuChoice()
    {
      text = new BouncyText("Arcade Mode", 100, 200, 20);
    }
    
    public void onClick()
    {
      
    }
  }
  
  public class GalleryMenuChoice extends MenuChoice
  { 
    public GalleryMenuChoice()
    {
      text = new BouncyText("Gallery", 100, 250, 20);
    }
    
    public void onClick()
    {
      
    }
  }
  
  public class ProfileMenuChoice extends MenuChoice
  { 
    public ProfileMenuChoice()
    {
      text = new BouncyText("Profile", 100, 300, 20);
    }
    
    public void onClick()
    {
      
    }
  }
  
  public class CreditsMenuChoice extends MenuChoice
  {
    public CreditsMenuChoice()
    {
      text = new BouncyText("Credits", 100, 350, 20);
    }
    
    public void onClick()
    {
      
    }
  }
  
  public class QuitMenuChoice extends MenuChoice
  { 
    public QuitMenuChoice()
    {
      text = new BouncyText("Quit", 100, 400, 20);
    }
    
    public void onClick()
    {
      if(highlighted)
        System.exit(0);
    }
  }
  
  int choice = 0;
  HashMap<Integer, MenuChoice> choices = new HashMap<Integer, MenuChoice>();
  
  public MenuState()
  { 
    choices.put(0, new ArcadeMenuChoice());
    choices.put(1, new GalleryMenuChoice());
    choices.put(2, new ProfileMenuChoice());
    choices.put(3, new CreditsMenuChoice());
    choices.put(4, new QuitMenuChoice());
    
    Utility.RegisterForClicks(this);
  }
  
  public void onClick(MouseEvent _)
  {
    for(MenuChoice m : choices.values())
    {
      m.onClick();
    }
  }
  
  public void update()
  {
    for(MenuChoice c : choices.values())
    {
      c.update();
    }
  }
  
  public void cleanUp()
  {
    
  }
}
