public class Camera {

    private double x;
    private double y;
    private MovingThing centre;
    private double lastTime=0;

    private double velocity;
    private double acceleration;

    public Camera(Integer x, Integer y, MovingThing c){
        this.x=x;
        this.y=y;
        this.centre=c;
    }

    public void update(long time){


        //Calculation of the position of the camera using a spring-mass like system

        if (lastTime != 0) {
            acceleration = 0.1*(centre.getX() - x - 150)-0.2*velocity;
            velocity += acceleration * 0.16;
            x += velocity * 0.16;
        }
        lastTime=time;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
