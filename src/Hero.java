import javafx.geometry.Rectangle2D;


public class Hero extends MovingThing {


    public Hero(String fileName, double x, double y, double width, double height) {
        super(fileName, x, y, width, height);
    }

    private Integer index;
    private Integer attitude = 0;
    private long lastTime = 0;
    private double velocityX=1;
    private double accelerationY;
    private double velocityY;
    private double mvtVelocityFactor = 90000000;

    public void update(long time) {
        double elapsedTime = (time - lastTime) / 1000000;
        if (lastTime != 0) {


            //Calculation of the x position of the hero depending on his speed

            x += elapsedTime * velocityX;
            accelerationY = 0.008;
            velocityY += elapsedTime * accelerationY;


            //Calculation of the y position of the hero using a gravity model

            y += elapsedTime * velocityY;
            if (y > 350) {
                y = 350;
                velocityY = 0;
                attitude = 0;
            }
            if (velocityY > 0) {
                attitude = 2;
            }
        }


        //Change the hero sprites display depending on if he is jumping or not

            //If he does not jump
        if (attitude == 0 && lastTime != 0) {
            index = (int) (Math.floor((time / mvtVelocityFactor)) % 8);
            getSprite().setViewport(new Rectangle2D(index * width, 0, width, height));
            hitbox.setWidth(width);
            hitbox.setHeight(height);
        }

            //If he is at the beginning of its jump and goes up
        if (attitude == 1 && lastTime != 0) {
            getSprite().setViewport(new Rectangle2D(0, 265, width, height));
            hitbox.setWidth(151);
            hitbox.setHeight(258);
        }

            //If he is at the end of its jump and goes down
        if (attitude == 2 && lastTime != 0) {
            getSprite().setViewport(new Rectangle2D(width, 265, width, height));
            hitbox.setWidth(151);
            hitbox.setHeight(258);
        }
        lastTime = time;
    }

    public int getAttitude() {
        return attitude;
    }


    //Methods used for user's actions on the hero

    public void jump() {
        if (velocityY == 0) {
            velocityY = -2.4;
            attitude = 1;
        }
    }

    public void forwardV() {
        velocityX = 1.4;
        mvtVelocityFactor = 70000000;
    }

    public void backwardV() {
        velocityX = 0.6;
        mvtVelocityFactor = 110000000;
    }

    public void normalV() {
        velocityX=1;
        mvtVelocityFactor = 90000000;
    }
}
