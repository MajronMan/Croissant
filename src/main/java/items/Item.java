package items;

import immaterial.enums.StatTypes;
import immaterial.enums.Visuals;
import engine.GameController;
import engine.GameObject;
import engine.Stat;
import level.Pavement;
import javafx.scene.paint.Color;

public class Item extends GameObject {
    String name;
	Stat modifier;
	String description;
    Pavement whereILay;
    private static int i = 0;
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

    public Stat getModifier() {
        return modifier;
    }

    @Override
    public String toString() {
        return name;
    }
}