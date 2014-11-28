package bouncyballapp;

import java.util.HashMap;
import java.awt.event.*;

import javax.swing.JOptionPane;

import javafx.event.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Point2D;
import bouncyballapp.TowerCreateState;

public class MenuState extends GameState implements ClickResponder
{
  Label messageLabel;
  Label player1Label;
  Label player2Label;
  
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
    public void cleanUp()
    {
      text.cleanUp();
    }
  }
  
  public class ArcadeMenuChoice extends MenuChoice
  {
    public ArcadeMenuChoice()
    {
      text = new BouncyText("Arcade Mode", 100, 200, 20);
    }
    
    public void onClick()
    {
      if(highlighted)
      {
        if(!Utility.player1Username.isEmpty())
          Utility.transitionGameState(new TowerCreateState(1));
        else
        {
          messageLabel.setText("Please specify Player 1's username!");
          messageLabel.setTextFill(Color.RED);
          messageLabel.setFont(new Font("Arial", 30));
        }
      }
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
      if(highlighted)
      {
        String creditsMessage = "Developed by\nSebastian Martinez,\nTomas Medina Inchauste,"
            + "\nThomas Seidel,\nand Austin Yarger. Developed for EECS 285, F14, University of Michigan, Ann Arbor.";
        
        JOptionPane.showMessageDialog(null, creditsMessage);
      }
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
    synchronized(choices)
    {
      choices.put(0, new ArcadeMenuChoice());
      choices.put(3, new GalleryMenuChoice());
      choices.put(4, new ProfileMenuChoice());
      choices.put(5, new CreditsMenuChoice());
      choices.put(6, new QuitMenuChoice());
    }
    Utility.RegisterForClicks(this);
    
    // Username Entry.
    player1Label = new Label("Player 1 Username:");
    player2Label = new Label("Player 2 Username:");
    messageLabel = new Label("Please enter a username. Enter 2 to play multiplayer!");
    TextField player1TextField = new TextField ();
    TextField player2TextField = new TextField ();
    HBox hbPlayer1 = new HBox();
    HBox hbPlayer2 = new HBox();
    VBox playerBoxes = new VBox();
    hbPlayer1.getChildren().addAll(player1Label, player1TextField);
    hbPlayer1.setSpacing(10);
    hbPlayer2.getChildren().addAll(player2Label, player2TextField);
    hbPlayer2.setSpacing(10);
    
    playerBoxes.getChildren().addAll(hbPlayer1, hbPlayer2, messageLabel);
    
    Utility.root.getChildren().add(playerBoxes);
    
    // Update the stored usernames as users type.
    player1TextField.textProperty().addListener((observable, oldValue, newValue) -> {
      Utility.player1Username = newValue;
    });
    player2TextField.textProperty().addListener((observable, oldValue, newValue) -> {
      Utility.player2Username = newValue;
    });
  }
  
  public void onClick(MouseEvent _)
  {
    synchronized(choices)
    {
      HashMap<Integer, MenuChoice> temp = (HashMap<Integer, MenuChoice>)choices.clone();
      for(MenuChoice m : temp.values())
      {
        m.onClick();
      }
    }
  }
  
  public void update()
  {
    synchronized(choices)
    {
      for(MenuChoice c : choices.values())
      {
        c.update();
      }
    }
  }
  
  public void cleanUp()
  {
    Utility.UnregisterForClicks(this);
    synchronized(choices)
    {
      for(MenuChoice c : choices.values())
      {
        c.cleanUp();
      }
      
      choices.clear();
      Utility.gc.clearRect(0, 0, Utility.WIDTH, Utility.HEIGHT);
      
      // Label and text fields.
      Utility.root.getChildren().remove(messageLabel);
      Utility.root.getChildren().remove(player1Label);
      Utility.root.getChildren().remove(player2Label);
    }
  }
}
