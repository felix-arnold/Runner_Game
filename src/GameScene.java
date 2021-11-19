import javafx.scene.Group;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import java.util.Collection;
import java.util.ArrayList;

public class GameScene extends Scene {

        //Definition of the different variabels used in this class

        private Integer numberOfLives = 3;
        private StaticThing background1Left;
        private StaticThing background1Middle;
        private StaticThing background1Right;
        private StaticThing background2Left;
        private StaticThing background2Middle;
        private StaticThing background2Right;
        private Hero hero;
        private Camera camera;
        private double offset;
        private ArrayList<Double> ennemyXList;
        private ArrayList<Foe> ennemyList;
        private ArrayList<StaticThing> heartList;
        private ArrayList<StaticThing> heartBrokenList;
        private Rectangle hitbox;
        private boolean immune=false;
        private long hitTime;
        private int heroAttitude;
        private AnimationTimer timer;
        private boolean goodDistance=true;
        private int win=0;


        //Constructor

        public GameScene(Group g) {
                super(g, 1540, 800);

                //Display of the two backgrounds (which will be using a parallax scrolling)

                background2Left = new StaticThing("background2.png", -1540, 0, 0, 0, 1540, 666);
                background2Middle = new StaticThing("background2.png", 0, 0, 0, 0, 1540, 666);
                background2Right = new StaticThing("background2.png", 1540, 0, 0, 0, 1540, 666);
                g.getChildren().add(background2Left.getBackground());
                g.getChildren().add(background2Middle.getBackground());
                g.getChildren().add(background2Right.getBackground());

                background1Left = new StaticThing("background1.png", -6160, 0, 0, 0, 6160, 800);
                background1Middle = new StaticThing("background1.png", 0, 0, 0, 0, 6160, 800);
                background1Right = new StaticThing("background1.png", 6160, 0, 0, 0, 6160, 800);
                g.getChildren().add(background1Left.getBackground());
                g.getChildren().add(background1Middle.getBackground());
                g.getChildren().add(background1Right.getBackground());


                //Display of the hero

                hero = new Hero("Lucario2.png", 0, 0, 285, 258);
                g.getChildren().add(hero.getSprite());


                //Creation of the camera

                camera = new Camera(-4000,0,hero);


                //Launch of the handle method which is used to update the position of the moving elements in the game

                timer = new AnimationTimer() {
                        public void handle(long time) {
                                hero.update(time);
                                camera.update(time);
                                for (Foe ennemy : ennemyList) {
                                        ennemy.update(time);
                                }
                                render(time, g);
                        }
                };

                //Start-up of the timer which is use by the update methods

                timer.start();


                //Definition of the actions depending on the key pressed

                this.setOnKeyPressed( (event)->{
                        KeyCode keyCode = event.getCode();
                        if (keyCode.equals(KeyCode.SPACE)) {
                                hero.jump();
                        }
                        if (keyCode.equals(KeyCode.LEFT)) {
                                hero.backwardV();
                        }
                        if (keyCode.equals(KeyCode.RIGHT)) {
                                hero.forwardV();
                        }
                });

                this.setOnKeyReleased( (event)->{
                        KeyCode keyCode = event.getCode();
                        if (keyCode.equals(KeyCode.LEFT) || keyCode.equals(KeyCode.RIGHT) ) {
                                hero.normalV();
                        }
                });


                //Creation of the ennemies with a random value of x and y, two ennemies can not be too much close from each other

                ennemyList = new ArrayList<Foe>();
                ennemyXList = new ArrayList<Double>();
                for (int i=0; i<30; i++) {
                         double j = Math.random() * 60000;
                         for (Double ennemyX : ennemyXList) {
                                 if (Math.abs(j-ennemyX)<150) {
                                         goodDistance=false;
                                 }
                         }
                        if (goodDistance == false) {
                                i-=1;
                                goodDistance=true;
                        }
                        else {
                                ennemyXList.add(j);
                                double k = Math.random() * 80 - 40;
                                ennemyList.add(new Foe("Ennemy.png", j + 8000, 420 + k, 130, 83));
                        }
                }


                //Display of the ennemies

                for (Foe ennemy : ennemyList){
                        g.getChildren().add(ennemy.getSprite());
                }


                //Recovery of the hero hitbox

                hitbox = hero.getHitbox();


                //Display of the hearts which represents the life of the character

                heartList = new ArrayList<StaticThing>();
                heartBrokenList = new ArrayList<StaticThing>();
                for (int j=0; j<numberOfLives; j++) {
                        heartList.add(new StaticThing("heart.png", 10+35*j, 10, 0, 0, 30, 26));
                        heartBrokenList.add(new StaticThing("heartBroken.png",10+35*j, 10, 0, 0, 30, 26));
                }
                for (StaticThing heart : heartList){
                        g.getChildren().add(heart.getBackground());
                }

        }

        //Method render which define the position of the object depending on their movements

        private void render(long time, Group g) {

                //Position of the hero

                offset = camera.getX() % 6160;
                hero.getSprite().setX(hero.getX() - camera.getX());
                hero.getSprite().setY(hero.getY());


                //Position of the hero's hitbox

                heroAttitude = hero.getAttitude();
                if (heroAttitude>0) {
                        heroAttitude = 1;
                }
                hitbox.setX(hero.getX() - camera.getX() + heroAttitude*67);
                hitbox.setY(hero.getY());


                //Position of the backgrounds

                background1Left.getBackground().setX(-6160 - offset);
                background1Middle.getBackground().setX(-offset);
                background1Right.getBackground().setX(6160 - offset);

                background2Left.getBackground().setX(-1540 - offset / 4);
                background2Middle.getBackground().setX(-offset / 4);
                background2Right.getBackground().setX(1540 - offset / 4);



                //Definition of the invicibility and update of the number of lifes

                if ((time-hitTime)/10>200000000) {
                        immune = false;
                }

                for (Foe ennemy : ennemyList){
                        ennemy.getSprite().setX(ennemy.getX() - camera.getX());
                        ennemy.getHitbox().setX(ennemy.getX() - camera.getX());
                        if (immune==false && hero.getHitbox().intersects(ennemy.getHitbox().getX(), ennemy.getHitbox().getY(),ennemy.getHitbox().getWidth(),ennemy.getHitbox().getWidth())){
                                immune=true;
                                hitTime=time;
                                numberOfLives-=1;
                                g.getChildren().remove(heartList.get(numberOfLives).getBackground());
                                g.getChildren().add(heartBrokenList.get(numberOfLives).getBackground());
                        }
                }


                //Display of the defeat screen

                if (numberOfLives<1) {
                        numberOfLives=0;
                        g.getChildren().add(new StaticThing("YouLose.png",370,0,0,0,800,800).getBackground());
                }


                //Display of the victory screen

                win = 0;
                for (double ennemyX : ennemyXList) {
                        if (ennemyX<hero.getX() && numberOfLives>0) {
                                win++;
                        }
                        if (win==30) {
                                g.getChildren().add(new StaticThing("YouWin.png",370,0,0,0,800,800).getBackground());
                        }
                }
        }
}
