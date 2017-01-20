package level;

import immaterial.Enums.Visuals;
import immaterial.Interfaces.IDrawable;
import immaterial.Vector;
import immaterial.VisualRepresentation;
import engine.GameObject;
import javafx.scene.paint.Color;

public class Cell extends GameObject implements IDrawable{
	boolean walkable = false;
    CellVisibility visibility;
    Color mainColor;
    protected Map map;

    Cell(int x, int y) {this(x, y, Color.BLACK);}

    Cell(int x, int y, Color color){
        position = new Vector(x, y);
        visual = new VisualRepresentation(color, Visuals.Square, Color.BLACK);
        visibility = new CellVisibility(false, true, this);
        mainColor = color;
    }


    void setXY(int x, int y){
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

    double getIntensity(){
        return visibility.intensity;
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

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }
}