package Characters;

import Abstract.Enums.StatTypes;
import Abstract.Enums.Visuals;
import Abstract.Interfaces.IContainer;
import Abstract.Vector;
import Engine.GameController;
import Engine.Stat;
import Items.Equipment;
import Items.Item;
import Level.Map;
import Level.Pavement;
import javafx.scene.paint.Color;
import Level.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static Engine.GameController.getCurrentMap;
import static Engine.GameController.getPlayer;


public class Enemy extends Fighting implements IContainer {

	private boolean asleep;
    private boolean visible;
    private boolean chasing;
    private Map myMap;
    private static int i;
    private String name;

    public Enemy(HashMap<StatTypes, Stat> stats, Map map){
        super(stats);
        myMap = map;
        asleep = true;
        setVisual(Color.DARKGREEN.interpolate(Color.CRIMSON, (double)stats.get(StatTypes.Attack).getValue()/(double)10), Visuals.Triangle);
        name = "Enemy "+i++;
    }

	public void Flee() {
        // TODO - implement Enemy.Flee
        throw new UnsupportedOperationException();
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
    }

	@Override
	public void Move(String dir) {
		throw new UnsupportedOperationException();
	}

	public void Move(Pavement target){
        ((Pavement) myMap.getCellAt(position)).removePedestrian();
        setPosition(target.getPosition());
        target.addPedestrian(this);
    }

	@Override
	public void Loot() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void RandomizeContent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean interact() {
		throw new UnsupportedOperationException();
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