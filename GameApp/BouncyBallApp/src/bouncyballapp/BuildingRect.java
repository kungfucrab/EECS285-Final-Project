package bouncyballapp;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
 
/**
 *
 * @author dilip
 */
public class BuildingRect{
 
    //JavaFX UI for ball
    public Node node;
     
    //X and Y position of the ball in JBox2D world
    private float posX;
    private float posY;
    private float width;
    private float height;
     
    
    private Color color;
    public BuildingRect(float posX, float posY, int width, int height, Color color){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.color = color;
        node = create();
    }
     
    /**
     * This method creates a ball by using Circle object from JavaFX and CircleShape from JBox2D
     */
    private Node create(){
        //Create an UI for ball - JavaFX code
        Rectangle rect = new Rectangle();
        rect.setHeight(height);
        rect.setWidth(width);
        rect.setFill(color); //set look and feel 
         
        /**
         * Set ball position on JavaFX scene. We need to convert JBox2D coordinates 
         * to JavaFX coordinates which are in pixels.
         */
        rect.setLayoutX(Utility.toPixelPosX(posX)); 
        rect.setLayoutY(Utility.toPixelPosY(posY));
        
        /*//Create an JBox2D body defination for ball.
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(Utility.toWidth(width), Utility.toHeight(height));
        ps.m_type = ShapeType.POLYGON;
        //bd.position.set(posX, posY);
         
        //CircleShape cs = new CircleShape();
        *///cs.m_radius = radius * 0.1f;  //We need to convert radius to JBox2D equivalent
        
      //Create an JBox2D body defination for ball.
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(posX, posY);
         
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(Utility.toWidth(width/2), Utility.toHeight(height/2));
        
        // Create a fixture for ball
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 0.6f;
        fd.friction = 0.3f;        
        //fd.restitution = 0.8f;
 
        /**
        * Virtual invisible JBox2D body of ball. Bodies have velocity and position. 
        * Forces, torques, and impulses can be applied to these bodies.
        */
        Body body = Utility.world.createBody(bd);
        body.createFixture(fd);
        rect.setUserData(body);
        return rect;
    }
     
}