package Items;

import Abstract.Enums.StatTypes;
import Abstract.Enums.Visuals;
import Engine.GameController;
import Engine.GameObject;
import Engine.Stat;
import Level.CellVisibility;
import Level.Pavement;
import javafx.scene.paint.Color;

public class Item extends GameObject {
    protected String name;
	protected Stat modifier;
	protected String description;
    public Pavement whereILay;
    private static int i = 0;
	//private String rarity;

    @Override
    public boolean interact() {
        if(GameController.getPlayer().CollectItem(this)) {
            whereILay.removeContent();
            whereILay = null;
            return true;
        }
        else return false;
    }
    public Item(){
        whereILay = null;
    }

    public Item(Pavement cell){
        name = "Potion " + Integer.toString(++i);
        whereILay = cell;
        setVisual(Color.AQUAMARINE, Visuals.Diamond);
        description = "Healing potion";
        modifier = new Stat(StatTypes.HP, 10, 0);
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

    public Stat getModifier() {
        return modifier;
    }

    @Override
    public String toString() {
        return name;
    }
}