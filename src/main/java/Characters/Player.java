package Characters;

import Abstract.Enums.PlayerBody;
import Abstract.Enums.StatTypes;
import Abstract.Enums.Visuals;
import Abstract.Vector;
import Abstract.VisualRepresentation;
import Engine.GameController;
import Engine.GameObject;
import Engine.Stat;
import Interface.UIwriter;
import Items.Equipment;
import Items.Item;
import Level.Map;
import javafx.beans.property.ObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Fighting{

	private String proffesion;
	private ArrayList<Item> backpack = new ArrayList<>();
	private HashMap<PlayerBody, Equipment> eq = new HashMap<>();
	private String race;
    private Vector facing = new Vector(0, -1);
    public Stat MaxHP;
    private ObjectProperty<EventHandler<Event>> onDeath;

    public Player(){
        super(Stat.playerStats);
        MaxHP = new Stat(StatTypes.HP, 100, 0);
        findFirstWalkable();
        for (PlayerBody part :
                PlayerBody.values()) {
            eq.put(part, null);
        }
        visual = new VisualRepresentation(Color.DARKSLATEBLUE, Visuals.ArrowU);
    }

    public Stat[] getHP(){
        return new Stat[] {stats.get(StatTypes.HP), MaxHP};
    }

    private void findFirstWalkable(){
        int posX = 0, posY = 0;
        Map theMap = GameController.getCurrentMap();
        while(!theMap.getCellAt(posX, posY).getWalkable()){
            posX++;
            if(posX >= theMap.x) {
                posX = 0;
                posY = (posY + 1) % theMap.y;
            }
        }
        position = new Vector(posX, posY);
    }

	public void Move(String dir){
        if(!alive) return;
        int posX = position.getX(), posY = position.getY();
        Map theMap = GameController.getCurrentMap();
        theMap.getCellAt(posX, posY).setWalkable(true);
        theMap.getCellAt(posX, posY).draw();
        int destx = posX, desty = posY;
        switch(dir) {
            case "LEFT":
                destx = Math.max(0, posX - 1);
                visual.setShape(Visuals.ArrowL);
                facing = new Vector(-1, 0);
                break;
            case "RIGHT":
                destx = Math.min(posX + 1, theMap.x - 1);
                visual.setShape(Visuals.ArrowR);
                facing = new Vector(1, 0);
                break;
            case "UP":
                desty = Math.max(0, posY - 1);
                visual.setShape(Visuals.ArrowU);
                facing = new Vector(0, -1);
                break;
            case "DOWN":
                desty = Math.min(posY + 1, theMap.y - 1);
                visual.setShape(Visuals.ArrowD);
                facing = new Vector(0, 1);
                break;
        }
        if(theMap.getCellAt(destx, desty).getWalkable()) {
            position = new Vector(destx, desty);
            theMap.getCellAt(destx, desty).setWalkable(false);
        }
        draw();
    }

    public void Equip(Equipment equipment){
        Equipment worn = eq.get(equipment.getMyPart());
        if(worn != null) {
            backpack.add(worn);
            Stat mod = worn.getModifier();
            stats.get(mod.getMyType()).increase(-mod.getValue());
        }
        eq.put(equipment.getMyPart(), equipment);
        Stat mod = equipment.getModifier();
        stats.get(mod.getMyType()).increase(mod.getValue());
    }

    public Vector getFacing() {
        return facing;
    }

    public boolean interact(){
        //some popup on click
        return true;
    }

    public boolean CollectItem(Item item) {
        if(backpack.size() > 29) {
            UIwriter.consoleWrite("Inventory is full");
            return false;
        }
        backpack.add(item);
        UIwriter.itemAdded(item);
        return true;
    }

    public void raytrace(){
        GameController.getCurrentMap().getCellAt(position.getX(), position.getY()).getVisibility().raytrace(facing);
    }

    public int itemsCount() {
        return backpack.size();
    }

    @Override
    public void takeHit(int howmuch, GameObject who){
        super.takeHit(howmuch, who);
        stats.get(StatTypes.HP).setValue(Math.min(stats.get(StatTypes.HP).getValue(), MaxHP.getValue()));
        UIwriter.HPChanged();
    }

    public String toString() {
        return "you";
    }

    public void useItem(Item item) {
        if(item.getClass() == Equipment.class){
            Equip((Equipment) item);
            UIwriter.consoleWrite("Equipped " + item + " " + item.getModifier().getValue() + " " + item.getModifier().getMyType());
        }
        else{
            takeHit(10, item);
            backpack.remove(item);
        }
        UIwriter.statsChanged();
    }
}