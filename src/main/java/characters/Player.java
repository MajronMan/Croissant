package characters;

import immaterial.Enums.PlayerBody;
import immaterial.Enums.StatTypes;
import immaterial.Enums.Visuals;
import immaterial.Vector;
import immaterial.VisualRepresentation;
import engine.GameController;
import engine.GameObject;
import engine.Stat;
import gui.UIwriter;
import items.Equipment;
import items.Item;
import level.Cell;
import level.Map;
import javafx.beans.property.ObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import level.Pavement;

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

	public void handleInput(String dir){
        if(!alive) return;
        Map theMap = GameController.getCurrentMap();
        Cell currentCell = theMap.getCellAt(position);
        currentCell.draw();
        int posX = position.getX(), posY = position.getY();
        int destX = posX, destY = posY;
        switch(dir) {
            case "LEFT":
                destX = Math.max(0, posX - 1);
                visual.setShape(Visuals.ArrowL);
                facing = new Vector(-1, 0);
                break;
            case "RIGHT":
                destX = Math.min(posX + 1, theMap.x - 1);
                visual.setShape(Visuals.ArrowR);
                facing = new Vector(1, 0);
                break;
            case "UP":
                destY = Math.max(0, posY - 1);
                visual.setShape(Visuals.ArrowU);
                facing = new Vector(0, -1);
                break;
            case "DOWN":
                destY = Math.min(posY + 1, theMap.y - 1);
                visual.setShape(Visuals.ArrowD);
                facing = new Vector(0, 1);
                break;
        }
        Cell target = theMap.getCellAt(destX, destY);
        if(!target.getWalkable())
            return;

        currentCell.setWalkable(true);
        Move((Pavement) target);
    }

    public void Move(Pavement target){
        target.setWalkable(false);
        position = target.getPosition();
        draw();
    }

    private void Equip(Equipment equipment){
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