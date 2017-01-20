package characters;

import immaterial.Enums.StatTypes;
import immaterial.Interfaces.IMoving;
import engine.GameObject;
import engine.Stat;
import gui.UIwriter;

import java.util.HashMap;

public abstract class Fighting extends GameObject implements IMoving {

	protected HashMap<StatTypes, Stat> stats = new HashMap<>();
    protected boolean alive=true;

	public Fighting(){
        for(StatTypes type: StatTypes.values()){
            stats.put(type, new Stat(type, 0, 0));
        }
    }

    public Fighting(HashMap<StatTypes, Stat> stats){
        this.stats = new HashMap<>(stats);
    }

	public void Attack(Fighting target) {
        int diff = Math.min(0, target.stats.get(StatTypes.Defense).getValue() - stats.get(StatTypes.Attack).getValue());
        target.takeHit(diff, this);
	}

    public HashMap<StatTypes, Stat> getStats() {
        return stats;
    }

    public void die(){
        alive = false;
        UIwriter.consoleWrite(this +" died.");
    }

    public void takeHit(int howmuch, GameObject who){
        stats.get(StatTypes.HP).increase(howmuch);
        UIwriter.consoleWrite(who + " changed HP of " + this + " by " + howmuch);
        if(stats.get(StatTypes.HP).getValue() <= 0)
            die();
    }

    public boolean isAlive() {
        return alive;
    }
}