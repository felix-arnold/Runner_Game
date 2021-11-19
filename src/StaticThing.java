import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class StaticThing {

    private ImageView background;
    private double x;
    private double y;

   public StaticThing (String fileName, double x, double y, Integer xCrop, Integer yCrop, Integer width, Integer length) {

        this.x=x;
        this.y=y;


        //Load the image and convert it into an ImageView, set its initial position

        Image preBackground = new Image(fileName);
        background = new ImageView(preBackground);
        background.setViewport(new Rectangle2D(xCrop,yCrop,width,length));
        background.setX(x);
        background.setY(y);
    }

    public ImageView getBackground(){
        return background;
    }

}
