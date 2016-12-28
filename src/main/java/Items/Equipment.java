package Items;

import Abstract.Enums.PlayerBody;

public class Equipment extends Item {

	protected Stat requirements;
	protected double Durability;
    protected PlayerBody myPart;

    public PlayerBody getMyPart() {
        return myPart;
    }
}