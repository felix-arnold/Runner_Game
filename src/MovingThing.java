import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public class MovingThing {

    private ImageView sprite;
    protected Rectangle hitbox;
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public MovingThing (String fileName, double x, double y, double width, double height) {

       this.x=x;
       this.y=y;
       this.width=width;
       this.height=height;


       //Load of the image and convert it into an ImageView, set its initial position

       Image preSprite = new Image(fileName);
       sprite = new ImageView(preSprite);
       sprite.setY(y);
       sprite.setX(x);


       //Definition of the hitbox of a MovingThing

       hitbox = new Rectangle(x,y,width,height);
    }

    public ImageView getSprite(){
        return sprite;
    }

    public double getX(){
       return x;
    }

    public double getY(){
        return y;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

}