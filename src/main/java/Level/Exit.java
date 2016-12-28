package Level;

import Abstract.Enums.Visuals;
import Abstract.Vector;
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
        position = new Vector(x, y);
        walkable = true;
    }
    public void setElevate(int e){
        elevate = e;
        Color color = e > 0? Color.GREENYELLOW : Color.DARKGREEN;
        setVisual(color, Visuals.Square);
        mainColor = color;
    }
}
