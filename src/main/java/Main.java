import Engine.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import static Engine.Constants.SH;
import static Engine.Constants.SW;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Group root = FXMLLoader.load(getClass().getResource("/resources/sample.fxml"));
        Group root = new Group();
        Scene theScene = new Scene(root, SW, SH);
        primaryStage.setTitle("Croissant");
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(SW, SH);
        root.getChildren().add(canvas);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        GameController gameController = new GameController(graphicsContext);
        gameController.BeginGame();

        theScene.setOnKeyPressed(gameController::HandleInput);

        /*
        new AnimationTimer()
        {
            private long t;
            public void handle(long currentNanoTime)
            {
                if(currentNanoTime - t > 228633600L) {
                    t = currentNanoTime;
                    gameController.currentMap.iterLife();
                    gameController.currentMap.draw(graphicsContext);
                }
            }
        }.start();
        */

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
