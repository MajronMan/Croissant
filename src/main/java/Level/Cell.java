package Level;

import Engine.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static Engine.Constants.cellSize;

public class Cell extends GameObject {
	protected boolean walkable = false;
    public int x;
    public int y;
    public Color color;

    public Cell(){this(0, 0);}

    public Cell(int x, int y) {this(x, y, Color.BLACK);}

    public Cell(int x, int y, Color color){
        this.y = y;
        this.x = x;
        this.color = color;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean getWalkable(){
        return walkable;
    }

    public void draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(color);
        graphicsContext.strokeRect(x*cellSize, y*cellSize, cellSize, cellSize);
        graphicsContext.fillRect(x*cellSize, y*cellSize, cellSize-1, cellSize-1);
    }

}