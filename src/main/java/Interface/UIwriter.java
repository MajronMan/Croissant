package Interface;

import Engine.GameController;
import Items.Item;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static java.lang.ClassLoader.getSystemResource;

/**
 * Created by MajronMan on 11/12/2016.
 */
public class UIwriter {
    private static Text console;
    private static TextArea inventory;
    private static GridPane inventoryItems;

    public static void setConsole(Text t){
        console = t;
    }

    public static void setInventory(TextArea ta){
        inventory = ta;
    }

    public static void consoleWrite(String s){
        console.setText(s);
    }

    public static void inventoryChanged(List<Item> backpack){

    }

    public static void setInventoryItems(GridPane inventoryItems) {
        UIwriter.inventoryItems = inventoryItems;
    }

    public static void itemAdded(Item item) {
        String name="sword.jpg";
        URL url = getSystemResource(name);
        Image image = null;
        try {
            image = new Image(url.openStream());
        } catch (IOException e) {
            System.out.println("No such image: "+ name);
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        Button button = new Button("", imageView);
        Tooltip tooltip = new Tooltip(item.getName() + '\n' + item.getDescription());
        button.setTooltip(tooltip);
        int itemsCount = GameController.getPlayer().itemsCount()-1;
        inventoryItems.add(button, itemsCount/5, itemsCount%5);
    }
}
