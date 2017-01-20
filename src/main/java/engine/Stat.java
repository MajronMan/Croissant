package engine;

import immaterial.enums.StatTypes;

import java.util.HashMap;

public class Stat {

	private StatTypes myType;
	private int value;
    private double modifier;

    public Stat(StatTypes myType, int value, double modifier) {
        this.myType = myType;
        this.value = value;
        this.modifier = modifier;
    }

    public StatTypes getMyType() {
        return myType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

	public Stat add(Stat other) {
        if(myType != other.myType)
            throw new UnsupportedOperationException(new Throwable("Types of "+ this + " and " + other + " don't match"));
        return new Stat(myType, value+other.value, modifier+other.modifier);
	}

	public void increase(int value){
        this.value += value;
    }

    @Override
    public String toString() {
        return myType+" "+ value + " + " + modifier +"%";
    }

    public static HashMap<StatTypes, Stat> playerStats = new HashMap<StatTypes, Stat>(){{
        for(StatTypes type: StatTypes.values()){
            if(type == StatTypes.Defense)
                put(type, new Stat(type, 1, 0));
            else if(type == StatTypes.HP){
                put(type, new Stat(type, 100, 0));
            }
            else
                put(type, new Stat(type, 10, 0));
        }
    }};

}