package Level;

import Abstract.Enums.Visuals;
import Abstract.Interfaces.IDrawable;
import Abstract.Vector;
import Abstract.VisualRepresentation;
import Engine.GameObject;
import javafx.scene.paint.Color;

public class Cell extends GameObject implements IDrawable{
	protected boolean walkable = false;
    protected CellVisibility visibility;
    protected Color mainColor;
    protected Map map;
    private double intensity;

    public Cell(){this(0, 0);}

    public Cell(int x, int y) {this(x, y, Color.BLACK);}

    public Cell(int x, int y, Color color){
        position = new Vector(x, y);
        visual = new VisualRepresentation(color, Visuals.Square, Color.BLACK);
        visibility = new CellVisibility(false, true, this);
        mainColor = color;
    }


    public void setXY(int x, int y){
        position = new Vector(x, y);
    }

    public boolean getWalkable(){
        return walkable;
    }

    public boolean interact(){
        return false;
    }

    public Map getMap(){return map;}

    public void draw(){
        super.draw(Color.BLACK.interpolate(mainColor, visibility.intensity));
    }


    public void setMap(Map map){
        this.map = map;
    }

    public CellVisibility getVisibility(){
        return visibility;
    }

    public void setVisible(boolean visible){
        visibility.visible = visible;
    }
    public void setIntensity(double intensity){
        visibility.intensity = intensity;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "walkable=" + walkable +
                ", visibility=" + visibility.visible +
                ", position=" + position +
                '}';
    }

    public double getIntensity() {
        return visibility.intensity;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }
}