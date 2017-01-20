package characters;

import immaterial.Enums.StatTypes;
import immaterial.Enums.Visuals;
import immaterial.Vector;
import engine.GameController;
import engine.Stat;
import items.Equipment;
import items.Item;
import level.Map;
import level.Pavement;
import javafx.scene.paint.Color;
import level.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static engine.GameController.getCurrentMap;
import static engine.GameController.getPlayer;


public class Enemy extends Fighting{

	private boolean asleep;
    private boolean visible;
    private boolean chasing;
    private Map myMap;
    private static int i;
    private String name;

    private Enemy(HashMap<StatTypes, Stat> stats, Map map){
        super(stats);
        myMap = map;
        asleep = true;
        setVisual(Color.DARKGREEN.interpolate(Color.CRIMSON, (double)stats.get(StatTypes.Attack).getValue()/(double)10), Visuals.Triangle);
        name = "Enemy "+i++;
    }

	private void chase(){
        if(getCurrentMap().depth != myMap.depth){
            chasing = false;
            return;
        }
        ArrayList<Cell> path = myMap.getPath(position, getPlayer().getPosition());
        if(path == null){
            chasing = false;
            return;
        }
        if(path.size() == 0){
            Attack(GameController.getPlayer());
        }
        else{
            Move((Pavement)path.get(0));
        }
    }

	public void roam() {
        if(!alive) return;
        visible = myMap.getCellAt(position).getVisibility().isVisible();
        if(visible && asleep) {
            asleep = false;
            return;
        }
        if(asleep) return;
        if(chasing){
            chase();
            return;
        }
        if(visible) {
            chasing = true;
            chase();
            return;
        }
        Vector direction = Vector.randomDirection();
        Cell target = myMap.getCellAt(position.sum(direction));
        for (int i = 0; !target.getWalkable() && i < 9; i++) {
            direction = direction.rotate(45);
            target = myMap.getCellAt(position.sum(direction));
        }
        if (target.getClass() == Pavement.class) {
            Move((Pavement)target);
        }
    }
    public void die(){
        super.die();
        Pavement myCell = (Pavement) myMap.getCellAt(position);
        Item drop;
        Random r = new Random();
        if(r.nextBoolean())
            drop = new Item(myCell);
        else
            drop = new Equipment(myCell);
        myCell.addContent(drop);
        myCell.removePedestrian();
        GameController.removeEnemy(this);
    }

    @Override
    public void Move(Pavement target){
        ((Pavement) myMap.getCellAt(position)).removePedestrian();
        setPosition(target.getPosition());
        target.addPedestrian(this);
    }

	@Override
	public boolean interact() {
        GameController.getPlayer().Attack(this);
        return true;
	}

	public static Enemy newRat(Map map){
        HashMap<StatTypes, Stat> stats = new HashMap<StatTypes, Stat>(){{
            for(StatTypes type: StatTypes.values()){
                put(type, new Stat(type, 2, 0));
            }
        }};
		return new Enemy(stats, map);
	}

	public static Enemy newSquid(Map map){
        HashMap<StatTypes, Stat> stats = new HashMap<StatTypes, Stat>(){{
            for(StatTypes type: StatTypes.values()){
                put(type, new Stat(type, 8, 0));
            }
        }};
        return new Enemy(stats, map);
	}

	public static Enemy newRandomStats(Map map){
        Random r = new Random();
        HashMap<StatTypes, Stat> stats = new HashMap<StatTypes, Stat>(){{
            for(StatTypes type: StatTypes.values()){
                put(type, new Stat(type, r.nextInt(10), 0));
            }
        }};
        return new Enemy(stats, map);
    }

    @Override
    public String toString() {
        return name;
    }
}