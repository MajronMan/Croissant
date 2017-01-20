package gui;

import engine.GameController;
import engine.Stat;
import engine.Statics;
import immaterial.Enums.StatTypes;
import items.Equipment;
import items.Item;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static engine.GameController.getPlayer;

/**
 * Created by MajronMan on 11/12/2016.
 */
public class UIwriter {
    private static Text console;
    private static ArrayList<String> history = new ArrayList<>();
    private static int i;
    private static TextArea inventory;
    private static GridPane inventoryItems;
    private static Text HP;
    private static ProgressBar HPBar;
    private static TextArea statBox;

    public static void setConsole(Text t) {
        if(console == null)
            console = t;
    }

    public static void setInventory(TextArea ta) {
        if(inventory == null)
            inventory = ta;
    }

    public static void consoleWrite(String s) {
        history.add(0, s);
        console.setText(s);
    }

    public static void clearHistory() {
        if(history.size() < 10) return;
        for (int j = 10; j < history.size(); j++) {
            history.remove(j);
        }
        i = 0;
        console.setText(history.get(i));
    }

    public static void toggleHistory() {
        i = (i + 1) % history.size();
        console.setText(history.get(i));
    }

    public static void setStatBox(TextArea ta){
        if(statBox == null) {
            statBox = ta;
            ta.setEditable(false);
            ta.setFocusTraversable(false);
        }
    }

    public static void setInventoryItems(GridPane inventoryItems) {
        if(UIwriter.inventoryItems == null)
            UIwriter.inventoryItems = inventoryItems;
    }

    public static void setHP(Text HP, ProgressBar HPBar) {
        UIwriter.HP = HP;
        UIwriter.HPBar = HPBar;
    }

    public static void itemAdded(Item item) {
        Image image;
        if (item.getClass() == Equipment.class) {
            image = Statics.swordImage();
        } else {
            image = Statics.potionImage();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        Button button = new Button("", imageView);
        button.setFocusTraversable(false);
        Tooltip tooltip = new Tooltip(item.getName() + '\n' + item.getDescription());
        button.setTooltip(tooltip);
        button.setOnMouseClicked((event) -> {
            getPlayer().useItem(item);
            if(item.getClass() != Equipment.class)
                inventoryItems.getChildren().remove(button);
        });
        int itemsCount = getPlayer().itemsCount() - 1;
        inventoryItems.add(button, itemsCount / 5, itemsCount % 5);
        consoleWrite("Collected " + item);
    }

    public static void HPChanged() {
        Stat[] hps = getPlayer().getHP();
        HP.setText("HP: " + Integer.toString(hps[0].getValue()));
        HPBar.setProgress((double) hps[0].getValue() / (double) hps[1].getValue());
        statsChanged();
    }

    public static void statsChanged() {
        StringBuilder sb = new StringBuilder();
        for (StatTypes type :
                StatTypes.values()) {
            sb.append(type).append(": ").append(GameController.getPlayer().getStats().get(type).getValue()).append("\n");
        }
        statBox.setText(sb.toString());
    }
}
