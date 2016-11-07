package Croissant.Level;

import Croissant.Engine.GameObject;
import javafx.scene.paint.Color;

public class Cell extends GameObject {
	private boolean walkable;
    public boolean visited = false;
    public int x;
    public int y;
    public Color color;

    public Cell(){this(0, 0);}

    public Cell(int y, int x) {this(y, x, Color.FIREBRICK);}

    public Cell(int y, int x, Color color){
        this.y = y;
        this.x = x;
        this.color = color;
    }

    public void setWalkable(boolean to){
        walkable = to;
        color = to? Color.FIREBRICK : Color.BLACK;
    }

    public boolean getWalkable(){
        return walkable;
    }

}