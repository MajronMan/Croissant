package gui;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by MajronMan on 09.12.2016.
 */
public class Popup {
    private AnchorPane parent;

    private SplitPane me;
    private ArrayList<Text> texts = new ArrayList<>();
    private Text title;
    Popup(AnchorPane parent, ArrayList<String> text, String title){
        me = new SplitPane();
        this.parent = parent;
        parent.getChildren().add(me);
        for(String t: text){
            Text newText = new Text(t);
            texts.add (newText);

            //me.getChildren().add(newText);

        }
    }
    public AnchorPane getParent() {
        return parent;
    }

    public void setParent(AnchorPane parent) {
        this.parent = parent;
    }

    public SplitPane getMe() {
        return me;
    }
}
