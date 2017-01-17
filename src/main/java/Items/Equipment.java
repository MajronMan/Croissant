package Items;

import Abstract.Enums.PlayerBody;
import Abstract.Enums.StatTypes;
import Abstract.Enums.Visuals;
import Engine.Stat;
import Level.Pavement;
import javafx.scene.paint.Color;

import java.util.Random;

public class Equipment extends Item {

	protected Stat requirements;
	protected double Durability;
    protected PlayerBody myPart;
    private static int i;

    public Equipment() {
    }

    public PlayerBody getMyPart() {
        return myPart;
    }

    public Equipment(Pavement whereILay){
        this.whereILay = whereILay;
        name = "Sword no. " + Integer.toString(++i);
        setVisual(Color.BLACK, Visuals.Diamond);
        Random r = new Random();
        modifier = new Stat(StatTypes.values()[2+r.nextInt(StatTypes.values().length-2)], 2 + r.nextInt(10), 0);
        description = "Mighty sword +"+ modifier.getValue() + " " + modifier.getMyType();
    }
}