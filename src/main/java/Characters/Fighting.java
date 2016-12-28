package Characters;

import Abstract.Enums.StatTypes;
import Abstract.Interfaces.IMoving;
import Engine.GameObject;
import Items.Stat;

import java.util.HashMap;

public abstract class Fighting extends GameObject implements IMoving {

	protected HashMap<StatTypes, Stat> stats = new HashMap<>();
    protected boolean alive;

	public void Attack() {
		// TODO - implement Fighting.Attack
		throw new UnsupportedOperationException();
	}



}