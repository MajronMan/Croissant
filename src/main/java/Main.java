import engine.GameController;
import gui.UIwriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

import static engine.Constants.*;

public class Main extends Application {
    private Stage stage;
    private Scene scene;
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
        UIwriter.setInventory((TextArea) theScene.lookup("#Inventory"));
        UIwriter.setInventoryItems((GridPane) theScene.lookup("#InventoryItems"));
        Button btn = (Button) theScene.lookup("#InventoryButton");
        GridPane eqPopup = (GridPane) theScene.lookup("#InventoryPopup");
        UIwriter.setHP((Text) theScene.lookup("#HP"), (ProgressBar) theScene.lookup("#HPBar"));
        UIwriter.setStatBox((TextArea) theScene.lookup("#Stats"));
        GameController.setGraphicsContext(canvas.getGraphicsContext2D());
        GameController.BeginGame();

        btn.setOnMouseClicked(event -> eqPopup.setVisible(!eqPopup.isVisible()));


        theScene.setOnKeyPressed(GameController::handleKeyboardInput);
        theScene.setOnMouseClicked(GameController::handleMouseInput);

        theScene.getWindow().setOnCloseRequest((param) -> primaryStage.close());
        stage = primaryStage;
        scene = theScene;

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
