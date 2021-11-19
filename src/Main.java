import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application{

    public void start(Stage primaryStage){
        primaryStage.setTitle("A simple runner");
        Group root = new Group();
        GameScene scene = new GameScene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}