package level;

import characters.Fighting;
import engine.GameController;
import engine.GameObject;
import javafx.scene.paint.Color;

public class Pavement extends Cell {

	GameObject content;
    Fighting pedestrian;

    public Pavement(){
        this(0, 0);
    }

    public Pavement(int x, int y){
        this(x, y, null);
    }

    public Pavement(int x, int y, GameObject content) {
        super(x, y, Color.DARKORANGE);
        this.visibility = new CellVisibility(false, false, this);
        this.walkable = true;
        this.content = content;
    }

    public void removeContent(){
        content = null;
        draw();
    }

    public void addContent(GameObject content) {
        this.content = content;
        content.setPosition(this.position);
    }

    @Override
    public void draw(){
        super.draw();
        if(content != null)
            content.draw(Color.BLACK.interpolate(content.getVisual().getColor(), visibility.intensity));
        if(pedestrian != null)
            pedestrian.draw(Color.BLACK.interpolate(pedestrian.getVisual().getColor(), visibility.intensity));
    }
    @Override
    public boolean interact(){
        if(pedestrian != null){
            return pedestrian.interact();
        }
        else if(content!= null) {
            content.interact();
            return true;
        }
        return false;
    }
    public void addPedestrian(Fighting pedestrian) {
        this.pedestrian = pedestrian;
        walkable = false;
    }

    public void removePedestrian(){
        pedestrian = null;
        walkable = true;
    }
}