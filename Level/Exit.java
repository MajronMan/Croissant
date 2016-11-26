package Croissant.Level;

import javafx.scene.paint.Color;

/**
 * Created by MajronMan on 26.11.2016.
 */
public class Exit extends Cell {
    int elevate;

    public Exit(int x, int y){
        this.x = x;
        this.y = y;
        color = Color.GREENYELLOW;
    }
}
