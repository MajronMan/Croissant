package Items;

import Abstract.Enums.Visuals;
import Engine.GameController;
import Engine.GameObject;
import Level.CellVisibility;
import Level.Pavement;
import javafx.scene.paint.Color;

public class Item extends GameObject {
    private String name;
	private Stat Stats;
	private String description;
    public Pavement whereILay;
    private static int i = 0;
	//private String rarity;

    @Override
    public void Interact() {
        GameController.getPlayer().CollectItem(this);
        whereILay.removeContent();
        whereILay = null;
    }
    public Item(){
        whereILay = null;
    }

    public Item(Pavement cell){
        name = Integer.toString(i++);
        whereILay = cell;
        setVisual(Color.AQUAMARINE, Visuals.Diamond);
        description = "Item no. " + Integer.toString(i);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CellVisibility getVisibility(){
        if(whereILay != null)
            return whereILay.getVisibility();
        return null;
    }

    @Override
    public void draw() {
        CellVisibility visibility = getVisibility();
        if(visibility != null && visibility.isVisible())
            super.draw();
        else
            super.hide();
    }
}