package Level;

import Engine.GameObject;
import javafx.scene.paint.Color;

public class Pavement extends Cell {

	private GameObject Content;

    public Pavement(){
        this(0, 0);
    }

    public Pavement(int x, int y){
        this(x, y, null);
    }

    public Pavement(int x, int y, GameObject content) {
        super(x, y, Color.DARKORANGE);
        this.walkable = true;
        Content = content;
    }
}