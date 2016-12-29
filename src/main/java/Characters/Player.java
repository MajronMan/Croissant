package Characters;

import Abstract.Enums.PlayerBody;
import Abstract.Enums.Visuals;
import Abstract.Vector;
import Abstract.VisualRepresentation;
import Engine.GameController;
import Interface.UIwriter;
import Items.Equipment;
import Items.Item;
import Level.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

import static Engine.Constants.cellSize;

public class Player extends Fighting{

	private String proffesion;
	private ArrayList<Item> backpack = new ArrayList<>();
	private HashMap<PlayerBody, Equipment> eq = new HashMap<>();
	private String race;
    private Vector facing = new Vector(0, -1);

    public Player(){
        findFirstWalkable();
        for (PlayerBody part :
                PlayerBody.values()) {
            eq.put(part, null);
        }
        visual = new VisualRepresentation(Color.DARKSLATEBLUE, Visuals.ArrowU);
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
        int posX = position.getX(), posY = position.getY();
        Map theMap = GameController.getCurrentMap();
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
        }
        theMap.getCellAt(destx, desty).Interact();
        draw();
    }

    public void Equip(Equipment equipment){
        Equipment worn = eq.get(equipment.getMyPart());
        if(worn != null)
            backpack.add(worn);
        eq.put(equipment.getMyPart(), equipment);
    }

    public Vector getFacing() {
        return facing;
    }

    public void Interact(){
        //some popup on click
    }

    public void CollectItem(Item item) {
        backpack.add(item);
        UIwriter.itemAdded(item);
    }

    public void raytrace(){
        GameController.getCurrentMap().getCellAt(position.getX(), position.getY()).getVisibility().raytrace(facing);
    }

    public int itemsCount() {
        return backpack.size();
    }
/*
    public void raytrace(){
        for (double alfa = 0; alfa < 361; alfa+=45) {
            GameController.getCurrentMap().getCellAt(
                    position.getX(), position.getY()
            ).getVisibility().propagateWave(new Vector(1.0, alfa));
        }
    }*/
}