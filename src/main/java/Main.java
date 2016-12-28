import Engine.GameController;
import Interface.UIwriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

import static Engine.Constants.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = getClass().getResource("/sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        AnchorPane root = fxmlLoader.load(url.openStream());
        Scene theScene = new Scene(root, SW, SH);
        primaryStage.setTitle("Croissant");
        primaryStage.setScene(theScene);

        Canvas canvas = (Canvas)theScene.lookup("#Canvas");//new Canvas(mapX*cellSize, SH);
        canvas.setHeight(mapY*cellSize);
        canvas.setWidth(mapX * cellSize);
        GridPane gp = (GridPane) theScene.lookup("#Buttons");
        gp.setPrefWidth(SW - mapX*cellSize);
        AnchorPane output = (AnchorPane) theScene.lookup("#Console");
        output.setPrefWidth(mapX*cellSize);
        UIwriter.setConsole((Text)output.getChildren().get(0));
        UIwriter.consoleWrite("dupiszcze");
        UIwriter.setInventory((TextArea) theScene.lookup("#Inventory"));
        UIwriter.setInventoryItems((GridPane) theScene.lookup("#InventoryItems"));
        Button btn = (Button) theScene.lookup("#InventoryButton");
        GridPane eqPopup = (GridPane) theScene.lookup("#InventoryPopup");
        GameController.setGraphicsContext(canvas.getGraphicsContext2D());
        GameController.BeginGame();

        btn.setOnMouseClicked(event -> eqPopup.setVisible(!eqPopup.isVisible()));


        theScene.setOnKeyPressed(GameController::handleKeyboardInput);
        theScene.setOnMouseClicked(GameController::handleMouseInput);

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

    private static void showPopup(AnchorPane root){
        //AnchorPane pane = new AnchorPane();
        //root.getChildren().add(pane);
        //Text text = new Text("Equipment");
        root.setVisible(false);
        System.out.println("Click");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
