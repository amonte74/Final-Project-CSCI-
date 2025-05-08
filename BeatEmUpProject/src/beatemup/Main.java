package beatemup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameManager gameManager = new GameManager();
        Pane root = gameManager.getGamePane();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Beat 'Em Up Survival");
        primaryStage.setScene(scene);
        primaryStage.show();
        gameManager.startGameLoop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
