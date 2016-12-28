package Items;

import Abstract.Enums.StatTypes;

public class Stat {

	private StatTypes myType;
	private int value;
    private double modifier;

    public StatTypes getMyType() {
        return myType;
    }

    public void setMyType(StatTypes myType) {
        this.myType = myType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getModifier() {
        return modifier;
    }

    public void setModifier(double modifier) {
        this.modifier = modifier;
    }

	public void Add() {
		// TODO - implement Stat.Add
		throw new UnsupportedOperationException();
	}

	public void Sub() {
		// TODO - implement Stat.Sub
		throw new UnsupportedOperationException();
	}

}