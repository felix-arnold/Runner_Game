public class Foe extends MovingThing{

    public Foe(String fileName, double x, double y, double width, double height) {
        super(fileName, x, y, width, height);
    }

    private long lastTime = 0;
    private double velocityX=-0.5;

    public void update(long time) {


        //Velocity of the ennemies

        double elapsedTime = (time - lastTime) / 1000000;
        if (lastTime != 0) {
            x += velocityX*elapsedTime;
        }

        lastTime=time;
    }
}
