package Croissant.Level;

import javafx.scene.paint.Color;

/**
 * Created by MajronMan on 26.11.2016.
 */
public class Exit extends Cell {
    int elevate;

    public Exit(){
        this(0, 0);
    }
    public Exit(int x, int y){
        this.x = x;
        this.y = y;
        walkable = true;
    }
    public void setElevate(int e){
        elevate = e;
        color = e > 0? Color.GREENYELLOW : Color.DARKGREEN;
    }
}
